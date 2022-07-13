package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Provincia {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String nombre;

    @NotNull
    private String codigo;

    @Generated(hash = 173790483)
    public Provincia(Long id, @NotNull String nombre, @NotNull String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    @Generated(hash = 1282290553)
    public Provincia() {
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
}
