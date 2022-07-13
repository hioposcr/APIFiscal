package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CodigoMoneda {

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String nombre_moneda;
    private String codigo_moneda;
    @NotNull
    private String pais;
    @Generated(hash = 233700664)
    public CodigoMoneda(Long id, @NotNull String nombre_moneda,
            String codigo_moneda, @NotNull String pais) {
        this.id = id;
        this.nombre_moneda = nombre_moneda;
        this.codigo_moneda = codigo_moneda;
        this.pais = pais;
    }
    @Generated(hash = 149980636)
    public CodigoMoneda() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre_moneda() {
        return this.nombre_moneda;
    }
    public void setNombre_moneda(String nombre_moneda) {
        this.nombre_moneda = nombre_moneda;
    }
    public String getCodigo_moneda() {
        return this.codigo_moneda;
    }
    public void setCodigo_moneda(String codigo_moneda) {
        this.codigo_moneda = codigo_moneda;
    }
    public String getPais() {
        return this.pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
}
