package fiscalapi.icg.es.apifiscal.Data.MDG.Models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

import fiscalapi.icg.es.apifiscal.Data.Hacienda.ComprobanteHacienda;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.FacturaElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.NotaCreditoElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.NotaDebitoElectronica;
import fiscalapi.icg.es.apifiscal.Data.Hacienda.TiqueteHacienda;

@Root(name = "ComprobanteElectronico")
//@Order(elements = {"NumeroResolucion", "FechaResolucion", "CC","BCC"})
public class ComprobanteElectronico {
    /*@Element(name = "NumeroResolucion")
    private String resolutionNumber;

    @Element(name = "FechaResolucion")
    private String resolutionDate;*/

    @ElementList(name = "CC", required = false)
    private ArrayList<ReceptorCorreo> cc;

    @ElementList(name = "BCC", required = false)
    private ArrayList<ReceptorCorreo> bcc;

    @ElementList(name = "FacturasElectronicas",required = false)
    private ArrayList<FacturaElectronica> facturasElectronicas;

    @ElementList(name = "TiquetesElectronicos",required = false)
    private ArrayList<TiqueteHacienda> tiquetesElectronicos;

    @ElementList(name = "NotasCredito",required = false)
    private ArrayList<NotaCreditoElectronica> notaCreditoElectronica;

    @ElementList(name = "NotasDebito",required = false)
    private ArrayList<NotaDebitoElectronica> notaDebitoElectronica;

    /*public void setResolutionNumber(String rNumber){
        resolutionNumber = rNumber;
    }

    public String getResolutionNumber(){
        return resolutionNumber;
    }

    public void setResolutionDate(String date){
        resolutionDate = date;
    }

    public String getResolutionDate(){
        return resolutionDate;
    }*/

    public void setCC(ArrayList<ReceptorCorreo> cc){
        this.cc = cc;
    }

    public ArrayList<ReceptorCorreo> getCC(){
        return cc;
    }

    public void setBCC(ArrayList<ReceptorCorreo> bcc){
        this.bcc = bcc;
    }

    public ArrayList<ReceptorCorreo> getBCC(){
        return bcc;
    }

    public void setFacturasElectronicas(ArrayList<FacturaElectronica> facturasElectronicas){
        this.facturasElectronicas = facturasElectronicas;
    }

    public ArrayList<FacturaElectronica> getFacturasElectronicas(){
        return facturasElectronicas;
    }

    public void setTiquetesElectronicos(ArrayList<TiqueteHacienda> tiquetesElectronicos){
        this.tiquetesElectronicos = tiquetesElectronicos;
    }

    public ArrayList<TiqueteHacienda> getTiquetesElectronicos(){
        return tiquetesElectronicos;
    }

    public void setNotasCredito(ArrayList<NotaCreditoElectronica> notaCreditoElectronica){
        this.notaCreditoElectronica = notaCreditoElectronica;
    }

    public ArrayList<NotaCreditoElectronica> getNotasCredito(){
        return notaCreditoElectronica;
    }

    public void setNotasDebito(ArrayList<NotaDebitoElectronica> notaDebitoElectronica){
        this.notaDebitoElectronica = notaDebitoElectronica;
    }

    public ArrayList<NotaDebitoElectronica> getNotasDebito(){
        return notaDebitoElectronica;
    }
}
