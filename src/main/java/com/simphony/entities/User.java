package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity(name = "User")
@Table(name = "Usuarios")
public class User extends Person implements Serializable, Cloneable {

    @Column(name = "nick", length = 10)
    @Basic
    private String nick;

    @Column(name = "contrasena", length = 10)
    @Basic
    private String password;

    public User() {

    }

    /**
     * Actualizamos el usuario.
     * @param userUpdated 
     */
    public void update(User userUpdated){
        super.update(userUpdated);
        this.nick = userUpdated.getNick();
        this.password = userUpdated.getPassword();
    }
    
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }
    
    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "nick=" + nick + ", password=" + password + '}';
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

}
