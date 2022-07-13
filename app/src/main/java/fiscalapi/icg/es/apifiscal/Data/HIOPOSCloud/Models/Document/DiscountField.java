package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class DiscountField {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "DiscountField", key = "Key", attribute = true, inline = true)
    private Map<String, String> DiscountFields;

    public Map<String, String> getDiscountFields() {
        return DiscountFields;
    }

    public void setDiscountFields(Map<String, String> discountFields) {
        DiscountFields = discountFields;
    }
}
