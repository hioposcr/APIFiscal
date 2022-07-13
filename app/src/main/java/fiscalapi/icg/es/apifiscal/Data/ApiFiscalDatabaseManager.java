package fiscalapi.icg.es.apifiscal.Data;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import fiscalapi.icg.es.apifiscal.Data.Models.Barrio;
import fiscalapi.icg.es.apifiscal.Data.Models.BarrioDao;
import fiscalapi.icg.es.apifiscal.Data.Models.Canton;
import fiscalapi.icg.es.apifiscal.Data.Models.CantonDao;
import fiscalapi.icg.es.apifiscal.Data.Models.Comprobante;
import fiscalapi.icg.es.apifiscal.Data.Models.ComprobanteDao;
import fiscalapi.icg.es.apifiscal.Data.Models.ComprobanteErroneo;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Data.Models.ConsecutivoComprobante;
import fiscalapi.icg.es.apifiscal.Data.Models.ConsecutivoComprobanteDao;
import fiscalapi.icg.es.apifiscal.Data.Models.DaoMaster;
import fiscalapi.icg.es.apifiscal.Data.Models.DaoSession;
import fiscalapi.icg.es.apifiscal.Data.Models.Distrito;
import fiscalapi.icg.es.apifiscal.Data.Models.DistritoDao;
import fiscalapi.icg.es.apifiscal.Data.Models.MediosPago;
import fiscalapi.icg.es.apifiscal.Data.Models.MediosPagoDao;
import fiscalapi.icg.es.apifiscal.Data.Models.Provincia;
import fiscalapi.icg.es.apifiscal.Data.Models.ProvinciaDao;
import fiscalapi.icg.es.apifiscal.Data.Models.TipoIdentificacion;
import fiscalapi.icg.es.apifiscal.Data.Models.TipoIdentificacionDao;
import fiscalapi.icg.es.apifiscal.Data.Models.ActividadesEconomicas;
import fiscalapi.icg.es.apifiscal.Data.Models.ActividadesEconomicasDao;

@Singleton
public class ApiFiscalDatabaseManager implements DatabaseManagerI {

    private final DaoSession daoSession;

    @Inject
    public ApiFiscalDatabaseManager(ApiFiscalDatabaseOpenHelper openHelper){
        daoSession = new DaoMaster(openHelper.getWritableDatabase()).newSession();
    }


