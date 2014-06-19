/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.UserService;
import com.simphony.entities.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class UserBean {

    private User user = new User();
    private User current = new User();
    private User selected = new User();
    private List<User> list = new ArrayList<User>();

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }

    @PostConstruct
    public void postInitialization() {

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
    
    /**
     * Controlador para navegar a crear usuario
     * @return 
     */
    public String addUser(){
        user = new User();
        return "addUser";
    }
    
    public String save() {
        this.userService.getUserRepository().save(user);
        user = new User();
        return "";
    }
    
    public String login(){
        System.out.println("Before" + current);
        current = this.userService.getUserRepository().login(current.getAlias(), current.getPassword());
        System.out.println("After" + current);
        if(current == null){
            current = new User();
        }
        return "toIndex";
    }
    
    /**
     * Controlador listar usuarios
     * @return 
     */
    public String toUsers(){
        list.clear();
        Iterable<User> c = this.getUserService().getUserRepository().findAll();
        Iterator<User> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toUsers";
    }

}
