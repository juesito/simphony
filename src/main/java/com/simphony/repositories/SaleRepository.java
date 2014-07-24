/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jueser
 */
public interface SaleRepository extends JpaRepository<Sale, Long>{
    
}
