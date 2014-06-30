package com.simphony.entities;

import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="User")
@Table(name="usuarios",schema="simphonybd")
public  class User implements Serializable, Cloneable {


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


    @Column(name="horaCreacion")
    @Basic
    private Date lastUpdate;


    @Column(name="nombre",length=60)
    @Basic
    private String name;


    @Transient
    private String action;


    @ManyToOne(targetEntity=WorkCenter.class)
    private WorkCenter workCenter;


    @Column(name="fechaCreacion")
    @Basic
    private Date createdDate;


    @Column(name="contrasena",length=10)
    @Basic
    private String password;


    @OneToOne(cascade={CascadeType.ALL},targetEntity=UserTypes.class)
    private UserTypes userType;


    @ManyToOne(targetEntity=Population.class)
    private Population population;

    public User(){

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



   public WorkCenter getWorkCenter() {
        return this.workCenter;
    }


  public void setWorkCenter (WorkCenter workCenter) {
        this.workCenter = workCenter;
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



   public UserTypes getUserType() {
        return this.userType;
    }


  public void setUserType (UserTypes userType) {
        this.userType = userType;
    }



   public Population getPopulation() {
        return this.population;
    }


  public void setPopulation (Population population) {
        this.population = population;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final User other = (User) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
  

}

