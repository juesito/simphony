/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.managedbeans;

import com.simphony.beans.PopulationService;
import com.simphony.entities.Population;
import com.simphony.interfases.IConfigurable;
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
public class PopulationBean {
    
    private List<Population> list = new ArrayList();
    private Population current = new Population();
    private Population population = new Population();
    private Population selected = new Population();
    
    @ManagedProperty(value = "#{populationService}")
    PopulationService populationService;

    /**
     * Creates a new instance of PopulationBean
     */
    public PopulationBean() {
    }
    
    @PostConstruct
    public void postInitialization() {
//        Calendar cal = Calendar.getInstance();
//        population.setDescription("MEXICO");
//        population.setCreateDate(cal.getTime());
//        population.setCreateHour(cal.getTime());
//        population.setStatus("A");
//        this.getPopulationService().getPopulationRepository().save(population);
    }

    public List<Population> getList() {
        return list;
    }

    public void setList(List<Population> list) {
        this.list = list;
    }

    public Population getCurrent() {
        return current;
    }

    public void setCurrent(Population current) {
        this.current = current;
    }

    public Population getSelected() {
        return selected;
    }

    public void setSelected(Population selected) {
        this.selected = selected;
    }
    
    public String addPopulation(){
        current = new Population();
        
        return "addPopulation";
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }
    
    
    public void save(){
        Calendar cal = Calendar.getInstance();
        this.current.setCreateDate(cal.getTime());
        this.current.setCreateHour(cal.getTime());
        this.current.setStatus(IConfigurable._ENABLED);
        this.getPopulationService().getPopulationRepository().save(this.current);
        this.current = new Population();
    }
    
    public String toPopulations(){
        list.clear();
        Iterable<Population> c = this.getPopulationService().getPopulationRepository().findAll();
        Iterator<Population> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toPopulations";
    }
    
}
