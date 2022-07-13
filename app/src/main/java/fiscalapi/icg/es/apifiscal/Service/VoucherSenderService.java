package fiscalapi.icg.es.apifiscal.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.ApiFiscalApp;
import fiscalapi.icg.es.apifiscal.Dagger2.Components.DaggerServiceComponent;
import fiscalapi.icg.es.apifiscal.Dagger2.Components.ServiceComponent;
import fiscalapi.icg.es.apifiscal.Dagger2.Modules.ServiceModule;
import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.EmisorReceptor;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.FacturaElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Identificacion;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.NotaCreditoElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.NotaDebitoElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Telefono;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.TiqueteHacienda;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Ubicacion;
import fiscalapi.icg.es.apifiscal.Data.MDG.MDGCloudService;
import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ApiEmisionRequest;
import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ApiEmisionResponse;
import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ComprobanteElectronico;
import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ValidateCredentialsResponse;
import fiscalapi.icg.es.apifiscal.Data.Models.Comprobante;
import fiscalapi.icg.es.apifiscal.Data.Models.ComprobanteErroneo;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherSenderService extends IntentService {

    private boolean active;
    private ArrayList<Comprobante> pendingVouchers;
    private Comprobante voucher;
    private ServiceComponent serviceComponent;
    private Configuracion configuration;

    private double NANO_MULTIPLIER = 1000000000d;

    private int maxTriesStopServiceForNoVouchers = Constants.MAX_TRIES_BEFORE_END_SERVICE_FOR_NO_VOUCHERS;
    private int maxTriesStopServiceForFailResponses = Constants.MAX_TRIES_BEFORE_END_SERVICE_FOR_FAIL_RESPONSES;

    private String access_token = null;
    double elapsedTimeSinceLastToken = 270;
    boolean waitingForToken = false;
    StringBuilder sb;
    ArrayList<String> errores;

    @Inject
    protected DatabaseManagerI databaseManager;

    @Inject
    protected MDGCloudService mdgCloudService;

    public VoucherSenderService() {
        super("VoucherSender");
        active = false;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .applicationComponent(((ApiFiscalApp) getApplication()).getComponent())
                .build();

        serviceComponent.inject(this);
    }

    @Override
    public void onHandleIntent(Intent intent) {
        if(active == false) {
            //Log.d("SERVICE"," intent start a service");
            active = true;
            configuration = databaseManager.getConfiguration();
            elapsedTimeSinceLastToken = 270;
            sb = new StringBuilder();
            startService();
        }
    }

    private void startService(){
        //Log.d("SERVICE ","service started");
        active = true;
        double maxTimeToLookForVouchers = 20d; // time in seconds
        double minTimeBetweenCalls = 1d; // time in seconds

        long lastTime = System.nanoTime();
        long currentTime;

        double deltaTime;
        double elapsedTimeSinceLastSearch = maxTimeToLookForVouchers;
        double elapsedTimeSinceCall = minTimeBetweenCalls;

        voucher = null;

        if(configuration != null){
            while(active){
                currentTime = System.nanoTime();
                deltaTime = (currentTime - lastTime) / NANO_MULTIPLIER;
                lastTime = currentTime;
                elapsedTimeSinceLastSearch+=deltaTime;
                elapsedTimeSinceCall += deltaTime;
                elapsedTimeSinceLastToken += deltaTime;

                if(elapsedTimeSinceLastSearch > maxTimeToLookForVouchers){
                    elapsedTimeSinceLastSearch = 0;
                    pendingVouchers = (ArrayList<Comprobante>)databaseManager.getPendingVouchers(configuration.isProduccion_activo());
                    if(pendingVouchers == null || pendingVouchers.size() == 0){
                        maxTriesStopServiceForNoVouchers--;
                    }else{
                        maxTriesStopServiceForNoVouchers = Constants.MAX_TRIES_BEFORE_END_SERVICE_FOR_NO_VOUCHERS;
                    }
                }

                if(maxTriesStopServiceForNoVouchers <= 0 || maxTriesStopServiceForFailResponses <= 0){
                    active = false;
                    break;
                }

                if(pendingVouchers != null && pendingVouchers.size() > 0 && voucher == null && elapsedTimeSinceCall > minTimeBetweenCalls) {
                    elapsedTimeSinceCall = 0;

                    if(elapsedTimeSinceLastToken >= 270d && waitingForToken == false) {
                        waitingForToken = true;
                        access_token = null;
                        RequestToken();
                    }

                    if(access_token != null && pendingVouchers.size() > 0) {
                        voucher = pendingVouchers.get(0);
                        if(voucher == null){
                            continue;
                        }

                        int environment = voucher.getAmbiente() ? 2 : 1;

                        String comprobanteXML = voucher.getComprobante();
                        ComprobanteElectronico comprobante = deserializeVoucher(comprobanteXML);

                        if(comprobante == null){
                            proccessVoucher(voucher);
                            voucher = null;
                            continue;
                        }

                        comprobante = addEmisorInformation(comprobante);
                        String xml = serializeVoucher(comprobante);

                        ApiEmisionRequest emisionRequest = new ApiEmisionRequest();
                        emisionRequest.Xml(xml);
                        emisionRequest.FormatoPDF(2);
                        emisionRequest.Ambiente(environment);
                        emisionRequest.setAdjuntos(null);
                        emisionRequest.EjectucionId("1");
                        //Log.d("SERVICE_LOG",xml);
                        voucher.setEstado(Constants.SENDED_STATE);
                        databaseManager.updateVoucher(voucher);
                        Call<ApiEmisionResponse> call = mdgCloudService.emitVoucher(emisionRequest, String.format("%s %s",Constants.BEARER,access_token));
                        call.enqueue(new Callback<ApiEmisionResponse>() {
                            @Override
                            public void onResponse(Call<ApiEmisionResponse> call, Response<ApiEmisionResponse> response) {
                                if (response.isSuccessful()) {
                                    maxTriesStopServiceForFailResponses = Constants.MAX_TRIES_BEFORE_END_SERVICE_FOR_FAIL_RESPONSES;
                                    ApiEmisionResponse body = response.body();
                                    //Log.d("SERVICE_LOG","voucher was send it to mdg cloud "+body.getMensaje()+" exitoso "+body.getExitoso()+" errores  "+body.getErrores());
                                    if(body.getExitoso()) {
                                        proccessVoucher(voucher);
                                    }else{
                                        proccessVoucher(voucher);
                                        ComprobanteErroneo error = new ComprobanteErroneo();
                                        errores = body.getErrores();
                                        for(int i = 0; i < errores.size(); ++i){
                                            sb.append(errores.get(i)+"\n");
                                        }
                                        error.setAmbiente(voucher.getAmbiente());
                                        error.setHiopos_id(voucher.getHiopos_id());
                                        error.setId_comprobante(voucher.getId());
                                        error.setError(sb.toString());
                                        databaseManager.saveComprobanteErroneo(error);
                                        sb.delete(0,sb.length());
                                    }
                                } else {
                                    //Log.d("SERVICE_LOG", "response code on  processing voucher " + response.code() + " respose message " + response.message());
                                    maxTriesStopServiceForFailResponses--;
                                }
                                voucher = null;
                            }

                            @Override
                            public void onFailure(Call<ApiEmisionResponse> call, Throwable t) {
                                voucher = null;
                                maxTriesStopServiceForFailResponses--;
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        active = false;
        sb = null;
        errores = null;
        //Log.d("SERVICE","service destroyed");
    }

    public void proccessVoucher(Comprobante v){
        pendingVouchers.remove(0);
        databaseManager.deleteVoucher(v);
    }

    public void RequestToken(){
        Call<ValidateCredentialsResponse> tokenCall = mdgCloudService.validateCredentials(Constants.GRANT_TYPE,configuration.getUsuario_mdg(),configuration.getClave_mdg());
        tokenCall.enqueue(new Callback<ValidateCredentialsResponse>(){

            @Override
            public void onResponse(Call<ValidateCredentialsResponse> call, Response<ValidateCredentialsResponse> response) {
                if(response.isSuccessful()){
                    access_token = response.body().getAccess_token();
                    elapsedTimeSinceLastToken = 0;
                }else{
                    access_token = null;
                }
                waitingForToken = false;
            }

            @Override
            public void onFailure(Call<ValidateCredentialsResponse> call, Throwable t) {
                //Log.d("SERVICE_LOG","Unable to get token");
                access_token = null;
                waitingForToken = false;
            }
        });
    }

    public String serializeVoucher(ComprobanteElectronico comprobanteElectronico){
        try {
            Format format = new Format(0, "<?xml version=\"1.0\" encoding= \"UTF-8\" ?>");
            Serializer serializer = new Persister(format);

            Writer writer = new StringWriter();
            serializer.write(comprobanteElectronico, writer);

            return writer.toString().replace(",", ".");
        }catch (Exception e){
            return null;
        }
    }

    public ComprobanteElectronico deserializeVoucher(String comprobanteElectronico){
        try {
            Format format = new Format(0, "<?xml version=\"1.0\" encoding= \"UTF-8\" ?>");
            Serializer serializer = new Persister(format);
            return serializer.read(ComprobanteElectronico.class,comprobanteElectronico);
        }catch (Exception e){
            return null;
        }
    }

    public ComprobanteElectronico addEmisorInformation(ComprobanteElectronico comprobanteElectronico){
        EmisorReceptor emisor = getEmisorInformation();

        ArrayList<FacturaElectronica> facturas = comprobanteElectronico.getFacturasElectronicas();
        if(facturas != null) {
            for (int i = 0; i < facturas.size(); ++i) {
                facturas.get(i).setEmisor(emisor);
            }
            comprobanteElectronico.setFacturasElectronicas(facturas);
        }

        ArrayList<NotaCreditoElectronica> notasCredito = comprobanteElectronico.getNotasCredito();
        if(notasCredito != null) {
            for (int i = 0; i < notasCredito.size(); ++i) {
                notasCredito.get(i).setEmisor(emisor);
            }
            comprobanteElectronico.setNotasCredito(notasCredito);
        }

        ArrayList<NotaDebitoElectronica> notasDebito = comprobanteElectronico.getNotasDebito();
        if(notasDebito != null) {
            for (int i = 0; i < notasDebito.size(); ++i) {
                notasDebito.get(i).setEmisor(emisor);
            }
            comprobanteElectronico.setNotasDebito(notasDebito);
        }

        ArrayList<TiqueteHacienda> tiquetes = comprobanteElectronico.getTiquetesElectronicos();
        if(tiquetes != null) {
            for (int i = 0; i < tiquetes.size(); ++i) {
                tiquetes.get(i).setEmisor(emisor);
            }
            comprobanteElectronico.setTiquetesElectronicos(tiquetes);
        }

        return comprobanteElectronico;
    }

    public EmisorReceptor getEmisorInformation(){
        if(configuration == null){
            return null;
        }

        EmisorReceptor emisor = new EmisorReceptor();
        emisor.setNombre(configuration.getNombre());

        Identificacion emisorIdentificacion = new Identificacion();
        emisorIdentificacion.setTipo(configuration.getTipo_identificacion());
        emisorIdentificacion.setNumero(configuration.getIdentificacion());
        emisor.setIdentificacion(emisorIdentificacion);

        if (configuration.getNombre_comercial() != null) {
            emisor.setNombreComercial(configuration.getNombre_comercial());
        }

        Ubicacion emisorUbicacion = new Ubicacion();
        emisorUbicacion.setProvincia(configuration.getCodigo_provincia());
        emisorUbicacion.setCanton(configuration.getCodigo_canton());
        emisorUbicacion.setDistrito(configuration.getCodigo_distrito());

        if (configuration.getCodigo_barrio() != null) {
            emisorUbicacion.setBarrio(configuration.getCodigo_barrio());
        }

        emisorUbicacion.setOtrasSenas(configuration.getDireccion());

        emisor.setUbicacion(emisorUbicacion);

        if (configuration.getCodigo_pais_telefono() != null && configuration.getTelefono() != null) {
            Telefono emisorTelefono = new Telefono();
            emisorTelefono.setCodigoPais(configuration.getCodigo_pais_telefono());
            emisorTelefono.setNumTelefono(configuration.getTelefono());
            emisor.setTelefono(emisorTelefono);
        }

        emisor.setCorreoElectronico(configuration.getCorreo_electronico());

        if (configuration.getCodigo_pais_fax() != null && configuration.getFax() != null) {
            Telefono emisorFax = new Telefono();
            emisorFax.setCodigoPais(configuration.getCodigo_pais_fax());
            emisorFax.setNumTelefono(configuration.getFax());
            emisor.setFax(emisorFax);
        }

        return emisor;
    }
}
