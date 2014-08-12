/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Seat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Soporte IT
 */
public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    @Query("SELECT a FROM Seat a WHERE a.status = 'A'")
    public List<Seat> findAllAvailable();
    
    
    
}
