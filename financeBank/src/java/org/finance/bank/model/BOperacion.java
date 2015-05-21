package org.finance.bank.model;

/**
 *
 * @author roger
 */
public class BOperacion {

    private String idOperacion;
    private String descripcion;
    private String tmpIdCuenta;
    private String fecha;
    private String tipoOperacion;

    /**
     * @return the idOperacion
     */
    public String getIdOperacion() {
        return idOperacion;
    }

    /**
     * @param idOperacion the idOperacion to set
     */
    public void setIdOperacion(String idOperacion) {
        this.idOperacion = idOperacion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the tmpIdCuenta
     */
    public String getTmpIdCuenta() {
        return tmpIdCuenta;
    }

    /**
     * @param tmpIdCuenta the tmpIdCuenta to set
     */
    public void setTmpIdCuenta(String tmpIdCuenta) {
        this.tmpIdCuenta = tmpIdCuenta;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the tipoOperacion
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion the tipoOperacion to set
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
}
