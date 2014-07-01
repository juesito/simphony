/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.CostService;
import com.simphony.entities.Population;
import com.simphony.entities.Cost;
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
 * @author root
 */
@ManagedBean
@SessionScoped
public class CostBean implements IConfigurable {

    private Cost cost = new Cost();
    private Cost current = new Cost();
    private Cost selected = new Cost();
    private List<Cost> list = new ArrayList<Cost>();
    private Population population = new Population();

    @ManagedProperty(value = "#{costService}")
    private CostService costService;

    /**
     * Creates a new instance of CostBean
     */
    public CostBean() {
    }

    @PostConstruct
    public void postInitialization() {
        
        Calendar cal = Calendar.getInstance();
        this.current.setCreateDate(cal.getTime());
       
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public CostService getCostService() {
        return costService;
    }

    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    public List<Cost> getList() {
        return list;
    }

    public void setList(List<Cost> list) {
        this.list = list;
    }

    public Cost getSelected() {
        return selected;
    }

    public void setSelected(Cost selected) {
        this.selected = selected;
    }

    public Cost getCurrent() {
        return current;
    }

    public void setCurrent(Cost current) {
        this.current = current;
    }

    public String addCost() {
        cost = new Cost();
        
        return "addCost";
    }
    
    /**
     * boton de cancelar
     *
     * @return
     */
    public String cancelCost() {
        this.fillCosts();
        return "toCosts";
    }

    public String save() {
        
        Calendar cal = Calendar.getInstance();
        cost.setCreateDate(cal.getTime());
         
        this.costService.getCostRepository().save(cost);
        cost = new Cost();
        return "";
    }

    private void fillCosts(){
        list.clear();
        Iterable<Cost> c = this.getCostService().getCostRepository().findAll();
        Iterator<Cost> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }
    /**
     * Controlador listar tarifas
     *
     * @return
     */
    public String toCosts() {
        fillCosts();
        return "toCosts";
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }
}
