package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Version;

import java.util.ArrayList;

@Order(elements = {"CodigoActividad","Clave", "NumeroConsecutivo", "FechaEmision", "Emisor", "Receptor", "CondicionVenta", "PlazoCredito", "MediosPago", "DetalleServicio", "OtrosCargos","ResumenFactura", "InformacionesReferencia", "Otros"})
public abstract class ComprobanteHacienda {
    @Version(revision=1.1)
    private double version;

    @Element(name = "CodigoActividad")
    private String CodigoActividad;

    @Element(name = "Clave")
    private String Clave;

    @Element(name = "NumeroConsecutivo")
    private String NumeroConsecutivo;

    @Element(name = "FechaEmision")
    private String FechaEmision;

    @Element(name = "Emisor")
    private EmisorReceptor Emisor;

    @Element(name = "Receptor", required = false)
    private EmisorReceptor Receptor;

    @Element(name = "CondicionVenta")
    private String CondicionVenta;

    @Element(name = "PlazoCredito", required = false)
    private String PlazoCredito;

    @ElementList(name = "MediosPago")
    private ArrayList<MedioPago> MedioPago;

    @ElementList(name = "DetalleServicio",required = false)
    private ArrayList<LineaDetalle> LineaDetalle;

    @ElementList(name = "OtrosCargos", required = false)
    private ArrayList<OtrosCargos> OtrosCargos;

    @Element(name = "ResumenFactura")
    private ResumenFactura ResumenFactura;

    @ElementList(name = "InformacionesReferencia", required = false)
    private ArrayList<InformacionReferencia> InformacionReferencia;

    @Element(name = "Otros", required = false)
    private Otros Otros;

    public String getCodigoActividad() {
        return CodigoActividad;
    }

    public void setCodigoActividad(String codigo) {
        CodigoActividad = codigo;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getNumeroConsecutivo() {
        return NumeroConsecutivo;
    }

    public void setNumeroConsecutivo(String numeroConsecutivo) {
        NumeroConsecutivo = numeroConsecutivo;
    }

    public String getFechaEmision() {
        return FechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        FechaEmision = fechaEmision;
    }

    public EmisorReceptor getEmisor() {
        return Emisor;
    }

    public void setEmisor(EmisorReceptor emisor) {
        Emisor = emisor;
    }

    public EmisorReceptor getReceptor() {
        return Receptor;
    }

    public void setReceptor(EmisorReceptor receptor) {
        Receptor = receptor;
    }

    public String getCondicionVenta() {
        return CondicionVenta;
    }

    public void setCondicionVenta(String condicionVenta) {
        CondicionVenta = condicionVenta;
    }

    public String getPlazoCredito() {
        return PlazoCredito;
    }

    public void setPlazoCredito(String plazoCredito) {
        PlazoCredito = plazoCredito;
    }

    public ArrayList<MedioPago> getMedioPago() {
        return MedioPago;
    }

    public void setMedioPago(ArrayList<MedioPago> medioPago) {
        MedioPago = medioPago;
    }

    public ArrayList<LineaDetalle> getLineaDetalle() {
        return LineaDetalle;
    }

    public void setLineaDetalle(ArrayList<LineaDetalle> lineaDetalle) {
        LineaDetalle = lineaDetalle;
    }

    public ArrayList<OtrosCargos> getOtrosCargos() {
        return OtrosCargos;
    }

    public void setOtrosCargos(ArrayList<OtrosCargos> codigo) {
        OtrosCargos = codigo;
    }

    public fiscalapi.icg.es.apifiscal.Data.Hacienda.ResumenFactura getResumenFactura() {
        return ResumenFactura;
    }

    public void setResumenFactura(ResumenFactura resumenFactura) {
        ResumenFactura = resumenFactura;
    }

    public ArrayList<InformacionReferencia> getInformacionReferencia() {
        return InformacionReferencia;
    }

    public void setInformacionReferencia(ArrayList<InformacionReferencia> informacionReferencia) {
        InformacionReferencia = informacionReferencia;
    }

    public Otros getOtros() {
        return Otros;
    }

    public void setOtros(Otros otros) {
        Otros = otros;
    }
}


