package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Cost")
@Table(name = "Tarifas")
public class Cost extends Catalog implements Serializable, Cloneable {

    @Column(name = "routeTime")
    @Basic
    private float routeTime;

    @Column(name = "kms", length = 4)
    @Basic
    private int kms;

    @ManyToOne(targetEntity = Population.class)
    private Population origin;

    @ManyToOne(targetEntity = Population.class)
    private Population destiny;

    @Column(name = "cost", length = 4)
    @Basic
    private int cost;

    public Cost() {

    }

    public float getRouteTime() {
        return this.routeTime;
    }

    public void setRouteTime(float routeTime) {
        this.routeTime = routeTime;
    }

    public int getKms() {
        return this.kms;
    }

    public void setKms(int kms) {
        this.kms = kms;
    }

    public Population getOrigin() {
        return this.origin;
    }

    public void setOrigin(Population origin) {
        this.origin = origin;
    }

    public Population getDestiny() {
        return this.destiny;
    }

    public void setDestiny(Population destiny) {
        this.destiny = destiny;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
