/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Bus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Soporte IT
 */
public interface BusRepository extends JpaRepository<Bus, Long> {
   
    @Query("SELECT w FROM Bus w WHERE UPPER(w.status) = UPPER('A')")
    public List<Bus> findAllEnabled();

    
    @Query("SELECT b FROM Bus b WHERE b.number = (:number)")
    public Bus findByNum(@Param("number") String number);

}
