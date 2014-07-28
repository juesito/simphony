/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.ItineraryService;
import com.simphony.beans.PopulationService;
import com.simphony.entities.Itinerary;
import com.simphony.entities.Population;
import com.simphony.pojos.ComboBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

/**
 *
 * @author Soporte IT
 */
@ManagedBean(name = "boxPopulationService", eager = true)
@ApplicationScoped
public class PopulationBox {

    @ManagedProperty(value = "#{populationService}")
    private PopulationService populationService;
    
    @ManagedProperty(value = "#{itineraryService}")
    private ItineraryService itineraryService;

    private List<Population> list = new ArrayList<Population>();
    private List<SelectItem> populationList = new ArrayList<SelectItem>();
    private List<SelectItem> populationOriSaleList = new ArrayList<SelectItem>();
    private List<SelectItem> populationDesSaleList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of PopulationBox
     */
    public PopulationBox() {
    }

    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<Population> optionList = this.getPopulationService().getPopulationRepository().findAllEnabled();

        for (Population population : optionList) {
            list.add(population);
            populationList.add(new SelectItem(population, population.getDescription()));
        }

    }
    
    public void fillPopulationOriSaleList(){
        
        populationOriSaleList.clear();
        //Llena solo poblaciones activas
        List<Population> optionList = this.getPopulationService().getPopulationRepository().
                findOriginsForSale();
        for (Population population : optionList) {            
            populationOriSaleList.add(new SelectItem(population, population.getDescription()));
        }
        
    }
    
    public void fillPopulationDesSaleList(Long originId){
        
        populationDesSaleList.clear();
        //Llena solo poblaciones activas
        List<Population> optionList = this.getPopulationService().getPopulationRepository().
                findDestiniesForSale(originId);

        for (Population population : optionList) {            
            populationDesSaleList.add(new SelectItem(population, population.getDescription()));
        }
        
    }

    public ItineraryService getItineraryService() {
        return itineraryService;
    }

    public void setItineraryService(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    
    public List<SelectItem> getPopulationList() {
        return populationList;
    }

    public void setPopulationList(List<SelectItem> populationList) {
        this.populationList = populationList;
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    public void fillBox() {
        populationList.clear();
        init();
    }

    public List<Population> getList() {
        return list;
    }

    public void setList(List<Population> list) {
        this.list = list;
    }

    public List<SelectItem> getPopulationOriSaleList() {
        return populationOriSaleList;
    }

    public void setPopulationOriSaleList(List<SelectItem> populationOriSaleList) {
        this.populationOriSaleList = populationOriSaleList;
    }

    public List<SelectItem> getPopulationDesSaleList() {
        return populationDesSaleList;
    }

    public void setPopulationDesSaleList(List<SelectItem> populationDesSaleList) {
        this.populationDesSaleList = populationDesSaleList;
    }
    
    

    
}
