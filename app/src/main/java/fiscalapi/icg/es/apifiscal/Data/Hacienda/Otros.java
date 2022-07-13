package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

@Root(name = "Otros")
@Order(elements = {"OtroTexto"})
public class Otros {

    @Element(name = "OtroTexto")
    private String OtroTexto;

}
