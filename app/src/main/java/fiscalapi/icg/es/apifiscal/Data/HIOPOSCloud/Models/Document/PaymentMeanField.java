package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PaymentMeanField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "PaymentMeanField", key = "Key", attribute = true, inline = true)
    private Map<String, String> PaymentMeanFields;

    @ElementList(entry = "CustomPaymentMeanFields",inline = true,required = false)
    private ArrayList<CustomPaymentMeanFields> CustomPaymentMeanFields;

    /*@Element(name = "CustomPaymentMeanFields",required = false)
    private CustomPaymentMeanFields CustomPaymentMeanFields;*/

    @Element(name = "Currency")
    private CurrencyField Currency;

    @ElementMap(entry = "CurrencyField", key = "Key", attribute = true, inline = true)
    private Map<String, String> CurrencyFields;

    public Map<String, String> getPaymentMeanFields() {
        return PaymentMeanFields;
    }

    public void setPaymentMeanFields(Map<String, String> paymentMeanFields) {
        PaymentMeanFields = paymentMeanFields;
    }

    public CurrencyField getCurrency() {
        return Currency;
    }

    public void setCurrency(CurrencyField currency) {
        Currency = currency;
    }

    public Map<String, String> getCurrencyFields() {
        return CurrencyFields;
    }

    public void setCurrencyFields(Map<String, String> currencyFields) {
        CurrencyFields = currencyFields;
    }

    public ArrayList<CustomPaymentMeanFields> getCustomPaymentMeanFields() {
        return CustomPaymentMeanFields;
    }

    public void setCustomPaymentMeanFields(ArrayList<CustomPaymentMeanFields> field) {
        this.CustomPaymentMeanFields = field;
    }

}
