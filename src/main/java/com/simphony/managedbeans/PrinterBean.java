/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.PrinterService;
import com.simphony.entities.User;
import com.simphony.entities.Printer;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ADD;
import static com.simphony.interfases.IConfigurable._DISABLE;
import static com.simphony.interfases.IConfigurable._ENABLED;
import static com.simphony.interfases.IConfigurable._MODIFY;
import com.simphony.tools.MessageProvider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class PrinterBean implements IConfigurable {

    private final MessageProvider mp;
    private Printer printer = new Printer();
    private Printer current = new Printer();
    private Printer selected = new Printer();
    private List<Printer> list = new ArrayList<Printer>();

    @ManagedProperty(value = "#{printerService}")
    private PrinterService printerService;
    private Calendar cal = Calendar.getInstance();

    /**
     * Creates a new instance of PrinterBean
     */
    public PrinterBean() {
        mp = new MessageProvider();
    }

    @PostConstruct
    public void postInitialization() {

    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public PrinterService getPrinterService() {
        return printerService;
    }

    public void setPrinterService(PrinterService printerService) {
        this.printerService = printerService;
    }

    public List<Printer> getList() {
        return list;
    }

    public void setList(List<Printer> list) {
        this.list = list;
    }

    public Printer getSelected() {
        return selected;
    }

    public void setSelected(Printer selected) {
        this.selected = selected;
    }

    public Printer getCurrent() {
        return current;
    }

    public void setCurrent(Printer current) {
        this.current = current;
    }

    public String addPrinter() {
        printer = new Printer();
        this.current.setAction(_ADD);
        return "addPrinter";
    }

    /**
     * Controlador para modificar Printer
     *
     * @return
     */
    public String modifyPrinter() {
        if (this.selected != null ) {
            this.current.setAction(_MODIFY);
            try {
                this.printer = (Printer) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PrinterBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addPrinter";
        }else
            return "toPrinter";
    }

    /**
     * deshabilitamos Printer
     *
     * @throws com.simphony.exceptions.PersonException
     */
    public void disablePrinter() throws PersonException {
        this.selected.setStatus(_DISABLE);

        Printer printerUpdated = this.printerService.getPrinterRepository().findOne(selected.getId());

        if (printerUpdated == null) {
            throw new PersonException("Estación de trabajo no existente");
        }

        printerUpdated.update(selected);
        this.printerService.getPrinterRepository().save(printerUpdated);

        this.fillPrinter();

    }

    public String cancelPrinter() {
        this.fillPrinter();
        return "toPrinter";
    }

    /**
     * Llenamos lista de printers
     */
    private void fillPrinter() {
        list.clear();
        Iterable<Printer> c = this.printerService.getPrinterRepository().findAll();
        Iterator<Printer> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Guardamos el Printer
     * @return
     */
    public String save(User user) {
        printer.setUser(user);
        printer.setCreateDate(cal.getTime());
        printer.setStatus(_ENABLED);

        this.printerService.getPrinterRepository().save(printer);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.printer.getDescription()+" "+mp.getValue("msj_record_save"));
        printer = new Printer();

        return "";
    }
    
    /**
     * Actualizamos el printer
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {
        
        Printer printerUpdated = this.printerService.getPrinterRepository().findOne(this.printer.getId());
        
        if(printerUpdated == null){
            throw new PersonException("Impresora no existente"); 
        }
        printer.setUser(user);
        printer.setLastUpdate(cal.getTime());
        printerUpdated.update(this.printer);
        this.printerService.getPrinterRepository().save(printerUpdated);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.printer.getBrand()+" "+mp.getValue("msj_record_update"));
        printer = new Printer();
        return toPrinter();
    }
 
    /**
     * Controlador listar Printer
     *
     * @return
     */
    public String toPrinter() {
        list.clear();
        Iterable<Printer> c = this.getPrinterService().getPrinterRepository().findAll();
        Iterator<Printer> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toPrinter";
    }

     /**
     * habilitamos Printer
     */
    public void enabledPrinter() {
        this.selected.setStatus(_ENABLED);

        Printer printerUpdated = this.printerService.getPrinterRepository().findOne(selected.getId());

        printerUpdated.update(selected);
        this.printerService.getPrinterRepository().save(printerUpdated);

        this.fillPrinter();

    }

}
