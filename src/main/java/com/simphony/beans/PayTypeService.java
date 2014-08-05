/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.PayTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Soporte IT
 */
@Component
public class PayTypeService {
    
    @Autowired
    PayTypeRepository payTypeRepository;

    public PayTypeRepository getPayTypeRepository() {
        return payTypeRepository;
    }

    public void setPayTypeRepository(PayTypeRepository payTypeRepository) {
        this.payTypeRepository = payTypeRepository;
    }
    
    
    
}
