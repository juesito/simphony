/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.entities;

import com.simphony.interfases.IConfigurable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Leonor
 */
@Entity(name="PayType")
@Table(name="TiposdePago")
public class PayType extends Catalog implements Serializable, Cloneable, IConfigurable {
    
    @Column(name="referencia")
    @Basic
    private String reference;
    
    @Transient
    private Double amount;
    
    @Transient
    private String paymentInfo;
    
    public PayType(){        
        this.reference = _NO;
        this.paymentInfo = "";
        this.amount = 0.0;
    }         

    public void update(PayType payTypeUpdated){
        super.update(payTypeUpdated);
        this.reference = payTypeUpdated.getReference();

     }
 
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public String getReference() {
        return this.reference;
    }


    public void setReference (String reference) {
        this.reference = reference;
    }

     public String getFormatStatus(){
        String texto = "";
        if(this.getStatus() != null){
            if (this.getStatus().equals("A")){
                texto = "Activo";
            }else texto = "Inactivo";
       
            return texto;
        }else return texto;
    }

     public String getFormatRef(){
        String texto = "";
        if(this.reference != null){
            if (this.reference.equals("S")){
                texto = "Si";
            }else texto = "No";
       
            return texto;
        }else return texto;
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
    
    
     
     
     
}
