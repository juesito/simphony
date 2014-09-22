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
    @Column(name="CveCosrta")
    private String cveCorta;

    @Basic
    @Column(name="Estado")
    private String state;

    @Basic
    @Column(name="CodigoPostal")
    private String zipCode;

    public Population(){
    }

    public String getCveCorta() {
        return cveCorta;
    }

    public void setCveCorta(String cveCorta) {
        this.cveCorta = cveCorta;
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
        this.cveCorta = populationUpdated.cveCorta;
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
