/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.managedbeans;

import com.simphony.beans.ItineraryService;
import com.simphony.entities.Itinerary;
import com.simphony.entities.User;
import com.simphony.interfases.IConfigurable;
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
import org.springframework.data.domain.Sort;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class ItineraryBean implements IConfigurable{
    
    private Itinerary itinerary = new Itinerary();
    private Itinerary selected = new Itinerary();
    private List<Itinerary> list = new ArrayList<Itinerary>();

    @ManagedProperty (value = "#{itineraryService}")
    private ItineraryService itineraryService;
    
    private Calendar cal = Calendar.getInstance();
    
    /**
     * Creates a new instance of ItineraryBean
     */
    public ItineraryBean() {
    }
    
    @PostConstruct
    public void postInitialization() {

    }

    public void setItineraryService(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    public ItineraryService getItineraryService() {
        return itineraryService;
    }
    
    

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Itinerary getSelected() {
        return selected;
    }

    public void setSelected(Itinerary selected) {
        this.selected = selected;
    }

    public List<Itinerary> getList() {
        return list;
    }

    public void setList(List<Itinerary> list) {
        this.list = list;
    }
    
    public String addItinerary() {
        itinerary = new Itinerary();
        this.itinerary.setAction(_ADD);
        return "addItinerary";
    }

    /**
     * Controlador para modificar itinerario
     *
     * @return
     */
    public String modifyItinerary() {
        if (this.selected != null ) {
            this.itinerary.setAction(_MODIFY);
            try {
                this.itinerary = (Itinerary) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ItineraryBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addItinerary";
        }else
            return "toItineraries";
    }

    /**
     * deshabilitamos itinerario
     *
     */
    public void disableItinerary() {
        this.selected.setStatus(_DISABLE);

        Itinerary itineraryUpdated = this.itineraryService.getItineraryRepository().findOne(selected.getId());

        itineraryUpdated.update(selected);
        this.itineraryService.getItineraryRepository().save(itineraryUpdated);

        this.fillItineraries();

    }

     /**
     * habilitamos agremiado
     */
    public void enabledItinerary() {
        this.selected.setStatus(_ENABLED);

        Itinerary itineraryUpdated = this.itineraryService.getItineraryRepository().findOne(selected.getId());

        itineraryUpdated.update(selected);
        this.itineraryService.getItineraryRepository().save(itineraryUpdated);

        this.fillItineraries();

    }

    public String cancelItinerary() {
        this.fillItineraries();
        return "toItineraries";
    }

    /**
     * Llenamos lista de itinerarios
     */
    private void fillItineraries() {
        list.clear();
        Iterable<Itinerary> c = this.itineraryService.getItineraryRepository().findAll(sortByKeyId());
        Iterator<Itinerary> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Guardamos el itinerario
     * @param user
     * @return
     */
    public String save(User user) {
        if (this.itinerary.getOrigin() != null && this.itinerary.getDestiny()!= null) {
            itinerary.setUserId(user);
            itinerary.setCreateDate(cal.getTime());
            itinerary.setStatus(_ENABLED);
            
            this.itineraryService.getItineraryRepository().save(itinerary);
            itinerary = new Itinerary();
        }
        return "";
    }
    
    
    /**
     * Controlador listar Itinerario
     *
     * @return
     */
    public String toItineraries() {
        this.fillItineraries();
        return "toItineraries";
    }

    private Sort sortByKeyId(){
        return new Sort(Sort.Direction.ASC, "origin");
    }
    
}
