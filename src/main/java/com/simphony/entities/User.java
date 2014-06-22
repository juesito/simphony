package com.simphony.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="User")
@Table(name="usuarios",schema="simphonybd",catalog="usuarios")
public  class User implements Serializable {


    @Column(name="id")
    @Id
    private Long id;


    @Column(name="nick",length=10)
    @Basic
    private String nick;


    @Column(name="estatus",length=1)
    @Basic
    private String status;


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
        Calendar cal = Calendar.getInstance();
        createdDate = cal.getTime();
        status = "A";
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

