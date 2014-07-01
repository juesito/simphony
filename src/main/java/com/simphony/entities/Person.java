package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Person implements Serializable {


    @Column(name="id")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Column(name="nick",length=10)
    @Basic
    private String nick;


    @Column(name="estatus",length=1)
    @Basic
    private String status;


    @Column(name="ultimaModificacion")
    @Basic
    private Date lastUpdate;


    @Column(name="nombre",length=60)
    @Basic
    private String name;


    @Transient
    private String action;


    @Column(name="fechaCreacion")
    @Basic
    private Date createDate;


    @Column(name="contrasena",length=10)
    @Basic
    private String password;

    public Person(){

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
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



   public Date getLastUpdate() {
        return this.lastUpdate;
    }


  public void setLastUpdate (Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }



   public String getName() {
        return this.name;
    }


  public void setName (String name) {
        this.name = name;
    }



   public String getAction() {
        return this.action;
    }


  public void setAction (String action) {
        this.action = action;
    }



   public Date getCreateDate() {
        return this.createDate;
    }


  public void setCreateDate (Date createDate) {
        this.createDate = createDate;
    }



   public String getPassword() {
        return this.password;
    }


  public void setPassword (String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
  
  

}

