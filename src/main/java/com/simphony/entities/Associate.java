package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name="Associate")
@Table(name="Agremiado")
public  class Associate extends Person implements Serializable, Cloneable {

    @Column(name="plataforma",length=10)
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

    public void update(Associate associateUpdated){
        super.update(associateUpdated);
        this.platform = associateUpdated.getPlatform();
        this.keyId = associateUpdated.getKeyId();
        this.city = associateUpdated.getCity();
        this.phoneId = associateUpdated.getPhoneId();
        this.section = associateUpdated.getSection();
        this.state = associateUpdated.getState();
        this.user = associateUpdated.getUser();
    }
    
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
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
        if ((this.keyId == null) ? (other.keyId != null) : !this.keyId.equals(other.keyId)) {
            return false;
        }
        return true;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
  

}

