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
    
    //Modificar
  @Query("SELECT g FROM Guide g " +
         "  WHERE g.origin.id = :origin " +
         "    AND g.destiny.id = :destiny " +
         "    AND g.departureDate = :departureDate" +
         "    AND g.rootRoute.id = :routeId" )
    public Guide findByItineraryAndDate(@Param("origin")Long origin, @Param("destiny")Long destiny, 
            @Param("departureDate")Date departureDate, @Param("routeId")Long routeId);
}

