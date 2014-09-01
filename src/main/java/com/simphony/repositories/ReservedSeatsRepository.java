/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.repositories;

import com.simphony.entities.ReservedSeats;
import com.simphony.entities.Seat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrador
 */
public interface ReservedSeatsRepository extends JpaRepository<ReservedSeats, Long> {

    @Query("SELECT a FROM ReservedSeats a WHERE a.guideId = :guideId"
            + "  AND a.routeId = :routeId")
    public List<ReservedSeats> findAllReserved(@Param("guideId") Long guideId, @Param("routeId") Long routeId);

   
}
