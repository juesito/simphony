package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="Bus")
@Table(name="Autobus")
public  class Bus implements Serializable {


    @Column(name="Id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Column(name="cupo")
    @Basic
    private int quota;


    @Column(name="estatus",length=1)
    @Basic
    private String status;


    @Column(name="numeroEconomico",length=10)
    @Basic
    private String number;


    @Column(name="tipo",length=2)
    @Basic
    private String type;


    @Column(name="fechaCreacion")
    @Basic
    private Date createDate;


    @OneToOne(targetEntity=User.class)
    private User user;

    public Bus(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public int getQuota() {
        return this.quota;
    }


  public void setQuota (int quota) {
        this.quota = quota;
    }



   public String getStatus() {
        return this.status;
    }


  public void setStatus (String status) {
        this.status = status;
    }



   public String getNumber() {
        return this.number;
    }


  public void setNumber (String number) {
        this.number = number;
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



   public User getUser() {
        return this.user;
    }


  public void setUser (User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bus other = (Bus) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
  

}

