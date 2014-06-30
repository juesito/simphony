/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.PopulationService;
import com.simphony.entities.Population;
import com.simphony.pojos.ComboBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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

    private Map<ComboBox, String> box = new TreeMap<ComboBox, String>(new ComboBoxComparator());
    private final Map<String, ComboBox> reverseComboBoxes = new HashMap<String, ComboBox>();
    private List<Population> list = new ArrayList<Population>();
    private List<SelectItem> populationList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of PopulationBox
     */
    public PopulationBox() {
    }

    @PostConstruct
    public void init() {
        Iterable<Population> c = this.getPopulationService().getPopulationRepository().findAll();
        Iterator<Population> cu = c.iterator();
//        if(box.size() > 0){
//            box.clear();
//        }

        while (cu.hasNext()) {
            Population population = cu.next();
            ComboBox cmb = new ComboBox(population.getId().toString(), population.getDescription().trim());
            box.put(cmb, cmb.getKey());
            reverseComboBoxes.put(cmb.getKey(), cmb);
            list.add(population);
            populationList.add(new SelectItem(population, population.getDescription()));
        }

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

    public Map<ComboBox, String> getBox() {
        return box;
    }

    public void setBox(Map<ComboBox, String> box) {
        this.box = box;
    }

   public ComboBox getBox(String key) {
        return reverseComboBoxes.get(key);
    }
   
   
    public void fillBox() {
//        Iterable<Population> c = this.getPopulationService().getPopulationRepository().findAll();
//        Iterator<Population> cu = c.iterator();
//        while (cu.hasNext()) {
//            Population population = cu.next();
//            box.add(population);
//        }
    }

    public List<Population> getList() {
        return list;
    }

    public void setList(List<Population> list) {
        this.list = list;
    }
    
    

}
