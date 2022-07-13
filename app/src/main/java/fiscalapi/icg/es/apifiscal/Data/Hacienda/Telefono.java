package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;

@Order(elements = {"CodigoPais", "NumTelefono"})
public class Telefono {

    @Element(name = "CodigoPais")
    private String CodigoPais;

    @Element(name = "NumTelefono")
    private String NumTelefono;

    public String getCodigoPais() {
        return CodigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        CodigoPais = codigoPais;
    }

    public String getNumTelefono() {
        return NumTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        NumTelefono = numTelefono;
    }
}
