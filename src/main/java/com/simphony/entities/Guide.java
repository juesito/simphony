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
    private Date departureDate;
            
    @ManyToOne(targetEntity=Population.class)
    private Population origin;
    
    @ManyToOne(targetEntity=Population.class)
    private Population destiny;
    
    
    @ManyToOne(targetEntity = Bus.class)
    private Bus bus;
    
    @ManyToOne(targetEntity = DriverMan.class)
    private DriverMan driverMan;
    
    @ManyToOne(targetEntity = Vendor.class)
    private Vendor vendor;

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
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Guide{" + "id=" + id + ", guideReference=" + guideReference + ", status=" + status + ", createDate=" + createDate + ", departureDate=" + departureDate + ", origin=" + origin + ", destiny=" + destiny + ", bus=" + bus + ", driverMan=" + driverMan + ", vendor=" + vendor + ", newGuide=" + newGuide + '}';
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
