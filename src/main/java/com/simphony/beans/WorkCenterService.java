/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.WorkCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class WorkCenterService {
    
    @Autowired
    WorkCenterRepository workCenterRepository;

    public WorkCenterRepository getWorkCenterRepository() {
        return workCenterRepository;
    }

    public void setWorkCenterRepository(WorkCenterRepository workCenterRepository) {
        this.workCenterRepository = workCenterRepository;
    }
    
}
