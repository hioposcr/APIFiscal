package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomPaymentMeanFields {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "CustomPaymentMeanField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customPaymentMeanField;

    public Map<String, String> getFields() {
        return customPaymentMeanField;
    }

    public void setFields(Map<String, String> fields) {
        this.customPaymentMeanField = fields;
    }
}
