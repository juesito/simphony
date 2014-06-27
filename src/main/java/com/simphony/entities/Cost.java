package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="Cost")
@Table(name="Tarifa",schema="simphonybd")
public  class Cost implements Serializable {


    @Basic
    private int routeTime;


    @Id
    private Long id;


    @Column(name="kms",length=4)
    @Basic
    private int kms;


    @ManyToOne(targetEntity=Population.class)
    private Population origin;


    @ManyToOne(targetEntity=Population.class)
    private Population destiny;


    @Basic
    private Date createDate;


    @Column(name="tarifa",length=4)
    @Basic
    private int cost;


    @Basic
    private Date createHour;

    public Cost(){

    }


   public int getRouteTime() {
        return this.routeTime;
    }


  public void setRouteTime (int routeTime) {
        this.routeTime = routeTime;
    }



   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public int getKms() {
        return this.kms;
    }


  public void setKms (int kms) {
        this.kms = kms;
    }



   public Population getOrigin() {
        return this.origin;
    }


  public void setOrigin (Population origin) {
        this.origin = origin;
    }



   public Population getDestiny() {
        return this.destiny;
    }


  public void setDestiny (Population destiny) {
        this.destiny = destiny;
    }



   public Date getCreateDate() {
        return this.createDate;
    }


  public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }



   public int getCost() {
        return this.cost;
    }


  public void setCost (int cost) {
        this.cost = cost;
    }



   public Date getCreateHour() {
        return this.createHour;
    }


  public void setCreateHour (Date createHour) {
        this.createHour = createHour;
    }

}

