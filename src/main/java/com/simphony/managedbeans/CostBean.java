/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.CostService;
import com.simphony.entities.Cost;
import com.simphony.entities.Population;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
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
        this.cost.setCreateDate(cal.getTime());
       
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
        this.current.setAction(_ADD); 
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

    public String save(User user) {
        
        cost.setUser(user);
        Calendar cal = Calendar.getInstance();
        cost.setLastUpdate(cal.getTime());
        cost.setCreateDate(cal.getTime());
        cost.setStatus(_ENABLED);
         
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

    /**
     * Controlador para modificar tarifas
     *
     * @return
     */
    public String modifyCost() {
        this.current.setAction(_MODIFY);
        try {
            this.cost = (Cost) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(CostBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addCost";
    }

    /**
     * deshabilitamos Tarifas
     *
     */
    public void disableCost() {
        this.selected.setStatus(_DISABLE);

        Cost costUpdated = this.costService.getCostRepository().findOne(selected.getId());

        costUpdated.update(selected);
        this.costService.getCostRepository().save(costUpdated);

        this.fillCosts();

    }

     /**
     * habilitamos tarifas
     */
    public void enableCost() {
        this.selected.setStatus(_ENABLED);

        Cost costUpdated = this.costService.getCostRepository().findOne(selected.getId());

        costUpdated.update(selected);
        this.costService.getCostRepository().save(costUpdated);

        this.fillCosts();

    }

    /**
     * Actualizamos tarifa
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update() throws PersonException {
        
        Cost costUpdated = this.costService.getCostRepository().findOne(this.cost.getId());
        
        if(costUpdated == null){
            throw new PersonException("Tarifa no existente"); 
        }
        
        costUpdated.update(this.cost);
        this.costService.getCostRepository().save(costUpdated);
        cost = new Cost();
        return toCosts();
    }


}
