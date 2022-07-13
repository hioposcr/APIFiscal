package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Distrito {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String codigo;
    @NotNull
    private Long id_canton;
    @Generated(hash = 613497058)
    public Distrito(Long id, @NotNull String nombre, @NotNull String codigo,
            @NotNull Long id_canton) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.id_canton = id_canton;
    }
    @Generated(hash = 729953415)
    public Distrito() {
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
    public Long getId_canton() {
        return this.id_canton;
    }
    public void setId_canton(Long id_canton) {
        this.id_canton = id_canton;
    }
}
