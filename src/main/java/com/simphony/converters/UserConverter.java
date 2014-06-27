/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.converters;

import com.simphony.entities.User;
import com.simphony.selectors.UserBox;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("userConverter")
public class UserConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0) {
            UserBox service = (UserBox) context.getExternalContext().getApplicationMap().get("boxPopulationService");
            Integer myValue = Integer.parseInt(value);
            return service.getBox().get(myValue - 1);
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null && value instanceof User) {
            
            return String.valueOf(((User) value).getId());
        }
        else {
            return "";
        }
    }
    
}
