package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Sign;

public class SignXMLRequest {
    private String tipoDoc;
    private String versionAPIFiscal;
    private String usuarioMdg;
    private String claveMdg;
    private String xml;
    private boolean produccionActivo;

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getVersionAPIFiscal() {
        return versionAPIFiscal;
    }

    public void setVersionAPIFiscal(String versionAPIFiscal) {
        this.versionAPIFiscal = versionAPIFiscal;
    }

    public String getUsuarioMdg() {
        return usuarioMdg;
    }

    public void setUsuarioMdg(String usuarioMdg) {
        this.usuarioMdg = usuarioMdg;
    }

    public String getClaveMdg() {
        return claveMdg;
    }

    public void setClaveMdg(String claveMdg) {
        this.claveMdg = claveMdg;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public boolean isProduccionActivo() {
        return produccionActivo;
    }

    public void setProduccionActivo(boolean produccionActivo) {
        this.produccionActivo = produccionActivo;
    }
}
