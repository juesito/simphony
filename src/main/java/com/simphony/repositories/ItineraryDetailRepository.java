/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.ItineraryDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrador
 */
public interface ItineraryDetailRepository extends JpaRepository<ItineraryDetail, Long> {
    @Query("SELECT a FROM ItineraryDetail a WHERE a.itinerary.id = (:itineraryId) ")
    public List<ItineraryDetail> findAllByItinerary(@Param("itineraryId") Long itineraryId);
}
