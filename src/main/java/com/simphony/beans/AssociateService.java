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
    private AssociateRepository repository;

    public AssociateRepository getRepository() {
        return repository;
    }

    public void setRepository(AssociateRepository repository) {
        this.repository = repository;
    }
        
}
