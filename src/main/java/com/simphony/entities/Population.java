package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="Population")
@Table(name="Poblaciones")
public  class Population extends Catalog implements Serializable, Cloneable {

    public Population(){
    }
 
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public void update(Population populationUpdated){
        super.update(populationUpdated);
    }

}
