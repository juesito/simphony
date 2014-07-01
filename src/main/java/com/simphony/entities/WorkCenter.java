package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="WorkCenter")
@Table(name="estacionTrabajo",schema="simphonybd")
public  class WorkCenter implements Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Column(name="estatus",length=1)
    @Basic
    private String status;


    @Column(name="descripcion",length=60)
    @Basic
    private String description;


    @Column(name="fechaCreacion")
    @Basic
    private Date createDate;


    @OneToOne(targetEntity=User.class)
    private User user;


    @Column(name="horaCreacion")
    @Basic
    private Date createHour;

    public WorkCenter(){

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



   public Date getCreateHour() {
        return this.createHour;
    }


  public void setCreateHour (Date createHour) {
        this.createHour = createHour;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final WorkCenter other = (WorkCenter) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
  
  

}

