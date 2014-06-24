/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.selectors;

import com.simphony.beans.PopulationService;
import com.simphony.entities.Population;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Soporte IT
 */
@ManagedBean(name="boxService", eager = true)
@SessionScoped
public class PopulationBox {

    @ManagedProperty(value = "#{populationService}")
    private PopulationService populationService;
    
    //private Map<String,String> box = new HashMap<String, String>();
    private List<Population> box = new ArrayList();
    
    /**
     * Creates a new instance of PopulationBox
     */
    public PopulationBox() {
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    public List<Population> getBox() {
        Iterable<Population> c = this.getPopulationService().getPopulationRepository().findAll();
        Iterator<Population> cu = c.iterator();
        while (cu.hasNext()) {
           Population population = cu.next();
           
           box.add(population);
        }
        
        return box;
    }

    
    
    
    
    
}
