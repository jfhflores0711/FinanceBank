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
 * TRegistroGiro generated by hbm2java
 */
@Entity
@Table(name="t_registro_giro"
    ,schema="public"
)
public class TRegistroGiro  implements java.io.Serializable {


     private String idregistro;
     private TPersona TPersona;
     private TOperacion TOperacion;
     private TFilial TFilial;
     private String idUserPkDestino;
     private String fecha;
     private BigDecimal importe;
     private String fpagoImporte;
     private BigDecimal comision;
     private String fpagoComision;
     private String fechaEntrega;
     private String idUser;
     private String ipUser;
     private String dateUser;
     private String estado;
     private String pagadorComision;
     private String idcuentapersona;
     private String idoperacioncobro;
     private String girador;
     private String recibidor;

    public TRegistroGiro() {
    }

	
    public TRegistroGiro(String idregistro, TPersona TPersona, TOperacion TOperacion, TFilial TFilial, String fecha, BigDecimal importe, String fechaEntrega, String idUser, String ipUser, String dateUser, String estado) {
        this.idregistro = idregistro;
        this.TPersona = TPersona;
        this.TOperacion = TOperacion;
        this.TFilial = TFilial;
        this.fecha = fecha;
        this.importe = importe;
        this.fechaEntrega = fechaEntrega;
        this.idUser = idUser;
        this.ipUser = ipUser;
        this.dateUser = dateUser;
        this.estado = estado;
    }
    public TRegistroGiro(String idregistro, TPersona TPersona, TOperacion TOperacion, TFilial TFilial, String idUserPkDestino, String fecha, BigDecimal importe, String fpagoImporte, BigDecimal comision, String fpagoComision, String fechaEntrega, String idUser, String ipUser, String dateUser, String estado, String pagadorComision, String idcuentapersona, String idoperacioncobro, String girador, String recibidor) {
       this.idregistro = idregistro;
       this.TPersona = TPersona;
       this.TOperacion = TOperacion;
       this.TFilial = TFilial;
       this.idUserPkDestino = idUserPkDestino;
       this.fecha = fecha;
       this.importe = importe;
       this.fpagoImporte = fpagoImporte;
       this.comision = comision;
       this.fpagoComision = fpagoComision;
       this.fechaEntrega = fechaEntrega;
       this.idUser = idUser;
       this.ipUser = ipUser;
       this.dateUser = dateUser;
       this.estado = estado;
       this.pagadorComision = pagadorComision;
       this.idcuentapersona = idcuentapersona;
       this.idoperacioncobro = idoperacioncobro;
       this.girador = girador;
       this.recibidor = recibidor;
    }
   
     @Id 
    
    @Column(name="idregistro", unique=true, nullable=false, length=41)
    public String getIdregistro() {
        return this.idregistro;
    }
    
    public void setIdregistro(String idregistro) {
        this.idregistro = idregistro;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user_pk_origen", nullable=false)
    public TPersona getTPersona() {
        return this.TPersona;
    }
    
    public void setTPersona(TPersona TPersona) {
        this.TPersona = TPersona;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_operacion", nullable=false)
    public TOperacion getTOperacion() {
        return this.TOperacion;
    }
    
    public void setTOperacion(TOperacion TOperacion) {
        this.TOperacion = TOperacion;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cod_filial", nullable=false)
    public TFilial getTFilial() {
        return this.TFilial;
    }
    
    public void setTFilial(TFilial TFilial) {
        this.TFilial = TFilial;
    }
    
    @Column(name="id_user_pk_destino", length=50)
    public String getIdUserPkDestino() {
        return this.idUserPkDestino;
    }
    
    public void setIdUserPkDestino(String idUserPkDestino) {
        this.idUserPkDestino = idUserPkDestino;
    }
    
    @Column(name="fecha", nullable=false, length=50)
    public String getFecha() {
        return this.fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    @Column(name="importe", nullable=false, precision=20)
    public BigDecimal getImporte() {
        return this.importe;
    }
    
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    
    @Column(name="fpago_importe", length=20)
    public String getFpagoImporte() {
        return this.fpagoImporte;
    }
    
    public void setFpagoImporte(String fpagoImporte) {
        this.fpagoImporte = fpagoImporte;
    }
    
    @Column(name="comision", precision=20)
    public BigDecimal getComision() {
        return this.comision;
    }
    
    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }
    
    @Column(name="fpago_comision", length=20)
    public String getFpagoComision() {
        return this.fpagoComision;
    }
    
    public void setFpagoComision(String fpagoComision) {
        this.fpagoComision = fpagoComision;
    }
    
    @Column(name="fecha_entrega", nullable=false, length=20)
    public String getFechaEntrega() {
        return this.fechaEntrega;
    }
    
    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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
    
    @Column(name="estado", nullable=false, length=50)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Column(name="pagador_comision", length=100)
    public String getPagadorComision() {
        return this.pagadorComision;
    }
    
    public void setPagadorComision(String pagadorComision) {
        this.pagadorComision = pagadorComision;
    }
    
    @Column(name="idcuentapersona", length=41)
    public String getIdcuentapersona() {
        return this.idcuentapersona;
    }
    
    public void setIdcuentapersona(String idcuentapersona) {
        this.idcuentapersona = idcuentapersona;
    }
    
    @Column(name="idoperacioncobro", length=41)
    public String getIdoperacioncobro() {
        return this.idoperacioncobro;
    }
    
    public void setIdoperacioncobro(String idoperacioncobro) {
        this.idoperacioncobro = idoperacioncobro;
    }
    
    @Column(name="girador", length=200)
    public String getGirador() {
        return this.girador;
    }
    
    public void setGirador(String girador) {
        this.girador = girador;
    }
    
    @Column(name="recibidor", length=200)
    public String getRecibidor() {
        return this.recibidor;
    }
    
    public void setRecibidor(String recibidor) {
        this.recibidor = recibidor;
    }




}

