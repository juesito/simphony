/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.ItineraryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrador
 */
@Component
public class ItineraryDetailService {
    
    @Autowired
    private ItineraryDetailRepository itineraryDetailRepository;

    public ItineraryDetailRepository getItineraryDetailRepository() {
        return itineraryDetailRepository;
    }

    public void setItineraryDetailRepository(ItineraryDetailRepository itineraryDetailRepository) {
        this.itineraryDetailRepository = itineraryDetailRepository;
    }
    
    
    
}
