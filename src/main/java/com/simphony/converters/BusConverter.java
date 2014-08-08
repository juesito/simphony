/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.BusService;
import com.simphony.entities.Bus;
import com.simphony.selectors.BusBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("busConverter")
public class BusConverter implements Converter {

    @ManagedProperty(value = "#{busService}")
    BusService busService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            BusBox service = (BusBox) context.getExternalContext().getApplicationMap().get("boxBusService");
            Bus bus = service.getBusService().getBusRepository().findOne(Long.parseLong(value));
            return bus;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Bus) {
            return String.valueOf(((Bus) value).getId());
        } else {
            return "";
        }
    }

    public BusService getBusService() {
        return busService;
    }

    public void setBusService(BusService busService) {
        this.busService = busService;
    }

}
