package org.finance.bank.bean;
// Generated 07/01/2014 06:22:58 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TMoneda generated by hbm2java
 */
@Entity
@Table(name="t_moneda"
    ,schema="public"
)
public class TMoneda  implements java.io.Serializable {


     private String codMoneda;
     private String nombre;
     private String simbolo;
     private String idUser;
     private String ipUser;
     private String dateUser;
     private String codParaNumCuenta;
     private String color;
     private String estado;
     private Set TTipoCambios = new HashSet(0);
     private Set TGarantiaClientes = new HashSet(0);
     private Set TBalancexmonedas = new HashSet(0);
     private Set TDetalleCajas = new HashSet(0);
     private Set TDenominacionMonedas = new HashSet(0);
     private Set TCuentaPersonas = new HashSet(0);
     private Set TOperacions = new HashSet(0);

    public TMoneda() {
    }

	
    public TMoneda(String codMoneda, String nombre, String simbolo, String idUser, String ipUser, String dateUser, String codParaNumCuenta) {
        this.codMoneda = codMoneda;
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.idUser = idUser;
        this.ipUser = ipUser;
        this.dateUser = dateUser;
        this.codParaNumCuenta = codParaNumCuenta;
    }
    public TMoneda(String codMoneda, String nombre, String simbolo, String idUser, String ipUser, String dateUser, String codParaNumCuenta, String color, String estado, Set TTipoCambios, Set TGarantiaClientes, Set TBalancexmonedas, Set TDetalleCajas, Set TDenominacionMonedas, Set TCuentaPersonas, Set TOperacions) {
       this.codMoneda = codMoneda;
       this.nombre = nombre;
       this.simbolo = simbolo;
       this.idUser = idUser;
       this.ipUser = ipUser;
       this.dateUser = dateUser;
       this.codParaNumCuenta = codParaNumCuenta;
       this.color = color;
       this.estado = estado;
       this.TTipoCambios = TTipoCambios;
       this.TGarantiaClientes = TGarantiaClientes;
       this.TBalancexmonedas = TBalancexmonedas;
       this.TDetalleCajas = TDetalleCajas;
       this.TDenominacionMonedas = TDenominacionMonedas;
       this.TCuentaPersonas = TCuentaPersonas;
       this.TOperacions = TOperacions;
    }
   
     @Id 
    
    @Column(name="cod_moneda", unique=true, nullable=false, length=41)
    public String getCodMoneda() {
        return this.codMoneda;
    }
    
    public void setCodMoneda(String codMoneda) {
        this.codMoneda = codMoneda;
    }
    
    @Column(name="nombre", nullable=false, length=20)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Column(name="simbolo", nullable=false, length=10)
    public String getSimbolo() {
        return this.simbolo;
    }
    
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
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
    
    @Column(name="cod_para_num_cuenta", nullable=false, length=10)
    public String getCodParaNumCuenta() {
        return this.codParaNumCuenta;
    }
    
    public void setCodParaNumCuenta(String codParaNumCuenta) {
        this.codParaNumCuenta = codParaNumCuenta;
    }
    
    @Column(name="color", length=20)
    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    @Column(name="estado", length=10)
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="TMoneda")
    public Set getTTipoCambios() {
        return this.TTipoCambios;
    }
    
    public void setTTipoCambios(Set TTipoCambios) {
        this.TTipoCambios = TTipoCambios;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="TMoneda")
    public Set getTGarantiaClientes() {
        return this.TGarantiaClientes;
    }
    
    public void setTGarantiaClientes(Set TGarantiaClientes) {
        this.TGarantiaClientes = TGarantiaClientes;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="TMoneda")
    public Set getTBalancexmonedas() {
        return this.TBalancexmonedas;
    }
    
    public void setTBalancexmonedas(Set TBalancexmonedas) {
        this.TBalancexmonedas = TBalancexmonedas;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="TMoneda")
    public Set getTDetalleCajas() {
        return this.TDetalleCajas;
    }
    
    public void setTDetalleCajas(Set TDetalleCajas) {
        this.TDetalleCajas = TDetalleCajas;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="TMoneda")
    public Set getTDenominacionMonedas() {
        return this.TDenominacionMonedas;
    }
    
    public void setTDenominacionMonedas(Set TDenominacionMonedas) {
        this.TDenominacionMonedas = TDenominacionMonedas;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="TMoneda")
    public Set getTCuentaPersonas() {
        return this.TCuentaPersonas;
    }
    
    public void setTCuentaPersonas(Set TCuentaPersonas) {
        this.TCuentaPersonas = TCuentaPersonas;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="TMoneda")
    public Set getTOperacions() {
        return this.TOperacions;
    }
    
    public void setTOperacions(Set TOperacions) {
        this.TOperacions = TOperacions;
    }




}


