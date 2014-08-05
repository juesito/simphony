/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.WorkCenterService;
import com.simphony.entities.WorkCenter;
import java.util.ArrayList;
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
@ManagedBean(name = "boxWorkCenterService", eager = true)
@ApplicationScoped
public class WorkCenterBox {

    @ManagedProperty(value = "#{workCenterService}")
    private WorkCenterService workCenterService;
    
    private List<WorkCenter> list = new ArrayList<WorkCenter>();
    private List<SelectItem> workCenterList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of WorkCenterBox
     */
    public WorkCenterBox() {
    }

    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<WorkCenter> optionList = this.getWorkCenterService().getWorkCenterRepository().findAllEnabled();

        for (WorkCenter workCenter : optionList) {
            list.add(workCenter);
            workCenterList.add(new SelectItem(workCenter, workCenter.getDescription()));
        }

    }
    
    public List<SelectItem> getWorkCenterList() {
        return workCenterList;
    }

    public void setWorkCenterList(List<SelectItem> workCenterList) {
        this.workCenterList = workCenterList;
    }

    public WorkCenterService getWorkCenterService() {
        return workCenterService;
    }

    public void setWorkCenterService(WorkCenterService workCenterService) {
        this.workCenterService = workCenterService;
    }

    public void fillBox() {
        workCenterList.clear();
        init();
    }

    public List<WorkCenter> getList() {
        return list;
    }

    public void setList(List<WorkCenter> list) {
        this.list = list;
    }

}
