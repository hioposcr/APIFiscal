package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

@Root(name = "ImpuestoLinea")
@Order(elements = {"Codigo","CodigoTarifa", "Tarifa","FactorIVA", "Monto","MontoExportacion", "Exoneracion"})
public class Impuesto {

    @Element(name = "Codigo")
    private String Codigo;

    @Element(name = "CodigoTarifa")
    private String CodigoTarifa;

    @Element(name = "Tarifa",required = false)
    private String Tarifa;

    @Element(name = "FactorIVA",required = false)
    private String FactorIVA;

    @Element(name = "Monto")
    private String Monto;

    @Element(name = "MontoExportacion",required = false)
    private String MontoExportacion;

    @Element(name = "Exoneracion", required = false)
    private Exoneracion Exoneracion;

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getCodigoTarifa() {
        return CodigoTarifa;
    }

    public void setCodigoTarifa(String codigo) {
        CodigoTarifa = codigo;
    }

    public String getTarifa() {
        return Tarifa;
    }

    public void setTarifa(String tarifa) {
        Tarifa = tarifa;
    }

    public String getFactorIVA() {
        return FactorIVA;
    }

    public void setFactorIVA(String tarifa) {
        FactorIVA = tarifa;
    }

    public String getMonto() {
        return Monto;
    }

    public void setMonto(String monto) {
        Monto = monto;
    }

    public String getMontoExportacion() {
        return MontoExportacion;
    }

    public void setMontoExportacion(String monto) {
        MontoExportacion = monto;
    }

    public Exoneracion getExoneracion() {
        return Exoneracion;
    }

    public void setExoneracion(Exoneracion exoneracion) {
        Exoneracion = exoneracion;
    }

    public boolean equals(Object obj){
        if(!(obj instanceof Impuesto)){
            return false;
        }


        Impuesto otro = (Impuesto)obj;
        if(!otro.getCodigo().equals(this.Codigo)
                || !otro.getCodigoTarifa().equals(this.CodigoTarifa)
                || !otro.getTarifa().equals(this.Tarifa)
                || !otro.getMonto().equals(this.Monto)
                || (otro.getFactorIVA() == null && this.FactorIVA != null || (otro.getFactorIVA() != null && !otro.getFactorIVA().equals(this.FactorIVA)))
                || (otro.getMontoExportacion() == null && this.MontoExportacion != null || (otro.getMontoExportacion() != null && !otro.getMontoExportacion().equals(this.MontoExportacion)))
                || (otro.getExoneracion() == null && this.Exoneracion != null || (otro.getExoneracion() != null && !otro.getExoneracion().equals(this.Exoneracion)))
        ){
            return false;
        }

        return true;
    }
}
