/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.converters;

import com.simphony.beans.AssociateService;
import com.simphony.entities.Associate;
import com.simphony.selectors.AssociateBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Jueser
 */
@FacesConverter("associateConverter")
public class AssociateConverter implements Converter {
    
    @ManagedProperty(value="#{associateService}")
    AssociateService associateService;

    public AssociateService getAssociateService() {
        return associateService;
    }

    public void setAssociateService(AssociateService associateService) {
        this.associateService = associateService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            AssociateBox service = (AssociateBox) context.getExternalContext().getApplicationMap().get("boxAssociateService");
            Associate associate = service.getAssociateService().getRepository().findOne(Long.parseLong(value));
            return associate;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Associate) {
            return String.valueOf(((Associate) value).getId());
        } else {
            return "";
        }
    }
    
    
}
