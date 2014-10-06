/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.pojos;

import com.simphony.entities.Guide;
import com.simphony.entities.GuideDetail;
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
    private Long detAssociates;
    private Long detRetires;
    private Long detPublico;
    private Long detIncome;
    
    private Guide guide;
    private GuideDetail guideDetail;

    public DailySales() {
        rowId = (int) (Math.random()*5000+1);
    }

    
    public DailySales(Payment payMent, Sale sale, Long detAssociates, Long detPublico, Long detRetires) {
        this.sale = sale;
        this.payment = payMent;
        this.detAssociates = detAssociates;
        this.detRetires = detRetires;
        this.detPublico = detPublico;
        rowId = (int) (Math.random()*5000+1);
        normalMode = true;
    }

    public DailySales(Guide guide, Long detIncome) {
        this.guide = guide;
        this.detIncome = detIncome;
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
        return "DailySales{" + "payMent=" + payment +", sale=" + sale + "}";
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

    public Long getDetAssociates() {
        return detAssociates;
    }

    public void setDetAssociates(Long detAssociates) {
        this.detAssociates = detAssociates;
    }

    public Long getDetRetires() {
        return detRetires;
    }

    public void setDetRetires(Long detRetires) {
        this.detRetires = detRetires;
    }

    public Long getDetPublico() {
        return detPublico;
    }

    public void setDetPublico(Long detPublico) {
        this.detPublico = detPublico;
    }

    public Long getDetIncome() {
        return detIncome;
    }

    public void setDetIncome(Long detIncome) {
        this.detIncome = detIncome;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public GuideDetail getGuideDetail() {
        return guideDetail;
    }

    public void setGuideDetail(GuideDetail guideDetail) {
        this.guideDetail = guideDetail;
    }

    public String getFormatTipoVta(){
        String texto = "";
        if(this.getSaleDetail().getType() != null){
            if (this.getSaleDetail().getType().equals("A")){
                texto = "Agremiado";
            }else texto = "Público";
       
            return texto;
        }else return texto;
    }

    public String getFormatTipoBol(){
        String texto = "";
        if(this.getSaleDetail().getBolType()!= null){
            if (this.getSaleDetail().getBolType().equals("J")){
                texto = "Jubilado";
            }else texto = "Normal";
       
            return texto;
        }else return texto;
    }
 }
