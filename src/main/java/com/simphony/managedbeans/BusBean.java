/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.managedbeans;

import com.simphony.beans.BusService;
import com.simphony.entities.Bus;
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
public class BusBean {
    
    private List<Bus> list = new ArrayList();
    private Bus current = new Bus();
    private Bus bus = new Bus();
    private Bus selected = new Bus();
    
    @ManagedProperty(value = "#{busService}")
    BusService busService;

    /**
     * Creates a new instance of BusBean
     */
    public BusBean() {
    }
    
    @PostConstruct
    public void postInitialization() {
    }

    public List<Bus> getList() {
        return list;
    }

    public void setList(List<Bus> list) {
        this.list = list;
    }

    public Bus getCurrent() {
        return current;
    }

    public void setCurrent(Bus current) {
        this.current = current;
    }

    public Bus getSelected() {
        return selected;
    }

    public void setSelected(Bus selected) {
        this.selected = selected;
    }
    
    public String addBus(){
        current = new Bus();
        
        return "addBus";
    }

    public BusService getBusService() {
        return busService;
    }

    public void setBusService(BusService busService) {
        this.busService = busService;
    }
    
    
    public void save(){
        Calendar cal = Calendar.getInstance();
        this.current.setCreateDate(cal.getTime());
        this.getBusService().getBusRepository().save(this.current);
        this.current = new Bus();
    }
    
    public String toBus(){
        list.clear();
        Iterable<Bus> c = this.getBusService().getBusRepository().findAll();
        Iterator<Bus> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toBus";
    }
    
}
