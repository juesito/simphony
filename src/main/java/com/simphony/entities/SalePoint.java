package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="SalePoint")
@Table(name="Puntodeventa")
public  class SalePoint extends Catalog implements Serializable, Cloneable {


    @Column(name="domicilio",length=100)
    @Basic
    private String domicilio;


    public String getDomicilio() {
        return this.domicilio;
    }


    public void setDomicilio (String domicilio) {
        this.domicilio = domicilio;
    }

    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public void update(SalePoint salePointUpdated){
        super.update(salePointUpdated);
        this.domicilio = salePointUpdated.getDomicilio();
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

