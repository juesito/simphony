/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 *
 * @author Leonor
 */
@Entity(name="PayType")
@Table(name="TiposdePago")
public class PayType extends Catalog implements Serializable, Cloneable {
    
    @Column(name="descuento")
    @Basic
    private float discount;

    public PayType(){

    }         

    public void update(PayType payTypeUpdated){
        super.update(payTypeUpdated);
        this.discount = payTypeUpdated.getDiscount();

     }
 
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public float getDiscount() {
        return this.discount;
    }


    public void setDiscount (float discount) {
        this.discount = discount;
    }

}
