/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class ItineraryService {
    
    @Autowired
    ItineraryRepository itineraryRepository;

    public ItineraryRepository getItineraryRepository() {
        return itineraryRepository;
    }

    public void setItineraryRepository(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }
    
    
}
