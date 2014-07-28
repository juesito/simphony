/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author root
 */
public interface CostRepository extends JpaRepository<Cost, Long>{

    @Query("SELECT c FROM Cost c "
         + " WHERE c.origin.id = (:originId) AND c.destiny.id = (:destinyId)")
    public Cost findByOriDes(@Param("originId") Long originId, @Param("destinyId") Long destinyId);
    
    


}
