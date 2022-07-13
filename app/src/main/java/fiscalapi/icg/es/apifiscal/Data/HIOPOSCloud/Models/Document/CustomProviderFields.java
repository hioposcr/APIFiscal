package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomProviderFields {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "CustomProviderField", key = "Key", attribute = true, inline = true)
    private Map<String, String> customProviderFields;

    public Map<String, String> getCustomProviderFields() {
        return customProviderFields;
    }

    public void setCustomProviderField(Map<String, String> fields) {
        this.customProviderFields = fields;
    }
}
