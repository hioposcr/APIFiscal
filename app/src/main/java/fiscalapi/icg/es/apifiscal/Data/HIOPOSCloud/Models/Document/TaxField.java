package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class TaxField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "TaxField", key = "Key", attribute = true, inline = true)
    private Map<String, String> TaxFields;

    @Element(name = "CustomDocTaxFields",required = false)
    private CustomDocTaxFields customDocTaxFields;

    @Element(name = "CustomTaxFields",required = false)
    private CustomTaxFields customTaxFields;

    public Map<String, String> getTaxFields() {
        return TaxFields;
    }

    public void setTaxFields(Map<String, String> taxFields) {
        TaxFields = taxFields;
    }

    public CustomDocTaxFields getCustomDocTaxFields() {
        return customDocTaxFields;
    }

    public void setCustomDocTaxFields(CustomDocTaxFields field) {
        this.customDocTaxFields = field;
    }

    public CustomTaxFields getCustomTaxFields() {
        return customTaxFields;
    }

    public void setCustomTaxFields(CustomTaxFields field) {
        this.customTaxFields = field;
    }
}
