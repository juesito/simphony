/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.ItineraryService;
import com.simphony.entities.Itinerary;
import com.simphony.selectors.MainItineraryBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("mainItineraryConverter")
public class MainItineraryConverter implements Converter {

    @ManagedProperty(value = "#{itineraryService}")
    ItineraryService mainItineraryService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            MainItineraryBox service = (MainItineraryBox) context.getExternalContext().getApplicationMap().get("boxMainItineraryService");
            Itinerary mainItinerary = service.getMainItineraryService().getItineraryRepository().findOne(Long.parseLong(value));
            return mainItinerary;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Itinerary) {
            return String.valueOf(((Itinerary) value).getId());
        } else {
            return "";
        }
    }

    public ItineraryService getMainItineraryService() {
        return mainItineraryService;
    }

    public void setMainItineraryService(ItineraryService mainItineraryService) {
        this.mainItineraryService = mainItineraryService;
    }

}
