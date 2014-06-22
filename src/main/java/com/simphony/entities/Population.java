package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Population")
@Table(name="poblaciones",schema="simphonybd",catalog="poblaciones")
public  class Population implements Serializable {


    @Column(name="id")
    @Id
    private Long id;

    public Population(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }

}

