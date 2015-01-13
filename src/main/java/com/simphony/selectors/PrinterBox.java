/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.PrinterService;
import com.simphony.entities.Printer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

/**
 *
 * @author Soporte IT
 */
@ManagedBean(name = "boxPrinterService", eager = true)
@ApplicationScoped
public class PrinterBox {
    
     private List<SelectItem> printerList = new ArrayList<SelectItem>();
    
    @ManagedProperty(value = "#{printerService}")
    private PrinterService printerService;

    
    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<Printer> optionList = this.getPrinterService().getPrinterRepository().findAll();

        for (Printer printer : optionList) {
            printerList.add(new SelectItem(printer, printer.getBrand()));
        }

    }
    
    //Inicializamos la lista
    public void fillPrinterBox(){
        this.printerList.clear();
        init();
    }
    
    public PrinterService getPrinterService() {
        return printerService;
    }

    public void setPrinterService(PrinterService printerService) {
        this.printerService = printerService;
    }

    public List<SelectItem> getPrinterList() {
        return printerList;
    }

    public void setPrinterList(List<SelectItem> printerList) {
        this.printerList = printerList;
    }
    

    
    
    
}
