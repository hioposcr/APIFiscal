package fiscalapi.icg.es.apifiscal.Data;

import java.util.List;

import fiscalapi.icg.es.apifiscal.Data.Models.Comprobante;

import fiscalapi.icg.es.apifiscal.Data.Models.Barrio;
import fiscalapi.icg.es.apifiscal.Data.Models.Canton;
import fiscalapi.icg.es.apifiscal.Data.Models.ComprobanteErroneo;
import fiscalapi.icg.es.apifiscal.Data.Models.Configuracion;
import fiscalapi.icg.es.apifiscal.Data.Models.Distrito;
import fiscalapi.icg.es.apifiscal.Data.Models.MediosPago;
import fiscalapi.icg.es.apifiscal.Data.Models.Provincia;
import fiscalapi.icg.es.apifiscal.Data.Models.TipoIdentificacion;
import fiscalapi.icg.es.apifiscal.Data.Models.ActividadesEconomicas;

public interface DatabaseManagerI {
    boolean saveConfiguration(Configuracion configuration);
    Configuracion getConfiguration();
    List<Comprobante> getPendingVouchers(boolean environment);
    void updateVoucher(Comprobante voucher);
    Comprobante getVoucher(boolean environment, String serial, String number);
    void deleteVoucher(Comprobante voucher);
    List<Provincia> getProvinces();
    Provincia getProvince(String name);
    List<Canton> getCantons(Long idProvince);
    Canton getCanton(String name,Long idProvince);
    List<Distrito> getDistricts(Long idCanton);
    Distrito getDistrict(String name,Long idCanton);
    List<Barrio> getNeighborhoods(Long idDistrict);
    Barrio getNeighborhood(String name, Long idDistrict);
    List<TipoIdentificacion> getIdsTypes();
    List<ActividadesEconomicas> getActividadesEconomicas();
    void saveVoucher(Comprobante request);
    int getConsecutiveNumberByDocumentType(String headquarters, String terminal, String documentType);
    MediosPago getPaymentMethod(String name);
    List<ComprobanteErroneo> getComprobantesErrones();
    void saveComprobanteErroneo(ComprobanteErroneo error);
    void deleteWrongVoucher(ComprobanteErroneo voucher);
}
