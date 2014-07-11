/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.AssociateService;
import com.simphony.entities.Associate;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ENABLED;
import static com.simphony.interfases.IConfigurable._MODIFY;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AssociateBean implements IConfigurable {

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

    public String addAssociate() {
        associate = new Associate();
        this.current.setAction(_ADD);
        return "addAssociate";
    }

    /**
     * Controlador para modificar agremiado
     *
     * @return
     */
    public String modifyAssociate() {
        this.current.setAction(_MODIFY);
        try {
            this.associate = (Associate) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(AssociateBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addAssociate";
    }

    /**
     * deshabilitamos agremiado
     *
     * @throws com.simphony.exceptions.PersonException
     */
    public void disableAssociate() throws PersonException {
        this.selected.setStatus(_DISABLE);

        Associate associateUpdated = this.associateService.getAssociateRepository().findOne(selected.getId());

        if (associateUpdated == null) {
            throw new PersonException("Agremiado no existente");
        }

        associateUpdated.update(selected);
        this.associateService.getAssociateRepository().save(associateUpdated);

        this.fillAssociates();

    }

    public String cancelAssociate() {
        this.fillAssociates();
        return "toAssociates";
    }

    /**
     * Llenamos lista de agremiados
     */
    private void fillAssociates() {
        list.clear();
        Iterable<Associate> c = this.associateService.getAssociateRepository().findAll();
        Iterator<Associate> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Guardamos el agremiado
     * @return
     */
    public String save() {
        if (this.associate.getKeyId() != null && this.associate.getName() != null) {
            Calendar cal = Calendar.getInstance();
            associate.setCreateDate(cal.getTime());
            associate.setLastUpdate(cal.getTime());
            associate.setStatus(_ENABLED);
            
            this.associateService.getAssociateRepository().save(associate);
            associate = new Associate();
        }
        return "";
    }
    
    /**
     * Actualizamos el usuario
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update() throws PersonException {
        
        Associate associateUpdated = this.associateService.getAssociateRepository().findOne(this.associate.getId());
        
        if(associateUpdated == null){
            throw new PersonException("Agremiado no existente"); 
        }
        
        associateUpdated.update(this.associate);
        this.associateService.getAssociateRepository().save(associateUpdated);
        associate = new Associate();
        return toAssociates();
    }

    /**
     * Controlador listar Associate
     *
     * @return
     */
    public String toAssociates() {
        list.clear();
        Iterable<Associate> c = this.getAssociateService().getAssociateRepository().findAll();
        Iterator<Associate> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toAssociates";
    }

}
