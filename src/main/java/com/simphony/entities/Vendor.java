package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public  class Vendor extends Person implements Serializable, Cloneable {


    @ManyToOne(targetEntity=WorkCenter.class)
    private WorkCenter workCenter;


    @ManyToOne(targetEntity=Population.class)
    private Population population;

    public Vendor(){

    }


   public WorkCenter getWorkCenter() {
        return this.workCenter;
    }


  public void setWorkCenter (WorkCenter workCenter) {
        this.workCenter = workCenter;
    }



   public Population getPopulation() {
        return this.population;
    }


  public void setPopulation (Population population) {
        this.population = population;
    }

  
  @Override
     public Object clone() throws CloneNotSupportedException{
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
}

