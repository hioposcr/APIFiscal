package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

@Root(name = "CodigoComercial")
@Order(elements = {"Tipo", "Codigo"})
public class CodigoComercial {

    @Element(name = "Tipo")
    private String Tipo;

    @Element(name = "Codigo", required = false)
    private String Codigo;

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }
}
