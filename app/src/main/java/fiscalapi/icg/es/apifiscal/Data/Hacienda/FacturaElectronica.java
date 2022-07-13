package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Root;

@Root(name = "FacturaElectronica")
public class FacturaElectronica extends ComprobanteHacienda {

    private String schemaLocation;

    public FacturaElectronica() {
        setSchemaLocation("https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/facturaElectronica https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/FacturaElectronica.xsd");
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }
}
