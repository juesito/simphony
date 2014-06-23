package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Population")
@Table(name="poblaciones",schema="simphonybd")
public  class Population implements Serializable {


    @Column(name="id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Column(name="estatus")
    @Basic
    private String status;


    @Column(name="descripcion",length=60)
    @Basic
    private String description;

    public Population(){
        status = "A";
    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public String getStatus() {
        return this.status;
    }


  public void setStatus (String status) {
        this.status = status;
    }



   public String getDescription() {
        return this.description;
    }


  public void setDescription (String description) {
        this.description = description;
    }

}

