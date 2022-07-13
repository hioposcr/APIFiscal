package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class LineTaxField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "LineTaxField", key = "Key", attribute = true, inline = true)
    private Map<String, String> LineTaxFields;

    @Element(name = "CustomDocLineTaxFields",required = false)
    private CustomDocLineTaxFields customDocLineTaxFields;

    @Element(name = "CustomTaxFields",required = false)
    private CustomTaxFields customTaxFields;

    public Map<String, String> getLineTaxFields() {
        return LineTaxFields;
    }

    public void setLineTaxFields(Map<String, String> lineTaxFields) {
        LineTaxFields = lineTaxFields;
    }

    public CustomDocLineTaxFields geCustomDocLineTaxFields() {
        return customDocLineTaxFields;
    }

    public void setCustomDocLineTaxFields(CustomDocLineTaxFields field) {
        this.customDocLineTaxFields = field;
    }

    public CustomTaxFields getCustomTaxFields() {
        return customTaxFields;
    }

    public void setCustomTaxFields(CustomTaxFields field) {
        this.customTaxFields = field;
    }
}
