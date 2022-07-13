package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Configuracion {
    @Id(autoincrement = true)
    private Long id;
    private String usuario_mdg;
    private String clave_mdg;
    private boolean produccion_activo;
    private boolean solo_cc; //JVB VARIABLE PARA SOLO GENERAR CLAVE Y CONSECUTIVO
    private String terminalsolocc;//JVB TERMINAL PARA SOLO CC
    private boolean log; //JVB VARIABLE PARA SOLO GENERAR LOGS 02/05/2022
    private boolean consecutivo_sincronizado;

    //atributos de emisor
    private String nombre;
    private String tipo_identificacion;
    private String actividad_economica;
    private String identificacion;
    private String nombre_comercial;
    private String codigo_provincia;
    private String codigo_canton;
    private String codigo_distrito;
    private String codigo_barrio;
    private String direccion;
    private String codigo_pais_telefono;
    private String telefono;
    private String codigo_pais_fax;
    private String fax;
    private String correo_electronico;
    @Generated(hash = 1996703481)
    public Configuracion(Long id, String usuario_mdg, String clave_mdg, boolean produccion_activo, boolean solo_cc, String terminalsolocc, boolean log, boolean consecutivo_sincronizado, String nombre,
            String tipo_identificacion, String actividad_economica, String identificacion, String nombre_comercial, String codigo_provincia, String codigo_canton, String codigo_distrito,
            String codigo_barrio, String direccion, String codigo_pais_telefono, String telefono, String codigo_pais_fax, String fax, String correo_electronico) {
        this.id = id;
        this.usuario_mdg = usuario_mdg;
        this.clave_mdg = clave_mdg;
        this.produccion_activo = produccion_activo;
        this.solo_cc = solo_cc;
        this.terminalsolocc = terminalsolocc;
        this.log = log;
        this.consecutivo_sincronizado = consecutivo_sincronizado;
        this.nombre = nombre;
        this.tipo_identificacion = tipo_identificacion;
        this.actividad_economica = actividad_economica;
        this.identificacion = identificacion;
        this.nombre_comercial = nombre_comercial;
        this.codigo_provincia = codigo_provincia;
        this.codigo_canton = codigo_canton;
        this.codigo_distrito = codigo_distrito;
        this.codigo_barrio = codigo_barrio;
        this.direccion = direccion;
        this.codigo_pais_telefono = codigo_pais_telefono;
        this.telefono = telefono;
        this.codigo_pais_fax = codigo_pais_fax;
        this.fax = fax;
        this.correo_electronico = correo_electronico;
    }
    @Generated(hash = 1608634464)
    public Configuracion() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsuario_mdg() {
        return this.usuario_mdg;
    }
    public void setUsuario_mdg(String usuario_mdg) {
        this.usuario_mdg = usuario_mdg;
    }
    public String getClave_mdg() {
        return this.clave_mdg;
    }
    public void setClave_mdg(String clave_mdg) {
        this.clave_mdg = clave_mdg;
    }
    public boolean getProduccion_activo() {
        return this.produccion_activo;
    }
    public void setProduccion_activo(boolean produccion_activo) {
        this.produccion_activo = produccion_activo;
    }
    public boolean getSolo_cc() { return this.solo_cc; } //JVB 29-03-2022
    public void setSolo_cc(boolean solo_cc) {//JVB
        this.solo_cc = solo_cc;
    }

    public String getTerminalsolocc() { return this.terminalsolocc;//JVB
    }
    public void setTerminalsolocc(String terminalsolocc) { this.terminalsolocc = terminalsolocc; }

    public boolean getLog() { return this.log; } //JVB 02-05-2022
    public void setLog(boolean log) {//JVB
        this.log = log;
    }


    public boolean getConsecutivo_sincronizado() {
        return this.consecutivo_sincronizado;
    }
    public void setConsecutivo_sincronizado(boolean consecutivo_sincronizado) {
        this.consecutivo_sincronizado = consecutivo_sincronizado;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo_identificacion() {
        return this.tipo_identificacion;
    }
    public String getActividadesEconomicas() {
        return this.actividad_economica;
    }
    public void setTipo_identificacion(String tipo_identificacion) {
        this.tipo_identificacion = tipo_identificacion;
    }
    public String getIdentificacion() {
        return this.identificacion;
    }
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    public String getNombre_comercial() {
        return this.nombre_comercial;
    }
    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }
    public String getCodigo_provincia() {
        return this.codigo_provincia;
    }
    public void setCodigo_provincia(String codigo_provincia) {
        this.codigo_provincia = codigo_provincia;
    }
    public String getCodigo_canton() {
        return this.codigo_canton;
    }
    public void setCodigo_canton(String codigo_canton) {
        this.codigo_canton = codigo_canton;
    }
    public String getCodigo_distrito() {
        return this.codigo_distrito;
    }
    public void setCodigo_distrito(String codigo_distrito) {
        this.codigo_distrito = codigo_distrito;
    }
    public String getCodigo_barrio() {
        return this.codigo_barrio;
    }
    public void setCodigo_barrio(String codigo_barrio) {
        this.codigo_barrio = codigo_barrio;
    }
    public String getDireccion() {
        return this.direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCodigo_pais_telefono() {
        return this.codigo_pais_telefono;
    }
    public void setCodigo_pais_telefono(String codigo_pais_telefono) {
        this.codigo_pais_telefono = codigo_pais_telefono;
    }
    public String getTelefono() {
        return this.telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCodigo_pais_fax() {
        return this.codigo_pais_fax;
    }
    public void setCodigo_pais_fax(String codigo_pais_fax) {
        this.codigo_pais_fax = codigo_pais_fax;
    }
    public String getFax() {
        return this.fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getCorreo_electronico() {
        return this.correo_electronico;
    }
    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public boolean isProduccion_activo() {
        return produccion_activo;
    }

    public boolean isSolo_cc() {//JVB
        return solo_cc;
    }//JVB 29-03-2022 constructores solo_cc

    public boolean isLog() {//JVB
        return log;
    }//JVB 02-05-2022 constructores log

    public String getActividad_economica() {
        return this.actividad_economica;
    }
    public void setActividad_economica(String actividad_economica) {
        this.actividad_economica = actividad_economica;
    }

}
