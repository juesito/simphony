/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity(name = "Printer")
@Table(name = "Impresoras")
public class Printer extends Catalog implements Cloneable {

    @Basic
    @Column(name = "direccionIp")
    private String ipAddress;

    @Basic
    @Column(name = "tipo")
    private String type;

    @Basic
    @Column(name = "anchoHoja")
    private Double width;

    @Basic
    @Column(name = "altoHoja")
    private Double height;

    @Basic
    @Column(name = "marca")
    private String brand;

    public Printer() {
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void update(Printer printerUpdated) {
        super.update(printerUpdated);
        this.ipAddress = printerUpdated.getIpAddress();
        this.type = printerUpdated.getType();
        this.width = printerUpdated.getWidth();
        this.height = printerUpdated.getHeight();
        this.brand = printerUpdated.getBrand();
    }

    public String getFormatStatus() {
        String texto = "";
        if (this.getStatus() != null) {
            if (this.getStatus().equals("A")) {
                texto = "Activo";
            } else {
                texto = "Inactivo";
            }

            return texto;
        } else {
            return texto;
        }
    }

}
