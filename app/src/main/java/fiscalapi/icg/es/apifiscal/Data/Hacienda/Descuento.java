package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

@Root(name = "Descuento")
@Order(elements = {"MontoDescuento", "NaturalezaDescuento"})
public class Descuento {

    @Element(name = "MontoDescuento", required = false)
    private String MontoDescuento;

    @Element(name = "NaturalezaDescuento", required = false)
    private String NaturalezaDescuento;

    public String getMontoDescuento() {
        return MontoDescuento;
    }

    public void setMontoDescuento(String montoDescuento) {

        MontoDescuento = montoDescuento;
    }

    public String getNaturalezaDescuento() {
        return NaturalezaDescuento;
    }

    public void setNaturalezaDescuento(String naturalezaDescuento) {
        NaturalezaDescuento = naturalezaDescuento;
    }
}
