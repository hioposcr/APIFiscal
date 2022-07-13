package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomDocLineTaxFields {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "customDocLineTaxField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customDocLineTaxField;

    public Map<String, String> getCustomDocLineTaxFields() {
        return customDocLineTaxField;
    }

    public void setCustomDocLineTaxFields(Map<String, String> fields) {
        this.customDocLineTaxField = fields;
    }
}
