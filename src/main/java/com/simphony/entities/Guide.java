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

/**
 *
 * @author Jueser
 */
@Entity(name="Guide")
@Table(name = "Guias")
public class Guide  implements Serializable, Cloneable{
    
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;
    
    @Column(name="referencia")
    @Basic(optional=true)
    private String guideReference;
    
    @Column(name="estaus")
    @Basic(optional=true)
    private String status;
    
    @Column(name = "fechaCompra")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createDate;
    
    @Column(name = "fechaSalida")    
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date checkDate;
            
    @ManyToOne(targetEntity = Itinerary.class)
    private Itinerary itinerary;
    
    @ManyToOne(targetEntity = ItineraryDetail.class)
    private ItineraryDetail itineraryDetail;    
    
    @ManyToOne(targetEntity = Bus.class)
    private Bus bus;
    
    @ManyToOne(targetEntity = DriverMan.class)
    private DriverMan driverMan;
    
    @ManyToOne(targetEntity = Vendor.class)
    private Vendor vendor;

    @PreUpdate
    public void preUpdate(){
        setCreateDate(new Date());
        
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuideReference() {
        return guideReference;
    }

    public void setGuideReference(String guideReference) {
        this.guideReference = guideReference;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public ItineraryDetail getItineraryDetail() {
        return itineraryDetail;
    }

    public void setItineraryDetail(ItineraryDetail itineraryDetail) {
        this.itineraryDetail = itineraryDetail;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public DriverMan getDriverMan() {
        return driverMan;
    }

    public void setDriverMan(DriverMan driverMan) {
        this.driverMan = driverMan;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Guide{" + "id=" + id + ", guideReference=" + guideReference + ", purchaseDate=" + createDate + ", checkDate=" + checkDate + ", itinerary=" + itinerary + ", itineraryDetail=" + itineraryDetail + ", bus=" + bus + ", driverMan=" + driverMan + '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Guide other = (Guide) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
}
