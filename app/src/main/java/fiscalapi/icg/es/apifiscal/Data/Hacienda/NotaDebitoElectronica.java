package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Root;

@Root(name = "NotaDebitoElectronica")
public class NotaDebitoElectronica extends ComprobanteHacienda {
    private String schemaLocation;

    public NotaDebitoElectronica() {
        setSchemaLocation("https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/notaDebitoElectronica https://tribunet.hacienda.go.cr/docs/esquemas/2017/v4.2/NotaDebitoElectronica.xsd");
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }
}
