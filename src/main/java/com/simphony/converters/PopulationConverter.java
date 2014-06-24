/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.converters;

import com.simphony.entities.Population;
import com.simphony.selectors.PopulationBox;
import java.lang.annotation.Annotation;
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

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0) {
            PopulationBox service = (PopulationBox) context.getExternalContext().getApplicationMap().get("boxService");
            return service.getBox().get(Integer.parseInt(value));
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null) {
            return String.valueOf(((Population) value).getId());
        }
        else {
            return null;
        }
    }

}
