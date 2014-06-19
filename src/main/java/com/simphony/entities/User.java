package com.simphony.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "User.login", 
        query = "SELECT p FROM User p WHERE LOWER(p.alias) = LOWER(?1) AND p.password = ?2")
@Table(name="usuarios",schema="simphonybd")
public  class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre",length=60)
    private String name;
    
    @Column(name="alias",length=10)
    private String alias;
    
    @Column(name="contrasena",length=10)
    private String password;

    public User(){
        this.id = 0L;

    }


   public Long getId() {
        return this.id;
    }


  public void setId (Long id) {
        this.id = id;
    }



   public String getName() {
        return this.name;
    }


  public void setName (String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", alias=" + alias + ", password=" + password + '}';
    }

  
}

