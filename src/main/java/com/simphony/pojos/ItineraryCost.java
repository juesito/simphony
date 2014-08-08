/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.pojos;

import com.simphony.entities.Cost;
import com.simphony.entities.Itinerary;
import com.simphony.entities.ItineraryDetail;
import java.util.Date;

/**
 *
 * @author root
 */
public class ItineraryCost {

    private Itinerary itinerary;
    private Itinerary alternateItinerary;
    private Cost cost;
    private Integer rowId = 1;

    public ItineraryCost() {

    }

    public ItineraryCost(Itinerary itinerary, Cost cost) {
        this.itinerary = itinerary;
        this.cost = cost;
        this.rowId++;
        Date date = new Date();
        Long dateL = date.getTime();
        rowId = rowId * dateL.intValue();

    }

    public ItineraryCost(Itinerary itinerary, Cost cost, Itinerary alternateItinerary) {
        this.itinerary = itinerary;
        this.alternateItinerary = alternateItinerary;
        this.cost = cost;
        this.rowId++;
        Date date = new Date();
        Long dateL = date.getTime();
        rowId = rowId * dateL.intValue();
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

    public void setRowId(Integer rowId) {
        Date date = new Date();
        Long dateL = date.getTime();
        rowId = rowId * dateL.intValue();
        System.out.println("ROWID -->" + rowId);
        this.rowId = rowId;
    }

}