    @Override
    public boolean saveConfiguration(Configuracion configuration) {
        if (configuration!=null){
            daoSession.getConfiguracionDao().insertOrReplace(configuration);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Configuracion getConfiguration() {
        //Configuracion configuration = daoSession.getConfiguracionDao().load(1L);
        /*if (configuration!=null){
            return configuration;
        }*/
        Configuracion configuracion = daoSession.getConfiguracionDao().load(1L);
        if (configuracion != null
                //&& configuracion.getConsecutivo_sincronizado()
                && configuracion.getNombre() != null
                && configuracion.getTipo_identificacion() != null
                && configuracion.getIdentificacion() != null
                && configuracion.getCodigo_provincia() != null
                && configuracion.getCodigo_canton() != null
                && configuracion.getCodigo_distrito() != null
                && configuracion.getDireccion() != null
                && configuracion.getCorreo_electronico() != null
                && configuracion.getUsuario_mdg() != null
                && configuracion.getClave_mdg() != null)
        {
            //Log.d("carga la configuración ",configuracion.getNombre());
            return configuracion;
        }
        return null;
    }

    @Override
    public List<Comprobante> getPendingVouchers(boolean environment) {
        try {
            return daoSession.getComprobanteDao().queryBuilder()
                    .where(ComprobanteDao.Properties.Ambiente.eq(environment))
                    .where(ComprobanteDao.Properties.Estado.eq(0))
                    .orderAsc(ComprobanteDao.Properties.Id)
                    .list();
                    //.unique();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Comprobante getVoucher(boolean environment, String serial, String number){
        try {

            return (Comprobante) daoSession.getComprobanteDao().queryBuilder()
                    .where(ComprobanteDao.Properties.Ambiente.eq(environment))
                    .where(ComprobanteDao.Properties.Hiopos_id.eq(String.format("%s-%s",serial,number)))
                    .unique();

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void updateVoucher(Comprobante voucher) {
        if (voucher!=null){
            daoSession.getComprobanteDao().update(voucher);
        }
    }

    @Override
    public void saveVoucher(Comprobante request) {
        if (request!=null){
            daoSession.getComprobanteDao().insert(request);
        }
    }

    @Override
    public void deleteVoucher(Comprobante voucher){
        if(voucher != null){
            daoSession.getComprobanteDao().delete(voucher);
        }
    }


    @Override
    public List<Provincia> getProvinces() {
        return daoSession.getProvinciaDao().loadAll();
    }

    @Override
    public Provincia getProvince(String name){
        return daoSession.getProvinciaDao().queryBuilder()
                .where(ProvinciaDao.Properties.Nombre.like(name))
                .limit(1)
                .unique();
    }

    @Override
    public List<Canton> getCantons(Long idProvince) {

        return  daoSession.getCantonDao().queryBuilder()
                .where(CantonDao.Properties.Id_provincia.eq(idProvince))
                .where(CantonDao.Properties.Codigo.notEq("00"))
                .list();
    }

    @Override
    public Canton getCanton(String name, Long idProvince){
        return daoSession.getCantonDao().queryBuilder()
                .where(CantonDao.Properties.Nombre.like(name))
                .where(CantonDao.Properties.Id_provincia.eq(idProvince))
                .where(CantonDao.Properties.Codigo.notEq("00"))
                .limit(1)
                .unique();
    }

    @Override
    public List<Distrito> getDistricts(Long idCanton) {
        return daoSession.getDistritoDao().queryBuilder()
                .where(DistritoDao.Properties.Id_canton.eq(idCanton))
                .where(DistritoDao.Properties.Codigo.notEq("00"))
                .list();
    }

    @Override
    public Distrito getDistrict(String name, Long idCanton){
        return daoSession.getDistritoDao().queryBuilder()
                .where(DistritoDao.Properties.Nombre.like(name))
                .where(DistritoDao.Properties.Id_canton.eq(idCanton))
                .where(DistritoDao.Properties.Codigo.notEq("00"))
                .limit(1)
                .unique();
    }

    @Override
    public List<Barrio> getNeighborhoods(Long idDistricts) {

        return daoSession.getBarrioDao().queryBuilder()
                .where(BarrioDao.Properties.Id_distrito.eq(idDistricts))
                .where(BarrioDao.Properties.Codigo.notEq("00"))
                .list();
    }

    @Override
    public Barrio getNeighborhood(String name, Long idDistrict){
        return daoSession.getBarrioDao().queryBuilder()
                .where(BarrioDao.Properties.Nombre.like(name))
                .where(BarrioDao.Properties.Id_distrito.eq(idDistrict))
                .where(BarrioDao.Properties.Codigo.notEq("00"))
                .limit(1)
                .unique();
    }

    @Override
    public List<TipoIdentificacion> getIdsTypes() {
        List<TipoIdentificacion> tempoIdTypes;
        tempoIdTypes = daoSession.getTipoIdentificacionDao().queryBuilder().list();
        return tempoIdTypes;
    }

    @Override
    public List<ActividadesEconomicas> getActividadesEconomicas() {
        List<ActividadesEconomicas> tempoActividadesEconomicas;
        tempoActividadesEconomicas = daoSession.getActividadesEconomicasDao()
                .queryBuilder()
                //.where(ActividadesEconomicasDao.Properties.Estado.eq(true))
                .orderAsc(ActividadesEconomicasDao.Properties.Nombre)
                .list();
        return tempoActividadesEconomicas;
    }

    @Override
    public int getConsecutiveNumberByDocumentType(String headquarters, String terminal, String documentType) {

        Configuracion configuracion = daoSession.getConfiguracionDao().queryBuilder().unique();

        ConsecutivoComprobante consecutivoComprobante = daoSession.getConsecutivoComprobanteDao().queryBuilder()
                .orderDesc(ConsecutivoComprobanteDao.Properties.CONSECUTIVO)
                .where(ConsecutivoComprobanteDao.Properties.CASA_MATRIZ.eq(headquarters))
                .where(ConsecutivoComprobanteDao.Properties.TERMINAL.eq(terminal))
                .where(ConsecutivoComprobanteDao.Properties.TIPO_DOCUMENTO.eq(documentType))
                .where(ConsecutivoComprobanteDao.Properties.EMISOR_IDENTIFICACION.eq(configuracion.getIdentificacion()))
                .limit(1)
                .unique();

        if (consecutivoComprobante != null) {
            consecutivoComprobante.setCONSECUTIVO(consecutivoComprobante.getCONSECUTIVO() + 1);
            daoSession.update(consecutivoComprobante);

            return consecutivoComprobante.getCONSECUTIVO();
        } else {

            consecutivoComprobante = new ConsecutivoComprobante();
            consecutivoComprobante.setCASA_MATRIZ(headquarters);
            consecutivoComprobante.setTERMINAL(terminal);
            consecutivoComprobante.setTIPO_DOCUMENTO(documentType);
            consecutivoComprobante.setEMISOR_IDENTIFICACION(configuracion.getIdentificacion());
            consecutivoComprobante.setCONSECUTIVO(1);

            daoSession.insert(consecutivoComprobante);
            return 1;
        }
    }

    @Override
    public MediosPago getPaymentMethod(String name){
        return daoSession.getMediosPagoDao().queryBuilder()
                .where(MediosPagoDao.Properties.Descripcion.like(name))
                .limit(1)
                .unique();
    }

    @Override
    public List<ComprobanteErroneo> getComprobantesErrones(){
        return daoSession.getComprobanteErroneoDao().loadAll();
    }

    @Override
    public void saveComprobanteErroneo(ComprobanteErroneo error){
        daoSession.getComprobanteErroneoDao().insert(error);
    }

    @Override
    public void deleteWrongVoucher(ComprobanteErroneo voucher) {
        try {
            daoSession.getComprobanteErroneoDao().delete(voucher);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
