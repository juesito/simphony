package com.simphony.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="User")
@Table(name="usuarios",schema="simphonybd")
public  class User extends Person implements Serializable, Cloneable {

    public User(){

    }
    
    
    
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

