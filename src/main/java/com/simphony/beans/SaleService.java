/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jueser
 */
@Component
public class SaleService {
    
    @Autowired
    SaleRepository saleRepository;

    public SaleRepository getSaleRepository() {
        return saleRepository;
    }

    public void setSaleRepository(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }
    
    
}
