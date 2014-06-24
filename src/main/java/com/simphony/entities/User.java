package com.simphony.entities;

import java.io.Serializable;

import java.lang.Boolean;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="User")
@Table(name="usuarios",schema="simphonybd")
public  class User implements Serializable {


    @Column(name="id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(targetEntity=Population.class)
    @JoinTable(name="Poblaciones",schema="simphonybd",catalog="Poblaciones")
    private Population populationId;


    @Column(name="nick",length=10)
    @Basic
    private String nick;


    @Column(name="estatus",length=1)
    @Basic
    private String status;


    @Transient
    private Boolean connected;


    @Column(name="nombre",length=60)
    @Basic
    private String name;


    @Column(name="fechaCreacion")
    @Basic
    private Date createdDate;


    @Column(name="contrasena",length=10)
    @Basic
    private String password;

    public User(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public Population getPopulationId() {
        return this.populationId;
    }


  public void setPopulationId (Population populationId) {
        this.populationId = populationId;
    }



   public String getNick() {
        return this.nick;
    }


  public void setNick (String nick) {
        this.nick = nick;
    }



   public String getStatus() {
        return this.status;
    }


  public void setStatus (String status) {
        this.status = status;
    }



    public Boolean isConnected() {
        return this.connected;
    }


  public void setConnected (Boolean connected) {
        this.connected = connected;
    }



   public String getName() {
        return this.name;
    }


  public void setName (String name) {
        this.name = name;
    }



   public Date getCreatedDate() {
        return this.createdDate;
    }


  public void setCreatedDate (Date createdDate) {
        this.createdDate = createdDate;
    }



   public String getPassword() {
        return this.password;
    }


  public void setPassword (String password) {
        this.password = password;
    }

}

