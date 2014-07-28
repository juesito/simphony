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
    
    /*Obtenemos las poblaciones origen en itinerarios*/
    @Query("SELECT distinct p FROM Population p, " + 
            " Itinerary i, ItineraryDetail d " + ""
            + " WHERE (p.id = i.origin.id " +
            "      OR p.id = d.origin.id) " +
            " AND ( UPPER(i.status) = UPPER('A') " +
            "   OR  UPPER(d.status) = UPPER('A')) " +
            " AND p.status = UPPER('A') ")
    public List<Population> findOriginsForSale();
    
    /*Obtenemos las poblaciones destino en itinerarios*/
    @Query("SELECT distinct p FROM Population p, " + 
            " Itinerary i, ItineraryDetail d " + ""
            + " WHERE (p.id = i.destiny.id " +
            "      OR p.id = d.destiny.id) " +
            " AND ( UPPER(i.status) = UPPER('A') " +
            "   OR  UPPER(d.status) = UPPER('A')) " +
            " AND i.origin.id = (:originId) " +
            " AND p.status = UPPER('A') ")
    public List<Population> findDestiniesForSale(@Param("originId") Long originId);

}
