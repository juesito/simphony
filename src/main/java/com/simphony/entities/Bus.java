package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Bus")
@Table(name="Autobus",schema="simphonybd")
public  class Bus implements Serializable {


    @Column(name="Id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Basic
    private int number;


    @Basic
    private Date createDate;


    @Basic
    private Date createHour;

    public Bus(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public int getNumber() {
        return this.number;
    }


  public void setNumber (int number) {
        this.number = number;
    }



   public Date getCreateDate() {
        return this.createDate;
    }


  public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }



   public Date getCreateHour() {
        return this.createHour;
    }


  public void setCreateHour (Date createHour) {
        this.createHour = createHour;
    }

}

