/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.WorkCenterService;
import com.simphony.entities.WorkCenter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author root
 */
@ManagedBean(name = "boxWorkCenterService", eager = true)
@ApplicationScoped
public class WorkCenterBox {

    @ManagedProperty(value = "#{workCenterService}")
    private WorkCenterService workCenterService;

    //private Map<String,String> box = new HashMap<String, String>();
    private final List<WorkCenter> box = new ArrayList();

    public WorkCenterBox() {
    }
    

    @PostConstruct
    public void init() {
        Iterable<WorkCenter> c = this.getWorkCenterService().getWorkCenterRepository().findAll();
        Iterator<WorkCenter> cu = c.iterator();
        while (cu.hasNext()) {
            WorkCenter workCenter = cu.next();
            box.add(workCenter);
        }
    }

    public WorkCenterService getWorkCenterService() {
        return workCenterService;
    }

    public void setWorkCenterService(WorkCenterService workCenterService) {
        this.workCenterService = workCenterService;
    }

    public List<WorkCenter> getBox() {
        return box;
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
