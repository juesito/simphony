package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name = "DriverMan")
@Table(name = "Operadores")
public class DriverMan extends Person implements Serializable, Cloneable {

    @Column(name = "TipoOperador", length = 1)
    @Basic
    private String type;

    @OneToOne(targetEntity = User.class)
    private User user;

    public DriverMan() {

    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public void update(DriverMan driverManUpdated){
        super.update(driverManUpdated);
        this.user = driverManUpdated.getUser();
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

}
