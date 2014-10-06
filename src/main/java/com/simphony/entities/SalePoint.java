package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="SalePoint")
@Table(name="Puntodeventa")
public  class SalePoint extends Catalog implements Serializable, Cloneable {


    @Column(name="domicilio",length=100)
    @Basic
    private String domicilio;

    @Column(name="colonia",length=40)
    @Basic
    private String colony;

    @Column(name="codigoPostal",length=10)
    @Basic
    private String zipCode;

    @Column(name="estado",length=50)
    @Basic
    private String state;

    @ManyToOne(targetEntity = Population.class)
    private Population city;
    
    @Column(name="telefono",length=10)
    @Basic
    private String phoneId;

    @Column(name="encargado",length=50)
    @Basic
    private String manager;

    @Column(name="Email",length=200)
    @Basic
    private String emailManager;
    
    @Column(name="titular1",length=50)
    @Basic
    private String titular1;

    @Column(name="titular2",length=50)
    @Basic
    private String titular2;

    @Column(name="type",length=5)
    @Basic
    private String type;
    
    public String getDomicilio() {
        return this.domicilio;
    }

    public void setDomicilio (String domicilio) {
        this.domicilio = domicilio;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getEmailManager() {
        return emailManager;
    }

    public void setEmailManager(String emailManager) {
        this.emailManager = emailManager;
    }

    public String getTitular1() {
        return titular1;
    }

    public void setTitular1(String titular1) {
        this.titular1 = titular1;
    }

    public String getTitular2() {
        return titular2;
    }

    public void setTitular2(String titular2) {
        this.titular2 = titular2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Population getCity() {
        return city;
    }

    public void setCity(Population city) {
        this.city = city;
    }
    
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public void update(SalePoint salePointUpdated){
        super.update(salePointUpdated);
        this.domicilio = salePointUpdated.getDomicilio();
        this.colony = salePointUpdated.getColony();
        this.city = salePointUpdated.getCity();
        this.manager = salePointUpdated.getManager();
        this.emailManager = salePointUpdated.getEmailManager();
        this.phoneId = salePointUpdated.getPhoneId();
        this.state = salePointUpdated.getState();
        this.zipCode = salePointUpdated.getZipCode();
        this.titular1 = salePointUpdated.getTitular1();
        this.titular2 = salePointUpdated.getTitular2();
        this.type = salePointUpdated.type;
    }

    public String getFormatStatus(){
        String texto = "";
        if(this.getStatus() != null){
            if (this.getStatus().equals("A")){
                texto = "Activo";
            }else texto = "Inactivo";
       
            return texto;
        }else return texto;
    }
    
}