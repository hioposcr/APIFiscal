package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Barrio {
    @Id(autoincrement = true)
    private Long id;
    private String nombre;
    @NotNull
    private String codigo;
    @NotNull
    private Long id_distrito;
    @Generated(hash = 1115128911)
    public Barrio(Long id, String nombre, @NotNull String codigo,
            @NotNull Long id_distrito) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.id_distrito = id_distrito;
    }
    @Generated(hash = 347752208)
    public Barrio() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCodigo() {
        return this.codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Long getId_distrito() {
        return this.id_distrito;
    }
    public void setId_distrito(Long id_distrito) {
        this.id_distrito = id_distrito;
    }
}
