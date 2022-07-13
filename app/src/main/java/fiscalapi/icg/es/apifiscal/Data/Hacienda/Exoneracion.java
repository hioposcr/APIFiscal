package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;

import java.util.Date;

@Order(elements = {"TipoDocumento", "NumeroDocumento", "NombreInstitucion", "FechaEmision", "PorcentajeExoneracion", "MontoExoneracion"})
public class Exoneracion {

    @Element(name = "TipoDocumento")
    private String TipoDocumento;

    @Element(name = "NumeroDocumento")
    private String NumeroDocumento;

    @Element(name = "NombreInstitucion")
    private String NombreInstitucion;

    @Element(name = "FechaEmision")
    private String FechaEmision;

    @Element(name = "PorcentajeExoneracion")
    private String PorcentajeExoneracion;

    @Element(name = "MontoExoneracion")
    private String MontoExoneracion;

    /*@Element(name = "MontoImpuesto")
    private double MontoImpuesto;

    @Element(name = "PorcentajeCompra")
    private int PorcentajeCompra;*/

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        NumeroDocumento = numeroDocumento;
    }

    public String getNombreInstitucion() {
        return NombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        NombreInstitucion = nombreInstitucion;
    }

    public String getFechaEmision() {
        return FechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        FechaEmision = fechaEmision;
    }

    public String getMontoExoneracion() {
        return MontoExoneracion;
    }

    public void setMontoExoneracion(String montoImpuesto) {
        MontoExoneracion = montoImpuesto;
    }

    public String getPorcentajeExoneracion() {
        return PorcentajeExoneracion;
    }

    public void setPorcentajeExoneracion(String porcentaje) {
        PorcentajeExoneracion = porcentaje;
    }


    public boolean equals(Object obj){
        if(!(obj instanceof Exoneracion)){
            return false;
        }


        Exoneracion otro = (Exoneracion)obj;
        if(!otro.getFechaEmision().equals(this.FechaEmision)
                || !otro.getMontoExoneracion().equals(this.MontoExoneracion)
                || !otro.getNombreInstitucion().equals(this.NombreInstitucion)
                || !otro.getNumeroDocumento().equals(this.NumeroDocumento)
                || !otro.getPorcentajeExoneracion().equals(this.PorcentajeExoneracion)
                || !otro.getTipoDocumento().equals(this.TipoDocumento)
        ){
            return false;
        }

        return true;
    }
}
