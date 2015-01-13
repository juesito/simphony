package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name = "Vendor")
@Table(name = "Vendedores")
public class Vendor extends Person implements Serializable, Cloneable {

    @Column(name = "nick", length = 10)
    @Basic
    private String nick;

    @ManyToOne(targetEntity = WorkCenter.class)
    private WorkCenter workCenter;

    @Column(name = "contrasena", length = 10)
    @Basic
    private String password;

    @ManyToOne(targetEntity = Population.class)
    private Population population;
    
    @ManyToOne(targetEntity = SalePoint.class)
    private SalePoint salePoint;

    @Column(name = "type", length = 10)
    @Basic
    private String type;

    @ManyToOne(targetEntity = Printer.class)
    private Printer printer;
    
    public Vendor() {

    }

    public void update(Vendor vendorUpdated) {
        super.update(vendorUpdated);
        this.nick = vendorUpdated.getNick();
        this.password = vendorUpdated.getPassword();
        this.salePoint = vendorUpdated.getSalePoint();
        this.type = vendorUpdated.getType();
        this.setPrinter(vendorUpdated.getPrinter());
    }

    @PreUpdate
    public void preUpdate() {
        super.setLastUpdate(new Date());
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public WorkCenter getWorkCenter() {
        return this.workCenter;
    }

    public void setWorkCenter(WorkCenter workCenter) {
        this.workCenter = workCenter;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Population getPopulation() {
        return this.population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public SalePoint getSalePoint() {
        return this.salePoint;
    }

    public void setSalePoint(SalePoint salePoint) {
        this.salePoint = salePoint;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vendor{" + "nick=" + nick + ", workCenter=" + workCenter + ", password=" + password + ", population=" + population + '}';
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(" no se puede duplicar");
        }
        return obj;
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
    
    public String getFormatType(){
        String texto = "";
        if(this.getType()!= null){
            if (this.getType().equals("C")){
                texto = "Completo";
            }else {
                if (this.getType().equals("C")){
                    texto = "Medio";
                }else {
                    texto = "Normal";
                }
            }
       
            return texto;
        }else return texto;
    }
}
