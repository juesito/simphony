/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.repositories;

import com.simphony.entities.Sale;
import com.simphony.pojos.DailySales;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Soporte IT
 */
public interface ReportsRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT NEW com.simphony.pojos.DailySales(p, d, s) " +
           " FROM Payment p, SaleDetail d, Sale s" +
           " WHERE s.vendor.id = :idVendor  " +
           " AND s.id = d.sale.id " +
           " AND s.id = p.sale.id")
    public List<DailySales> dailySales(@Param("idVendor") Long idVendor);
 
}
