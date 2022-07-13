package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomDocLineFields {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "customDocLineField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customDocLineFields;

    public Map<String, String> getCustomDocLineFields() {
        return customDocLineFields;
    }

    public void setCustomDocLineFields(Map<String, String> fields) {
        this.customDocLineFields = fields;
    }
}
