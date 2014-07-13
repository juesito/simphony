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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author root
 */
@Entity(name = "ItineraryDetail")
@Table(name = "detalleItinerarios")
public class ItineraryDetail implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @OneToOne(targetEntity = Itinerary.class)
    private Itinerary itineraryId;

    @Id
    @OneToOne(targetEntity = Population.class)
    private Population destiny;

    @Id
    @OneToOne(targetEntity = Population.class)
    private Population origin;

    @Basic
    @Column(name = "tipoRuta")
    private String typeOfRoute;

    @Column(name = "horasDesdeOrigen")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date hoursFromOrigin;

    @Column(name = "horasADestino")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date hoursToDestiny;

    @Column(name = "estatus")
    @Basic
    private String status;

    public ItineraryDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Itinerary getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Itinerary itineraryId) {
        this.itineraryId = itineraryId;
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

    public Date getHoursFromOrigin() {
        return hoursFromOrigin;
    }

    public void setHoursFromOrigin(Date hoursFromOrigin) {
        this.hoursFromOrigin = hoursFromOrigin;
    }

    public Date getHoursToDestiny() {
        return hoursToDestiny;
    }

    public void setHoursToDestiny(Date hoursToDestiny) {
        this.hoursToDestiny = hoursToDestiny;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.itineraryId != null ? this.itineraryId.hashCode() : 0);
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
        if (this.itineraryId != other.itineraryId && (this.itineraryId == null || !this.itineraryId.equals(other.itineraryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItineraryDetail{" + "id=" + id + ", itineraryId=" + itineraryId + ", destiny=" + destiny + ", origin=" + origin + ", typeOfRoute=" + typeOfRoute + ", hoursFromOrigin=" + hoursFromOrigin + ", hoursToDestiny=" + hoursToDestiny + ", status=" + status + '}';
    }
    
    

}
