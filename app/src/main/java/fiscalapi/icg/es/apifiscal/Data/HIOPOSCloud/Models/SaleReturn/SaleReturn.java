package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.SaleReturn;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "SaleResult")
public class SaleReturn {

    @ElementList(inline = true)
    private ArrayList<SaleReturnField> fieldList;

    @ElementList(name = "AdditionalFields")
    private ArrayList<AddicionalFieldReturn> addicionalFieldReturns;

    public ArrayList<SaleReturnField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(ArrayList<SaleReturnField> fieldList) {
        this.fieldList = fieldList;
    }

    public ArrayList<AddicionalFieldReturn> getAddicionalFieldReturns() {
        return addicionalFieldReturns;
    }

    public void setAddicionalFieldReturns(ArrayList<AddicionalFieldReturn> addicionalFieldReturns) {
        this.addicionalFieldReturns = addicionalFieldReturns;
    }
}
