/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.AssociateService;
import com.simphony.entities.Associate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author root
 */
@ManagedBean(name = "boxAssociateService", eager = true)
@ApplicationScoped
public class AssociateBox {

    @ManagedProperty(value = "#{associateService}")
    private AssociateService associateService;

    private List<SelectItem> associateList = new ArrayList<SelectItem>();
    private List<Associate> associates = new ArrayList<Associate>();

    /**
     * Creates a new instance of AssociateBox
     */
    public AssociateBox() {
    }
    
    @PostConstruct
    public void init() {
        associateList.clear();
        //Llena solo poblaciones activas
        List<Associate> optionList = this.getAssociateService().getRepository().findAll();
        for (Associate associate : optionList) {            
            associateList.add(new SelectItem(associate, associate.getKeyId() + associate.getName()));
        }
    }
    
    public List<Associate> fillAssociateList(String query){
        associates = this.getAssociateService().getRepository().findAll();
        List<Associate> filteredAssociates = new ArrayList<Associate>();
        
        for(int i = 0; i< associates.size(); i++){
            Associate associate = associates.get(i);
            if(associate.getName().toLowerCase().startsWith(query)){
                filteredAssociates.add(associate);
            }
            
        }
        return filteredAssociates;
    }

    public AssociateService getAssociateService() {
        return associateService;
    }

    public void setAssociateService(AssociateService associateService) {
        this.associateService = associateService;
    }

    public List<SelectItem> getAssociateList() {
        return associateList;
    }

    public void setAssociateList(List<SelectItem> associateList) {
        this.associateList = associateList;
    }
    
    

}
