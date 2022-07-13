package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class ShopField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "ShopField", key = "Key", attribute = true, inline = true)
    private Map<String, String> shopFields;

    public Map<String, String> getShopFields() {
        return shopFields;
    }

    public void setShopFields(Map<String, String> shopFields) {
        this.shopFields = shopFields;
    }
}
