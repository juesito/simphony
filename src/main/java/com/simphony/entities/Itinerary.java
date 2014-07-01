package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="Itinerary")
@Table(name="Itinerarios")
public  class Itinerary implements Serializable {

    @Column(name="id")
    @Id
    private Long id;


    @Basic
    private int secuence;


    @Id@OneToOne(targetEntity=User.class)
    private User user1;


    @Id@OneToOne(targetEntity=Population.class)
    private Population destiny;


    @Basic
    private Date createDate;

    public Itinerary(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public int getSecuence() {
        return this.secuence;
    }


  public void setSecuence (int secuence) {
        this.secuence = secuence;
    }



   public User getUser1() {
        return this.user1;
    }


  public void setUser1 (User user1) {
        this.user1 = user1;
    }



   public Population getDestiny() {
        return this.destiny;
    }


  public void setDestiny (Population destiny) {
        this.destiny = destiny;
    }



   public Date getCreateDate() {
        return this.createDate;
    }


  public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Itinerary other = (Itinerary) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

  
}

