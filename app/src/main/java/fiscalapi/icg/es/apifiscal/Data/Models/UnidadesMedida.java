package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UnidadesMedida {

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String simbolo;
    @NotNull
    private String descripcion;
    @Generated(hash = 1710791375)
    public UnidadesMedida(Long id, @NotNull String simbolo,
            @NotNull String descripcion) {
        this.id = id;
        this.simbolo = simbolo;
        this.descripcion = descripcion;
    }
    @Generated(hash = 45199788)
    public UnidadesMedida() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSimbolo() {
        return this.simbolo;
    }
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
