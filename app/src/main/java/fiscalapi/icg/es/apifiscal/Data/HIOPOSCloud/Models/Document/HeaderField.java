package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Map;

public class HeaderField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "HeaderField", key = "Key", attribute = true, inline = true)
    private Map<String, String> HeaderFields;

    @Element(name = "CustomDocHeaderFields",required = false)
    private CustomDocHeaderFields customDocHeaderFields;

    @Element(name="CustomSerieFields",required = false)
    private CustomSerieFields customSerieFields;

    @Element(name = "Discount")
    private DiscountField Discount;

    @Element(name = "ServiceCharge", required = false)
    private ServiceChargeField ServiceCharge;

    //@Element(name = "Company", required = false)
    //private CompanyFields Company;

    @Element(name = "Shop")
    private ShopField Shop;

    @Element(name = "Seller")
    private SellerField Seller;

    @Element(name = "Customer")
    private CustomerField Customer;

    @Element(name = "Provider", required = false)
    private ProviderField Provider;

    public Map<String, String> getHeaderFields() {
        return HeaderFields;
    }

    public void setHeaderFields(Map<String, String> headerFields) {
        this.HeaderFields = headerFields;
    }

    public CustomDocHeaderFields getCustomDocHeaderFields() {
        return customDocHeaderFields;
    }

    public void setCustomDocHeaderFields(CustomDocHeaderFields field) {
        customDocHeaderFields = field;
    }

    public CustomSerieFields getCustomSerieFields() {
        return customSerieFields;
    }

    public void setCustomSerieFields(CustomSerieFields fields) {
        this.customSerieFields = fields;
    }

    public DiscountField getDiscount() {
        return Discount;
    }

    public void setDiscount(DiscountField discount) {
        Discount = discount;
    }

    public ServiceChargeField getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(ServiceChargeField serviceCharge) {
        ServiceCharge = serviceCharge;
    }

    /*public CompanyFields getCompany() {
        return Company;
    }

    public void setCompany(CompanyFields shop) {
        Company = shop;
    }*/

    public ShopField getShop() {
        return Shop;
    }

    public void setShop(ShopField shop) {
        Shop = shop;
    }

    public SellerField getSeller() {
        return Seller;
    }

    public void setSeller(SellerField seller) {
        Seller = seller;
    }

    public CustomerField getCustomer() {
        return Customer;
    }

    public void setCustomer(CustomerField customer) {
        Customer = customer;
    }

    public ProviderField getProvider() {
        return Provider;
    }

    public void setProvider(ProviderField provider) {
        Provider = provider;
    }
}
