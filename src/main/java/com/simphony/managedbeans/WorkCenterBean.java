/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.WorkCenterService;
import com.simphony.entities.WorkCenter;
import com.simphony.interfases.IConfigurable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class WorkCenterBean {

    private List<WorkCenter> list = new ArrayList();
    private WorkCenter current = new WorkCenter();
    private WorkCenter selected = new WorkCenter();

    @ManagedProperty(value = "#{workCenterService}")
    WorkCenterService workCenterService;

    /**
     * Creates a new instance of WorkCenterBean
     */
    public WorkCenterBean() {
    }

    public List<WorkCenter> getList() {
        return list;
    }

    public void setList(List<WorkCenter> list) {
        this.list = list;
    }

    public WorkCenter getCurrent() {
        return current;
    }

    public void setCurrent(WorkCenter current) {
        this.current = current;
    }

    public WorkCenter getSelected() {
        return selected;
    }

    public void setSelected(WorkCenter selected) {
        this.selected = selected;
    }

    public WorkCenterService getWorkCenterService() {
        return workCenterService;
    }

    public void setWorkCenterService(WorkCenterService workCenterService) {
        this.workCenterService = workCenterService;
    }

    public String addWorkCenter() {
        return "addWorkCenter";
    }

    public void save() {
        Calendar cal = Calendar.getInstance();
        this.current.setCreateDate(cal.getTime());
        this.current.setCreateHour(cal.getTime());
        this.current.setStatus(IConfigurable._ENABLED);
        this.getWorkCenterService().getWorkCenterRepository().save(current);
        current = new WorkCenter();
    }

    public String toWorkCenter() {
        list.clear();
        Iterable<WorkCenter> c = this.getWorkCenterService().getWorkCenterRepository().findAll();
        Iterator<WorkCenter> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toWorkCenter";
    }
}
