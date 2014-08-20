/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.pojos;

import com.simphony.entities.Cost;
import com.simphony.entities.Itinerary;
import com.simphony.interfases.IConfigurable;
import java.util.Date;

/**
 *
 * @author root
 */
public class ItineraryCost implements IConfigurable {

    private Itinerary itinerary;
    private Itinerary alternateItinerary;
    private Cost cost;
    private Integer rowId;
    private Date departureTime;
    private Boolean normalMode;

    public ItineraryCost() {
        rowId = (int) (Math.random()*5000+1);
    }

    public ItineraryCost(Itinerary itinerary, Cost cost) {
        this.itinerary = itinerary;
        this.cost = cost;        
        rowId = (int) (Math.random()*5000+1);
        normalMode = true;

    }

    public ItineraryCost(Itinerary itinerary, Cost cost, Itinerary alternateItinerary) {
        this.itinerary = itinerary;
        this.alternateItinerary = alternateItinerary;
        this.cost = cost;
        rowId = (int) (Math.random()*5000+1);
        normalMode = false;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Itinerary getAlternateItinerary() {
        return alternateItinerary;
    }

    public void setAlternateItinerary(Itinerary alternateItinerary) {
        this.alternateItinerary = alternateItinerary;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ItineraryCost{" + "itinerary=" + itinerary + ",  cost=" + cost + '}';
    }

    public String getRowId() {

        String id = rowId.toString();

        return id;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Boolean isNormalMode() {
        return normalMode;
    }

    public void setNormalMode(Boolean normalMode) {
        this.normalMode = normalMode;
    }

    public String getFormatDepartureHourTime() {
        return _SHM.format(this.departureTime);
    }

    public void setRowId(Integer rowId) {        
        this.rowId = rowId;
    }
    
    public String getFormatDepartureTime(){
        if(this.departureTime != null){
            return _SHM.format(this.departureTime);
        }else return "";
        
    }

}
