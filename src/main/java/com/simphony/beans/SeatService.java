/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Soporte IT
 */
@Component
public class SeatService {
    
    @Autowired
    private SeatRepository repository;

    public SeatRepository getRepository() {
        return repository;
    }

    public void setRepository(SeatRepository repository) {
        this.repository = repository;
    }
    
    
    
}
