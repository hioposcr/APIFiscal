package fiscalapi.icg.es.apifiscal.Data.MDG.Models;

import java.util.ArrayList;

public class ApiEmisionRequest {
    //this variables must be Upper case
    private String Xml;
    private int FormatoPDF;
    private int Ambiente;
    private String EjecucionId;

    private ArrayList<Adjunto> adjuntos;


    public String Xml(){
        return Xml;
    }

    public void Xml(String xml){
        this.Xml = xml;
    }

    public int Ambiente(){
        return Ambiente;
    }

    public void Ambiente(int i){
        Ambiente = i;
    }

    public int FormatoPDF(){
        return FormatoPDF;
    }

    public void FormatoPDF(int i){
        FormatoPDF = i;
    }

    public ArrayList<Adjunto> getAdjuntos(){
        return adjuntos;
    }

    public void setAdjuntos(ArrayList<Adjunto> a){
        adjuntos = a;
    }

    public String EjectucionId(){
        return EjecucionId;
    }

    public void EjectucionId(String xml){
        this.EjecucionId = xml;
    }

}
