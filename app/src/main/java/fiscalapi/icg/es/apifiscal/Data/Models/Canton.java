package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Canton {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String codigo;
    @NotNull
    private Long id_provincia;
    @Generated(hash = 1621999668)
    public Canton(Long id, @NotNull String nombre, @NotNull String codigo,
            @NotNull Long id_provincia) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.id_provincia = id_provincia;
    }
    @Generated(hash = 1363168946)
    public Canton() {
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
    public Long getId_provincia() {
        return this.id_provincia;
    }
    public void setId_provincia(Long id_provincia) {
        this.id_provincia = id_provincia;
    }
}
