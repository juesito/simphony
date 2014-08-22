package com.simphony.entities;

import com.simphony.interfases.IConfigurable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="SaleDetail")
@Table(name="DetalleVenta")
public  class SaleDetail implements Serializable, IConfigurable {

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

    @Column(name="status")
    @Basic
    private String status;

    @Column(name = "tipoVta", length = 2)
    @Basic
    private String type;

    @Column(name = "tipoBol", length = 1)
    @Basic
    private String bolType;

    @ManyToOne(targetEntity = Associate.class)
    private Associate associate;

    
    @Transient
    private String associateKey;
    
    public SaleDetail(){
        associate = new Associate();
        this.bolType = _PASSENGER;

    }

    public SaleDetail(double amount, Seat seat, Customer customer, Associate associate, String bolType) {
        this.amount = amount;
        this.seat = seat;
        this.associate = associate;
        this.bolType = bolType;
        associate = new Associate();
        this.bolType = _PASSENGER;
    }
    
    public void update(SaleDetail saleDetail){
        this.status = saleDetail.getStatus();
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
        this.status = "V";
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

  public String getStatus() {
        return status;
    }

  public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    
    public String getBolType() {
        return bolType;
    }

    public void setBolType(String bolType) {
        this.bolType = bolType;
    }

    public String getAssociateKey() {
        return associateKey;
    }

    public void setAssociateKey(String associateKey) {
        this.associateKey = associateKey;
    }

    
  
}

