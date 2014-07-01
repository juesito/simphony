/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.UserRepository;
import com.simphony.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class VendorService {
    
    @Autowired
    private VendorRepository vendorRepository;

    public VendorRepository getVendorRepository() {
        return vendorRepository;
    }

    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
    
}
