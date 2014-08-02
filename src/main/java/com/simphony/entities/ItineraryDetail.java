/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author root
 */
@Entity(name = "ItineraryDetail")
@Table(name = "DetalleItinerarios")
public class ItineraryDetail implements Serializable, Cloneable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne(targetEntity = Itinerary.class)
    private Itinerary itinerary;

    @ManyToOne(targetEntity = Population.class)
    private Population destiny;

    @ManyToOne(targetEntity = Population.class)
    private Population origin;

    @Basic
    @Column(name = "tipoRuta")
    private String typeOfRoute;

    @Column(name = "estatus")
    @Basic
    private String status;
    
    @Column(name = "secuencia")
    @Basic
    private Integer sequence;
    
    @Column(name = "horaSalida")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date departureTime;
    
    @Transient
    private String action;

    public ItineraryDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Population getDestiny() {
        return destiny;
    }

    public void setDestiny(Population destiny) {
        this.destiny = destiny;
    }

    public Population getOrigin() {
        return origin;
    }

    public void setOrigin(Population origin) {
        this.origin = origin;
    }

    public String getTypeOfRoute() {
        return typeOfRoute;
    }

    public void setTypeOfRoute(String typeOfRoute) {
        this.typeOfRoute = typeOfRoute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    
    
    
    public void update(ItineraryDetail itineraryDetailUpdated) {
        this.destiny = itineraryDetailUpdated.destiny;
        this.origin = itineraryDetailUpdated.origin;
        this.status = itineraryDetailUpdated.status;
        this.typeOfRoute = itineraryDetailUpdated.typeOfRoute;
        this.itinerary = itineraryDetailUpdated.itinerary;
        this.departureTime = itineraryDetailUpdated.departureTime;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.itinerary != null ? this.itinerary.hashCode() : 0);
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
        final ItineraryDetail other = (ItineraryDetail) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.itinerary != other.itinerary && (this.itinerary == null || !this.itinerary.equals(other.itinerary))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItineraryDetail{" + "id=" + id + ", itinerary=" + itinerary + ", destiny=" + destiny + ", origin=" + origin + ", typeOfRoute=" + typeOfRoute + ", status=" + status + ", sequence=" + sequence + ", departureTime=" + departureTime + ", action=" + action + '}';
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    
}
