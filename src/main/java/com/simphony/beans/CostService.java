/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class CostService {
    
    @Autowired
    private CostRepository costRepository;

    public CostRepository getCostRepository() {
        return costRepository;
    }

    public void setCostRepository(CostRepository costRepository) {
        this.costRepository = costRepository;
    }
    
    
}
