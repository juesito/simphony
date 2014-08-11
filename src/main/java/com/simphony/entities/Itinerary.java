package com.simphony.entities;

import com.simphony.interfases.IConfigurable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity(name = "Itinerary")
@Table(name = "Itinerarios")
public class Itinerary extends Catalog implements IConfigurable, Serializable, Cloneable {

    @ManyToOne(targetEntity=Itinerary.class)
    private Itinerary route;

    @Column(name = "horaSalida")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date departureTime;

    @ManyToOne(targetEntity = Population.class)
    private Population origin;

    @ManyToOne(targetEntity = Population.class)
    private Population destiny;
 
    @Column(name = "secuencia")
    @Basic
    private Integer sequence;

    @Basic
    @Column(name = "tipoRuta")
    private String typeOfRoute;

    @Column(name = "horaLlegada")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date checkTime;


 
    public Itinerary() {

    }

    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public void update(Itinerary itineraryUpdated) {
        super.update(itineraryUpdated);
        this.route = itineraryUpdated.route;
        this.checkTime = itineraryUpdated.checkTime;
        this.departureTime = itineraryUpdated.departureTime;
        this.destiny = itineraryUpdated.destiny;
        this.origin = itineraryUpdated.origin;
        this.typeOfRoute = itineraryUpdated.typeOfRoute;
        this.sequence = itineraryUpdated.sequence;
    }

    public Population getDestiny() {
        return this.destiny;
    }

    public void setDestiny(Population destiny) {
        this.destiny = destiny;
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

    public Itinerary getRoute() {
        return route;
    }

    public void setRoute(Itinerary route) {
        this.route = route;
    }

        public String getTypeOfRoute() {
        return typeOfRoute;
    }

    public void setTypeOfRoute(String typeOfRoute) {
        this.typeOfRoute = typeOfRoute;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
 
    public String getFormatDepartureTime(){
        if(this.departureTime != null){
            return _SHM.format(this.departureTime);
        }else return "";
        
    }

    public String getFormatCheckTime(){
         if(this.checkTime != null){
            return _SHM.format(this.checkTime);
        }else return "";
    }

    public String getFormatTypeOfRoute(){
        String texto = "";
        if(this.typeOfRoute != null){
            if (this.typeOfRoute.equals("L")){
                texto = "Local";
            }else texto = "De paso";
        
            return texto;
        }else return texto;
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
