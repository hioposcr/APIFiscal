package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class SellerField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "SellerField", key = "Key", attribute = true, inline = true)
    private Map<String, String> sellerFields;

    public Map<String, String> getSellerFields() {
        return sellerFields;
    }

    public void setSellerFields(Map<String, String> sellerFields) {
        this.sellerFields = sellerFields;
    }
}
