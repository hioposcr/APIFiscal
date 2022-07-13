package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class ProviderField {

    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "ProviderField", key = "Key", attribute = true, inline = true)
    private Map<String, String> providerFields;

    @Element(name = "CustomerProviderFields",required = false)
    private CustomProviderFields customProviderFields;

    public Map<String, String> getProviderFields() {
        return providerFields;
    }

    public void setProviderFields(Map<String, String> providerFields) {
        this.providerFields = providerFields;
    }

    public CustomProviderFields getCustomProviderFields(){
        return customProviderFields;
    }

    public void setCustomProviderFields(CustomProviderFields fields){
        this.customProviderFields = fields;
    }
}
