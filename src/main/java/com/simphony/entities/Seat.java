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
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Soporte IT
 */
@Entity(name="Seat")
@Table(name="Asientos")
public class Seat implements Serializable, Cloneable {
    
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(name="asiento")
    private String seat;
    
    @Basic
    @Column(name="estatus")
    private String status;
    
    @Basic
    @Column(name="fechaCreacion")
    @Temporal(value=TemporalType.DATE)
    private Date createDate;
    
    @Basic
    @Column(name="ultimaModificacion")
    @Temporal(value=TemporalType.DATE)
    private Date lastUpdated;
    
    @ManyToOne(targetEntity = User.class)
    private User user;

    @Transient
    private String action;

    public Seat() {
    }
    
    @PreUpdate
    public void preUpdate(){
        setLastUpdated(new Date());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public void update(Seat updatedSeat){
        this.id = updatedSeat.id;
        this.createDate = updatedSeat.createDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Seat other = (Seat) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Seat{" + "id=" + id + ", seat=" + seat + ", status=" + status + ", user=" + user + '}';
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
 
      public String getFormatStatus(){
        String texto = "";
        if(this.getStatus() != null){
            if (this.getStatus().equals("A")){
                texto = "Activo";
            }else texto = "Inactivo";
       
            return texto;
        }else return texto;
    }
    
}
