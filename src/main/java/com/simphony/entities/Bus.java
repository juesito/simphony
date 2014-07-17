package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="Bus")
@Table(name="Autobus")
public  class Bus extends Catalog implements Serializable, Cloneable {

    @Column(name="cupo")
    @Basic
    private int quota;


    @Column(name="numeroEconomico",length=10)
    @Basic
    private String number;


    @Column(name="tipo",length=7)
    @Basic
    private String type;

    
    public Bus(){

    }

    public void update(Bus busUpdated){
        super.update(busUpdated);
        this.number = busUpdated.getNumber();
        this.quota = busUpdated.getQuota();
        this.type = busUpdated.getType();
     }
 
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }


    public int getQuota() {
        return this.quota;
    }


  public void setQuota (int quota) {
        this.quota = quota;
    }


  public String getNumber() {
        return this.number;
    }


  public void setNumber (String number) {
        this.number = number;
    }


   public String getType() {
        return this.type;
    }


  public void setType (String type) {
        this.type = type;
    }

}