package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomDocHeaderFields {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "CustomDocHeaderField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> CustomDocHeaderFields;

    public Map<String, String> getCustomDocHeaderFields() {
        return CustomDocHeaderFields;
    }

    public void setCustomDocHeaderFields(Map<String, String> headerFields) {
        this.CustomDocHeaderFields = headerFields;
    }
}
