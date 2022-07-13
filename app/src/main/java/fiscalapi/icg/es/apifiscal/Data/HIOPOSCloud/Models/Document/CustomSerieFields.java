package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomSerieFields {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "CustomSerieField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customSerieFields;

    public Map<String, String> getCustomSerieFields() {
        return customSerieFields;
    }

    public void setCustomSerieFields(Map<String, String> fields) {
        this.customSerieFields = fields;
    }
}
