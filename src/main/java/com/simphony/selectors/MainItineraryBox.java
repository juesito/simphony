/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.ItineraryService;
import com.simphony.entities.Itinerary;
import com.simphony.interfases.IConfigurable;
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
@ManagedBean(name = "boxMainItineraryService", eager = true)
@ApplicationScoped
public class MainItineraryBox implements IConfigurable{

    @ManagedProperty(value = "#{itineraryService}")
    private ItineraryService mainItineraryService;
    
    private List<Itinerary> list = new ArrayList<Itinerary>();
    private List<SelectItem> mainItineraryList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of MainItineraryBox
     */
    public MainItineraryBox() {
    }

    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<Itinerary> optionList = this.getMainItineraryService().getItineraryRepository().findAllLocal();

        for (Itinerary mainItinerary : optionList) {
            String msgBox = _SHM.format(mainItinerary.getDepartureTime()) + " " +
                    mainItinerary.getOrigin().getDescription() + " " +
                    mainItinerary.getDestiny().getDescription();
            
            mainItineraryList.add(new SelectItem(mainItinerary, msgBox));
        }

    }
    
   

    public void fillBox() {
        mainItineraryList.clear();
        init();
    }

    public ItineraryService getMainItineraryService() {
        return mainItineraryService;
    }

    public void setMainItineraryService(ItineraryService mainItineraryService) {
        this.mainItineraryService = mainItineraryService;
    }

    public List<Itinerary> getList() {
        return list;
    }

    public void setList(List<Itinerary> list) {
        this.list = list;
    }

    public List<SelectItem> getMainItineraryList() {
        return mainItineraryList;
    }

    public void setMainItineraryList(List<SelectItem> mainItineraryList) {
        this.mainItineraryList = mainItineraryList;
    }

    

}
