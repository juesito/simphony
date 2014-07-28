/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.ItineraryDetailService;
import com.simphony.entities.Itinerary;
import com.simphony.entities.ItineraryDetail;
import com.simphony.exceptions.ItineraryException;
import com.simphony.interfases.IConfigurable;
import com.simphony.tools.MessageProvider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ItineraryDetailBean implements IConfigurable {

    private final MessageProvider mp;
    ItineraryDetail itineraryDetail = new ItineraryDetail();
    ItineraryDetail selected = new ItineraryDetail();
    List<ItineraryDetail> list = new ArrayList();

    @ManagedProperty(value = "#{itineraryDetailService}")
    private ItineraryDetailService itineraryDetailService;

    private Calendar cal = Calendar.getInstance();
    /**
     * Creates a new instance of ItineraryDetailBean
     */
    public ItineraryDetailBean() {
        mp = new MessageProvider();
    }

    public ItineraryDetailService getItineraryDetailService() {
        return itineraryDetailService;
    }

    public void setItineraryDetailService(ItineraryDetailService itineraryDetailService) {
        this.itineraryDetailService = itineraryDetailService;
    }

    public ItineraryDetail getItineraryDetail() {
        return itineraryDetail;
    }

    public void setItineraryDetail(ItineraryDetail itineraryDetail) {
        this.itineraryDetail = itineraryDetail;
    }

    public ItineraryDetail getSelected() {
        return selected;
    }

    public void setSelected(ItineraryDetail selected) {
        this.selected = selected;
    }

    public List<ItineraryDetail> getList() {
        return list;
    }

    public void setList(List<ItineraryDetail> list) {
        this.list = list;
    }

    /**
     * Llenamos lista del detalle de itinerarios
     */
    public void fillItineraries(Long itineraryId) {
        list.clear();
        list = this.itineraryDetailService.getItineraryDetailRepository().findAllByItinerary(itineraryId);
    }

    public String addItineraryDetail() {
        itineraryDetail = new ItineraryDetail();
        this.itineraryDetail.setAction(_ADD);
        return "addItineraryDetail";
    }

    public String modifyItinerary() {
        if (this.selected != null) {
            try {
                this.itineraryDetail = (ItineraryDetail) this.selected.clone();
                this.itineraryDetail.setAction(_MODIFY);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ItineraryDetailBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addItineraryDetail";
        } else {
            return "toItineraryDetail";
        }
    }

    /**
     * Guardamos el registro
     * @param itinerary
     */
    public void save(Itinerary itinerary) {
        Boolean exist = true;
        if (this.itineraryDetail.getOrigin() != null && this.itineraryDetail.getDestiny() != null) {
            
            itineraryDetail.setItinerary(itinerary);
            itineraryDetail.setStatus(_ENABLED);

            this.itineraryDetailService.getItineraryDetailRepository().save(itineraryDetail);
            GrowlBean.simplyInfoMessage(mp.getValue("msj_save"), mp.getValue("msj_record_save") + this.itineraryDetail.getOrigin().getDescription());
            itineraryDetail = new ItineraryDetail();
            itineraryDetail.setAction(_ADD);

        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("msj_save"), mp.getValue("msj_record_save") + this.itineraryDetail.getOrigin().getDescription());
        }
    }
    
    /**
     * deshabilitamos itinerario
     *
     */
    public void disableItineraryDetail() {
        this.selected.setStatus(_DISABLE);
        
        ItineraryDetail itineraryUpdated = this.itineraryDetailService.getItineraryDetailRepository().findOne(selected.getId());
        
        itineraryUpdated.update(selected);
        this.itineraryDetailService.getItineraryDetailRepository().save(itineraryUpdated);
        
        this.fillItineraries(this.selected.getItinerary().getId());
        
    }

    /**
     * habilitamos agremiado
     */
    public void enabledItineraryDetail() {
        this.selected.setStatus(_ENABLED);
        
        ItineraryDetail itineraryUpdated = this.itineraryDetailService.getItineraryDetailRepository().findOne(selected.getId());
        
        itineraryUpdated.update(selected);
        this.itineraryDetailService.getItineraryDetailRepository().save(itineraryUpdated);
        
        this.fillItineraries(this.selected.getItinerary().getId());
        
    }
    
    /**
     * Actualizamos el detalle de itinerarios
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update() throws ItineraryException {
        
        ItineraryDetail itineraryDetailUpdated = this.itineraryDetailService.getItineraryDetailRepository().findOne(this.itineraryDetail.getId());
        
        if (itineraryDetailUpdated == null) {
            throw new ItineraryException(mp.getValue("cat_itinerary") + " " + mp.getValue("not_founded"));
        }
        
        itineraryDetailUpdated.update(itineraryDetailUpdated);
        this.itineraryDetailService.getItineraryDetailRepository().save(itineraryDetailUpdated);
        
        GrowlBean.simplyInfoMessage(mp.getValue("msj_modified"), mp.getValue("msj_record_modified") + itineraryDetailUpdated.getOrigin().getDescription());
        
        itineraryDetail = new ItineraryDetail();
        fillItineraries(itineraryDetailUpdated.getItinerary().getId());
        return "toItineraryDetail";
    }

    public String cancelItineraryDetail() {
        return "toItineraryDetail";
    }
}
