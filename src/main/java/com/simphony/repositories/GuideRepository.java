/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Bus;
import com.simphony.entities.DriverMan;
import com.simphony.entities.Guide;
import com.simphony.entities.SaleDetail;
import java.util.Date;
import java.util.List;
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
         "    AND g.rootRoute = :routeId" )
    public Guide findByItineraryAndDate(@Param("origin")Long origin, @Param("destiny")Long destiny, 
            @Param("departureDate")Date departureDate, @Param("routeId")Long routeId);
    
    @Query("SELECT g FROM Guide g " +
         "  WHERE g.origin.id = :origin " +
         "    AND g.destiny.id = :destiny " +
         "    AND g.rootRoute = :routeId" )
    public Guide findByItineraryAndDate2(@Param("origin")Long origin, @Param("destiny")Long destiny, 
            @Param("routeId")Long routeId);

    @Query("SELECT d from SaleDetail d " +
       " WHERE d.sale.id IN  ( SELECT v.id FROM GuideDetail g, Sale v " +
       " WHERE g.sale.id = v.id AND g.guide.id = :guideId) " +
       " AND d.status != 'C' ORDER BY d.seat.id"  )
    public List<SaleDetail> qryGuideDetail(@Param("guideId")Long guideId);

//    @Query("SELECT d from SaleDetail d " +
//       " WHERE d.sale.tripDate = :departureDate AND d.sale.idRoute = :idRoute " +
//       " AND d.status = 'V' ORDER BY d.seat.id"  )
//    public List<SaleDetail> qryGuideDetailLocal(@Param("departureDate")Date departureDate, @Param("idRoute")Long id);
       
    @Query("SELECT g FROM Guide g " +
         "  WHERE g.rootRoute = :routeId " +
         "    AND g.departureDate = :departureDate " +
         "    AND g.guideType = 'L' ")
    public Guide findRootGuide(@Param("routeId")Long routeId, @Param("departureDate")Date departureDate);

    @Query("SELECT g FROM Guide g " +
         "  WHERE g.guideType = 'L' ")
    public List<Guide>findAllLocal();
    
    @Query("UPDATE Guide g " +
         "SET  g.quota = :quota ")
    public void updateGuide(@Param("quota")Integer quota);

    @Query("SELECT status from Guide g " +
       " WHERE g.id IN  ( SELECT d.guide.id FROM GuideDetail d " +
       " WHERE d.sale.id = :saleId) " )
    public String selectStatus(@Param("saleId")Long saleId);

}

