/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity(name="Payment")
@Table(name="PagosVenta")
public class Payment {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(targetEntity = PayType.class)
    private PayType payType;
    
    @ManyToOne(targetEntity = Sale.class)
    private Sale sale;
    
    @Column(name="monto")
     @Basic
    private Double amount;
    
    @Column(name="informacionPago")
    @Basic
    private String paymentInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Payment() {
        this.paymentInfo = "";
        this.amount = 0.0;
    }

    public Payment(PayType payType, Sale sale) {
        this.payType = payType;
        this.sale = sale;
        this.amount = payType.getAmount();
        this.paymentInfo = payType.getPaymentInfo().trim();
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Payment other = (Payment) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Payment{" + "id=" + id + ", payType=" + payType + ", sale=" + sale + '}';
    }
    
    
}
