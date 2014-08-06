/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.PayTypeService;
import com.simphony.entities.PayType;
import com.simphony.selectors.PayTypeBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("payTypeConverter")
public class PayTypeConverter implements Converter {

    @ManagedProperty(value = "#{payTypeService}")
    PayTypeService payTypeService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            PayTypeBox service = (PayTypeBox) context.getExternalContext().getApplicationMap().get("boxPayTypeService");
            PayType payType = service.getPayTypeService().getPayTypeRepository().findOne(Long.parseLong(value));
            return payType;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof PayType) {
            return String.valueOf(((PayType) value).getId());
        } else {
            return "";
        }
    }

    public PayTypeService getPayTypeService() {
        return payTypeService;
    }

    public void setPayTypeService(PayTypeService payTypeService) {
        this.payTypeService = payTypeService;
    }

}
