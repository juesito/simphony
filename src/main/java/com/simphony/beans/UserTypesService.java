/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.UserTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class UserTypesService {
    
    @Autowired
    private UserTypesRepository userTypesRepository;

    public UserTypesRepository getUserTypesRepository() {
        return userTypesRepository;
    }

    public void setUserTypesRepository(UserTypesRepository userTypesRepository) {
        this.userTypesRepository = userTypesRepository;
    }
    
    
    
}
