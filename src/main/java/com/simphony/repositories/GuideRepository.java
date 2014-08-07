/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Guide;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrador
 */
public interface GuideRepository extends JpaRepository<Guide, Long> {
    
    @Query("SELECT g FROM Guide g WHERE g.itinerary.id = :itineraryId AND g.departureDate = :departureDate")
    public Guide findByItineraryAndDate(@Param("itineraryId")Long itineraryId, @Param("departureDate")Date departureDate);
}
