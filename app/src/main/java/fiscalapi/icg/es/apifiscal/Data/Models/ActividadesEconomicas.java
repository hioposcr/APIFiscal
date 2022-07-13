package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ActividadesEconomicas {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String codigo;
    @NotNull
    private String nombre;
    @NotNull
    private Boolean estado;
    @Generated(hash = 213446629)
    public ActividadesEconomicas(Long id, @NotNull String codigo,
            @NotNull String nombre, @NotNull Boolean estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }
    @Generated(hash = 705288509)
    public ActividadesEconomicas() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCodigo() {
        if(this.codigo.length() == 5) {
            this.codigo = "0" + this.codigo;
        }
        return this.codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Boolean getEstado() {
        return this.estado;
    }
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
