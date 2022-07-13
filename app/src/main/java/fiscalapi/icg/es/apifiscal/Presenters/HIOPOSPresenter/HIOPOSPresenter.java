package fiscalapi.icg.es.apifiscal.Presenters.HIOPOSPresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.decimal4j.util.DoubleRounder;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import java.io.StringWriter;
import java.io.Writer;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import fiscalapi.icg.es.apifiscal.ApiFiscalApp;
import fiscalapi.icg.es.apifiscal.Data.DatabaseManagerI;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.CurrencyField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.DiscountField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.HioposSale;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.LineField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.LineTaxField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.ModifierField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.PaymentMeanField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document.ServiceChargeField;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.SaleReturn.AddicionalFieldReturn;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.SaleReturn.SaleReturn;
import fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.SaleReturn.SaleReturnField;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.CodigoComercial;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.CodigoTipoMoneda;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.ComprobanteHacienda;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Descuento;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.EmisorReceptor;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Exoneracion;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.FacturaElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Identificacion;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Impuesto;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.InformacionReferencia;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.LineaDetalle;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.MedioPago;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.NotaCreditoElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.NotaDebitoElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.ResumenFactura;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Telefono;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.TiqueteHacienda;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.Ubicacion;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.OtrosCargos;
import fiscalapi.icg.es.apifiscal.Data.MDG.Models.ComprobanteElectronico;
import fiscalapi.icg.es.apifiscal.Data.Models.Barrio;
import fiscalapi.icg.es.apifiscal.Data.Models.Canton;
import fiscalapi.icg.es.apifiscal.Data.Models.Comprobante;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Data.Models.Distrito;
import fiscalapi.icg.es.apifiscal.Data.Models.Provincia;
import fiscalapi.icg.es.apifiscal.Presenters.Presenter;
import fiscalapi.icg.es.apifiscal.R;
import fiscalapi.icg.es.apifiscal.Service.VoucherSenderService;
import fiscalapi.icg.es.apifiscal.Utils.Constants;
import fiscalapi.icg.es.apifiscal.Utils.HIOPOSInputFormatException;
import fiscalapi.icg.es.apifiscal.Utils.LogWriter;
import fiscalapi.icg.es.apifiscal.Utils.XMLHelper;
import fiscalapi.icg.es.apifiscal.Views.HIOPOSActivity.HIOPOSActivity;

public class HIOPOSPresenter <V extends HIOPOSActivity> extends Presenter<V> {

    private Configuracion configuracion;
    private static HIOPOSPresenter _instancia;

    private Intent intencionVenta;
    private Intent resultadoVenta;

    private DecimalFormat doubleFormat;
    private DecimalFormat intFormat;
    private DecimalFormat decimalFormat;
    private DecimalFormat unitsFormat;
    private NumberFormat nfCurrency;

    private final String claseFactura;
    private final String claseNotaCredito;
    private final String claseNotaDebito;
    private final String claseTiquete;

    boolean exento = false;
    boolean exonerado = false;


    boolean useBroadcast = false;
    boolean useExonerationDoc = false;
    String tipoExoneracion;
    String numeroDocumento;
    String nombreInstitucion;
    String fechaEmision; //Necesitamos
    String porcentajeImpuestosExoneracion;
    String codigoSeguridadFE; //Dato para codigo de seguridad de FE (FA)

    private double mercanciasGravadas = 0d;
    private double mercanciasExoneradas = 0d;
    private double mercanciasExcentas = 0d;

    private double serviciosGravados = 0d;
    private double serviciosExonerados = 0;
    private double serviciosExentos = 0;

    private double totalIVADevuelto = 0;
    private double totalOtrosCargos = 0;
    private double totalDescuentosGeneral = 0d;
    private double totalImpuestosGeneral = 0d;
    private double subtotalGeneral = 0d;

    private double porcentajeExoneradoPorLinea;
    private double totalMontoExoneradoPorLinea;
    private double totalMontoImpuestosPorLinea;


    @Inject
    public HIOPOSPresenter(DatabaseManagerI dataManager) {
        super(dataManager);
        _instancia = this;
        doubleFormat = new DecimalFormat("##0.00000");
        intFormat = new DecimalFormat("##0");
        decimalFormat = new DecimalFormat("##0.00");
        unitsFormat = new DecimalFormat("#.###");
        nfCurrency = NumberFormat.getInstance(Locale.ENGLISH);

        claseFactura = FacturaElectronica.class.getName();
        claseNotaCredito = NotaCreditoElectronica.class.getName();
        claseNotaDebito = NotaDebitoElectronica.class.getName();
        claseTiquete = TiqueteHacienda.class.getName();


    }

    public void manejarVersionIntent(){
        Intent resultado = new Intent(Constants.GET_VERSION);
        resultado.putExtra("Version", ApiFiscalApp.getVersionCode());
        view.setResult(Activity.RESULT_OK, resultado);
    }

    public void manejarComportamientoVersionIntent(){
        Log.d("ACTIVIDAD_HIOPOS","Manejar el comportamiento de la versión del Intent");
        Intent resultado = new Intent(Constants.GET_BEHAVIOR);
        HashMap<String, Boolean> mapa = new HashMap<String, Boolean>();
        mapa.put("canPrintSale", false);
        mapa.put("canPrintCopy", false);
        mapa.put("canOpenCashDrawer", false);
        mapa.put("canDoSubTotal", false);
        mapa.put("canPrintsSubTotal", false);
        mapa.put("canPrintXReport", false);
        mapa.put("canPrintZReport", false);
        mapa.put("canRegisterCashIn", false);
        mapa.put("canRegisterCashOut", false);
        mapa.put("canRegisterUserLogIn", false);
        mapa.put("canRegisterUserLogOut", false);

        String comportamientoXML = XMLHelper.getXMLFromBooleanMap("Behavior", "Values", "Value", mapa);
        resultado.putExtra("Behavior", comportamientoXML);
        view.setResult(Activity.RESULT_OK, resultado);
    }

    public void manejarVentaOIntentVacio(Intent i) {

        resultadoVenta = new Intent(i.getAction());
        configuracion = getDataManager().getConfiguration();
        if(configuracion.getLog())// Se añade Log para inicio de transacciones JVB 03-05-2022
            LogWriter.WriteLine("Inicio de transaccion ;"+i.getAction() );

        if (configuracion != null) {
            intencionVenta = i;
            Context contexto = view.getContext();
            Intent intencion = new Intent(contexto, VoucherSenderService.class);
            contexto.startService(intencion);
            procesarComprobante();
        } else {
            enviarResultado(resultadoVenta,Activity.RESULT_CANCELED,view.getContext().getString(R.string.configuracion_no_disponible));
        }
    }

    public void manejarPrintSale(Intent i){
        view.setResult(Activity.RESULT_OK,i);
    }

    public void devolverResultadoVenta(String hioposSerie, String hioposNumero,  int estadoComprobante, String numeroConsecutivo, String clave, String observaciones, String tipoDocumentoHda, Date fechaEmision) throws Exception {

        String piePagina;
        if(tipoDocumentoHda.equals(view.getString(R.string.invitation_document_type))){
            piePagina = view.getString(R.string.invitation_document_type);
        }else {
            piePagina = String.format(Locale.getDefault(), "%s: %s \n%s: %s\n%s\n%s", view.getString(R.string.consecutivo), numeroConsecutivo, view.getString(R.string.clave), clave,Constants.RESOLUTION,"Versión: 4.3");
        }

        Serializer serializador = new Persister(new Format(0));

        SaleReturn devolucionVenta = new SaleReturn();
        ArrayList<SaleReturnField> camposVenta = new ArrayList<SaleReturnField>();
        ArrayList<AddicionalFieldReturn> camposAdicionalesVenta = new ArrayList<AddicionalFieldReturn>();

        SaleReturnField campoSerie = new SaleReturnField();
        campoSerie.setKey("Serie");
        campoSerie.setValue(hioposSerie);


        camposVenta.add(campoSerie);

        SaleReturnField campoNumero = new SaleReturnField();
        campoNumero.setKey("Number");
        campoNumero.setValue(hioposNumero);

        camposVenta.add(campoNumero);

        SaleReturnField campoCodigoControl = new SaleReturnField();
        campoCodigoControl.setKey("ControlCode");
        campoCodigoControl.setValue(clave);

        camposVenta.add(campoCodigoControl);

        SaleReturnField bloqueImprimirCampo = new SaleReturnField();
        bloqueImprimirCampo.setKey("BlockToPrint");
        bloqueImprimirCampo.setValue(piePagina);

        camposVenta.add(bloqueImprimirCampo);

        AddicionalFieldReturn campoEstado = new AddicionalFieldReturn();
        campoEstado.setKey("1");
        campoEstado.setValue(String.valueOf(estadoComprobante));

        camposAdicionalesVenta.add(campoEstado);

        AddicionalFieldReturn campoNumConsecutivo = new AddicionalFieldReturn();
        campoNumConsecutivo.setKey("2");
        campoNumConsecutivo.setValue(numeroConsecutivo);

        camposAdicionalesVenta.add(campoNumConsecutivo);

        AddicionalFieldReturn campoClave = new AddicionalFieldReturn();
        campoClave.setKey("3");
        campoClave.setValue(clave);

        camposAdicionalesVenta.add(campoClave);

        AddicionalFieldReturn campoObservaciones = new AddicionalFieldReturn();
        campoObservaciones.setKey("4");
        campoObservaciones.setValue(observaciones);

        camposAdicionalesVenta.add(campoObservaciones);

        AddicionalFieldReturn campoTipoDocumento = new AddicionalFieldReturn();
        campoTipoDocumento.setKey("5");
        campoTipoDocumento.setValue(tipoDocumentoHda);

        camposAdicionalesVenta.add(campoTipoDocumento);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        AddicionalFieldReturn campoFechaEmision = new AddicionalFieldReturn();
        campoFechaEmision.setKey("6");
        campoFechaEmision.setValue(sdf.format(fechaEmision));

        camposAdicionalesVenta.add(campoFechaEmision);

        devolucionVenta.setFieldList(camposVenta);
        devolucionVenta.setAddicionalFieldReturns(camposAdicionalesVenta);

        Writer escritorSalida = new StringWriter();
        serializador.write(devolucionVenta, escritorSalida);

        enviarResultado(resultadoVenta,Activity.RESULT_OK,escritorSalida.toString());
    }

