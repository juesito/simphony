package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public  class Vendor extends Person implements Serializable, Cloneable {


    @Column(name="nick",length=10)
    @Basic
    private String nick;


    @ManyToOne(targetEntity=WorkCenter.class)
    private WorkCenter workCenter;


    @Column(name="contrasena",length=10)
    @Basic
    private String password;


    @ManyToOne(targetEntity=Population.class)
    private Population population;

    public Vendor(){

    }


   public String getNick() {
        return this.nick;
    }


  public void setNick (String nick) {
        this.nick = nick;
    }



   public WorkCenter getWorkCenter() {
        return this.workCenter;
    }


  public void setWorkCenter (WorkCenter workCenter) {
        this.workCenter = workCenter;
    }



   public String getPassword() {
        return this.password;
    }


  public void setPassword (String password) {
        this.password = password;
    }



   public Population getPopulation() {
        return this.population;
    }


  public void setPopulation (Population population) {
        this.population = population;
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

