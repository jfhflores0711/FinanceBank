package org.finance.bank.bean;
// Generated 07/01/2014 06:22:58 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Sumasnashot generated by hbm2java
 */
@Entity
@Table(name="sumasnashot"
    ,schema="public"
)
public class Sumasnashot  implements java.io.Serializable {


     private String idsuma;
     private String moneda;
     private String cajero;
     private String montodenominacio;
     private String estado;
     private Integer cantidad;

    public Sumasnashot() {
    }

	
    public Sumasnashot(String idsuma) {
        this.idsuma = idsuma;
    }
    public Sumasnashot(String idsuma, String moneda, String cajero, String montodenominacio, String estado, Integer cantidad) {
       this.idsuma = idsuma;
       this.moneda = moneda;
       this.cajero = cajero;
       this.montodenominacio = montodenominacio;
       this.estado = estado;
       this.cantidad = cantidad;
    }
   
     @Id 
    
    @Column(name="idsuma", unique=true, nullable=false, length=41)
    public String getIdsuma() {
        return this.idsuma;
    }
    
    public void setIdsuma(String idsuma) {
        this.idsuma = idsuma;
    }
    
    @Column(name="moneda", length=50)
    public String getMoneda() {
        return this.moneda;
    }
    
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    
    @Column(name="cajero", length=50)
    public String getCajero() {
        return this.cajero;
    }
    
    public void setCajero(String cajero) {
        this.cajero = cajero;
    }
    
    @Column(name="montodenominacio", length=50)
    public String getMontodenominacio() {
        return this.montodenominacio;
    }
    
    public void setMontodenominacio(String montodenominacio) {
        this.montodenominacio = montodenominacio;
    }
    
    @Column(name="estado", length=50)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Column(name="cantidad")
    public Integer getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }




}


