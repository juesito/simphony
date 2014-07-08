package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ruta")
public class Route implements Serializable {

    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "fechaInicial")
    @Basic
    private Date startDate;

    @Column(name = "distancia")
    @Basic
    private float distance;

    @Column(name = "horaLLegada")
    @Basic
    private Date arriveTime;

    @Column(name = "fechaFinal")
    @Basic
    private Date finalDate;

    @ManyToOne(targetEntity = Population.class)
    private Population origin;

    @Column(name = "via", length = 20)
    @Basic
    private String track;

    @ManyToOne(targetEntity = Itinerary.class)
    private Itinerary itinerary;

    @ManyToOne(targetEntity = Population.class)
    private Population destiny;

    @Column(name = "hora")
    @Basic
    private Date hour;

    @Column(name = "fechaCreacion")
    @Basic
    private Date createDate;

    @Column(name = "unidadDistancia", length = 5)
    @Basic
    private String distanceUnit;

    @Column(name = "tipo", length = 2)
    @Basic
    private String type;

    public Route() {

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Date getArriveTime() {
        return this.arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getFinalDate() {
        return this.finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public Population getOrigin() {
        return this.origin;
    }

    public void setOrigin(Population origin) {
        this.origin = origin;
    }

    public String getTrack() {
        return this.track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public Itinerary getItinerary() {
        return this.itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Population getDestiny() {
        return this.destiny;
    }

    public void setDestiny(Population destiny) {
        this.destiny = destiny;
    }

    public Date getHour() {
        return this.hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDistanceUnit() {
        return this.distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Route other = (Route) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
