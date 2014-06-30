/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.managedbeans;

import com.simphony.beans.PopulationService;
import com.simphony.beans.UserTypesService;
import com.simphony.entities.Population;
import com.simphony.entities.User;
import com.simphony.entities.UserTypes;
import com.simphony.interfases.IConfigurable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Soporte IT
 */
@ManagedBean
@SessionScoped
public class UserTypesBean {
    
    private List<UserTypes> list = new ArrayList();
    private UserTypes current = new UserTypes();
    private UserTypes userTypes = new UserTypes();
    private UserTypes selected = new UserTypes();
    
    @ManagedProperty(value = "#{userTypesService}")
    UserTypesService userTypesService;

    /**
     * Creates a new instance of PopulationBean
     */
    public UserTypesBean() {
    }
    
    @PostConstruct
    public void postInitialization() {
//        Calendar cal = Calendar.getInstance();
//        population.setDescription("MEXICO");
//        population.setCreateDate(cal.getTime());
//        population.setCreateHour(cal.getTime());
//        population.setStatus("A");
//        this.getPopulationService().getPopulationRepository().save(population);
    }

    public List<UserTypes> getList() {
        return list;
    }

    public void setList(List<UserTypes> list) {
        this.list = list;
    }

    public UserTypes getCurrent() {
        return current;
    }

    public void setCurrent(UserTypes current) {
        this.current = current;
    }

    public UserTypes getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(UserTypes userTypes) {
        this.userTypes = userTypes;
    }

    public UserTypes getSelected() {
        return selected;
    }

    public void setSelected(UserTypes selected) {
        this.selected = selected;
    }

    public UserTypesService getUserTypesService() {
        return userTypesService;
    }

    public void setUserTypesService(UserTypesService userTypesService) {
        this.userTypesService = userTypesService;
    }

    
    
    public void save(User currentUser){
        this.current.setStatus(IConfigurable._ENABLED);
        this.getUserTypesService().getUserTypesRepository().save(this.current);
        this.current = new UserTypes();
    }
    
    public void update(){}
    
    public String toUserTypes(){
        list.clear();
        Iterable<UserTypes> c = this.userTypesService.getUserTypesRepository().findAll();
        Iterator<UserTypes> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toUserTypes";
    }
    
    public String addUserTypes(){
        current = new UserTypes();
        
        return "addUserTypes";
    }
    
}
