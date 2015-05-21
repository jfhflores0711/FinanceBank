package org.finance.bank.bean;
// Generated 07/01/2014 06:22:58 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TControlModulo generated by hbm2java
 */
@Entity
@Table(name="t_control_modulo"
    ,schema="public"
)
public class TControlModulo  implements java.io.Serializable {


     private String idcontrolmodulo;
     private TModulo TModulo;
     private TTipoPersona TTipoPersona;
     private String idUser;
     private String ipUser;
     private String dateUser;
     private String estado;

    public TControlModulo() {
    }

    public TControlModulo(String idcontrolmodulo, TModulo TModulo, TTipoPersona TTipoPersona, String idUser, String ipUser, String dateUser, String estado) {
       this.idcontrolmodulo = idcontrolmodulo;
       this.TModulo = TModulo;
       this.TTipoPersona = TTipoPersona;
       this.idUser = idUser;
       this.ipUser = ipUser;
       this.dateUser = dateUser;
       this.estado = estado;
    }
   
     @Id 
    
    @Column(name="idcontrolmodulo", unique=true, nullable=false, length=41)
    public String getIdcontrolmodulo() {
        return this.idcontrolmodulo;
    }
    
    public void setIdcontrolmodulo(String idcontrolmodulo) {
        this.idcontrolmodulo = idcontrolmodulo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idmodulo", nullable=false)
    public TModulo getTModulo() {
        return this.TModulo;
    }
    
    public void setTModulo(TModulo TModulo) {
        this.TModulo = TModulo;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idtipopersona", nullable=false)
    public TTipoPersona getTTipoPersona() {
        return this.TTipoPersona;
    }
    
    public void setTTipoPersona(TTipoPersona TTipoPersona) {
        this.TTipoPersona = TTipoPersona;
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
    
    @Column(name="estado", nullable=false, length=20)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }




}

