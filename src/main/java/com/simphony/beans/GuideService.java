/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jueser
 */
@Component
public class GuideService {
    
    @Autowired
    GuideRepository repository;

    public GuideRepository getRepository() {
        return repository;
    }

    public void setRepository(GuideRepository repository) {
        this.repository = repository;
    }
        
}
