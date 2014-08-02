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
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity(name = "Itinerary")
@Table(name = "Itinerarios")
public class Itinerary implements Serializable, Cloneable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;
    
    @OneToOne(targetEntity = User.class)
    private User user;
    
    @ManyToOne(targetEntity = Population.class)
    private Population destiny;
    
    @ManyToOne(targetEntity = Population.class)
    private Population origin;

    @Column(name = "fechaCreacion")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createDate;

    @Column(name = "horaSalida")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date departureTime;

    @Column(name = "horaLlegada")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date checkTime;

    @Column(name = "estatus")
    @Basic
    private String status;
    
    @Column(name="ultimaModificacion")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastUpdated;

    @Transient
    private String action;

    public Itinerary() {

    }

    public Itinerary(Long id) {
        this.id = id;
    }
    
    
    
    @PreUpdate
    public void preUpdate(){
        setLastUpdated(new Date());
    }

    public void update(Itinerary itineraryUpdated) {
        this.checkTime = itineraryUpdated.checkTime;
        this.departureTime = itineraryUpdated.departureTime;
        this.destiny = itineraryUpdated.destiny;
        this.origin = itineraryUpdated.origin;
        this.user = itineraryUpdated.user;
        this.status = itineraryUpdated.status;
        this.createDate = itineraryUpdated.createDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Population getDestiny() {
        return this.destiny;
    }

    public void setDestiny(Population destiny) {
        this.destiny = destiny;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }   

    public Population getOrigin() {
        return origin;
    }

    public void setOrigin(Population origin) {
        this.origin = origin;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
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
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Itinerary other = (Itinerary) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
