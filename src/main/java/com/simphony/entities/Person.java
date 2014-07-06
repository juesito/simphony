package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Person implements Serializable {


    @Column(name="id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Column(length=200)
    @Basic
    private String email;


    @Column(name="estatus",length=1)
    @Basic
    private String status;


    @Column(name="ultimaModificacion")
    @Basic
    private Date lastUpdate;


    @Column(name="nombre",length=60)
    @Basic
    private String name;


    @Transient
    private String action;
    
    @Transient
    private boolean looged;


    @Column(name="apellidoPaterno",length=40)
    @Basic
    private String secondLastName;


    @Column(name="fechaCreacion")
    @Basic
    private Date createDate;


    @Column(name="apellidoMaterno",length=60)
    @Basic
    private String FirstLastName;

    public Person(){
        this.looged = false;

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public String getEmail() {
        return this.email;
    }


  public void setEmail (String email) {
        this.email = email;
    }



   public String getStatus() {
        return this.status;
    }


  public void setStatus (String status) {
        this.status = status;
    }



   public Date getLastUpdate() {
        return this.lastUpdate;
    }


  public void setLastUpdate (Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }



   public String getName() {
        return this.name;
    }


  public void setName (String name) {
        this.name = name;
    }



   public String getAction() {
        return this.action;
    }


  public void setAction (String action) {
        this.action = action;
    }



   public String getSecondLastName() {
        return this.secondLastName;
    }


  public void setSecondLastName (String secondLastName) {
        this.secondLastName = secondLastName;
    }



   public Date getCreateDate() {
        return this.createDate;
    }


  public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }



   public String getFirstLastName() {
        return this.FirstLastName;
    }


  public void setFirstLastName (String FirstLastName) {
        this.FirstLastName = FirstLastName;
    }

    public boolean isLooged() {
        return looged;
    }

    public void setLooged(boolean looged) {
        this.looged = looged;
    }
  
  

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Person other = (Person) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}

