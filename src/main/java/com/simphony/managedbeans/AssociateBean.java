/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.AssociateService;
import com.simphony.entities.Associate;
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
public class AssociateBean {

    private Associate associate = new Associate();
    private Associate current = new Associate();
    private Associate selected = new Associate();
    private List<Associate> list = new ArrayList<Associate>();
    

    @ManagedProperty(value = "#{associateService}")
    private AssociateService associateService;

    /**
     * Creates a new instance of AssociateBean
     */
    public AssociateBean() {
    }

    @PostConstruct
    public void postInitialization() {

    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public AssociateService getAssociateService() {
        return associateService;
    }

    public void setAssociateService(AssociateService associateService) {
        this.associateService = associateService;
    }

    public List<Associate> getList() {
        return list;
    }

    public void setList(List<Associate> list) {
        this.list = list;
    }

    public Associate getSelected() {
        return selected;
    }

    public void setSelected(Associate selected) {
        this.selected = selected;
    }
    
    public Associate getCurrent() {
        return current;
    }

    public void setCurrent(Associate current) {
        this.current = current;
    }
    
    public String addAssociate(){
        associate = new Associate();
        return "addAssociate";
    }
    
    public String save() {
        this.associateService.getAssociateRepository().save(associate);
        associate = new Associate();
        return "";
    }    
        
    /**
     * Controlador listar Associate
     * @return 
     */
    public String toAssociates(){
        list.clear();
        Iterable<Associate> c = this.getAssociateService().getAssociateRepository().findAll();
        Iterator<Associate> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toAssociates";
    }
    
}
