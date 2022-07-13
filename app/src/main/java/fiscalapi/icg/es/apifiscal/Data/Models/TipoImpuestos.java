package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TipoImpuestos {

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String codigo;
    @NotNull
    private String descripcion;
    @NotNull
    private Boolean es_excepcion;
    @Generated(hash = 1351145955)
    public TipoImpuestos(Long id, @NotNull String codigo,
            @NotNull String descripcion, @NotNull Boolean es_excepcion) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.es_excepcion = es_excepcion;
    }
    @Generated(hash = 2014556385)
    public TipoImpuestos() {
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
    public Boolean getEs_excepcion() {
        return this.es_excepcion;
    }
    public void setEs_excepcion(Boolean es_excepcion) {
        this.es_excepcion = es_excepcion;
    }
}
