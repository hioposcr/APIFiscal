package fiscalapi.icg.es.apifiscal.Data.Hacienda;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Order;

@Order(elements = {"Nombre", "Identificacion", "IdentificacionExtranjero", "NombreComercial", "Ubicacion", "Telefono", "Fax", "CorreoElectronico"})
public class EmisorReceptor {

    @Element(name = "Nombre")
    private String Nombre;

    @Element(name = "Identificacion", required = false)
    private Identificacion Identificacion;

    @Element(name = "IdentificacionExtranjero", required = false)
    private String IdentificacionExtranjero;

    @Element(name = "NombreComercial", required = false)
    private String NombreComercial;

    @Element(name = "Ubicacion", required = false)
    private Ubicacion Ubicacion;

    @Element(name = "Telefono", required = false)
    private Telefono Telefono;

    @Element(name = "Fax", required = false)
    private Telefono Fax;

    @Element(name = "CorreoElectronico",required = false)
    private String CorreoElectronico;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Identificacion getIdentificacion() {
        return Identificacion;
    }

    public void setIdentificacion(Identificacion identificacion) {
        Identificacion = identificacion;
    }

    public String getIdentificacionExtranjero() {
        return IdentificacionExtranjero;
    }

    public void setIdentificacionExtranjero(String identificacionExtranjero) {
        IdentificacionExtranjero = identificacionExtranjero;
    }

    public String getNombreComercial() {
        return NombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        NombreComercial = nombreComercial;
    }

    public Ubicacion getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        Ubicacion = ubicacion;
    }

    public Telefono getTelefono() {
        return Telefono;
    }

    public void setTelefono(Telefono telefono) {
        Telefono = telefono;
    }

    public Telefono getFax() {
        return Fax;
    }

    public void setFax(Telefono fax) {
        Fax = fax;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        CorreoElectronico = correoElectronico;
    }
}
