package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.SaleReturn;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "AdditionalField")
public class AddicionalFieldReturn {

    @Attribute(name = "Key")
    private String key;

    @Text
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
