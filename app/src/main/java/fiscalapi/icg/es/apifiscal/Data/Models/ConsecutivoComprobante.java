package fiscalapi.icg.es.apifiscal.Data.Models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ConsecutivoComprobante {
    @Id(autoincrement = true)
    private Long id;
    private String EMISOR_IDENTIFICACION;
    private String CASA_MATRIZ;
    private String TERMINAL;
    private String TIPO_DOCUMENTO;
    private int CONSECUTIVO;
    @Generated(hash = 1842276490)
    public ConsecutivoComprobante(Long id, String EMISOR_IDENTIFICACION,
            String CASA_MATRIZ, String TERMINAL, String TIPO_DOCUMENTO,
            int CONSECUTIVO) {
        this.id = id;
        this.EMISOR_IDENTIFICACION = EMISOR_IDENTIFICACION;
        this.CASA_MATRIZ = CASA_MATRIZ;
        this.TERMINAL = TERMINAL;
        this.TIPO_DOCUMENTO = TIPO_DOCUMENTO;
        this.CONSECUTIVO = CONSECUTIVO;
    }
    @Generated(hash = 1230802975)
    public ConsecutivoComprobante() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEMISOR_IDENTIFICACION() {
        return this.EMISOR_IDENTIFICACION;
    }
    public void setEMISOR_IDENTIFICACION(String EMISOR_IDENTIFICACION) {
        this.EMISOR_IDENTIFICACION = EMISOR_IDENTIFICACION;
    }
    public String getCASA_MATRIZ() {
        return this.CASA_MATRIZ;
    }
    public void setCASA_MATRIZ(String CASA_MATRIZ) {
        this.CASA_MATRIZ = CASA_MATRIZ;
    }
    public String getTERMINAL() {
        return this.TERMINAL;
    }
    public void setTERMINAL(String TERMINAL) {
        this.TERMINAL = TERMINAL;
    }
    public String getTIPO_DOCUMENTO() {
        return this.TIPO_DOCUMENTO;
    }
    public void setTIPO_DOCUMENTO(String TIPO_DOCUMENTO) {
        this.TIPO_DOCUMENTO = TIPO_DOCUMENTO;
    }
    public int getCONSECUTIVO() {
        return this.CONSECUTIVO;
    }
    public void setCONSECUTIVO(int CONSECUTIVO) {
        this.CONSECUTIVO = CONSECUTIVO;
    }
}