    public void enviarResultado(Intent i, int codigo, String mensaje){
        if(codigo == Activity.RESULT_CANCELED) {
            if(useBroadcast){
                i.putExtra("IsOk",false);
            }

            i.putExtra("ErrorMessage", mensaje);
        }else{
            if(useBroadcast){
                i.putExtra("IsOk",true);
            }

            i.putExtra("SaleResult",mensaje);
        }

        if(useBroadcast){
            view.sendBroadcast(i);
        }else{
            view.setResult(codigo,i);
        }

    }

    public void procesarComprobante(){

        Bundle b = intencionVenta.getExtras();
        if(b != null){
            try {
                useBroadcast = b.getBoolean("UseBroadcast");

                HioposSale venta = serializarDocumentoHIOPOS(b.getString("Document"));
                if(venta == null){
                    throw new HIOPOSInputFormatException(Constants.EMPTY_SALE_INTENT);

                }

                Map<String,String> encabezados = venta.getHeader().getHeaderFields();
                if(encabezados == null || encabezados.size() == 0){
                    throw new HIOPOSInputFormatException(Constants.NO_HEADERS);
                }

                Integer tipoDocumento;
                try {
                    tipoDocumento = Integer.parseInt(encabezados.get("DocumentTypeId"));
                }catch (Exception e){
                    throw new HIOPOSInputFormatException(Constants.NO_DOC_TYPE);
                }

                if(useBroadcast){
                    tipoDocumento = Constants.HIOPOS_TICKET;
                }

                //fecha emision del comprobante de Hiopos
                String hioposFecha = encabezados.get("Date");
                String hioposHora = encabezados.get("Time");
                String hioposSerial = encabezados.get("Serie");

                String hioposNumero = encabezados.get("Number");

                Date fecha = null;

                if(isNullOrEmpty(hioposFecha) || isNullOrEmpty(hioposHora) || isNullOrEmpty(hioposSerial) || isNullOrEmpty(hioposNumero)){
                    throw new HIOPOSInputFormatException(Constants.EMPTY_BASIC_INFO);
                }

                fecha = formatearFecha(hioposFecha,hioposHora,"MM-dd-yyyy HH:mm:ss");

                // Check if document is an invitation from hiopos
                if(tipoDocumento == Constants.HIOPOS_INVITATION){
                    devolverResultadoVenta(hioposSerial, hioposNumero, Constants.SENDED_STATE, "", view.getString(R.string.invitation_document_type).toUpperCase(), view.getString(R.string.sale_result_invitation), view.getString(R.string.invitation_document_type), fecha);
                    return;
                }

                //check is voucher wasn't already in the database
                if(verificarComprobanteExistente(hioposSerial,hioposNumero)){
                    enviarResultado(resultadoVenta,Activity.RESULT_CANCELED,Constants.VOUCHER_ALREADY_SENDED);
                    return;
                }


             //   String  posId = encabezados.get("PosId")
                String posId = null;

                if(configuracion.getSolo_cc()){//JVB

                     posId = configuracion.getTerminalsolocc();//JVB

                }else {
                    posId = encabezados.get("PosId");//JVB
                }

                if(posId != null) {
                    if(tipoDocumento == Constants.HIOPOS_CREDIT_TICKET){
                        //Esto para diferenciar las notas de crédito cuando son tiquetes ya que manejan una numeración diferente
                        posId = String.format(Locale.getDefault(), "%05d",Integer.parseInt(posId)+100);
                    }else if(useBroadcast) {
                        posId = String.format(Locale.getDefault(), "%05d",Integer.parseInt(posId)+500);
                    }else {
                        posId = String.format(Locale.getDefault(), "%05d", Integer.parseInt(posId));
                    }
                }else{
                    throw new HIOPOSInputFormatException(Constants.NULL_POS_ID);
                }

                String hdaTipoDocumento = obtenerTipoDocumentoHacienda(tipoDocumento);

                //PASO 4. Obtengo el número consecutivo por tipo de documento
                int numConsecutivoPorTipoDocumento =  transformarTextoEnNumero(hioposNumero).intValue();//getDataManager().getConsecutiveNumberByDocumentType(Constants.DEFAULT_HEADQUARTERS, posId, hdaTipoDocumento);

                //PASO 5. Genero el número consecutivo del comprobante
                String numConsecutivoFinal = obtenerNumeroConsecutivoFinal(posId, hdaTipoDocumento, numConsecutivoPorTipoDocumento);

                //PASO 7. Genero la clave
                String emisorId = String.format(Locale.getDefault(), "%012d", Long.parseLong(configuracion.getIdentificacion()));
                String contrasena = obtenerContrasena(hioposFecha, emisorId, "3", numConsecutivoFinal);

                //PASO 8: Genero Comprobante
                String comprobanteElectronico = generarComprobanteElectronico(hdaTipoDocumento, numConsecutivoFinal, fecha, contrasena, venta);

                //enviarResultado(resultadoVenta,Activity.RESULT_CANCELED,Constants.EMPTY_SALE_INTENT);

                generarComprobanteYEncolarEnBaseDeDatos(hioposSerial,hioposNumero,comprobanteElectronico,hdaTipoDocumento,fecha);
                devolverResultadoVenta(hioposSerial,hioposNumero,Constants.UNSENDED_STATE, numConsecutivoFinal,contrasena,view.getString(R.string.sale_result_positive),hdaTipoDocumento,fecha);

            }catch (Exception e){
                enviarResultado(resultadoVenta,Activity.RESULT_CANCELED,e.getMessage());
            }
        }else{
            enviarResultado(resultadoVenta,Activity.RESULT_CANCELED,Constants.EMPTY_SALE_INTENT);
        }
    }

    public void generarComprobanteYEncolarEnBaseDeDatos(String serial, String numero,String rawXML, String tipo, Date fecha){
        Comprobante comprobante = new Comprobante();
        comprobante.setAmbiente(configuracion.isProduccion_activo());
        comprobante.setSerie_hiopos(String.format("%s-%s",serial,numero));
        comprobante.setComprobante(rawXML);
        comprobante.setTipo_documento(tipo);
        comprobante.setEstado(Constants.UNSENDED_STATE);
        comprobante.setFechaEmision(fecha);
        if(!configuracion.getSolo_cc() ) {//JVB 29-03-2022 SE ENCAPSULA EN UN CONDICIONAL PARA QUE NO ENCOLE EN BD SI ESTÁ MARCADO SOLO CC
            getDataManager().saveVoucher(comprobante);
            if(configuracion.getLog()){//JVB LOG INSERT TRANSACCIÓN
                try{
                    LogWriter.WriteLine("saveVoucher() ok!! ; Doc: "+serial+"/"+numero+"; Fecha Doc: "+fecha+"; Tipo Doc: "+comprobante.getTipo_documento());// Se incorpora log de seguimiento  JVB 02/05/2022
                    }catch(Exception e)
                    {Log.e("Hiopos", "Error Escribiendo Log");}
            }
        }
    }

