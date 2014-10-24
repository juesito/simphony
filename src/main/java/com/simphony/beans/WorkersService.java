/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.WorkersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class WorkersService {
    
    @Autowired
    private WorkersRepository repository;

    public WorkersRepository getRepository() {
        return repository;
    }

    public void setRepository(WorkersRepository repository) {
        this.repository = repository;
    }
        
}
