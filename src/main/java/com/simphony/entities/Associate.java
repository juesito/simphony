package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Associate")
@Table(name="Agremiado",schema="simphonybd")
public  class Associate implements Serializable {


    @Column(name="id")
    @Id
    private Long id;


    @Basic
    private String platform;


    @Column(name="email",length=100)
    @Basic
    private String email;


    @Column(name="Nombre",length=60)
    @Basic
    private String name;


    @Column(name="estado",length=50)
    @Basic
    private String state;


    @Column(name="telefono",length=10)
    @Basic
    private String phoneId;


    @Column(name="apellidoMaterno",length=60)
    @Basic
    private String lastName2;


    @Column(name="apellidoPaterno",length=60)
    @Basic
    private String lastName1;


    @Column(name="seccion",length=15)
    @Basic
    private String section;


    @Column(name="NoFicha",length=10)
    @Basic
    private String keyId;


    @Column(name="ciudad",length=50)
    @Basic
    private String city;

    public Associate(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public String getPlatform() {
        return this.platform;
    }


  public void setPlatform (String platform) {
        this.platform = platform;
    }



   public String getEmail() {
        return this.email;
    }


  public void setEmail (String email) {
        this.email = email;
    }



   public String getName() {
        return this.name;
    }


  public void setName (String name) {
        this.name = name;
    }



   public String getState() {
        return this.state;
    }


  public void setState (String state) {
        this.state = state;
    }



   public String getPhoneId() {
        return this.phoneId;
    }


  public void setPhoneId (String phoneId) {
        this.phoneId = phoneId;
    }



   public String getLastName2() {
        return this.lastName2;
    }


  public void setLastName2 (String lastName2) {
        this.lastName2 = lastName2;
    }



   public String getLastName1() {
        return this.lastName1;
    }


  public void setLastName1 (String lastName1) {
        this.lastName1 = lastName1;
    }



   public String getSection() {
        return this.section;
    }


  public void setSection (String section) {
        this.section = section;
    }



   public String getKeyId() {
        return this.keyId;
    }


  public void setKeyId (String keyId) {
        this.keyId = keyId;
    }



   public String getCity() {
        return this.city;
    }


  public void setCity (String city) {
        this.city = city;
    }

}

