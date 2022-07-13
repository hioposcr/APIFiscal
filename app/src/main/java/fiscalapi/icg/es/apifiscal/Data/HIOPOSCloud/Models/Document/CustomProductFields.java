package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomProductFields {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "CustomProductField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customProductFields;

    public Map<String, String> getCustomProductFields() {
        return customProductFields;
    }

    public void setCustomProductFields(Map<String, String> fields) {
        this.customProductFields = fields;
    }
}
