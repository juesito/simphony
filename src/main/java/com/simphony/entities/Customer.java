package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="Customer")
@Table(name="Cliente")
public  class Customer extends Person implements Serializable, Cloneable {

    @Column(name="nombreCompleto",length=50)
    @Basic
    private String fullName;

    public Customer(){

    }

    public void update(Associate associateUpdated){
        super.update(associateUpdated);
    }
    
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
  

}

