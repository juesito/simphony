package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="workCenter")
@Table(name="estacionTrabajo",schema="simphonybd")
public  class workCenter implements Serializable {


    @Column(name="id")
    @Id
    private Long id;


    @Column(name="estatus",length=1)
    @Basic
    private String status;


    @Column(name="descripcion",length=60)
    @Basic
    private String description;


    @Id@OneToOne(targetEntity=User.class)
    private User userId;


    @Column(name="fechaCreacion")
    @Basic
    private Date createDate;


    @Column(name="horaCreacion")
    @Basic
    private Date createHour;

    public workCenter(){

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



   public User getUserId() {
        return this.userId;
    }


  public void setUserId (User userId) {
        this.userId = userId;
    }



   public Date getCreateDate() {
        return this.createDate;
    }


  public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }



   public Date getCreateHour() {
        return this.createHour;
    }


  public void setCreateHour (Date createHour) {
        this.createHour = createHour;
    }

}

