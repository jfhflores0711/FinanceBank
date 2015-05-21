package org.finance.bank.bean;
// Generated 07/01/2014 06:22:58 PM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TRegistroOtros generated by hbm2java
 */
@Entity
@Table(name="t_registro_otros"
    ,schema="public"
)
public class TRegistroOtros  implements java.io.Serializable {


     private String idregistrootros;
     private TPersonaCaja TPersonaCaja;
     private TOperacion TOperacion;
     private String descripcion;
     private BigDecimal monto;
     private String idUser;
     private String ipUser;
     private String dateUser;
     private String estado;

    public TRegistroOtros() {
    }

	
    public TRegistroOtros(String idregistrootros, String descripcion, BigDecimal monto, String idUser) {
        this.idregistrootros = idregistrootros;
        this.descripcion = descripcion;
        this.monto = monto;
        this.idUser = idUser;
    }
    public TRegistroOtros(String idregistrootros, TPersonaCaja TPersonaCaja, TOperacion TOperacion, String descripcion, BigDecimal monto, String idUser, String ipUser, String dateUser, String estado) {
       this.idregistrootros = idregistrootros;
       this.TPersonaCaja = TPersonaCaja;
       this.TOperacion = TOperacion;
       this.descripcion = descripcion;
       this.monto = monto;
       this.idUser = idUser;
       this.ipUser = ipUser;
       this.dateUser = dateUser;
       this.estado = estado;
    }
   
     @Id 
    
    @Column(name="idregistrootros", unique=true, nullable=false, length=41)
    public String getIdregistrootros() {
        return this.idregistrootros;
    }
    
    public void setIdregistrootros(String idregistrootros) {
        this.idregistrootros = idregistrootros;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idpersonacaja")
    public TPersonaCaja getTPersonaCaja() {
        return this.TPersonaCaja;
    }
    
    public void setTPersonaCaja(TPersonaCaja TPersonaCaja) {
        this.TPersonaCaja = TPersonaCaja;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_operacion")
    public TOperacion getTOperacion() {
        return this.TOperacion;
    }
    
    public void setTOperacion(TOperacion TOperacion) {
        this.TOperacion = TOperacion;
    }
    
    @Column(name="descripcion", nullable=false, length=500)
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Column(name="monto", nullable=false, precision=20)
    public BigDecimal getMonto() {
        return this.monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    @Column(name="id_user", nullable=false, length=50)
    public String getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    
    @Column(name="ip_user", length=50)
    public String getIpUser() {
        return this.ipUser;
    }
    
    public void setIpUser(String ipUser) {
        this.ipUser = ipUser;
    }
    
    @Column(name="date_user", length=50)
    public String getDateUser() {
        return this.dateUser;
    }
    
    public void setDateUser(String dateUser) {
        this.dateUser = dateUser;
    }
    
    @Column(name="estado", length=50)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }




}

