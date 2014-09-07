/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.pojos;

import com.simphony.entities.SaleDetail;

/**
 *
 * @author Administrador
 */
public class ReservedSeatInDetailSale {
    
    private SaleDetail saleDetail;
    private Long reservedSeatId;
    private Integer rowId;

    public ReservedSeatInDetailSale() {
        rowId = (int) (Math.random()*5000+1);
    }

    
    public ReservedSeatInDetailSale(SaleDetail saleDetail, Long reservedSeatId) {
        this.saleDetail = saleDetail;
        this.reservedSeatId = reservedSeatId;
        rowId = (int) (Math.random()*5000+1);
    }

    public SaleDetail getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(SaleDetail saleDetail) {
        this.saleDetail = saleDetail;
    }

    public Long getReservedSeatId() {
        return reservedSeatId;
    }

    public void setReservedSeatId(Long reservedSeatId) {
        this.reservedSeatId = reservedSeatId;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }
    
    
    
}
