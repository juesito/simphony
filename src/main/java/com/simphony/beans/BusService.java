/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Soporte IT
 */
@Component
public class BusService {
    
    @Autowired
    BusRepository busRepository;

    public BusRepository getBusRepository() {
        return busRepository;
    }

    public void setBusRepository(BusRepository busRepository) {
        this.busRepository = busRepository;
    }
    
    
    
}