    public Date formatearFecha(String fecha, String formato) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
            return sdf.parse(String.format("%s", fecha));
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.WRONG_DATE_FORMAT);
        }
    }

    public Date formatearFecha(String fecha, String hora, String formato) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
            return sdf.parse(String.format("%s %s", fecha, hora));
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.WRONG_DATE_FORMAT);
        }
    }

    public String formatearFecha(Date fecha, String formato) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
            return sdf.format(fecha);
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.WRONG_DATE_FORMAT);
        }
    }

    public HioposSale serializarDocumentoHIOPOS(String rawXML) throws Exception {
        try {
            rawXML = rawXML.replace(",",".");
            Format formato = new Format(0, "<?xml version=\"1.0\" encoding= \"UTF-8\" ?>");
            Serializer serializador = new Persister(formato);
            HioposSale hioposSale = serializador.read(HioposSale.class, rawXML);
            return  hioposSale;
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.WRONG_XML_FORMAT);
        }
    }

    public boolean verificarComprobanteExistente(String serial, String number){
        return (null != getDataManager().getVoucher(configuracion.isProduccion_activo(),serial,number));
    }

    public String obtenerNumeroConsecutivoFinal(String posId, String tipoDocumento, int consecutivoTipoDocumento) throws Exception{
        if(isNullOrEmpty(posId) || isNullOrEmpty(tipoDocumento)) {
            throw new HIOPOSInputFormatException(Constants.UNABLE_CONSECUTIVE_NUMBER);
        }
        String consecutivo = String.format(Locale.getDefault(), "%010d", consecutivoTipoDocumento);
        return String.format(Locale.getDefault(), "%s%s%s%s", Constants.DEFAULT_HEADQUARTERS, posId, tipoDocumento, consecutivo);
    }

    public String obtenerTipoDocumentoHacienda(int tipoDocumentoHiopos) throws Exception {
        switch (tipoDocumentoHiopos) {
            case Constants.HIOPOS_TICKET:
                return Constants.MH_TICKET;
            case Constants.HIOPOS_INVOICE:
                return Constants.MH_INVOICE;
            case Constants.HIOPOS_CREDIT_TICKET:
                return Constants.MH_CREDIT_NOTE;
            case Constants.HIOPOS_CREDIT_INVOICE:
                return Constants.MH_CREDIT_NOTE;
            case Constants.HIOPOS_NON_PRINTABLE_TICKET:
                return Constants.MH_TICKET;
            case Constants.HIOPOS_INVITATION:
                return Constants.MH_TICKET;
            case Constants.HIOPOS_PURCHASE:
                return Constants.MH_INVOICE;
            default:
                throw new HIOPOSInputFormatException(Constants.UNSUPPORTED_HIOPOS_DOC_TYPE);
        }
    }

    public String obtenerContrasena(String fechaHiopos, String idEmisor, String situacionComprobante, String numeroConsecutivoHda) throws Exception {
        try {
            String codigoPais = "506";
            String mes = fechaHiopos.substring(0, 2);
            String dia = fechaHiopos.substring(3, 5);
            String anno = fechaHiopos.substring(8, 10);
            codigoSeguridadFE = dia + mes + anno + "01"; //Este seteo de codigo de seguridad (FA)

            SecureRandom criterio = new SecureRandom(); //Esto es consecutivo
          /*  int resultado = criterio.nextInt(100000000);  */
            int resultado = Integer.parseInt(codigoSeguridadFE); // Parseo del datos de FE   (FA)
            StringBuilder codigoSeguridad = new StringBuilder(resultado + "");
            if (codigoSeguridad.length() != 8) {
                for (int x = codigoSeguridad.length(); x < 8; x++) {
                    codigoSeguridad.insert(0, "0");
                }
            }


            return String.format(Locale.getDefault(), "%s%s%s%s%s%s%s%s", codigoPais, dia, mes, anno, idEmisor, numeroConsecutivoHda, situacionComprobante, codigoSeguridad.toString());
        }catch(Exception e){
            throw new HIOPOSInputFormatException(Constants.UNABLE_TO_CREATE_PASSWORD);
        }
    }

    public String generarComprobanteElectronico (String tipoDocumento, String numeroConsecutivo, Date fecha,
                                                 String llaveElectronica, HioposSale hioposVenta) throws Exception {

        ComprobanteElectronico mdgDoc = new ComprobanteElectronico();

        //mdgDoc.setResolutionNumber(Constants.RESOLUTION_CODE);
        //mdgDoc.setResolutionDate(Constants.RESOLUTION_DATE);

        ComprobanteHacienda comprobanteHacienda = obtenerDocumentoMH(tipoDocumento);

        //NUMERO CONSECUTIVO Y CLAVE
        comprobanteHacienda.setCodigoActividad(configuracion.getActividad_economica());
        comprobanteHacienda.setNumeroConsecutivo(numeroConsecutivo);
        comprobanteHacienda.setClave(llaveElectronica);

        comprobanteHacienda.setFechaEmision(formatearFecha(fecha,"yyyy-MM-dd'T'HH:mm:ss"));

        //region EMISOR
        //se agrega una version vacía del emisor.
        comprobanteHacienda.setEmisor(establecerInformacionEmisorVacio());
        //endregion

        //region RECEPTOR
        Map<String,String> camposCliente = hioposVenta.getHeader()
                .getCustomer()
                .getCustomerFields();
        if(camposCliente == null){
            throw new HIOPOSInputFormatException(Constants.UNABLE_TO_GET_CUSTOMER);
        }

        String idCliente = camposCliente.get("customerId");
        String idFiscal = camposCliente.get("FiscalId");

        boolean receptorExistente = !idCliente.equals("0") && !idCliente.equals("null") && idFiscal != null && !idFiscal.equals("null");
        if (receptorExistente && !useBroadcast) {
            EmisorReceptor receptor = obtenerInformacionDelReceptor(camposCliente);
            comprobanteHacienda.setReceptor(receptor);

            exento = Boolean.parseBoolean(camposCliente.get("IsTaxExempt"));

            if(!exento){
                exonerado = false;
                useExonerationDoc = !Boolean.parseBoolean(hioposVenta.getHeader().getCustomer().getCustomCustomerFields().getCustomCustomerFields().get("SinDocumentosExo"));
                if(useExonerationDoc) {
                    String taxExemption = hioposVenta.getHeader().getCustomer().getCustomerFields().get("TaxExemption");
                    double taxExemptionValue = 0;
                    if (!isNullOrEmpty(taxExemption)) {
                        taxExemptionValue = transformarTextoEnNumero(taxExemption).doubleValue();

                        if (taxExemptionValue > 0) {
                            exonerado = true;
                            porcentajeImpuestosExoneracion = taxExemption;

                            Map<String, String> infoExoneracion = hioposVenta.getHeader().getCustomer().getCustomCustomerFields().getCustomCustomerFields();

                            tipoExoneracion = infoExoneracion.get(Constants.TIPO_EXONERACION);
                            numeroDocumento = infoExoneracion.get(Constants.NUMERO_DOC_EXONERACION);
                            nombreInstitucion = infoExoneracion.get(Constants.NOMBRE_INSTITUCION_EXONERACION);
                            fechaEmision = infoExoneracion.get(Constants.FECHA_EMISION_EXONERACION);

                            if (isNullOrEmpty(tipoExoneracion) || isNullOrEmpty(numeroDocumento) || isNullOrEmpty(nombreInstitucion) || isNullOrEmpty(fechaEmision)) {
                                throw new HIOPOSInputFormatException(Constants.UNABLE_TO_GET_EXONERATION_INFO);
                            }

                        }
                    }
                }
                if(!exonerado){
                    porcentajeImpuestosExoneracion = "0";
                }
            }
        }
        //endregion

        //region CONDICION DE VENTA
        // HIOPOS solo hace operacion al contado.
        comprobanteHacienda.setCondicionVenta("01");
        //endregion

        //region MEDIOS DE PAGO
        ArrayList<MedioPago> metodosPago = obtenerMetodosPago(hioposVenta.getPaymentMeans());
        if (metodosPago.size() > 0) {
            comprobanteHacienda.setMedioPago(metodosPago);
        }
        //endregion

        //region LINEAS DE DETALLE
        ArrayList<LineaDetalle> lineasDetalles = new ArrayList<LineaDetalle>();
        LineField[] lineas = hioposVenta.getLines().toArray(new LineField[hioposVenta.getLines().size()]);
        ArrayList<OtrosCargos> otrosCargos = new ArrayList<OtrosCargos>();
        OtrosCargos tempOtroCargo;

        if(lineas == null){
            throw new HIOPOSInputFormatException(Constants.EMPTY_LINES);
        }

        int filaActual = 1;

        for (int i = 0; i < lineas.length; ++i) {

            if( Boolean.parseBoolean(lineas[i].getCustomProductFields().get(0).getCustomProductFields().get(Constants.ES_OTROS_CARGOS))){
                tempOtroCargo = procesarOtrosCargos(lineas[i]);
                if(tempOtroCargo != null) {
                    otrosCargos.add(tempOtroCargo);
                }
            }else {
                LineaDetalle detalle = procesarLinea(lineas[i], filaActual);
                lineasDetalles.add(detalle);
                filaActual++;
            }
        }

        DiscountField campoDescuento = hioposVenta.getHeader().getDiscount();
        if(campoDescuento != null){
            double descuentoSinImpuestos = Math.abs(procesarDescuentoGeneral(campoDescuento,hioposVenta.getHeader().getHeaderFields().get("TaxesAmount"),tipoDocumento));

            //if(descuentoSinImpuestos > 0) {
                procesarDescuentoSinImpuestos(filaActual,descuentoSinImpuestos,lineasDetalles);
            //}
        }

        comprobanteHacienda.setLineaDetalle(lineasDetalles);

        ServiceChargeField cargoServicio = hioposVenta.getHeader().getServiceCharge();

        if (cargoServicio != null){
            tempOtroCargo = procesarCargoPorServicio(cargoServicio);
            if(tempOtroCargo != null) {
                otrosCargos.add(tempOtroCargo);
            }
        }

        String redondeo = hioposVenta.getHeader().getHeaderFields().get("Rounding");
        if (redondeo != null) {
            double redondeoCantidad = transformarTextoEnNumero(redondeo).doubleValue();

            if(tipoDocumento.equals(Constants.MH_CREDIT_NOTE) ){
                redondeoCantidad *= -1;
            }

            if(redondeoCantidad >= 0) {
                tempOtroCargo = procesarRedondeo(redondeoCantidad, tipoDocumento);
                if (tempOtroCargo != null) {
                    otrosCargos.add(tempOtroCargo);
                }
            }else{
                redondeoCantidad *= -1;

                procesarDescuentoSinImpuestos(filaActual,redondeoCantidad,lineasDetalles);
            }
        }

        if (otrosCargos.size() > 15) {
            throw new HIOPOSInputFormatException(Constants.MAX_OTHER_CHARGES_MAXLIMIT);
        }
        if (otrosCargos.size() > 0) {
            comprobanteHacienda.setOtrosCargos(otrosCargos);
        }
        //endregion

        String moneda = hioposVenta.getHeader().getHeaderFields().get("CurrencyISOCode");
        String tipoCambio = "1";
        Collection<PaymentMeanField> paymentMeansFields = hioposVenta.getPaymentMeans();
        for(PaymentMeanField field : paymentMeansFields){
            CurrencyField currency = field.getCurrency();
            tipoCambio = currency.getCustomCurrencyFields().getCustomCurrencyFields().get("TipoCambio");
        }
        //region resumen factura
        comprobanteHacienda.setResumenFactura(crearResumenDeFactura(moneda,tipoCambio));
        //endregion

        //region INFORMACION REFERENCIA
        if (tipoDocumento.equals(Constants.MH_DEBIT_NOTE) || tipoDocumento.equals(Constants.MH_CREDIT_NOTE)) {

            if (hioposVenta.getAdditionalFields() != null) {
                String tipoDoc = hioposVenta.getAdditionalFields().getAdditionalField().get("5");
                String numeroDoc = hioposVenta.getAdditionalFields().getAdditionalField().get("3");
                String fechaEmision = hioposVenta.getAdditionalFields().getAdditionalField().get("6");

                if (tipoDoc != null && numeroDoc != null && fechaEmision != null) {
                    InformacionReferencia informacionReferencia = new InformacionReferencia();
                    informacionReferencia.setTipoDoc(tipoDoc);
                    informacionReferencia.setNumero(numeroDoc);
                    informacionReferencia.setFechaEmision(fechaEmision);
                    informacionReferencia.setCodigo("01");//Anula Documento de Referencia
                    informacionReferencia.setRazon("Anula Documento de Referencia, Clave: " + informacionReferencia.getNumero());

                    comprobanteHacienda.setInformacionReferencia(new ArrayList<InformacionReferencia>());
                    comprobanteHacienda.getInformacionReferencia().add(informacionReferencia);
                } else {
                    throw new HIOPOSInputFormatException(Constants.CREDIT_DEBIT_NOTE_INCOMPLETED);
                }
            } else {
                throw new HIOPOSInputFormatException(Constants.CREDIT_DEBIT_NOTE_INCOMPLETED);
            }
        }

        //endregion
        mdgDoc = agregarComprobanteHaciendaADocumentoMDG(comprobanteHacienda,tipoDocumento,mdgDoc);

        Format formato = new Format(0, "<?xml version=\"1.0\" encoding= \"UTF-8\" ?>");
        Serializer serializador = new Persister(formato);

        Writer writer = new StringWriter();

        try {
            serializador.write(mdgDoc, writer);
        }catch (Exception e){
            throw new HIOPOSInputFormatException(e.getMessage());
        }

        return writer.toString().replace(",", ".");
    }

    private StringBuilder replaceKey(StringBuilder sb, int i, int e, String value){
        sb.delete(i,e);
        sb.insert(i,value);
        return sb;
    }

    public EmisorReceptor establecerInformacionEmisorVacio(){
        EmisorReceptor emisor = new EmisorReceptor();
        emisor.setNombre("empty");

        Identificacion emisorIdentificacion = new Identificacion();
        emisorIdentificacion.setTipo("01");
        emisorIdentificacion.setNumero("000000000");
        emisor.setIdentificacion(emisorIdentificacion);
        emisor.setCorreoElectronico("empty");

        return emisor;
    }

    public ComprobanteElectronico agregarComprobanteHaciendaADocumentoMDG(ComprobanteHacienda d,String tipoDocumento, ComprobanteElectronico mdgDoc) throws Exception {
        //try {
        switch (tipoDocumento) {
            case Constants.MH_INVOICE: {
                ArrayList<FacturaElectronica> documentos = mdgDoc.getFacturasElectronicas();
                if (documentos == null) {
                    documentos = new ArrayList<FacturaElectronica>();
                }
                documentos.add((FacturaElectronica) d);
                mdgDoc.setFacturasElectronicas(documentos);
                break;
            }
            case Constants.MH_DEBIT_NOTE: {
                ArrayList<NotaDebitoElectronica> documentos = mdgDoc.getNotasDebito();
                if (documentos == null) {
                    documentos = new ArrayList<NotaDebitoElectronica>();
                }
                documentos.add((NotaDebitoElectronica) d);
                mdgDoc.setNotasDebito(documentos);
                break;
            }
            case Constants.MH_CREDIT_NOTE: {
                ArrayList<NotaCreditoElectronica> documentos = mdgDoc.getNotasCredito();
                if (documentos == null) {
                    documentos = new ArrayList<NotaCreditoElectronica>();
                }
                documentos.add((NotaCreditoElectronica) d);
                mdgDoc.setNotasCredito(documentos);
                break;
            }
            case Constants.MH_TICKET: {
                ArrayList<TiqueteHacienda> documentos = mdgDoc.getTiquetesElectronicos();
                if (documentos == null) {
                    documentos = new ArrayList<TiqueteHacienda>();
                }
                documentos.add((TiqueteHacienda) d);
                mdgDoc.setTiquetesElectronicos(documentos);
                break;
            }
            default: {
                throw new HIOPOSInputFormatException(Constants.UNSUPPORTED_MH_DOC_TYPE);
            }
        }
        return mdgDoc;
    }

    public ComprobanteHacienda obtenerDocumentoMH(String tipoDocumento) throws Exception{
        switch (tipoDocumento) {
            case Constants.MH_INVOICE:
                return new FacturaElectronica();
            case Constants.MH_DEBIT_NOTE:
                return new NotaDebitoElectronica();
            case Constants.MH_CREDIT_NOTE:
                return new NotaCreditoElectronica();
            case Constants.MH_TICKET:
                return new TiqueteHacienda();
            default:
                throw new HIOPOSInputFormatException(Constants.UNSUPPORTED_MH_DOC_TYPE);
        }
    }

    public EmisorReceptor obtenerInformacionDelReceptor(Map<String,String> cliente) throws Exception{

        EmisorReceptor receptor = new EmisorReceptor();

        String[] idInfo = obtenerInformacionId(cliente.get("FiscalId"));

        if (idInfo[0].equals(Constants.CEDULA_EXTRANJERA)) {
            receptor.setIdentificacionExtranjero(idInfo[1]);
        } else {
            Identificacion receptorIdentificacion = obtenerReceptorIDDeHIOPOS(idInfo[0], idInfo[1]);
            receptor.setIdentificacion(receptorIdentificacion);
        }

        if(receptor.getIdentificacion()== null && isNullOrEmpty(receptor.getIdentificacionExtranjero())){
            throw new HIOPOSInputFormatException(Constants.UNABLE_GET_CUSTOMER_INFO);
        }
        try {
            receptor.setNombre(cliente.get("Name"));

            //Ubicacion receptorUbicacion = obtenerReceptorUbicacionDeHIOPOS(cliente.get("State"), cliente.get("City"), cliente.get("Address"));

            receptor.setUbicacion(null);

        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.UNABLE_GET_CUSTOMER_INFO);
        }

        Telefono receptorTelefono = obtenerReceptorTelefonoDeHIOPOS(validarTelefono(cliente.get("Phone")));

        receptor.setTelefono(receptorTelefono);

        Telefono receptorFax = obtenerReceptorTelefonoDeHIOPOS(validarTelefono(cliente.get("Phone2")));

        receptor.setFax(receptorFax);

        String correo = cliente.get("Email");
        receptor.setCorreoElectronico(validarEmail(correo).trim());

        return receptor;

    }

    public String validarTelefono(String telefono) throws Exception{
        if(isNullOrEmpty(telefono)){
            return null;
        }

        String patronTelefono = Constants.PHONE_REGULAR_EXPRESION;
        if(telefono.matches(patronTelefono)){
            return telefono;
        }else {
            throw new HIOPOSInputFormatException(Constants.INVALID_PHONE_FORMAT);
        }
    }

    public String validarEmail(String correo) throws Exception{
        if(isNullOrEmpty(correo)){
            throw new HIOPOSInputFormatException(Constants.INVALID_EMAIL_FORMAT);
        }

        String patronACompilar = Constants.EMAIL_REGULAR_EXPRESION;

        Pattern patron = Pattern.compile(patronACompilar);

        Matcher matcher = patron.matcher(correo);

        if(matcher.matches()){
            return correo.trim();
        }

        throw new HIOPOSInputFormatException(Constants.INVALID_EMAIL_FORMAT);
    }

    public String[] obtenerInformacionId(String id) throws Exception{
        String[] idInfo = new String[2];

        String tipoDocumento = "";
        StringBuilder sb = new StringBuilder();
        if(id.length() < 9) {
            throw new HIOPOSInputFormatException(Constants.CUSTOMER_ID_ERROR);
        }
        String c = "";
        boolean tipoDocumentoEncontrado = false;
        for(int i = 0; i < id.length(); ++i){
            c = Character.toString(id.charAt(i));
            if(c.matches("\\d")){
                sb.append(id.charAt(i));
            }else if(c.equals("-") && sb.length() == 2 && !tipoDocumentoEncontrado){
                tipoDocumento = sb.toString();
                sb.delete(0,2);
                tipoDocumentoEncontrado = true;
            }
        }

        if(!tipoDocumentoEncontrado){
            switch (sb.length()){
                case 9:{
                    tipoDocumento = Constants.CEDULA_FISCA;
                    tipoDocumentoEncontrado = true;
                    break;
                }

                case 10:{
                    tipoDocumento = Constants.CEDULA_JURIDICA;
                    tipoDocumentoEncontrado = true;
                    break;
                }
                case 11:
                case 12:{
                    tipoDocumento = Constants.CEDULA_DIMEX;
                    tipoDocumentoEncontrado = true;
                    break;
                }
            }
        }

        if(!tipoDocumentoEncontrado){
            throw new HIOPOSInputFormatException(Constants.CUSTOMER_ID_ERROR);
        }else {
            switch (tipoDocumento) {
                case Constants.CEDULA_FISCA: {
                    idInfo[0] = tipoDocumento;
                    if(validarIdInfo(sb,9)){
                        idInfo[1] = sb.toString();
                    }
                    break;
                }
                case Constants.CEDULA_JURIDICA: {
                    idInfo[0] = tipoDocumento;
                    if(validarIdInfo(sb,10)){
                        idInfo[1] = sb.toString();
                    }
                    break;
                }
                case Constants.CEDULA_DIMEX: {
                    idInfo[0] = tipoDocumento;
                    if(validarIdInfo(sb,12)){
                        idInfo[1] = sb.toString();
                    }
                    if(validarIdInfo(sb,11)){
                        idInfo[1] = sb.toString();
                    }
                    break;
                }
                case Constants.CEDULA_NITE: {
                    idInfo[0] = tipoDocumento;
                    idInfo[1] = sb.toString();
                    break;
                }
                case Constants.CEDULA_EXTRANJERA: {
                    idInfo[0] = tipoDocumento;
                    idInfo[1] = sb.toString();
                    break;
                }
                default: {
                    throw new HIOPOSInputFormatException(Constants.CUSTOMER_ID_ERROR);
                }
            }
        }

        if(isNullOrEmpty(idInfo[0]) || isNullOrEmpty(idInfo[1])){
            throw new HIOPOSInputFormatException(Constants.CUSTOMER_ID_ERROR);
        }

        return idInfo;
    }

    public boolean validarIdInfo(StringBuilder sb, int tamano){
        return sb.length() == tamano && sb.charAt(0) != '0';
    }

    public Identificacion obtenerReceptorIDDeHIOPOS(String tipo,String id){
        Identificacion receptorIdentificacion = new Identificacion();
        receptorIdentificacion.setTipo(tipo);
        receptorIdentificacion.setNumero(id);
        return receptorIdentificacion;
    }

    public Ubicacion obtenerReceptorUbicacionDeHIOPOS(String estado, String ciudad, String direccion){
        if(estado == null || (ciudad == null && direccion == null)){
            return null;
        }

        Ubicacion receptorUbicacion = new Ubicacion();

        String provincia = null;
        String canton = null;
        String distrito = null;
        String barrio = null;

        if(estado != null) {
            provincia = estado.split(",")[0];
            canton = estado.split(",")[1].replaceAll("^\\s+", "");
        }
        if(ciudad != null){
            distrito = ciudad.split(",")[0];
            barrio = ciudad.split(",")[1].replaceAll("^\\s+", "");
        }

        if(direccion != null) {
            receptorUbicacion.setOtrasSenas(direccion);
        }

        if(provincia == null) {
            return null;
        }

        Provincia provinciaDTO = getDataManager().getProvince(provincia);
        if (provinciaDTO == null) {
            return receptorUbicacion;
        }
        receptorUbicacion.setProvincia(provinciaDTO.getCodigo());

        if(canton == null) {
            return receptorUbicacion;
        }

        Canton cantonDTO = getDataManager().getCanton(canton, provinciaDTO.getId());
        if (cantonDTO == null) {
            return receptorUbicacion;
        }
        receptorUbicacion.setCanton(cantonDTO.getCodigo());

        if(distrito == null) {
            return receptorUbicacion;
        }

        Distrito distritoDTO = getDataManager().getDistrict(distrito, cantonDTO.getId());
        if (distritoDTO == null) {
            return receptorUbicacion;
        }
        receptorUbicacion.setDistrito(distritoDTO.getCodigo());

        if (barrio == null) {
            return receptorUbicacion;
        }

        Barrio barrioDTO = getDataManager().getNeighborhood(barrio, distritoDTO.getId());
        if (barrioDTO != null) {
            receptorUbicacion.setBarrio(barrioDTO.getCodigo());
        }

        return receptorUbicacion;
    }

    public Telefono obtenerReceptorTelefonoDeHIOPOS(String telefonoCliente){
        if(telefonoCliente == null || telefonoCliente.isEmpty()){
            return null;
        }

        String[] infoTelefono = telefonoCliente.split(" ");

        String extensionTelefono, telefono;
        if (infoTelefono.length > 1) {
            extensionTelefono = infoTelefono[0];
            telefono = infoTelefono[1];
        } else {
            extensionTelefono = "506";
            telefono = infoTelefono[0];
        }

        Telefono receptorTelefono = new Telefono();
        receptorTelefono.setCodigoPais(extensionTelefono);
        receptorTelefono.setNumTelefono(telefono);

        return receptorTelefono;
    }

    public ArrayList<MedioPago> obtenerMetodosPago(Collection<PaymentMeanField> metodosPagoDeHIOPOS) throws Exception{
        try {
            ArrayList<MedioPago> metodosPago = new ArrayList<MedioPago>();
            int tipoPago = -1;
            boolean pagoYaIncluido;
            for (PaymentMeanField campoMedioPago : metodosPagoDeHIOPOS) {
                tipoPago = transformarTextoEnNumero(campoMedioPago.getPaymentMeanFields().get("PaymentMeanId")).intValue();
                MedioPago pago = new MedioPago();
                switch (tipoPago){
                    case Constants.EFECTIVO_HIOPOS:{
                        pago.setMedio(Constants.EFECTIVO_MH);
                    }break;
                    case Constants.DATAFONO_BAC_HIOPOS:
                    case Constants.DATAFONO_EXTERNO_HIOPOS:
                    case Constants.DATAFONO_HIOPOS:
                    case Constants.TARJETA_HIOPOS:{
                        pago.setMedio(Constants.TARJETA_MH);
                    }break;
                    case Constants.TRANSFERENCIA_HIOPOS:{
                        pago.setMedio(Constants.TRASNFERENCIA_MH);
                    }break;
                    default: {
                        pago.setMedio(Constants.OTROS_MH);
                    }
                }

                pagoYaIncluido = false;
                for (int i  = 0; i < metodosPago.size(); ++i) {
                    if (pago.getMedio().equals(metodosPago.get(i).getMedio())) {
                        pagoYaIncluido = true;
                        break;
                    }
                }

                if (!pagoYaIncluido) {
                    metodosPago.add(pago);
                }
            }
            return metodosPago;
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.UNABLE_PAYMENT_MEANS);
        }
    }

    public CodigoComercial obtenerCodigoPorLinea(String idProducto){
        CodigoComercial codigoLineaUno = new CodigoComercial();
        codigoLineaUno.setTipo("04");
        codigoLineaUno.setCodigo(idProducto);
        return codigoLineaUno;
    }

    public Number transformarTextoEnNumeroDecimal(String entrada) throws Exception{
        if(entrada.contains(",")){
            entrada = entrada.replace(',','.');
        }
        return doubleFormat.parse(entrada);
    }

    public Number transformarTextoEnNumero(String entrada) throws Exception{
        if(entrada.contains(",")){
            entrada = entrada.replace(',','.');
        }
        return nfCurrency.parse(entrada);
    }

    public Number transformarTextoEnUnidades(String entrada) throws  Exception{
        if(entrada.contains(",")){
            entrada = entrada.replace(',','.');
        }
        return unitsFormat.parse(entrada);
    }

    public double[] vectorSum(double[] arg1 ,double[] arg2){
        if(arg1.length == arg2.length){
            double[] sumResultado = new double[arg1.length];
            for(int i = 0; i < sumResultado.length; ++i){
                sumResultado[i] = arg1[i] + arg2[i];
            }

            return sumResultado;
        }
        return null;
    }

    public double procesarDescuentoGeneral(DiscountField campoDescuento,String impuestos,String tipoDocumento) throws Exception{
        String descuentoTotal = campoDescuento.getDiscountFields().get("AmountWithTaxes");
        String descuentoNeto = campoDescuento.getDiscountFields().get("Amount");

        if(isNullOrEmpty(descuentoTotal) && isNullOrEmpty(descuentoNeto)){
            throw new HIOPOSInputFormatException(Constants.EMPTY_BASIC_INFO);
        }

        double descuento = transformarTextoEnNumero(descuentoTotal).doubleValue();

        boolean incluyeImpuestos = true;
        if(descuento == 0){
            incluyeImpuestos = false;
            descuentoTotal = descuentoNeto;
        }


        double descuentoSinImpuestos = 0;
        //double totalImpuestosCalculados = 0;
        //double totalDescuentosPorLinea = 0;

        if(!exento && incluyeImpuestos) {
            if (!isNullOrEmpty(impuestos)) {
                double descuentoConImpuestos = transformarTextoEnNumero(descuentoTotal).doubleValue();

                if (tipoDocumento.equals(Constants.MH_CREDIT_NOTE)) {
                    descuentoConImpuestos *= -1;
                }

                if (descuentoConImpuestos > 0.0d) {
                    double valorImpuestosGenerales = Math.abs(transformarTextoEnNumero(impuestos).doubleValue());

                    double montoImpuestoAlDescuento = totalImpuestosGeneral - valorImpuestosGenerales;
                    descuentoSinImpuestos = descuentoConImpuestos - montoImpuestoAlDescuento;
                }
            }
        }else{
            descuentoSinImpuestos = transformarTextoEnNumero(descuentoTotal).doubleValue();
        }

        return descuentoSinImpuestos;
    }

    public void procesarDescuentoSinImpuestos(int filaActual, double descuentoSinImpuestos, ArrayList<LineaDetalle> lineasDetalles) throws Exception {
        totalImpuestosGeneral = 0;
        totalDescuentosGeneral = 0;

        double descuentoPorLinea;
        for (int i = 0; i < (filaActual - 1); i++) {
            descuentoPorLinea = descuentoSinImpuestos * (transformarTextoEnNumero(lineasDetalles.get(i).getSubTotal()).doubleValue() / subtotalGeneral);
            modificarLineaParaIncluirDescuento(lineasDetalles.get(i), descuentoPorLinea);
        }
    }

    public void modificarLineaParaIncluirDescuento(LineaDetalle lineaEntrante,double descuento) throws Exception {
        try {
            double montoTotalVenta = transformarTextoEnNumero(lineaEntrante.getMontoTotal()).doubleValue();
            double montoDescuentos = 0;

            ArrayList<Descuento> descuentos = lineaEntrante.getDescuento();


            if (descuentos == null) {
                descuentos = new ArrayList<>();

            }

            Descuento descuentoNuevo = new Descuento();
            descuentoNuevo.setMontoDescuento(doubleFormat.format(descuento));
            descuentoNuevo.setNaturalezaDescuento(Constants.DISCOUNT_REASON);
            descuentos.add(descuentoNuevo);

            lineaEntrante.setDescuento(descuentos);

            for (int i = 0; i < descuentos.size(); ++i) {
                montoDescuentos += transformarTextoEnNumero(descuentos.get(i).getMontoDescuento()).doubleValue();
            }

            totalDescuentosGeneral += montoDescuentos;

            double subTotal = 0.0d;
            double impuestoPorLinea;
            double exoneracionPorLinea = 0d;
            double impuestosTotales = 0.0d;
            double exoneracionTotal = 0d;
            double impuestosNetos = 0d;

            subTotal = ApplyRounding(montoTotalVenta - montoDescuentos,5);
            lineaEntrante.setSubTotal(doubleFormat.format(subTotal));
            lineaEntrante.setBaseImponible(doubleFormat.format(subTotal));

            if(!exento){
                double tempValue = 0;
                for (Impuesto impuesto : lineaEntrante.getImpuesto()) {
                    if(impuesto.getCodigo().equals(Constants.IVA_REGIMEN_BIENES_USADOS)){
                        impuestoPorLinea = calcularMontoImpuesto(subTotal, transformarTextoEnNumero(impuesto.getFactorIVA()).doubleValue(), impuesto.getCodigo());
                    }
                    else{
                        impuestoPorLinea = calcularMontoImpuesto(subTotal, transformarTextoEnNumeroDecimal(impuesto.getTarifa()).doubleValue(), impuesto.getCodigo());
                    }

                    impuesto.setMonto(doubleFormat.format(impuestoPorLinea));

                    Exoneracion exoneracion = impuesto.getExoneracion();
                    if (exoneracion != null) {
                        exoneracionPorLinea = ApplyRounding(impuestoPorLinea * transformarTextoEnNumero(exoneracion.getPorcentajeExoneracion()).doubleValue()/100,5);
                        exoneracion.setMontoExoneracion(doubleFormat.format(exoneracionPorLinea));
                    }

                    impuestosTotales += impuestoPorLinea;
                    exoneracionTotal += exoneracionPorLinea;
                }
            }

            impuestosTotales = ApplyRounding(impuestosTotales,5);
            impuestosNetos = impuestosTotales - exoneracionTotal;

            totalImpuestosGeneral += impuestosNetos;

            double totalLinea = subTotal + impuestosNetos;
            lineaEntrante.setMontoTotalLinea(doubleFormat.format(totalLinea));
        }catch (Exception e){
            throw new HIOPOSInputFormatException("No se pudo procesar el descuento general");
        }
    }

    public LineaDetalle procesarLinea(LineField linea,int numeroLinea) throws Exception{
        Map<String, String> camposLinea = linea.getLineFields();
        if(camposLinea == null){
            throw new HIOPOSInputFormatException(Constants.UNABLE_GET_LINE_FIELDS);
        }

        boolean esMercancia = isMercancia(linea.getCustomProductFields().get(0).getCustomProductFields().get(Constants.TIPO_DE_ARTICULO));

        Collection<LineTaxField> impuestos;
        double[] resultadoModificadores;

        double precioUnitario = 0d;
        double montoTotalVenta = 0d;
        double montoDescuentos = 0d;
        double subTotal = 0d;
        //Double totalImpuestoLinea = 0d;
        double impuestoNetoLinea = 0d;
        double totalLinea = 0d;

        Double precioUnitarioModificadores = 0d;
        Double ventaTotalModificadores = 0d;
        Double montoDescuentoModificadores = 0d;
        Double montoTotalVentaModificadores = 0d;

        LineaDetalle detalle = new LineaDetalle();

        ArrayList<CodigoComercial> codigoComercial = new ArrayList<>();
        codigoComercial.add(obtenerCodigoPorLinea(camposLinea.get("ProductId")));
        detalle.setCodigoComercial(codigoComercial);

        String factorIVA = linea.getCustomProductFields().get(0).getCustomProductFields().get(Constants.FACTOR_IVA);

        if(!isNullOrEmpty(linea.getCustomProductFields().get(0).getCustomProductFields().get(Constants.CODIGO_TIPO_DE_ARTICULO))) {
            detalle.setCodigo(linea.getCustomProductFields().get(0).getCustomProductFields().get(Constants.CODIGO_TIPO_DE_ARTICULO));
        }

        detalle.setNumeroLinea(numeroLinea);

        String units = camposLinea.get("Units");

        double cantidad = Math.abs (transformarTextoEnUnidades(units).doubleValue());
        cantidad = ApplyRoundingUp(cantidad,3);
        detalle.setCantidad(cantidad);

        if(esMercancia) {
            detalle.setUnidadMedida(Constants.UNIDAD_MEDIDA_UNIDADES);
        }else{
            detalle.setUnidadMedida(Constants.UNIDAD_MEDIDA_SERVICIOS);
        }

        detalle.setDetalle(camposLinea.get("Name"));

        resultadoModificadores = procesarModificadores(linea.getModifiers());

        montoDescuentoModificadores = resultadoModificadores[0];
        ventaTotalModificadores = resultadoModificadores[1];
        precioUnitarioModificadores = resultadoModificadores[2];
        montoTotalVentaModificadores = resultadoModificadores[3];

        try {
            //cantidad = detalle.getCantidad();
            double descuento = Math.abs(transformarTextoEnNumero(camposLinea.get("DiscountAmount")).doubleValue());
            double precio = Math.abs(transformarTextoEnNumero(camposLinea.get("BaseAmount")).doubleValue());

            precioUnitario = (precio+descuento)/cantidad;
            montoTotalVenta = ApplyRounding((precioUnitario*cantidad)+ montoTotalVentaModificadores,5);

            if(cantidad == 1){
                precioUnitario = montoTotalVenta;
            }else{
                precioUnitario = ApplyRounding(precioUnitario+(precioUnitarioModificadores/cantidad),5);
            }

            detalle.setMontoTotal(doubleFormat.format(montoTotalVenta));
            detalle.setPrecioUnitario(doubleFormat.format(precioUnitario));

            montoDescuentos = descuento + montoDescuentoModificadores;

            if (montoDescuentos > 0) {
                ArrayList<Descuento> descuentos = new ArrayList<>();
                Descuento descuentoNuevo = new Descuento();
                descuentoNuevo.setMontoDescuento(doubleFormat.format(montoDescuentos));
                descuentoNuevo.setNaturalezaDescuento(Constants.DISCOUNT_REASON);
                descuentos.add(descuentoNuevo);
                detalle.setDescuento(descuentos);
            }

            subTotal = ApplyRounding(montoTotalVenta - montoDescuentos,5);
            subtotalGeneral += subTotal;
            double baseImponible = subTotal;
            detalle.setSubTotal(doubleFormat.format(subTotal));
            detalle.setBaseImponible(doubleFormat.format(subTotal));

            impuestos = linea.getLineTaxes();

            if (!exento) {
                detalle.setImpuesto(obtenerImpuestosPorLinea(impuestos,subTotal,baseImponible,factorIVA));
            }else{
                totalMontoImpuestosPorLinea = 0;
                totalMontoExoneradoPorLinea = 0;
                porcentajeExoneradoPorLinea = 0;
            }

            impuestoNetoLinea = totalMontoImpuestosPorLinea - totalMontoExoneradoPorLinea;

            totalLinea = subTotal + impuestoNetoLinea;

            detalle.setImpuestoNeto(doubleFormat.format(impuestoNetoLinea));
            detalle.setMontoTotalLinea(doubleFormat.format(totalLinea));

            if(exento){
                if (esMercancia) {
                    mercanciasExcentas += montoTotalVenta;
                }
                else {
                    serviciosExentos += montoTotalVenta;
                }
            }else {
                if (esMercancia) {
                    mercanciasGravadas += ApplyRounding(montoTotalVenta*(1-porcentajeExoneradoPorLinea),5);
                    mercanciasExoneradas += ApplyRounding(montoTotalVenta*(porcentajeExoneradoPorLinea),5);
                } else {
                    serviciosGravados += ApplyRounding(montoTotalVenta*(1-porcentajeExoneradoPorLinea),5);
                    serviciosExonerados += ApplyRounding(montoTotalVenta*(porcentajeExoneradoPorLinea),5);
                }
            }

            totalDescuentosGeneral += montoDescuentos;
            totalImpuestosGeneral += impuestoNetoLinea;

            return detalle;
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.UNABLE_LINE_DETAILS+": "+e.getMessage());
        }
    }

    public ArrayList<Impuesto> obtenerImpuestosPorLinea(Collection<LineTaxField> impuestosLinea,double subTotal,double baseImponible,String factor) throws Exception{
        //TODO improve exceptions
        ArrayList<Impuesto> impuestos = new ArrayList<Impuesto>();
        Exoneracion exoneracion = null;

        double porcentajeExoneracion = 0d;
        double montoExoneracion = 0d;
        double factorIVA = 0d;
        double montoImpuesto = 0;
        String codigoImpuesto = Constants.IMPUESTO_AL_VALOR_AGREGADO; //TODO por ahora esto va a ser siempre IVA
        String codigoTarifa = "01"; //TODO se obtiene segun el porcentaje de impuesto, se prioriza el tipo transitorio
        int porcentajeImpuesto;

        totalMontoImpuestosPorLinea = 0;
        totalMontoExoneradoPorLinea = 0;
        porcentajeExoneradoPorLinea = 0;

        for (LineTaxField impuestoPorLinea : impuestosLinea) {
            Impuesto impuestoLinea = new Impuesto();

            if(!isNullOrEmpty(factor)) {
                factorIVA = transformarTextoEnNumeroDecimal(factor).doubleValue();
                impuestoLinea.setFactorIVA(decimalFormat.format(factorIVA/100d));
                if(factorIVA>0) {
                    codigoImpuesto = Constants.IVA_REGIMEN_BIENES_USADOS;
                }
            }

            if(!exonerado) {
                try {
                    porcentajeImpuesto = transformarTextoEnNumeroDecimal(impuestoPorLinea.getLineTaxFields().get("Percentage")).intValue();
                }catch(Exception e) {
                    throw new HIOPOSInputFormatException(Constants.UNABLE_TO_GET_TAX_RATE);
                }
            }else{
                porcentajeImpuesto = 13;//TODO queda 13 en espera a los cambios solicitados el 25/6/2019
            }

            try {
                if (codigoImpuesto.equals(Constants.IVA_REGIMEN_BIENES_USADOS)) {
                    montoImpuesto = calcularMontoImpuesto(subTotal, factorIVA, codigoImpuesto);
                } else {
                    montoImpuesto = calcularMontoImpuesto(subTotal, porcentajeImpuesto, codigoImpuesto);
                }
            }catch (Exception e){
                throw new HIOPOSInputFormatException(Constants.UNABLE_LINE_TAX);
            }

            codigoTarifa = impuestoPorLinea.getCustomTaxFields().getCustomTaxFields().get("CodigoMdh");

            if(!CompararCodigoConTarifa(codigoTarifa,decimalFormat.format(porcentajeImpuesto))) {

                codigoTarifa = getCodigoTarifaReducida(decimalFormat.format(porcentajeImpuesto));

            }

            if(isNullOrEmpty(codigoTarifa)){
                if(!configuracion.getSolo_cc() ) {
                    throw new HIOPOSInputFormatException(Constants.UNABLE_TO_GET_TAX_RATE_CODE);//JVB 29-03-2022 en caso de que no se encuentre el código de tarifa y está marcado solo cc de igual manera se genera el comprobante
                }


            }

            totalMontoImpuestosPorLinea += montoImpuesto;

            impuestoLinea.setCodigo(codigoImpuesto);
            impuestoLinea.setCodigoTarifa(codigoTarifa);
            impuestoLinea.setTarifa(decimalFormat.format(porcentajeImpuesto));
            impuestoLinea.setMonto(doubleFormat.format(montoImpuesto));

            if(exonerado) {
                exoneracion = new Exoneracion();
                if(!isNullOrEmpty(tipoExoneracion))
                {
                    exoneracion.setFechaEmision(formatearFecha(formatearFecha(fechaEmision,"yyyy-MM-dd"),"yyyy-MM-dd'T'HH:mm:ss"));
                    exoneracion.setNombreInstitucion(nombreInstitucion);
                    exoneracion.setNumeroDocumento(numeroDocumento);
                    exoneracion.setTipoDocumento(tipoExoneracion);
                }

                porcentajeExoneracion = transformarTextoEnNumero(porcentajeImpuestosExoneracion).doubleValue();

                int porcentajeParaMostar = (int)Math.round((porcentajeExoneracion*porcentajeImpuesto)/100d);
                double porcentajeReal = ((double)porcentajeParaMostar)/100d;

                montoExoneracion = ApplyRounding(subTotal * porcentajeReal,5);

                totalMontoExoneradoPorLinea += montoExoneracion;
                porcentajeExoneradoPorLinea = ((double)porcentajeParaMostar/(double)porcentajeImpuesto);

                exoneracion.setPorcentajeExoneracion(intFormat.format(porcentajeParaMostar));
                exoneracion.setMontoExoneracion(doubleFormat.format(montoExoneracion));
            }

            impuestoLinea.setExoneracion(exoneracion);
            impuestos.add(impuestoLinea);
        }

        return impuestos;
    }

    public double calcularMontoImpuesto(double subTotal, double tarifa, String codigoImpuesto){
        if(codigoImpuesto.equals(Constants.IVA_REGIMEN_BIENES_USADOS)){
            return ApplyRounding((tarifa - 1) * subTotal,5);
        }else{
            return ApplyRounding((subTotal * tarifa) / 100d,5);
        }
    }

    public double[] procesarModificadores(Collection<ModifierField> modificadores)throws Exception{
        try {
            double[] resultados = new double[4];

            if (modificadores != null && modificadores.size() > 0) {
                for (ModifierField modificador : modificadores) {
                    resultados = vectorSum(resultados, procesarModificadoresRecursivo(modificador));
                }
            }

            return resultados;
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.UNABLE_PROCESS_MODIFIERS);
        }
    }

    public double[] procesarModificadoresRecursivo(ModifierField modificador)throws Exception{
        double[] valores = new double[4];
        Collection<ModifierField> subModificador = modificador.getModifiers();
        if(subModificador != null && subModificador.size() > 0){
            for(ModifierField subModifier : subModificador){
                valores = vectorSum(valores , procesarModificadoresRecursivo(subModifier));
            }
        }else{
            valores = obtenerValorModificadores(modificador.getModifierFields());
        }

        return valores;
    }

    public double[] obtenerValorModificadores(Map<String,String> modificador)throws Exception{
        String units = modificador.get("Units").replace('.',',');
        double unidad = Math.abs(transformarTextoEnUnidades(units).doubleValue());
        double cantidadBase = Math.abs(transformarTextoEnNumero(modificador.get("BaseAmount")).doubleValue());

        double cantidadDescuento = 0d;
        String descuento = modificador.get("DiscountAmount");
        if ( descuento != null) {
            cantidadDescuento = Math.abs(transformarTextoEnNumero(descuento).doubleValue());
        }
        double[] valores = new double[4];
        valores[0] = cantidadDescuento;
        valores[1] = cantidadBase;
        valores[2] = (cantidadBase / unidad) + (cantidadDescuento / unidad);
        valores[3] = ((cantidadBase / unidad) + (cantidadDescuento / unidad)) * unidad;

        return valores;
    }

    public OtrosCargos procesarOtrosCargos(LineField linea) throws Exception{

        Map<String, String> camposLinea = linea.getLineFields();
        if(camposLinea == null){
            throw new HIOPOSInputFormatException(Constants.UNABLE_GET_LINE_FIELDS);
        }

        String detalle = camposLinea.get("Name");
        double[] resultadoModificadores;
        double montoTotalVentaModificadores = 0d;

        resultadoModificadores = procesarModificadores(linea.getModifiers());
        montoTotalVentaModificadores = resultadoModificadores[3];

        String tipoOtrosCargos = linea.getCustomProductFields().get(0).getCustomProductFields().get(Constants.TIPO_OTROS_CARGOS);

        double precio = Math.abs(transformarTextoEnNumero(camposLinea.get("BaseAmount")).doubleValue());
        double montoTotal = ApplyRounding(precio + montoTotalVentaModificadores,5);


        return procesarOtrosCargos(tipoOtrosCargos,detalle,0,montoTotal,null,null);
    }

    public OtrosCargos procesarOtrosCargos(String tipoDocumento, String detalle, double porcentaje, double montoCargo, String numeroIdentidadTercero,String nombreTercero) throws Exception {

        if(montoCargo == 0){
            return null;
        }

        if(isNullOrEmpty(tipoDocumento)){
            throw new HIOPOSInputFormatException(Constants.TIPO_OTROS_CARGOS_IS_NULL);
        }

        if(tipoDocumento.equals(Constants.COBRO_DE_UN_TERCERO) && isNullOrEmpty(numeroIdentidadTercero)) {
            throw new HIOPOSInputFormatException(Constants.MAX_OTHER_CHARGES_MISSING_ID);
        }

        OtrosCargos _cargo = new OtrosCargos();
        _cargo.setTipoDocumento(tipoDocumento);
        _cargo.setNumeroIdentidadTercero(numeroIdentidadTercero);
        _cargo.setNombreTercero(nombreTercero);
        _cargo.setDetalle(detalle);

        if(porcentaje > 0) {
            _cargo.setPorcentaje(doubleFormat.format(porcentaje) );
        }

        _cargo.setMontoCargo(doubleFormat.format(montoCargo));
        totalOtrosCargos += montoCargo;
        return _cargo;
    }


    public OtrosCargos procesarCargoPorServicio(ServiceChargeField cargoServicio) throws Exception {
        try {
            double cargoPorcentaje = transformarTextoEnNumero( cargoServicio.getServiceChargeFields().get("Percentage") ).doubleValue()/100d;
            double totalServicio = transformarTextoEnNumero(cargoServicio.getServiceChargeFields().get("AmountWithTaxes")).doubleValue();
            if(totalServicio < 0){
                totalServicio = totalServicio * -1;
            }
            return procesarOtrosCargos(Constants.IMPUESTO_DE_SERVICIO, "Cargo de servicio",cargoPorcentaje,totalServicio,null,null);
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.UNABLE_PROCESS_SERVICE_CHARGE);
        }
    }

    public OtrosCargos procesarRedondeo(double redondeo,String tipoDocumento) throws Exception{
        try {
            //double rounding = transformarTextoEnNumero(redondeo).doubleValue();

            return procesarOtrosCargos(Constants.OTROS_CARGOS_OTROS_CARGOS, "Redondeo",0,redondeo,null,null);
        }catch (Exception e){
            throw new HIOPOSInputFormatException(Constants.UNABLE_PROCESS_ROUNDING);
        }
    }

    private ResumenFactura crearResumenDeFactura(String codigoMonedaHiopos, String tipoCambioHiopos) throws Exception{
        //region RESUMEN FACTURA
        ResumenFactura resumenFactura = new ResumenFactura();

        String codigoMoneda = "CRC";
        String tipoCambio = "1";

        //TODO comoprobar si el codigo de HIOPOS esta en la lista de monedas
        if (!isNullOrEmpty(codigoMonedaHiopos) && codigoMonedaHiopos != codigoMoneda) {
            if (isNullOrEmpty(tipoCambioHiopos)){
                throw new HIOPOSInputFormatException(Constants.INCOMPLETE_CURRENCY_INFO);
            }

            codigoMoneda = codigoMonedaHiopos;
            tipoCambio = tipoCambioHiopos;
        }

        CodigoTipoMoneda codigoTipoMoneda = new CodigoTipoMoneda();
        codigoTipoMoneda.setCodigoMoneda(codigoMoneda);
        codigoTipoMoneda.setTipoCambio(tipoCambio);

        resumenFactura.setCodigoTipoMoneda(codigoTipoMoneda);

        double totalGravado = mercanciasGravadas+serviciosGravados;
        double totalExonerado = mercanciasExoneradas+serviciosExonerados;
        double totalExento = mercanciasExcentas+serviciosExentos;
        double totalVenta = totalGravado+totalExonerado+totalExento;

        resumenFactura.setTotalMercanciasGravadas(doubleFormat.format(mercanciasGravadas));
        resumenFactura.setTotalMercanciasExentas(doubleFormat.format(mercanciasExcentas));
        resumenFactura.setTotalMercExonerada(doubleFormat.format(mercanciasExoneradas));

        resumenFactura.setTotalServGravados(doubleFormat.format(serviciosGravados));
        resumenFactura.setTotalServExentos(doubleFormat.format(serviciosExentos));
        resumenFactura.setTotalServExonerado(doubleFormat.format(serviciosExonerados));

        resumenFactura.setTotalGravado(doubleFormat.format(totalGravado));
        resumenFactura.setTotalExonerado(doubleFormat.format(totalExonerado));
        resumenFactura.setTotalExento(doubleFormat.format(totalExento));
        resumenFactura.setTotalIVADevuelto(doubleFormat.format(totalIVADevuelto));
        if(totalOtrosCargos != 0) {
            resumenFactura.setTotalOtrosCargos(doubleFormat.format(totalOtrosCargos));
        }
        resumenFactura.setTotalVenta(doubleFormat.format(totalVenta));
        resumenFactura.setTotalDescuentos(doubleFormat.format(totalDescuentosGeneral));
        resumenFactura.setTotalVentaNeta(doubleFormat.format((totalVenta) - totalDescuentosGeneral));
        resumenFactura.setTotalImpuesto(doubleFormat.format(totalImpuestosGeneral));
        resumenFactura.setTotalComprobante(doubleFormat.format((totalVenta - totalDescuentosGeneral) + totalImpuestosGeneral+totalOtrosCargos-totalIVADevuelto));

        return resumenFactura;
    }

    private boolean isNullOrEmpty(String entrada){
        return entrada == null || entrada.equals("");
    }

    private double ApplyRounding(double value, int precision){
        return DoubleRounder.round(value,precision,RoundingMode.HALF_EVEN);
    }

    private double ApplyRoundingUp(double value, int precision){
        return DoubleRounder.round(value,precision,RoundingMode.HALF_UP);
    }

    private boolean isMercancia(String msg){
        if(isNullOrEmpty(msg) || !msg.toLowerCase().equals(Constants.TIPO_SERVICIO)){
            return true;
        }else{
            return false;
        }
    }

    private boolean CompararCodigoConTarifa(String codigoTarifa,String porcentaje){
        switch (codigoTarifa){
            case Constants.TARIFA_0:{
                return porcentaje.equals("0.00");
            }
            case Constants.TARIFA_RED_1:{
                return porcentaje.equals("1.00");
            }
            case Constants.TARIFA_RED_2:{
                return porcentaje.equals("2.00");
            }
            case Constants.TARIFA_RED_4:{
                return porcentaje.equals("4.00");
            }
            case Constants.TRANSITORIO_0:{
                return porcentaje.equals("0.00");
            }
            case Constants.TRANSITORIO_4:{
                return porcentaje.equals("4.00");
            }
            case Constants.TRANSITORIO_8:{
                return porcentaje.equals("8.00");
            }
            case Constants.TRANSITORIO_13:{
                return porcentaje.equals("13.00");
            }
            default:{
                return false;
            }
        }
    }

    private String getCodigoTarifaReducida(String porcentaje){
        switch (porcentaje){
            case "1.00":{
                return Constants.TARIFA_RED_1;
            }
            case "2.00":{
                return Constants.TARIFA_RED_2;
            }
            case "4.00":{
                return Constants.TARIFA_RED_4;
            }
            default: {
                return null;
            }
        }
    }
}
