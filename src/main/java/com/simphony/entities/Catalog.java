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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Administrador
 */
@MappedSuperclass
public abstract class Catalog implements Serializable{
    @Column(name="id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Transient
    private String action;
    
    @Column(name="descripcion",length=60)
    @Basic
    private String description;


    @Column(name="fechaCreacion")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createDate;
    
     @Column(name="estatus")
    @Basic
    private String status;


    @OneToOne(targetEntity=User.class)
    private User user;
    
    @Column(name="ultimaModificacion")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastUpdate;
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Catalog other = (Catalog) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    public String getAction() {
        return this.action;
    }


  public void setAction (String action) {
        this.action = action;
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

    public void update(Catalog catalogUpdated){
        this.user = catalogUpdated.getUser();
        this.description = catalogUpdated.getDescription();
        this.status = catalogUpdated.getStatus();
    }
    
}
