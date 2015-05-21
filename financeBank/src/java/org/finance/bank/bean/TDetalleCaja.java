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
 * TDetalleCaja generated by hbm2java
 */
@Entity
@Table(name="t_detalle_caja"
    ,schema="public"
)
public class TDetalleCaja  implements java.io.Serializable {


     private String iddetallecaja;
     private TCaja TCaja;
     private TMoneda TMoneda;
     private BigDecimal montoInicial;
     private BigDecimal montoFinal;
     private String fechaTransaccion;
     private String idUser;
     private String ipUser;
     private String dateUser;
     private String estado;
     private BigDecimal montoRecibido;
     private BigDecimal montoEntregado;

    public TDetalleCaja() {
    }

	
    public TDetalleCaja(String iddetallecaja, TCaja TCaja, TMoneda TMoneda, String fechaTransaccion, String idUser, String ipUser, String dateUser) {
        this.iddetallecaja = iddetallecaja;
        this.TCaja = TCaja;
        this.TMoneda = TMoneda;
        this.fechaTransaccion = fechaTransaccion;
        this.idUser = idUser;
        this.ipUser = ipUser;
        this.dateUser = dateUser;
    }
    public TDetalleCaja(String iddetallecaja, TCaja TCaja, TMoneda TMoneda, BigDecimal montoInicial, BigDecimal montoFinal, String fechaTransaccion, String idUser, String ipUser, String dateUser, String estado, BigDecimal montoRecibido, BigDecimal montoEntregado) {
       this.iddetallecaja = iddetallecaja;
       this.TCaja = TCaja;
       this.TMoneda = TMoneda;
       this.montoInicial = montoInicial;
       this.montoFinal = montoFinal;
       this.fechaTransaccion = fechaTransaccion;
       this.idUser = idUser;
       this.ipUser = ipUser;
       this.dateUser = dateUser;
       this.estado = estado;
       this.montoRecibido = montoRecibido;
       this.montoEntregado = montoEntregado;
    }
   
     @Id 
    
    @Column(name="iddetallecaja", unique=true, nullable=false, length=41)
    public String getIddetallecaja() {
        return this.iddetallecaja;
    }
    
    public void setIddetallecaja(String iddetallecaja) {
        this.iddetallecaja = iddetallecaja;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cod_caja", nullable=false)
    public TCaja getTCaja() {
        return this.TCaja;
    }
    
    public void setTCaja(TCaja TCaja) {
        this.TCaja = TCaja;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cod_moneda", nullable=false)
    public TMoneda getTMoneda() {
        return this.TMoneda;
    }
    
    public void setTMoneda(TMoneda TMoneda) {
        this.TMoneda = TMoneda;
    }
    
    @Column(name="monto_inicial", precision=20)
    public BigDecimal getMontoInicial() {
        return this.montoInicial;
    }
    
    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }
    
    @Column(name="monto_final", precision=20)
    public BigDecimal getMontoFinal() {
        return this.montoFinal;
    }
    
    public void setMontoFinal(BigDecimal montoFinal) {
        this.montoFinal = montoFinal;
    }
    
    @Column(name="fecha_transaccion", nullable=false, length=50)
    public String getFechaTransaccion() {
        return this.fechaTransaccion;
    }
    
    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }
    
    @Column(name="id_user", nullable=false, length=50)
    public String getIdUser() {
        return this.idUser;
    }
    
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    
    @Column(name="ip_user", nullable=false, length=50)
    public String getIpUser() {
        return this.ipUser;
    }
    
    public void setIpUser(String ipUser) {
        this.ipUser = ipUser;
    }
    
    @Column(name="date_user", nullable=false, length=50)
    public String getDateUser() {
        return this.dateUser;
    }
    
    public void setDateUser(String dateUser) {
        this.dateUser = dateUser;
    }
    
    @Column(name="estado", length=10)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Column(name="monto_recibido", precision=20)
    public BigDecimal getMontoRecibido() {
        return this.montoRecibido;
    }
    
    public void setMontoRecibido(BigDecimal montoRecibido) {
        this.montoRecibido = montoRecibido;
    }
    
    @Column(name="monto_entregado", precision=20)
    public BigDecimal getMontoEntregado() {
        return this.montoEntregado;
    }
    
    public void setMontoEntregado(BigDecimal montoEntregado) {
        this.montoEntregado = montoEntregado;
    }




}

