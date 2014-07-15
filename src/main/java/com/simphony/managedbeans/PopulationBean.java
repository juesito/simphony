/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.PopulationService;
import com.simphony.entities.Population;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ADD;
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
public class PopulationBean implements IConfigurable {

    private Population population = new Population();
    private Population current = new Population();
    private Population selected = new Population();
    private List<Population> list = new ArrayList<Population>();
    private Calendar cal = Calendar.getInstance();
        
    @ManagedProperty(value = "#{populationService}")
    private PopulationService populationService;
    

    /**
     * Creates a new instance of Population
     */
    public PopulationBean() {
    }

    @PostConstruct
    public void postInitialization() {

    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    public List<Population> getList() {
        return list;
    }

    public void setList(List<Population> list) {
        this.list = list;
    }

    public Population getSelected() {
        return selected;
    }

    public void setSelected(Population selected) {
        this.selected = selected;
    }

    public Population getCurrent() {
        return current;
    }

    public void setCurrent(Population current) {
        this.current = current;
    }

    public String addPopulation() {
        population = new Population();
        this.current.setAction(_ADD);
        return "addPopulation";
    }

    /**
     * Controlador para modificar Population
     *
     * @return
     */
    public String modifyPopulation() {
        this.current.setAction(_MODIFY);
        try {
            this.population = (Population) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(PopulationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addPopulation";
    }

    /**
     * deshabilitamos Population
    */
    public void disablePopulation()  {
        this.selected.setStatus(_DISABLE);

        Population populationUpdated = this.populationService.getPopulationRepository().findOne(selected.getId());

        populationUpdated.update(selected);
        this.populationService.getPopulationRepository().save(populationUpdated);

        this.fillPopulation();

    }
    /*
         * habilitamos Population
     */
    public void enablePopulation() {
        this.selected.setStatus(_ENABLED);

        Population populationUpdated = this.populationService.getPopulationRepository().findOne(selected.getId());

        populationUpdated.update(selected);
        this.populationService.getPopulationRepository().save(populationUpdated);

        this.fillPopulation();

    }

    public String cancelPopulation() {
        this.fillPopulation();
        return "toPopulations";
    }

    /**
     * Llenamos lista de agremiados
     */
    private void fillPopulation() {
        list.clear();
        Iterable<Population> c = this.populationService.getPopulationRepository().findAll();
        Iterator<Population> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Guardamos el Population
     * @return
     */
    public String save(User user) {
        population.setUser(user);
        population.setCreateDate(cal.getTime());
        population.setStatus(_ENABLED);
            
        this.populationService.getPopulationRepository().save(population);
        population = new Population();
        return "";
    }
    
    /**
     * Actualizamos el usuario
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {
        
        Population populationUpdated = this.populationService.getPopulationRepository().findOne(this.population.getId());
        
        if(populationUpdated == null){
            throw new PersonException("Población no existente"); 
        }

        population.setLastUpdate(cal.getTime());
        population.setUser(user);
        populationUpdated.update(this.population);
        this.populationService.getPopulationRepository().save(populationUpdated);
        population = new Population();
        return toPopulations();
    }

    /**
     * Controlador listar Population
     *
     * @return
     */
    public String toPopulations() {
        list.clear();
        Iterable<Population> c = this.getPopulationService().getPopulationRepository().findAll();
        Iterator<Population> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toPopulations";
    }

}
