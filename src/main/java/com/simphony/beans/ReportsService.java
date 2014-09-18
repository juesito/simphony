/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Soporte IT
 */
@Component
public class ReportsService {
    
    @Autowired
    ReportsRepository reportsRepository;

    public ReportsRepository getReportsRepository() {
        return reportsRepository;
    }

    public void setReportsRepository(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }
    
    
    
}
