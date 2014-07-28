/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.PopulationService;
import com.simphony.entities.Population;
import com.simphony.selectors.PopulationBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("populationConverter")
public class PopulationConverter implements Converter {

    @ManagedProperty(value = "#{populationService}")
    PopulationService populationService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            PopulationBox service = (PopulationBox) context.getExternalContext().getApplicationMap().get("boxPopulationService");
            Population population = service.getPopulationService().getPopulationRepository().findOne(Long.parseLong(value));
            return population;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Population) {
            return String.valueOf(((Population) value).getId());
        } else {
            return "";
        }
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

}
