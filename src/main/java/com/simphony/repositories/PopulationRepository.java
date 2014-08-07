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

    /*Obtenemos las poblaciones por descripción*/
    @Query("SELECT p FROM Population p WHERE p.description = (:description)")
    public Population findByDesc(@Param("description") String description);
    
    /*Obtenemos las poblaciones origen en itinerarios
  
    */
    @Query("SELECT distinct i.origin FROM Itinerary i  " + 
             " WHERE i.status = 'A' ")
    public List<Population> findOriginsForSale();
    
    @Query("SELECT p " +
            "  FROM Population p " +
            "  WHERE p.id IN (SELECT j.destiny.id " +
            "        FROM Itinerary i, Itinerary j " +
            "        WHERE (j.route.id = i.id OR j.id = i.id) " +
            "          AND i.origin.id = :originId AND j.status = 'A') " +
            "    and p.status = 'A' " +
            "  ORDER BY p.description ")
    public List<Population> findDestiniesForSale(@Param("originId") Long originId);

}
