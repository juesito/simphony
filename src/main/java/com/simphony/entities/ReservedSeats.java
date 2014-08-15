/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity(name = "ReservedSeats")
@Table(name = "AsientosReservados")
public class ReservedSeats implements Serializable, Cloneable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guia")
    private Long guideId;

    @ManyToOne(targetEntity = Seat.class)
    private Seat seat;

    @Column(name = "ruta")
    private Long routeId;

    @Column(name = "tipoRuta")
    private String routeType;

    @Column(name = "inicio")
    private Integer initialSequence;

    @Column(name = "final")
    private Integer finalSequence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuideId() {
        return guideId;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public Integer getInitialSequence() {
        return initialSequence;
    }

    public void setInitialSequence(Integer initialSequence) {
        this.initialSequence = initialSequence;
    }

    public Integer getFinalSequence() {
        return finalSequence;
    }

    public void setFinalSequence(Integer finalSequence) {
        this.finalSequence = finalSequence;
    }

    @Override
    public String toString() {
        return "ReservedSeats{" + "id=" + id + ", guideId=" + guideId + ", seatId=" + seat.getSeat() + ", routeId=" + routeId + ", routeType=" + routeType + ", initialSequence=" + initialSequence + ", finalSequence=" + finalSequence + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ReservedSeats other = (ReservedSeats) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
