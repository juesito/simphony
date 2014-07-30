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
    private ItineraryDetail itineraryDetail;
    private Cost cost;
    private Integer rowId = 1;

    public ItineraryCost() {
        
    }

    
    public ItineraryCost(Itinerary itinerary, Cost cost) {
        this.itinerary = itinerary;
        this.cost = cost;
        this.rowId++;
    }

    public ItineraryCost(ItineraryDetail itineraryDetail, Cost cost) {
        this.itineraryDetail = itineraryDetail;
        this.cost = cost;
        this.rowId++;
    }
    
    
    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public ItineraryDetail getItineraryDetail() {
        return itineraryDetail;
    }

    public void setItineraryDetail(ItineraryDetail itineraryDetail) {
        this.itineraryDetail = itineraryDetail;
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

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }
    
    
    
}
