package fiscalapi.icg.es.apifiscal.Data.MDG.Models;

import java.util.ArrayList;
import java.util.List;

public class ApiEmisionResponse {
    //this variables must be Upper case
    private boolean Exitoso;
    private ArrayList<String> Errores;
    private String Mensaje;

    public void setExitoso(boolean i){
        Exitoso = i;
    }

    public boolean getExitoso(){
        return  Exitoso;
    }

    public void setErrores(ArrayList<String> e){
        Errores = e;
    }

    public ArrayList<String> getErrores(){
        return Errores;
    }

    public void setMensaje(String m){
        Mensaje = m;
    }

    public String getMensaje(){
        return Mensaje;
    }
}
