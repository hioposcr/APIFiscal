package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class AdditionalField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "AdditionalField", key = "Key", attribute = true, inline = true)
    private Map<String, String> AdditionalField;

    public Map<String, String> getAdditionalField() {
        return AdditionalField;
    }

    public void setAdditionalField(Map<String, String> additionalField) {
        AdditionalField = additionalField;
    }
}
