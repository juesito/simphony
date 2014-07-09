package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="WorkCenter")
@Table(name="estacionesTrabajo")
public  class WorkCenter implements Serializable, Cloneable {

    @Transient
    private String action;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Column(name="estatus",length=1)
    @Basic
    private String status;


    @Column(name="descripcion",length=60)
    @Basic
    private String description;


    @Column(name="fechaCreacion")
    @Basic
    private Date createDate;


    @OneToOne(targetEntity=User.class)
    private User user;

    public WorkCenter(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public String getStatus() {
        return this.status;
    }


  public void setStatus (String status) {
        this.status = status;
    }



   public String getDescription() {
        return this.description;
    }


  public void setDescription (String description) {
        this.description = description;
    }



   public Date getCreateDate() {
        return this.createDate;
    }


  public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }



   public User getUser() {
        return this.user;
    }


  public void setUser (User user) {
        this.user = user;
    }

  public String getAction() {
        return this.action;
    }


  public void setAction (String action) {
        this.action = action;
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

        public void update(WorkCenter workCenterUpdated){
        this.description = workCenterUpdated.getDescription();
    }

}

