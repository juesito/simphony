/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.converters;

import com.simphony.beans.PrinterService;
import com.simphony.entities.Printer;
import com.simphony.selectors.PrinterBox;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author root
 */
@FacesConverter("printerConverter")
public class PrinterConverter implements Converter {

    @ManagedProperty(value = "#{printerService}")
    PrinterService printerService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            PrinterBox service = (PrinterBox) context.getExternalContext().getApplicationMap().get("boxPrinterService");
            Printer printer = service.getPrinterService().getPrinterRepository().findOne(Long.parseLong(value));
            return printer;
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Printer) {
            return String.valueOf(((Printer) value).getId());
        } else {
            return "";
        }
    }

    public PrinterService getPrinterService() {
        return printerService;
    }

    public void setPrinterService(PrinterService printerService) {
        this.printerService = printerService;
    }
    
    

}
