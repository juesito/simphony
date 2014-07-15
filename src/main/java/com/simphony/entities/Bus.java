package com.simphony.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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


    @Column(name="tipo",length=2)
    @Basic
    private String type;

    
    public Bus(){

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