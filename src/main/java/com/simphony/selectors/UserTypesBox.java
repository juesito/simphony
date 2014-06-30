/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.UserTypesService;
import com.simphony.entities.UserTypes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

/**
 *
 * @author Soporte IT
 */
@ManagedBean(name = "boxUserTypesService", eager = true)
@ApplicationScoped
public class UserTypesBox {

    @ManagedProperty(value = "#{userTypesService}")
    private UserTypesService userTypesService;

    private List<SelectItem> userTypesList = new ArrayList<SelectItem>();

    public UserTypesService getUserTypesService() {
        return userTypesService;
    }

    public void setUserTypesService(UserTypesService userTypesService) {
        this.userTypesService = userTypesService;
    }

    public List<SelectItem> getUserTypesList() {
        return userTypesList;
    }

    public void setUserTypesList(List<SelectItem> userTypesList) {
        this.userTypesList = userTypesList;
    }

    /**
     * Creates a new instance of PopulationBox
     */
    public UserTypesBox() {
    }

    @PostConstruct
    public void init() {
        Iterable<UserTypes> c = this.getUserTypesService().getUserTypesRepository().findAll();
        Iterator<UserTypes> cu = c.iterator();
//        if(box.size() > 0){
//            box.clear();
//        }

        while (cu.hasNext()) {
            UserTypes userType = cu.next();
            userTypesList.add(new SelectItem(userType, userType.getDescription()));
        }

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
