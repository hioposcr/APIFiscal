package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;

@Order(elements = {"Provincia", "Canton", "Distrito", "Barrio", "OtrasSenas","OtrasSenasExtranjero"})
public class Ubicacion {

    @Element(name = "Provincia")
    private String Provincia;

    @Element(name = "Canton")
    private String Canton;

    @Element(name = "Distrito")
    private String Distrito;

    @Element(name = "Barrio", required = false)
    private String Barrio;

    @Element(name = "OtrasSenas", required = false)
    private String OtrasSenas;

    @Element(name = "OtrasSenasExtranjero", required = false)
    private String OtrasSenasExtranjero;

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String provincia) {
        Provincia = provincia;
    }

    public String getCanton() {
        return Canton;
    }

    public void setCanton(String canton) {
        Canton = canton;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getBarrio() {
        return Barrio;
    }

    public void setBarrio(String barrio) {
        Barrio = barrio;
    }

    public String getOtrasSenas() {
        return OtrasSenas;
    }

    public void setOtrasSenas(String otrasSenas) {
        OtrasSenas = otrasSenas;
    }

    public String getOtrasSenasExtranjero() {
        return OtrasSenasExtranjero;
    }

    public void setOtrasSenasExtranjero(String otrasSenas) {
        OtrasSenasExtranjero = otrasSenas;
    }
}
