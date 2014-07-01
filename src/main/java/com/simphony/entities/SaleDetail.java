package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="SaleDetail")
@Table(name="detalleVenta")
public  class SaleDetail implements Serializable {


    @Column(name="importe")
    @Basic
    private float amount;


    @Column(name="id")
    @Id
    private Long id;


    @Column(name="asiento")
    @Basic
    private int seatId;


    @Id@ManyToOne(targetEntity=Sale.class)
    private Sale sale;

    public SaleDetail(){

    }


   public float getAmount() {
        return this.amount;
    }


  public void setAmount (float amount) {
        this.amount = amount;
    }



   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public int getSeatId() {
        return this.seatId;
    }


  public void setSeatId (int seatId) {
        this.seatId = seatId;
    }



   public Sale getSale() {
        return this.sale;
    }


  public void setSale (Sale sale) {
        this.sale = sale;
    }

}

