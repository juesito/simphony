/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.PopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Soporte IT
 */
@Component
public class PopulationService {
    
    @Autowired
    PopulationRepository populationRepository;

    public PopulationRepository getPopulationRepository() {
        return populationRepository;
    }

    public void setPopulationRepository(PopulationRepository populationRepository) {
        this.populationRepository = populationRepository;
    }
    
    
    
}
