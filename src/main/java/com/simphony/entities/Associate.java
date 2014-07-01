package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="Associate")
@Table(name="Agremiado",schema="simphonybd")
public  class Associate extends Person implements Serializable {


    @Basic
    private String platform;


    @Column(name="estado",length=50)
    @Basic
    private String state;


    @Column(name="telefono",length=10)
    @Basic
    private String phoneId;


    @OneToOne(targetEntity=User.class)
    private User user;


    @Column(name="seccion",length=15)
    @Basic
    private String section;


    @Column(name="NoFicha",length=10)
    @Basic
    private String keyId;


    @Column(name="ciudad",length=50)
    @Basic
    private String city;

    public Associate(){

    }


   public String getPlatform() {
        return this.platform;
    }


  public void setPlatform (String platform) {
        this.platform = platform;
    }



   public String getState() {
        return this.state;
    }


  public void setState (String state) {
        this.state = state;
    }



   public String getPhoneId() {
        return this.phoneId;
    }


  public void setPhoneId (String phoneId) {
        this.phoneId = phoneId;
    }



   public User getUser() {
        return this.user;
    }


  public void setUser (User user) {
        this.user = user;
    }



   public String getSection() {
        return this.section;
    }


  public void setSection (String section) {
        this.section = section;
    }



   public String getKeyId() {
        return this.keyId;
    }


  public void setKeyId (String keyId) {
        this.keyId = keyId;
    }



   public String getCity() {
        return this.city;
    }


  public void setCity (String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.platform != null ? this.platform.hashCode() : 0);
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
        final Associate other = (Associate) obj;
        if ((this.platform == null) ? (other.platform != null) : !this.platform.equals(other.platform)) {
            return false;
        }
        return true;
    }
  

}

