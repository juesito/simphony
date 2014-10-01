/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.converters;

import com.simphony.beans.ItineraryService;
import com.simphony.entities.Itinerary;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.NamingException;

/**
 *
 * @author Administrador
 */
@FacesConverter("itineraryConverter")
public class ItineraryConverter implements Converter{
    @ManagedProperty(value="#{itineraryService}")
    ItineraryService itineraryService;
    
    private List<Itinerary>itineraries;

    public ItineraryService getItineraryService() {
        return itineraryService;
    }

    public void setItineraryService(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    public ItineraryConverter() {
        itineraries = itineraryService.getItineraryRepository().findAll();
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    
    
    
    
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {  
  
                int myId = Integer.parseInt(submittedValue);
                for (Itinerary p : itineraries) {  
                    if (p.getId() == myId) {  
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Reactivo Invalido"));  
            }  
        }  
  
        return null;  
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Itinerary) value).getId());  
        }  
    }

    
    
}
