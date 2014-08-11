/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 *
 * @author root
 */
@Entity(name = "GuideDetail")
@Table(name = "DetalleGuia")
public class GuideDetail implements Serializable {

    @ManyToOne(targetEntity = Guide.class)
    private Guide guide;

    @ManyToOne(targetEntity = Sale.class)
    private Sale sale;
    
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    public GuideDetail() {
    }

    public void update(GuideDetail guideDetailUpdated) {
        this.guide = guideDetailUpdated.getGuide();
        this.sale = guideDetailUpdated.getSale();
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
}
