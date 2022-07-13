package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;


import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ComprobanteErroneo {
    @Id(autoincrement = true)
    private Long id;

    private Long id_comprobante;
    private String hiopos_id;
    private boolean ambiente;
    private String error;

    @Generated(hash = 1289291012)
    public ComprobanteErroneo(Long id, Long id_comprobante, String hiopos_id, boolean ambiente, String error) {
        this.id = id;
        this.id_comprobante = id_comprobante;
        this.hiopos_id = hiopos_id;
        this.ambiente = ambiente;
        this.error = error;
    }

    @Generated(hash = 482734162)
    public ComprobanteErroneo() {
    }

    public Long getId_comprobante(){
        return id_comprobante;
    }

    public void setId_comprobante(Long id){
        this.id_comprobante = id;
    }

    public String getHiopos_id(){
        return this.hiopos_id;
    }

    public void setHiopos_id(String hiopos_id){
        this.hiopos_id = hiopos_id;
    }

    public boolean getAmbiente(){
        return this.ambiente;
    }

    public void setAmbiente(boolean ambiente){
        this.ambiente = ambiente;
    }

    public String getError(){
        return this.error;
    }

    public void setError(String e){
        this.error = e;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
