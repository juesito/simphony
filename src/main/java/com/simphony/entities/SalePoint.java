package com.simphony.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="SalePoint")
@Table(name="puntodeventa")
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

}

