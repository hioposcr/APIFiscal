package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Root;

@Root(name = "NotaCreditoElectronica")
public class NotaCreditoElectronica extends ComprobanteHacienda {

    private String schemaLocation;

    public NotaCreditoElectronica() {
        setSchemaLocation("https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaCreditoElectronica https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/NotaCreditoElectronica.xsd");
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }
}
