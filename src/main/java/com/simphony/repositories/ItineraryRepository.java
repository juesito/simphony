/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author root
 */
public interface ItineraryRepository extends JpaRepository<Itinerary, Long>{
    
}
