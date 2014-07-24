package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name="rutas")
public class Route implements Serializable, Cloneable {

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

    
    
    @ManyToOne(targetEntity = Bus.class)
    private Bus bus;

    @ManyToOne(targetEntity = DriverMan.class)
    private DriverMan driverMan;

    @Column(name = "status", length = 1)
    @Basic
    private String status;
 
    @Column(name="totalVta")
    @Basic
    private float total;
    
    @Column(name="totalPas")
    @Basic
    private float passengers;
    
    @OneToOne(targetEntity = User.class)
    private User user;

    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastUpdated;


    @Transient
    private String action;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getPassengers() {
        return passengers;
    }

    public void setPassengers(float passengers) {
        this.passengers = passengers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
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

        public void update(Route routeUpdated) {
            this.bus = routeUpdated.bus;
            this.driverMan = routeUpdated.driverMan;
            this.user = routeUpdated.user;
    }

    
}
