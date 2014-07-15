package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="WorkCenter")
@Table(name="estacionesTrabajo")
public  class WorkCenter extends Catalog implements Serializable, Cloneable {
    public WorkCenter(){

    }
}

