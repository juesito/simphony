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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Jueser
 */
@Entity(name="Guide")
@Table(name = "Guias")
public class Guide  extends Catalog implements Serializable, Cloneable{
    
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    @Column(name="referencia")
    @Basic(optional=true)
    private String guideReference;
    
    @Column(name = "fechaSalida")    
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date departureDate;
            
    @Column(name = "horaSalida")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date departureTime;

    @ManyToOne(targetEntity=Population.class)
    private Population origin;
    
    @ManyToOne(targetEntity=Population.class)
    private Population destiny;
    
    
    @ManyToOne(targetEntity = Bus.class)
    private Bus bus;
    
    @ManyToOne(targetEntity = DriverMan.class)
    private DriverMan driverMan1;

    @ManyToOne(targetEntity = DriverMan.class)
    private DriverMan driverMan2;

    @Transient
    private boolean newGuide;

    public Guide() {
        this.newGuide = false;
    }

    public Guide(boolean newGuide) {
        this.newGuide = newGuide;
    }
    
    @PreUpdate
    public void preUpdate(){
        setCreateDate(new Date());
    }
    
    public void update(Guide guideUpdated){
        super.update(guideUpdated);
        this.bus = guideUpdated.getBus();
        this.driverMan1 = guideUpdated.getDriverMan1();
        this.driverMan2 = guideUpdated.getDriverMan2();
    }

 
    public String getGuideReference() {
        return guideReference;
    }

    public void setGuideReference(String guideReference) {
        this.guideReference = guideReference;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }


    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public DriverMan getDriverMan1() {
        return driverMan1;
    }

    public void setDriverMan1(DriverMan driverMan1) {
        this.driverMan1 = driverMan1;
    }

    public DriverMan getDriverMan2() {
        return driverMan2;
    }

    public void setDriverMan2(DriverMan driverMan2) {
        this.driverMan2 = driverMan2;
    }

    public boolean isNewGuide() {
        return newGuide;
    } 

    public void setNewGuide(boolean newGuide) {
        this.newGuide = newGuide;
    }

    public Population getOrigin() {
        return origin;
    }

    public void setOrigin(Population origin) {
        this.origin = origin;
    }

    public Population getDestiny() {
        return destiny;
    }

    public void setDestiny(Population destiny) {
        this.destiny = destiny;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
}
