/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.SaleDetail;
import com.simphony.pojos.ReservedSeatInDetailSale;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrador
 */
public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long>{
    
     @Query("SELECT NEW com.simphony.pojos.ReservedSeatInDetailSale(dv, ar.id) " +
            "  FROM GuideDetail dg,  ReservedSeats ar , Guide g, " +
            "    SaleDetail dv " +
            "   WHERE dg.guide.id = g.id " +
            "     AND g.rootGuide = ar.guideId " +
            "     AND dv.sale.id = dg.sale.id " +
            "     AND dv.seat.id = ar.seat.id" +
            "     AND dv.status = :status " +
            "     AND dg.sale.id = :saleId" +
            "  ORDER BY dv.sale.id, dv.seat.id")
    public List<ReservedSeatInDetailSale> findSeatsBySale(@Param("saleId") Long saleId, @Param("status") String status);
    
    @Query("SELECT NEW com.simphony.pojos.ReservedSeatInDetailSale(dv, ar.id) " +
            "  FROM GuideDetail dg,  ReservedSeats ar , Guide g, " +
            "    SaleDetail dv " +
            "   WHERE dg.guide.id = g.id " +
            "     AND g.rootGuide = ar.guideId " +
            "     AND dv.sale.id = dg.sale.id " +
            "     AND dv.seat.id = ar.seat.id" +
            "     AND dv.status = :status " +
            "     AND dv.customerName LIKE CONCAT('%', :customerName, '%')" +
            "  ORDER BY dv.sale.id, dv.seat.id")
    public List<ReservedSeatInDetailSale> findSeatsByCustomerName(@Param("customerName") String customerName, @Param("status") String status);
    
    
    @Query("SELECT COUNT(dv) FROM SaleDetail dv WHERE dv.sale.id = :saleId AND dv.status = 'V'")
    public Integer countDetailBySale(@Param("saleId")Long saleId);
}
