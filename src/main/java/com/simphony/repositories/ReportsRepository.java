/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Sale;
import com.simphony.pojos.DailySales;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Soporte IT
 */
public interface ReportsRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT NEW com.simphony.pojos.DailySales(p, s, " +
           "(SELECT COUNT(type) FROM SaleDetail WHERE type = 'A' AND s.id = sale.id) , " +
           "(SELECT COUNT(type) FROM SaleDetail WHERE type = 'P' AND s.id = sale.id) , " +
           "(SELECT COUNT(bolType) FROM SaleDetail WHERE bolType = 'J' AND s.id = sale.id)) " +
           " FROM Payment p, Sale s " +
           " WHERE s.vendor.id = :idVendor " +
           " AND s.id = p.sale.id " +
           " AND s.createDate BETWEEN :iniDate AND :finDate ")
    public List<DailySales> dailySales(@Param("idVendor") Long idVendor, @Param("iniDate") Date iniDate,
                                       @Param("finDate") Date finDate);
 
    @Query("SELECT NEW com.simphony.pojos.DailySales(p, s, " +
           "(SELECT COUNT(type) FROM SaleDetail WHERE type = 'A' AND status = 'V' AND s.id = sale.id) , " +
           "(SELECT COUNT(type) FROM SaleDetail WHERE type = 'P' AND bolType <> 'J' AND status = 'V' AND s.id = sale.id) , " +
           "(SELECT COUNT(bolType) FROM SaleDetail WHERE bolType = 'J' AND status = 'V' AND s.id = sale.id)) " +
           " FROM Payment p, Sale s " +
           " WHERE s.vendor.id IN ( SELECT v.id FROM Vendor v WHERE v.salePoint.id = :idSalePoint) " +
           " AND s.id = p.sale.id " +
           " AND s.createDate BETWEEN :iniDate AND :finDate ORDER BY s.vendor.id ")
    public List<DailySales> dailySalesPoint(@Param("idSalePoint") Long idVendor, @Param("iniDate") Date iniDate,
                                       @Param("finDate") Date finDate);
}
