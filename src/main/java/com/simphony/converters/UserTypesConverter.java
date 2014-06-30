/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.PopulationService;
import com.simphony.beans.UserTypesService;
import com.simphony.entities.Population;
import com.simphony.entities.UserTypes;
import com.simphony.managedbeans.PopulationBean;
import com.simphony.pojos.ComboBox;
import com.simphony.selectors.PopulationBox;
import com.simphony.selectors.UserTypesBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("userTypesConverter")
public class UserTypesConverter implements Converter {

    @ManagedProperty(value = "#{userTypesService}")
    UserTypesService userTypesService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            UserTypesBox service = (UserTypesBox) context.getExternalContext().getApplicationMap().get("boxUserTyesService");
            UserTypes userType = service.getUserTypesService().getUserTypesRepository().findOne(Long.parseLong(value));
            return userType;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof UserTypes) {
            return String.valueOf(((UserTypes) value).getId());
        } else {
            return "";
        }
    }

    public UserTypesService getUserTypesService() {
        return userTypesService;
    }

    public void setUserTypesService(UserTypesService userTypesService) {
        this.userTypesService = userTypesService;
    }

    

}
