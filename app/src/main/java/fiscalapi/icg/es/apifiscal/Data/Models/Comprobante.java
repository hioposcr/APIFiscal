package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Comprobante {
    @Id(autoincrement = true)
    private Long id;
    private String tipo_documento;
    private String hiopos_id;
    private String comprobante;
    private boolean ambiente;
    private int estado;
    private Date fechaEmision;

    @Generated(hash = 1767564296)
    public Comprobante(Long id, String tipo_documento, String hiopos_id,
            String comprobante, boolean ambiente, int estado, Date fechaEmision) {
        this.id = id;
        this.tipo_documento = tipo_documento;
        this.hiopos_id = hiopos_id;
        this.comprobante = comprobante;
        this.ambiente = ambiente;
        this.estado = estado;
        this.fechaEmision = fechaEmision;
    }

    @Generated(hash = 1607247442)
    public Comprobante() {
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTipo_documento() {
        return this.tipo_documento;
    }
    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }
    public String getSerie_hiopos() {
        return this.hiopos_id;
    }
    public void setSerie_hiopos(String serie_hiopos) {
        this.hiopos_id = serie_hiopos;
    }
    public boolean getAmbiente() {
        return this.ambiente;
    }
    public void setAmbiente(boolean ambiente) {
        this.ambiente = ambiente;
    }
    public int getEstado() {
        return this.estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public Date getFechaEmision() {
        return this.fechaEmision;
    }
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getComprobante(){
        return this.comprobante;
    }

    public void setComprobante(String comprobante){
        this.comprobante = comprobante;
    }

    public String getHiopos_id() {
        return this.hiopos_id;
    }

    public void setHiopos_id(String hiopos_id) {
        this.hiopos_id = hiopos_id;
    }
}
