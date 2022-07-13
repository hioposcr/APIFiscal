package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TipoDocumentoExoneraciones {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String codigo;
    @NotNull
    private String descripcion;
    @Generated(hash = 142271123)
    public TipoDocumentoExoneraciones(Long id, @NotNull String codigo,
            @NotNull String descripcion) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    @Generated(hash = 2037446516)
    public TipoDocumentoExoneraciones() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCodigo() {
        return this.codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
