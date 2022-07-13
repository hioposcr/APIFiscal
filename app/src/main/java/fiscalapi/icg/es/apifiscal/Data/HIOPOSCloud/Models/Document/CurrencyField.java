package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;

import java.util.Map;

public class CurrencyField {

    @ElementMap(entry = "CurrencyField", key = "Key", attribute = true, inline = true,required = false)
    private Map<String, String> CurrencyFields;

    @Element(name = "CustomCurrencyFields",required = false)
    private CustomCurrencyFields customCurrencyFields;

    public Map<String, String> getCurrencyFields() {
        return CurrencyFields;
    }

    public void setCurrencyFields(Map<String, String> currencyFields) {
        CurrencyFields = currencyFields;
    }

    public CustomCurrencyFields getCustomCurrencyFields() {
        return customCurrencyFields;
    }

    public void setCustomCurrencyFields(CustomCurrencyFields field) {
        this.customCurrencyFields = field;
    }
}
