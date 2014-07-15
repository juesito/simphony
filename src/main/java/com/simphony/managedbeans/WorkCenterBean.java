/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.WorkCenterService;
import com.simphony.entities.WorkCenter;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ADD;
import static com.simphony.interfases.IConfigurable._DISABLE;
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
public class WorkCenterBean implements IConfigurable {

    private WorkCenter workCenter = new WorkCenter();
    private WorkCenter current = new WorkCenter();
    private WorkCenter selected = new WorkCenter();
    private List<WorkCenter> list = new ArrayList<WorkCenter>();

    @ManagedProperty(value = "#{workCenterService}")
    private WorkCenterService workCenterService;

    /**
     * Creates a new instance of WorkCenterBean
     */
    public WorkCenterBean() {
    }

    @PostConstruct
    public void postInitialization() {

    }

    public WorkCenter getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(WorkCenter workCenter) {
        this.workCenter = workCenter;
    }

    public WorkCenterService getWorkCenterService() {
        return workCenterService;
    }

    public void setWorkCenterService(WorkCenterService workCenterService) {
        this.workCenterService = workCenterService;
    }

    public List<WorkCenter> getList() {
        return list;
    }

    public void setList(List<WorkCenter> list) {
        this.list = list;
    }

    public WorkCenter getSelected() {
        return selected;
    }

    public void setSelected(WorkCenter selected) {
        this.selected = selected;
    }

    public WorkCenter getCurrent() {
        return current;
    }

    public void setCurrent(WorkCenter current) {
        this.current = current;
    }

    public String addWorkCenter() {
        workCenter = new WorkCenter();
        this.current.setAction(_ADD);
        return "addWorkCenter";
    }

    /**
     * Controlador para modificar WorkCenter
     *
     * @return
     */
    public String modifyWorkCenter() {
        this.current.setAction(_MODIFY);
        try {
            this.workCenter = (WorkCenter) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(WorkCenterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addWorkCenter";
    }

    /**
     * deshabilitamos WorkCenter
     *
     * @throws com.simphony.exceptions.PersonException
     */
    public void disableWorkCenter() throws PersonException {
        this.selected.setStatus(_DISABLE);

        WorkCenter workCenterUpdated = this.workCenterService.getWorkCenterRepository().findOne(selected.getId());

        if (workCenterUpdated == null) {
            throw new PersonException("EstaciÃ³n de trabajo no existente");
        }

        workCenterUpdated.update(selected);
        this.workCenterService.getWorkCenterRepository().save(workCenterUpdated);

        this.fillWorkCenter();

    }

    public String cancelWorkCenter() {
        this.fillWorkCenter();
        return "toWorkCenter";
    }

    /**
     * Llenamos lista de agremiados
     */
    private void fillWorkCenter() {
        list.clear();
        Iterable<WorkCenter> c = this.workCenterService.getWorkCenterRepository().findAll();
        Iterator<WorkCenter> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Guardamos el WorkCenter
     * @return
     */
    public String save() {
        Calendar cal = Calendar.getInstance();
        workCenter.setCreateDate(cal.getTime());
        workCenter.setStatus(_ENABLED);
            
        this.workCenterService.getWorkCenterRepository().save(workCenter);
        workCenter = new WorkCenter();
        return "";
    }
    
    /**
     * Actualizamos el usuario
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update() throws PersonException {
        
        WorkCenter workCenterUpdated = this.workCenterService.getWorkCenterRepository().findOne(this.workCenter.getId());
        
        if(workCenterUpdated == null){
            throw new PersonException("EstaciÃ³n de trabajo no existente"); 
        }
        
        workCenterUpdated.update(this.workCenter);
        this.workCenterService.getWorkCenterRepository().save(workCenterUpdated);
        workCenter = new WorkCenter();
        return toWorkCenter();
    }

    /**
     * Controlador listar WorkCenter
     *
     * @return
     */
    public String toWorkCenter() {
        list.clear();
        Iterable<WorkCenter> c = this.getWorkCenterService().getWorkCenterRepository().findAll();
        Iterator<WorkCenter> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toWorkCenter";
    }

     /**
     * habilitamos WorkCenter
     */
    public void enableWorkCenter() {
        this.selected.setStatus(_ENABLED);

        WorkCenter workCenterUpdated = this.workCenterService.getWorkCenterRepository().findOne(selected.getId());

        workCenterUpdated.update(selected);
        this.workCenterService.getWorkCenterRepository().save(workCenterUpdated);

        this.fillWorkCenter();

    }

}
