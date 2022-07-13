package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomCurrencyFields {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "CustomCurrencyField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customCurrencyFields;

    public Map<String, String> getCustomCurrencyFields() {
        return customCurrencyFields;
    }

    public void CustomCurrencyFields(Map<String, String> fields) {
        this.customCurrencyFields = fields;
    }
}
