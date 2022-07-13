package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class ServiceChargeField {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "ServiceChargeField", key = "Key", attribute = true, inline = true)
    private Map<String, String> serviceChargeFields;

    public Map<String, String> getServiceChargeFields() {
        return serviceChargeFields;
    }

    public void setServiceChargeFields(Map<String, String> serviceChargeFields) {
        this.serviceChargeFields = serviceChargeFields;
    }
}
