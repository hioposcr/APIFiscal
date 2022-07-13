package fiscalapi.icg.es.apifiscal.Data.MDG.Models;

import org.simpleframework.xml.Element;

public class ReceptorCorreo {

    @Element(name = "Correo")
    private String mail;

    public String getMail(){
        return  mail;
    }

    public void setMail(String e){
        mail = e;
    }
}
