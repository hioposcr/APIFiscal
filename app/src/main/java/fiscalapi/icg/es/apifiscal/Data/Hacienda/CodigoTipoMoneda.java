package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

@Root(name = "CodigoTipoMoneda")
@Order(elements = {"CodigoMoneda", "TipoCambio"})
public class CodigoTipoMoneda {
    @Element(name = "CodigoMoneda")
    private String CodigoMoneda;

    @Element(name = "TipoCambio")
    private String TipoCambio;

    public String getCodigoMoneda() {
        return CodigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        CodigoMoneda = codigoMoneda;
    }

    public String getTipoCambio() {
        return TipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        TipoCambio = tipoCambio;
    }
}
