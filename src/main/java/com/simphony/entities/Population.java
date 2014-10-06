package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="Population")
@Table(name="Poblaciones")
public  class Population extends Catalog implements Serializable, Cloneable {

    @Basic
    @Column(name="CveCorta")
    private String shortKey;

    @Basic
    @Column(name="Estado")
    private String state;

    @Basic
    @Column(name="CodigoPostal")
    private String zipCode;

    public Population(){
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
 
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public void update(Population populationUpdated){
        super.update(populationUpdated);
        this.shortKey = populationUpdated.shortKey;
        this.state = populationUpdated.state;
        this.zipCode = populationUpdated.zipCode;
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
