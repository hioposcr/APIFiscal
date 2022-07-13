package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomCustomerFields {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "CustomCustomerField", key = "Key", attribute = true, inline = true)
    private Map<String, String> customCustomerFields;

    public Map<String, String> getCustomCustomerFields() {
        return customCustomerFields;
    }

    public void setCustomCustomerFields(Map<String, String> fields) {
        this.customCustomerFields = fields;
    }
}
