/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.VendorService;
import com.simphony.entities.Vendor;
import com.simphony.selectors.VendorBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("vendorConverter")
public class VendorConverter implements Converter {

    @ManagedProperty(value = "#{vendorService}")
    VendorService vendorService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            VendorBox service = (VendorBox) context.getExternalContext().getApplicationMap().get("boxVendorService");
            Vendor vendor = service.getVendorService().getVendorRepository().findOne(Long.parseLong(value));
            return vendor;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Vendor) {
            return String.valueOf(((Vendor) value).getId());
        } else {
            return "";
        }
    }

    public VendorService getVendorService() {
        return vendorService;
    }

    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

}
