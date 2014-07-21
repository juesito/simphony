package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="Sale")
@Table(name="Ventas")
public  class Sale implements Serializable {


    @Column(name="id")
    @Id
    private Long id;


    @OneToOne(targetEntity=Vendor.class)
    private Vendor cancelVendor;


    @ManyToOne(targetEntity=Vendor.class)
    private Vendor vendor;


    @ManyToOne(targetEntity=Population.class)
    private Population origin;


    @Column(name="formaPago",length=2)
    @Basic
    private String payType;


    @Column(name="fechaSalida")
    @Basic
    private Date tripDate;


    @ManyToOne(targetEntity=Population.class)
    private Population destiny;


    @Column(name="tipo",length=2)
    @Basic
    private String type;


    @Column(name="fechaCreacion")
    @Basic
    private Date createDate;


    @ManyToOne(targetEntity=Associate.class)
    private Associate associate;

    public Sale(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public Vendor getCancelVendor() {
        return this.cancelVendor;
    }


  public void setCancelVendor (Vendor cancelVendor) {
        this.cancelVendor = cancelVendor;
    }



   public Vendor getVendor() {
        return this.vendor;
    }


  public void setVendor (Vendor vendor) {
        this.vendor = vendor;
    }



   public Population getOrigin() {
        return this.origin;
    }


  public void setOrigin (Population origin) {
        this.origin = origin;
    }



   public String getPayType() {
        return this.payType;
    }


  public void setPayType (String payType) {
        this.payType = payType;
    }



   public Date getTripDate() {
        return this.tripDate;
    }


  public void setTripDate (Date tripDate) {
        this.tripDate = tripDate;
    }



   public Population getDestiny() {
        return this.destiny;
    }


  public void setDestiny (Population destiny) {
        this.destiny = destiny;
    }



   public String getType() {
        return this.type;
    }


  public void setType (String type) {
        this.type = type;
    }



   public Date getCreateDate() {
        return this.createDate;
    }


  public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }



   public Associate getAssociate() {
        return this.associate;
    }


  public void setAssociate (Associate associate) {
        this.associate = associate;
    }

}

