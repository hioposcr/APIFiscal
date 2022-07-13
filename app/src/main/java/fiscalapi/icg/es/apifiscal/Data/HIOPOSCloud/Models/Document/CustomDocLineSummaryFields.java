package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class CustomDocLineSummaryFields {
    @Version(revision=1.1)
    private double version;
    @ElementMap(entry = "CustomDocLineSummaryField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> customDocLineSummaryFields;

    public Map<String, String> getCustomDocLineSummaryFields() {
        return customDocLineSummaryFields;
    }

    public void setCustomDocLineSummaryFields(Map<String, String> fields) {
        this.customDocLineSummaryFields = fields;
    }
}
