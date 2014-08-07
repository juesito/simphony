/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Sale;
import com.simphony.pojos.ItineraryCost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Jueser
 */
public interface SaleRepository extends JpaRepository<Sale, Long>{
    
    @Query("SELECT NEW com.simphony.pojos.ItineraryCost(i, c) " +
           " FROM Itinerary i, Cost c" +
           " WHERE i.origin.id = c.origin.id  " +
           "   AND i.destiny.id = c.destiny.id " +
           "   AND c.origin.id = (:originId) " +
           "   AND c.destiny.id = (:destinyId)" +
           "   AND c.status = 'A'")
    public List<ItineraryCost> findItineraryCost(@Param("originId")Long originId,@Param("destinyId") Long destinyId);
    
    @Query("SELECT NEW com.simphony.pojos.ItineraryCost(i, c) " +
           " FROM Itinerary i, Itinerary j, Cost c" +
           " WHERE i.origin.id = c.origin.id  " +
           "   AND j.destiny.id = c.destiny.id " +
           "   AND j.route.id = i.id " + 
           "   AND c.origin.id = (:originId) " +
           "   AND c.destiny.id = (:destinyId)" +
           "   AND c.status = 'A'")
    public List<ItineraryCost> findItineraryDetailCost(@Param("originId")Long originId,@Param("destinyId") Long destinyId);
    
}
