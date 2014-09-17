/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.pojos;

import com.simphony.entities.Payment;
import com.simphony.entities.Sale;
import com.simphony.entities.SaleDetail;

/**
 *
 * @author Administrador
 */
public class DailySales  {
    
    private Sale sale;
    private SaleDetail saleDetail;
    private Payment payment;
    private Integer rowId;
    private Boolean normalMode;

    public DailySales() {
        rowId = (int) (Math.random()*5000+1);
    }

    
    public DailySales(Payment payMent, SaleDetail detail, Sale sale) {
        this.sale = sale;
        this.payment = payMent;
        this.saleDetail = detail;
        rowId = (int) (Math.random()*5000+1);
        normalMode = true;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public SaleDetail getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(SaleDetail saleDetail) {
        this.saleDetail = saleDetail;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    @Override
    public String toString() {
        return "DailySales{" + "payMent=" + payment + ",  detail=" + saleDetail +", sale=" + sale + "}";
    }

    public Integer getRowId() {
        return rowId;
    }

     public Boolean isNormalMode() {
        return normalMode;
    }

    public void setNormalMode(Boolean normalMode) {
        this.normalMode = normalMode;
    }
   
}
