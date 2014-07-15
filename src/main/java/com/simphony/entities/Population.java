package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="Population")
@Table(name="poblaciones")
public  class Population extends Catalog implements Serializable, Cloneable {

    public Population(){
    }
    
}
