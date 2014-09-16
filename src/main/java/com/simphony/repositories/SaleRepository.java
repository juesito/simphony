/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Sale;
import com.simphony.entities.SaleDetail;
import com.simphony.entities.Vendor;
import com.simphony.pojos.DailySales;
import com.simphony.pojos.ItineraryCost;
import java.util.Date;
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
           "   AND i.typeOfRoute IN ('L') " + 
           "   AND c.status = 'A'")
    public List<ItineraryCost> findItineraryCost(@Param("originId")Long originId,@Param("destinyId") Long destinyId);
    
    @Query("SELECT NEW com.simphony.pojos.ItineraryCost(i, c, j) " +
           " FROM Itinerary i, Itinerary j, Cost c" +
           " WHERE i.origin.id = c.origin.id    " +
           "   AND ((j.origin.id = c.destiny.id " +
           "          AND j.typeOfRoute = 'P'   " +
           "          AND i.typeOfRoute = 'L'  " +
           "          AND i.destiny.id <> c.destiny.id ) " +
           "    OR (j.destiny.id = c.destiny.id " +
           "          AND j.typeOfRoute = 'P'   " +
           "          AND i.typeOfRoute = 'P')) " +
           "   AND j.route.id = i.route.id " + 
           "   AND c.origin.id = (:originId) " +
           "   AND c.destiny.id = (:destinyId)" +
           "   AND (i.sequence <= j.sequence ) " +
           "   AND c.status = 'A'")
    public List<ItineraryCost> findItineraryDetailCost(@Param("originId")Long originId,@Param("destinyId") Long destinyId);
 
 
    @Query("SELECT d FROM SaleDetail d " +
         "  WHERE d.seat.seat = :seat AND d.sale.id IN ( SELECT s.id FROM Sale s WHERE s.origin.id = :origin " +
         "    AND s.destiny.id = :destiny " +
         "    AND s.tripDate = :tripDate) AND d.status = 'V' " )
    public SaleDetail findSeat(@Param("origin")Long origin, @Param("destiny")Long destiny, 
            @Param("tripDate")Date tripDate, @Param("seat")String seat);
    
    /*
    * Buscamos una venta por itinerario
    */
    @Query("SELECT s FROM Sale s " +
         "  WHERE  s.origin.id = :origin " +
         "    AND s.destiny.id = :destiny " +
         "    AND s.tripDate = :tripDate) " )
    public List<Sale> findSale(@Param("origin")Long origin, @Param("destiny")Long destiny, 
            @Param("tripDate")Date tripDate);

@Query("SELECT NEW com.simphony.pojos.DailySales(p, d, s) " +
           " FROM Payment p, SaleDetail d, Sale s" +
           " WHERE s.vendor.id = :idVendor  " +
           " AND s.id = d.sale.id " +
           " AND s.id = p.sale.id")
    public List<DailySales> dailySales(@Param("idVendor") Long idVendor);
    

}
