package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "MedioPago")
public class MedioPago {

    @Element(name = "Medio")
    private String Medio;

    public String getMedio() {
        return Medio;
    }

    public void setMedio(String medio) {
        Medio = medio;
    }
}
