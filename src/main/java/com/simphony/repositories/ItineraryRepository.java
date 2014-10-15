/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.repositories;

import com.simphony.entities.Itinerary;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author root
 */
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    @Query("SELECT a FROM Itinerary a "
            + " WHERE a.departureTime = (:departureTime) AND a.origin.id = (:originId) "
            + "   AND a.destiny.id = (:destinyId)  AND a.route.id = (:routeId) ") 
    public Itinerary findByOriginAndDestiny(@Param("departureTime") Date departureTime, 
            @Param("originId") Long originId, @Param("destinyId") Long destinyId,
            @Param("routeId") Long routeId);

    @Query("SELECT a FROM Itinerary a WHERE a.typeOfRoute = 'L' ")
    public List<Itinerary> findAllLocal();

    @Query("SELECT a FROM Itinerary a WHERE a.destiny.id = :destiny and a.route.id = :route ")
    public Itinerary findForSequence(@Param("destiny") Long destiny, @Param("route") Long route);

    @Query("SELECT a FROM Itinerary a WHERE a.route.id = :routeId AND a.typeOfRoute = 'L' ")
    public Itinerary findByRouteId(@Param("routeId") Long routeId);

}
