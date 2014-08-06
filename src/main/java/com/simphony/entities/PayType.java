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

    public PayType(){        
        this.reference = _NO;
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

    
}
