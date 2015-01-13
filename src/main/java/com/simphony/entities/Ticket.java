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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Soporte IT
 */
@Table(name="boletos")
@Entity(name="Ticket")
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="folioVenta")
    @Basic
    private Long saleId;
    @Column(name="fechaViaje")
    @Basic
    private String travelDate;
    @Column(name="horaViaje")
    @Basic
    private String travelHour;
    @Column(name="origen")
    @Basic
    private String origin;
    @Column(name="destino")
    @Basic
    private String destiny;
    @Column(name="asiento")
    @Basic
    private String seatNumber;
    @Column(name="pasajero")
    @Basic
    private String passenger;
    @Column(name="vendedor")
    @Basic
    private String vendorName;
    @Column(name="fechaVenta")
    @Basic
    private String saleDate;
    @Column(name="horaVenta")
    @Basic
    private String saleHour;
    @Column(name="nota")
    @Basic
    private String nota;
    @Column(name = "fechaImpresion")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date printDate;

    public Ticket() {
        printDate = new Date();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.printDate = new Date();
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getTravelHour() {
        return travelHour;
    }

    public void setTravelHour(String travelHour) {
        this.travelHour = travelHour;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getSaleHour() {
        return saleHour;
    }

    public void setSaleHour(String saleHour) {
        this.saleHour = saleHour;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }
    
    
    

    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.simphony.entities.Ticket[ id=" + id + " ]";
    }
    
}
