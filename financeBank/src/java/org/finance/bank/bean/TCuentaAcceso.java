package org.finance.bank.bean;
// Generated 07/01/2014 06:22:58 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TCuentaAcceso generated by hbm2java
 */
@Entity
@Table(name="t_cuenta_acceso"
    ,schema="public"
)
public class TCuentaAcceso  implements java.io.Serializable {


     private String idAcceso;
     private TPersona TPersona;
     private String login;
     private String contrasenia;
     private String ipAcceso;
     private String estado;
     private String idUser;
     private String ipUser;
     private String dateUser;
     private String keyring;
     private Set TSesions = new HashSet(0);

    public TCuentaAcceso() {
    }

	
    public TCuentaAcceso(String idAcceso, TPersona TPersona, String login, String contrasenia, String ipAcceso, String estado, String idUser, String ipUser, String dateUser) {
        this.idAcceso = idAcceso;
        this.TPersona = TPersona;
        this.login = login;
        this.contrasenia = contrasenia;
        this.ipAcceso = ipAcceso;
        this.estado = estado;
        this.idUser = idUser;
        this.ipUser = ipUser;
        this.dateUser = dateUser;
    }
    public TCuentaAcceso(String idAcceso, TPersona TPersona, String login, String contrasenia, String ipAcceso, String estado, String idUser, String ipUser, String dateUser, String keyring, Set TSesions) {
       this.idAcceso = idAcceso;
       this.TPersona = TPersona;
       this.login = login;
       this.contrasenia = contrasenia;
       this.ipAcceso = ipAcceso;
       this.estado = estado;
       this.idUser = idUser;
       this.ipUser = ipUser;
       this.dateUser = dateUser;
       this.keyring = keyring;
       this.TSesions = TSesions;
    }
   
     @Id 
    
    @Column(name="id_acceso", unique=true, nullable=false, length=41)
    public String getIdAcceso() {
        return this.idAcceso;
    }
    
    public void setIdAcceso(String idAcceso) {
        this.idAcceso = idAcceso;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_user_pk", nullable=false)
    public TPersona getTPersona() {
        return this.TPersona;
    }
    
    public void setTPersona(TPersona TPersona) {
        this.TPersona = TPersona;
    }
    
    @Column(name="login", nullable=false, length=50)
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    @Column(name="contrasenia", nullable=false, length=50)
    public String getContrasenia() {
        return this.contrasenia;
    }
    
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    @Column(name="ip_acceso", nullable=false, length=500)
    public String getIpAcceso() {
        return this.ipAcceso;
    }
    
    public void setIpAcceso(String ipAcceso) {
        this.ipAcceso = ipAcceso;
    }
    
    @Column(name="estado", nullable=false, length=10)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
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
    
    @Column(name="keyring", length=100)
    public String getKeyring() {
        return this.keyring;
    }
    
    public void setKeyring(String keyring) {
        this.keyring = keyring;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="TCuentaAcceso")
    public Set getTSesions() {
        return this.TSesions;
    }
    
    public void setTSesions(Set TSesions) {
        this.TSesions = TSesions;
    }




}


