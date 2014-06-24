/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.AssociateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class AssociateService {
    
    @Autowired
    private AssociateRepository associateRepository;

    public AssociateRepository getAssociateRepository() {
        return associateRepository;
    }

    public void setAssociateRepository(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }
        
}
