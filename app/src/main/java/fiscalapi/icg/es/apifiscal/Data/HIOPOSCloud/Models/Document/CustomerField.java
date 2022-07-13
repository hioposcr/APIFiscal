package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomerField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "CustomerField", key = "Key", attribute = true, inline = true)
    private Map<String, String> customerFields;

    @Element(name = "CustomCustomerFields", required = false)
    private CustomCustomerFields customCustomerFields;

    public Map<String, String> getCustomerFields() {
        return customerFields;
    }

    public void setCustomerFields(Map<String, String> customerFields) {
        this.customerFields = customerFields;
    }

    public CustomCustomerFields getCustomCustomerFields(){
        return this.customCustomerFields;
    }

    public void setCustomCustomerFields(CustomCustomerFields fields){
        this.customCustomerFields = fields;
    }

}
