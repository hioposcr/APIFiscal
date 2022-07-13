package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomDocTaxFields {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "CustomDocTaxField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customDocTaxFields;

    public Map<String, String> getCustomDocTaxFields() {
        return customDocTaxFields;
    }

    public void setCustomDocTaxFields(Map<String, String> fields) {
        this.customDocTaxFields = fields;
    }
}
