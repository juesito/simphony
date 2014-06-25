/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.SalePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class SalePointService {
    
    @Autowired
    private SalePointRepository salePointRepository;

    public SalePointRepository getSalePointRepository() {
        return salePointRepository;
    }

    public void setSalePointRepository(SalePointRepository salePointRepository) {
        this.salePointRepository= salePointRepository;
    }
        
}
