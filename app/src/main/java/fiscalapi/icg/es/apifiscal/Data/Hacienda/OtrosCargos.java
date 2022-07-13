package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import androidx.core.app.NavUtils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

@Root(name = "OtroCargo")
@Order(elements = {"TipoDocumento", "NumeroIdentidadTercero", "NombreTercero", "Detalle", "Porcentaje", "MontoCargo"})
public class OtrosCargos {
    @Element(name = "TipoDocumento")
    private String  TipoDocumento;

    @Element(name = "NumeroIdentidadTercero",required = false)
    private String  NumeroIdentidadTercero;

    @Element(name = "NombreTercero",required = false)
    private String NombreTercero;

    @Element(name = "Detalle")
    private String Detalle;

    @Element(name = "Porcentaje", required = false)
    private String Porcentaje;

    @Element(name = "MontoCargo")
    private String MontoCargo;


    public String getTipoDocumento (){
        return TipoDocumento;
    }

    public void setTipoDocumento (String value){
        TipoDocumento = value;
    }

    public String getNumeroIdentidadTercero (){
        return NumeroIdentidadTercero;
    }

    public void setNumeroIdentidadTercero (String value){
        NumeroIdentidadTercero = value;
    }

    public String getNombreTercero (){
        return NombreTercero;
    }

    public void setNombreTercero (String value){
        NombreTercero = value;
    }

    public String getDetalle (){
        return Detalle;
    }

    public void setDetalle (String value){
        Detalle = value;
    }

    public String getPorcentaje (){
        return Porcentaje;
    }

    public void setPorcentaje (String value){
        Porcentaje = value;
    }

    public String getMontoCargo (){
        return MontoCargo;
    }

    public void setMontoCargo (String value){
        MontoCargo = value;
    }
}
