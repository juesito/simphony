/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Population;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Soporte IT
 */
public interface PopulationRepository extends JpaRepository<Population, Long> {
    
    @Query("SELECT p FROM Population p "
        + "WHERE UPPER(p.status) = UPPER('A')")
    public List<Population> findAllEnabled();
    
        @Query("SELECT p FROM Population p WHERE p.description = (:description)")
    public Population findByDesc(@Param("description") String description);
    
    @Query("SELECT DISTINCT p FROM Itinerary a, ItineraryDetail d, Population p " +
            " WHERE (a.origin.id = p.id OR " +
            "       d.origin.id = p.id) " +
            "   AND p.status = 'A'")
    public List<Population> findAllOriginsForSale();

}
