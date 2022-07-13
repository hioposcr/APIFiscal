package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;

@Order(elements = {"Tipo", "Numero"})
public class Identificacion {

    @Element(name = "Tipo")
    private String Tipo;

    @Element(name = "Numero")
    private String Numero;

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }
}
