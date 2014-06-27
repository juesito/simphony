/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.UserService;
import com.simphony.entities.Population;
import com.simphony.entities.User;
import com.simphony.interfases.IConfigurable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class UserBean implements IConfigurable {

    private User user = new User();
    private User current = new User();
    private User selected = new User();
    private List<User> list = new ArrayList<User>();
    private Population population = new Population();

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }

    @PostConstruct
    public void postInitialization() {
        
        Calendar cal = Calendar.getInstance();
        this.current.setCreatedDate(cal.getTime());
        this.current.setStatus(_DISABLE);
        this.current.setName(_BLANK);
        
        //Esta inicializacion sera temporal solo pruebas

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public User getSelected() {
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public String addUser() {
        user = new User();
        this.current.setAction(_ADD);
        return "addUser";
    }
    
    public String modifyUser(){
        this.current.setAction(_MODIFY);
        return "addUser";
    }
    
    public String cancelUser() {
        return "toUsers";
    }

    public String save() {
        
        Calendar cal = Calendar.getInstance();
        user.setCreatedDate(cal.getTime());
        user.setLastUpdate(cal.getTime());
        user.setStatus(_ENABLED);
        
        this.userService.getUserRepository().save(user);
        user = new User();
        return "";
    }
    
    public void update(){
        Calendar cal = Calendar.getInstance();
        user.setLastUpdate(cal.getTime());
        
        this.userService.getUserRepository().save(selected);
    }

    /**
     * Controlador listar usuarios
     *
     * @return
     */
    public String toUsers() {
        list.clear();
        Iterable<User> c = this.getUserService().getUserRepository().findAll();
        Iterator<User> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toUsers";
    }

    public String login() {
        current = this.userService.getUserRepository().login(current.getNick().trim(), current.getPassword().trim());
        if (current != null) {
        }
        return "toindex";
    }
    
    public String logout(){
        current = new User();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }
    
    

}
