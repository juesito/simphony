/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.BusService;
import com.simphony.entities.Bus;
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
@ManagedBean(name = "boxBusService", eager = true)
@ApplicationScoped
public class BusBox {

    @ManagedProperty(value = "#{busService}")
    private BusService busService;
    
    private List<Bus> list = new ArrayList<Bus>();
    private List<SelectItem> busList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of BusBox
     */
    public BusBox() {
    }

    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<Bus> optionList = this.getBusService().getBusRepository().findAllEnabled();

        for (Bus bus : optionList) {
            list.add(bus);
            busList.add(new SelectItem(bus, bus.getDescription()));
        }

    }
    
    public List<SelectItem> getBusList() {
        return busList;
    }

    public void setBusList(List<SelectItem> busList) {
        this.busList = busList;
    }

    public BusService getBusService() {
        return busService;
    }

    public void setBusService(BusService busService) {
        this.busService = busService;
    }

    public void fillBox() {
        busList.clear();
        init();
    }

    public List<Bus> getList() {
        return list;
    }

    public void setList(List<Bus> list) {
        this.list = list;
    }

}
