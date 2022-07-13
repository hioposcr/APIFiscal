package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

@Root(name = "InformacionReferencia")
@Order(elements = {"TipoDoc", "Numero", "FechaEmision", "Codigo", "Razon"})
public class InformacionReferencia {

    @Element(name = "TipoDoc")
    private String TipoDoc;

    @Element(name = "Numero",required = false)
    private String Numero;

    @Element(name = "FechaEmision",required = false)
    private String FechaEmision;

    @Element(name = "Codigo",required = false)
    private String Codigo;

    @Element(name = "Razon",required = false)
    private String Razon;

    public String getTipoDoc() {
        return TipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        TipoDoc = tipoDoc;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getFechaEmision() {
        return FechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        FechaEmision = fechaEmision;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getRazon() {
        return Razon;
    }

    public void setRazon(String razon) {
        Razon = razon;
    }
}

