package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

@Root(name = "ResumenFactura")
@Order(elements = {"CodigoTipoMoneda", "TotalServGravados", "TotalServExentos", "TotalServExonerado","TotalMercanciasGravadas", "TotalMercanciasExentas","TotalMercExonerada", "TotalGravado", "TotalExento","TotalExonerado","TotalVenta", "TotalDescuentos", "TotalVentaNeta", "TotalImpuesto","TotalIVADevuelto","TotalOtrosCargos", "TotalComprobante"})
public class ResumenFactura {

    @Element(name = "CodigoTipoMoneda",required = false)
    private CodigoTipoMoneda CodigoTipoMoneda;

    @Element(name = "TotalServGravados", required = false)
    private String TotalServGravados;

    @Element(name = "TotalServExentos", required = false)
    private String TotalServExentos;

    @Element(name = "TotalServExonerado", required = false)
    private String TotalServExonerado ;

    @Element(name = "TotalMercanciasGravadas", required = false)
    private String TotalMercanciasGravadas;

    @Element(name = "TotalMercanciasExentas", required = false)
    private String TotalMercanciasExentas;

    @Element(name = "TotalMercExonerada", required = false)
    private String TotalMercExonerada;

    @Element(name = "TotalGravado", required = false)
    private String TotalGravado;

    @Element(name = "TotalExento", required = false)
    private String TotalExento;

    @Element(name = "TotalExonerado", required = false)
    private String TotalExonerado;

    @Element(name = "TotalVenta")
    private String TotalVenta;

    @Element(name = "TotalDescuentos", required = false)
    private String TotalDescuentos;

    @Element(name = "TotalVentaNeta")
    private String TotalVentaNeta;

    @Element(name = "TotalImpuesto", required = false)
    private String TotalImpuesto;

    @Element(name = "TotalIVADevuelto", required = false)
    private String TotalIVADevuelto;

    @Element(name = "TotalOtrosCargos", required = false)
    private String TotalOtrosCargos;

    @Element(name = "TotalComprobante")
    private String TotalComprobante;


    public CodigoTipoMoneda getCodigoTipoMoneda() {
        return CodigoTipoMoneda;
    }

    public void setCodigoTipoMoneda(CodigoTipoMoneda codigoMoneda) {
        CodigoTipoMoneda = codigoMoneda;
    }

    public String getTotalServGravados() {
        return TotalServGravados;
    }

    public void setTotalServGravados(String totalServGravados) {
        TotalServGravados = totalServGravados;
    }

    public String getTotalServExentos() {
        return TotalServExentos;
    }

    public void setTotalServExentos(String totalServExentos) {
        TotalServExentos = totalServExentos;
    }

    public String getTotalMercanciasGravadas() {
        return TotalMercanciasGravadas;
    }

    public void setTotalMercanciasGravadas(String totalMercanciasGravadas) {
        TotalMercanciasGravadas = totalMercanciasGravadas;
    }

    public String getTotalMercanciasExentas() {
        return TotalMercanciasExentas;
    }

    public void setTotalMercanciasExentas(String totalMercanciasExentas) {
        TotalMercanciasExentas = totalMercanciasExentas;
    }

    public String getTotalGravado() {
        return TotalGravado;
    }

    public void setTotalGravado(String totalGravado) {
        TotalGravado = totalGravado;
    }

    public String getTotalExento() {
        return TotalExento;
    }

    public void setTotalExento(String totalExento) {
        TotalExento = totalExento;
    }

    public String getTotalServExonerado() {
        return TotalServExonerado;
    }

    public void setTotalServExonerado(String total) {
        TotalServExonerado = total;
    }

    public String getTotalMercExonerada() {
        return TotalMercExonerada;
    }

    public void setTotalMercExonerada(String total) {
        TotalMercExonerada = total;
    }

    public String getTotalSExonerado() {
        return TotalExonerado;
    }

    public void setTotalExonerado(String total) {
        TotalExonerado = total;
    }

    public String getTotalIVADevuelto() {
        return TotalIVADevuelto;
    }

    public void setTotalIVADevuelto(String total) {
        TotalIVADevuelto = total;
    }

    public String getTotalOtrosCargos() {
        return TotalOtrosCargos;
    }

    public void setTotalOtrosCargos(String total) {
        TotalOtrosCargos = total;
    }

    public String getTotalVenta() {
        return TotalVenta;
    }

    public void setTotalVenta(String totalVenta) {
        TotalVenta = totalVenta;
    }

    public String getTotalDescuentos() {
        return TotalDescuentos;
    }

    public void setTotalDescuentos(String totalDescuentos) {
        TotalDescuentos = totalDescuentos;
    }

    public String getTotalVentaNeta() {
        return TotalVentaNeta;
    }

    public void setTotalVentaNeta(String totalVentaNeta) {
        TotalVentaNeta = totalVentaNeta;
    }

    public String getTotalImpuesto() {
        return TotalImpuesto;
    }

    public void setTotalImpuesto(String totalImpuesto) {
        TotalImpuesto = totalImpuesto;
    }

    public String getTotalComprobante() {
        return TotalComprobante;
    }

    public void setTotalComprobante(String totalComprobante) {
        TotalComprobante = totalComprobante;
    }
}
