/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.DriverManService;
import com.simphony.entities.DriverMan;
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
@ManagedBean(name = "boxDriverManService", eager = true)
@ApplicationScoped
public class DriverManBox {

    @ManagedProperty(value = "#{driverManService}")
    private DriverManService driverManService;
    
    private List<DriverMan> list = new ArrayList<DriverMan>();
    private List<SelectItem> driverManList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of DriverManBox
     */
    public DriverManBox() {
    }

    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<DriverMan> optionList = this.getDriverManService().getDriverManRepository().findAllEnabled();

        for (DriverMan driverMan : optionList) {
            list.add(driverMan);
            String fullName = driverMan.getFirstLastName()+" "+ driverMan.getSecondLastName()+" "+driverMan.getName();
            driverManList.add(new SelectItem(driverMan, fullName));
        }

    }
    
    public List<SelectItem> getDriverManList() {
        return driverManList;
    }

    public void setDriverManList(List<SelectItem> driverManList) {
        this.driverManList = driverManList;
    }

    public DriverManService getDriverManService() {
        return driverManService;
    }

    public void setDriverManService(DriverManService driverManService) {
        this.driverManService = driverManService;
    }

    public void fillBox() {
        driverManList.clear();
        init();
    }

    public List<DriverMan> getList() {
        return list;
    }

    public void setList(List<DriverMan> list) {
        this.list = list;
    }

}
