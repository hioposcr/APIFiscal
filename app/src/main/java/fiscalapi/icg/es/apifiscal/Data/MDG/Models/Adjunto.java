package fiscalapi.icg.es.apifiscal.Data.MDG.Models;

public class Adjunto {
    private String nombreArchivo;
    private byte[] archivo;
    private String clave;

    public void setNombreArchivo(String n){
        nombreArchivo = n;
    }

    public String getNombreArchivo(){
        return nombreArchivo;
    }

    public void setArchivo(byte[] data){
        archivo = data;
    }

    public byte[] getArchivo(){
        return archivo;
    }

    public void setClave(String c){
        clave = c;
    }

    public String getClave(){
        return  clave;
    }

}
