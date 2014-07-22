/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.managedbeans;

import com.simphony.beans.ItineraryDetailService;
import com.simphony.entities.ItineraryDetail;
import com.simphony.tools.MessageProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Administrador
 */
@ManagedBean
@ViewScoped
public class ItineraryDetailBean {
    
    private final MessageProvider mp;
    ItineraryDetail itineraryDetail = new ItineraryDetail();
    ItineraryDetail selected = new ItineraryDetail();
    List<ItineraryDetail> list = new ArrayList();

    
    @ManagedProperty(value = "#{itineraryDetailService}")
    private ItineraryDetailService itineraryDetailService;
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
     * Llenamos lista del detalle de  itinerarios
     */
    public void fillItineraries(Long itineraryId) {
        list.clear();
        list = this.itineraryDetailService.getItineraryDetailRepository().findAllByItinerary(itineraryId);
    }
    
    
    
}
