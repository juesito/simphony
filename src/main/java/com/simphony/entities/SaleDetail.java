package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="SaleDetail")
@Table(name="DetalleVenta")
public  class SaleDetail implements Serializable {

    @Column(name="importe")
    @Basic
    private double amount;

    @Column(name="id")
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity=Seat.class)
    private Seat seat;
    
    @Column(name="nombreCliente")
    @Basic
    private String customerName;

    @ManyToOne(targetEntity=Sale.class)
    private Sale sale;

    public SaleDetail(){

    }

    public SaleDetail(double amount, Seat seat, Customer customer) {
        this.amount = amount;
        this.seat = seat;
    }
    
    /**
     * Limpiamos el detalle de la venta.
     */
    public void clear(){
        this.id = 0L;
        this.amount = 0.0;
        this.customerName = "";
        this.seat = new Seat();
        this.sale = new Sale();
    }

   public double getAmount() {
        return this.amount;
    }

  public void setAmount (double amount) {
        this.amount = amount;
    }

   public Long getId() {
        return this.id;
    }

  public void setId (Long id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

   public Sale getSale() {
        return this.sale;
    }

  public void setSale (Sale sale) {
        this.sale = sale;
    }

}

