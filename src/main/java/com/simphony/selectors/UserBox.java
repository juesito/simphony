/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.PopulationService;
import com.simphony.beans.UserService;
import com.simphony.entities.Population;
import com.simphony.entities.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Soporte IT
 */
@ManagedBean(name = "boxUserService", eager = true)
@ApplicationScoped
public class UserBox {

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    //private Map<String,String> box = new HashMap<String, String>();
    private final List<User> box = new ArrayList();

    /**
     * Creates a new instance of PopulationBox
     */
    public UserBox() {
    }
    
    @PostConstruct
    public void init(){
        Iterable<User> c = this.getUserService().getUserRepository().findAll();
        Iterator<User> cu = c.iterator();
//        if(box.size() > 0){
//            box.clear();
//        }
        
        while (cu.hasNext()) {
            User user = cu.next();
            box.add(user);
        }
    
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    

    public List<User> getBox() {        
        return box;
    }

    public void fillBox() {
//        Iterable<Population> c = this.getPopulationService().getPopulationRepository().findAll();
//        Iterator<Population> cu = c.iterator();
//        while (cu.hasNext()) {
//            Population population = cu.next();
//            box.add(population);
//        }
    }

}
