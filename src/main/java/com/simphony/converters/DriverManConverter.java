/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.DriverManService;
import com.simphony.entities.DriverMan;
import com.simphony.selectors.DriverManBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("driverManConverter")
public class DriverManConverter implements Converter {

    @ManagedProperty(value = "#{driverManService}")
    DriverManService driverManService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            DriverManBox service = (DriverManBox) context.getExternalContext().getApplicationMap().get("boxDriverManService");
            DriverMan driverMan = service.getDriverManService().getDriverManRepository().findOne(Long.parseLong(value));
            return driverMan;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof DriverMan) {
            return String.valueOf(((DriverMan) value).getId());
        } else {
            return "";
        }
    }

    public DriverManService getDriverManService() {
        return driverManService;
    }

    public void setDriverManService(DriverManService driverManService) {
        this.driverManService = driverManService;
    }

}
