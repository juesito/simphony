/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.SalePointService;
import com.simphony.entities.SalePoint;
import com.simphony.selectors.SalePointBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("salePointConverter")
public class SalePointConverter implements Converter {

    @ManagedProperty(value = "#{salePointService}")
    SalePointService salePointService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            SalePointBox service = (SalePointBox) context.getExternalContext().getApplicationMap().get("boxSalePointService");
            SalePoint salePoint = service.getSalePointService().getSalePointRepository().findOne(Long.parseLong(value));
            return salePoint;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof SalePoint) {
            return String.valueOf(((SalePoint) value).getId());
        } else {
            return "";
        }
    }

    public SalePointService getSalePointService() {
        return salePointService;
    }

    public void setSalePointService(SalePointService salePointService) {
        this.salePointService = salePointService;
    }

}
