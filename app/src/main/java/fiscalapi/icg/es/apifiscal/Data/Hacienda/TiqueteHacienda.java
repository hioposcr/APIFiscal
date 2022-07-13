package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Root;

@Root(name = "TiqueteElectronico")
public class TiqueteHacienda extends ComprobanteHacienda {
    private String schemaLocation;

    public TiqueteHacienda() {
        setSchemaLocation("https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/tiqueteElectronico https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/TiqueteHacienda.xsd");
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }
}
