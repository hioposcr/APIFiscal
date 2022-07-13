package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Version;

import java.util.Collection;

public class HioposSale {
    @Version(revision=1.1)
    private double version;

    @Element(name = "Header")
    private HeaderField Header;

    @ElementList(name = "Lines", required = false)
    private Collection<LineField> Lines;

    @ElementList(name = "Taxes", required = false)
    private Collection<TaxField> Taxes;

    @ElementList(name = "PaymentMeans", required = false)
    private Collection<PaymentMeanField> PaymentMeans;

    @Element(name = "AdditionalFields", required = false)
    private AdditionalField AdditionalFields;

    public Collection<LineField> getLines() {
        return Lines;
    }

    public void setLines(Collection<LineField> lines) {
        this.Lines = lines;
    }

    public Collection<TaxField> getTaxes() {
        return Taxes;
    }

    public void setTaxes(Collection<TaxField> taxes) {
        Taxes = taxes;
    }

    public Collection<PaymentMeanField> getPaymentMeans() {
        return PaymentMeans;
    }

    public HeaderField getHeader() {
        return Header;
    }

    public void setHeader(HeaderField header) {
        Header = header;
    }

    public AdditionalField getAdditionalFields() {
        return AdditionalFields;
    }

    public void setPaymentMeans(Collection<PaymentMeanField> paymentMeans) {
        PaymentMeans = paymentMeans;
    }

    public void setAdditionalFields(AdditionalField additionalFields) {
        AdditionalFields = additionalFields;
    }
}
