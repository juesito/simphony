package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="WorkCenter")
@Table(name="estacionesTrabajo")
public  class WorkCenter extends Catalog implements Serializable, Cloneable {
    public WorkCenter(){

    }

    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public void update(SalePoint salePointUpdated){
        super.update(salePointUpdated);
    }

}

