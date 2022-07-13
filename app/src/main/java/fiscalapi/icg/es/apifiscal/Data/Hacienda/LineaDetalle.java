package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "LineaDetalle")
@Order(elements = {"NumeroLinea","PartidaArancelaria","Codigo", "CodigoComercial", "Cantidad", "UnidadMedida", "UnidadMedidaComercial", "Detalle", "PrecioUnitario", "MontoTotal", "Descuentos", "SubTotal","BaseImponible", "Impuestos","ImpuestoNeto", "MontoTotalLinea"})
public class LineaDetalle {

    @Element(name = "NumeroLinea")
    private int NumeroLinea;

    @Element(name = "PartidaArancelaria",required = false)
    private String PartidaArancelaria;

    @Element(name = "Codigo", required = false)
    private String Codigo;

    @ElementList(name = "CodigoComercial", required = false)
    private ArrayList<CodigoComercial> CodigoComercial;

    @Element(name = "Cantidad")
    private double Cantidad;

    @Element(name = "UnidadMedida")
    private String UnidadMedida;

    @Element(name = "UnidadMedidaComercial", required = false)
    private String UnidadMedidaComercial;

    @Element(name = "Detalle",required = false)
    private String Detalle;

    @Element(name = "PrecioUnitario")
    private String PrecioUnitario;

    @Element(name = "MontoTotal")
    private String MontoTotal;

    @ElementList(name="Descuentos",required = false)
    private ArrayList<Descuento> Descuento;

    @Element(name = "SubTotal")
    private String SubTotal;

    @Element(name = "BaseImponible",required = false)
    private String BaseImponible;

    @ElementList(name = "Impuestos", required = false)
    private ArrayList<Impuesto> Impuesto;

    @Element(name = "ImpuestoNeto")
    private String ImpuestoNeto;

    @Element(name = "MontoTotalLinea")
    private String MontoTotalLinea;

    public int getNumeroLinea() {
        return NumeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        NumeroLinea = numeroLinea;
    }

    public String getPartidaArancelaria(){return PartidaArancelaria;}

    public void setPartidaArancelaria(String partida){PartidaArancelaria = partida;}

    public String getCodigo(){return Codigo;}

    public void setCodigo(String codigo){Codigo = codigo;}

    public ArrayList<CodigoComercial> getCodigoComercial() {
        return CodigoComercial;
    }

    public void setCodigoComercial(ArrayList<CodigoComercial> codigo) {
        CodigoComercial = codigo;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double cantidad) {
        Cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        UnidadMedida = unidadMedida;
    }

    public String getUnidadMedidaComercial() {
        return UnidadMedidaComercial;
    }

    public void setUnidadMedidaComercial(String unidadMedidaComercial) {
        UnidadMedidaComercial = unidadMedidaComercial;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    public String getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        PrecioUnitario = precioUnitario;
    }

    public String getMontoTotal() {
        return MontoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        MontoTotal = montoTotal;
    }

    public ArrayList<Descuento> getDescuento(){
        return Descuento;
    }

    public void setDescuento(ArrayList<Descuento> descuento){
        Descuento = descuento;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }

    public String getBaseImponible() {
        return BaseImponible;
    }

    public void setBaseImponible(String base) {
        BaseImponible = base;
    }


    public ArrayList<Impuesto> getImpuesto() {
        return Impuesto;
    }

    public void setImpuesto(ArrayList<Impuesto> impuesto) {
        Impuesto = impuesto;
    }

    public String getImpuestoNeto() {
        return ImpuestoNeto;
    }

    public void setImpuestoNeto(String montoTotal) {
        ImpuestoNeto = montoTotal;
    }

    public String getMontoTotalLinea() {
        return MontoTotalLinea;
    }

    public void setMontoTotalLinea(String montoTotalLinea) {
        MontoTotalLinea = montoTotalLinea;
    }
}
