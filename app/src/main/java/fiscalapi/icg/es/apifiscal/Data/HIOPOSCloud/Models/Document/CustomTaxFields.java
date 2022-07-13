package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomTaxFields {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "CustomTaxField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customTaxField;

    public Map<String, String> getCustomTaxFields() {
        return customTaxField;
    }

    public void setCustomTaxFields(Map<String, String> fields) {
        this.customTaxField = fields;
    }
}
