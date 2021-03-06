package com.simphony.entities;

import static com.simphony.interfases.IConfigurable._SHM;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity(name = "Cost")
@Table(name = "Tarifas")
public class Cost extends Catalog implements Serializable, Cloneable {

    @Column(name = "routeTime")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date routeTime;

    @Column(name = "kms", length = 4)
    @Basic
    private int kms;

    @ManyToOne(targetEntity = Population.class)
    private Population origin;

    @ManyToOne(targetEntity = Population.class)
    private Population destiny;

    @Column(name = "cost", length = 4)
    @Basic
    private double cost;

    public Cost() {

    }

    public void update(Cost costUpdated){
        super.update(costUpdated);
        this.origin = costUpdated.getOrigin();
        this.destiny = costUpdated.getDestiny();
        this.cost = costUpdated.getCost();
        this.kms = costUpdated.getKms();
        this.routeTime = costUpdated.getRouteTime();
     }
    
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    
    public Date getRouteTime() {
        return this.routeTime;
    }

    public void setRouteTime(Date routeTime) {
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public String getFormatRouteTime(){
    if(this.routeTime != null){
       return _SHM.format(this.routeTime);
    }else return "";
    }

}
