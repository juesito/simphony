/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.converters;

import com.simphony.entities.WorkCenter;
import com.simphony.selectors.WorkCenterBox;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("workCenterConverter")
public class WorkCenterConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.trim().length() > 0) {
            WorkCenterBox service = (WorkCenterBox) context.getExternalContext().getApplicationMap().get("boxWorkCenterService");
            Integer myValue = Integer.parseInt(value);
            return service.getBox().get(myValue - 1);
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null && value instanceof WorkCenter) {
            return String.valueOf(((WorkCenter) value).getId());
        }
        else {
            return "";
        }
    }
    
}
