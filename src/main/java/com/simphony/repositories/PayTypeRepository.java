/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.PayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Soporte IT
 */
public interface PayTypeRepository extends JpaRepository<PayType, Long> {
   
      @Query("SELECT b FROM PayType b WHERE b.description = (:description)")
    public PayType findByDes(@Param("description") String description);

}
