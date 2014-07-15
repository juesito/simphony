/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.managedbeans;

import com.simphony.beans.BusService;
import com.simphony.entities.Bus;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ADD;
import static com.simphony.interfases.IConfigurable._DISABLE;
import static com.simphony.interfases.IConfigurable._ENABLED;
import static com.simphony.interfases.IConfigurable._MODIFY;
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
 * @author Soporte IT
 */
@ManagedBean
@SessionScoped
public class BusBean implements IConfigurable {
    
    private List<Bus> list = new ArrayList<Bus>();
    private Bus current = new Bus();
    private Bus bus = new Bus();
    private Bus selected = new Bus();
    
    @ManagedProperty(value = "#{busService}")
    private BusService busService;

    /**
     * Creates a new instance of BusBean
     */
    public BusBean() {
    }
    
    @PostConstruct
    public void postInitialization() {
    }

    public List<Bus> getList() {
        return list;
    }

    public void setList(List<Bus> list) {
        this.list = list;
    }

    public Bus getCurrent() {
        return current;
    }

    public void setCurrent(Bus current) {
        this.current = current;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Bus getSelected() {
        return selected;
    }

    public void setSelected(Bus selected) {
        this.selected = selected;
    }
    
    public String addBus(){
        current = new Bus();
        this.current.setAction(_ADD);
                
        return "addBus";
    }

    public BusService getBusService() {
        return busService;
    }

    public void setBusService(BusService busService) {
        this.busService = busService;
    }
    
    
    public void save(){
        Calendar cal = Calendar.getInstance();
        this.current.setCreateDate(cal.getTime());
        this.current.setLastUpdate(cal.getTime());
        this.current.setStatus(_ENABLED);
        this.getBusService().getBusRepository().save(this.current);
        this.current = new Bus();
    }
    
    public String toBus(){
        list.clear();
        Iterable<Bus> c = this.getBusService().getBusRepository().findAll();
        Iterator<Bus> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toBus";
    }
   
    /**
     * Controlador para modificar Bus
     *
     * @return
     */
    public String modifyBus() {
        this.current.setAction(_MODIFY);
        try {
            this.bus = (Bus) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(PopulationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addBus";
    }

    /**
     * deshabilitamos Bus
    */
    public void disableBus()  {
        this.selected.setStatus(_DISABLE);

        Bus busUpdated = this.busService.getBusRepository().findOne(selected.getId());

        busUpdated.update(selected);
        this.busService.getBusRepository().save(busUpdated);

        this.fillBus();

    }
    /*
         * habilitamos Bus
     */
    public void enableBus() {
        this.selected.setStatus(_ENABLED);

        Bus busUpdated = this.busService.getBusRepository().findOne(selected.getId());

        busUpdated.update(selected);
        this.busService.getBusRepository().save(busUpdated);

        this.fillBus();

    }

    public String cancelBus() {
        this.fillBus();
        return "toBus";
    }

    /**
     * Llenamos lista de agremiados
     */
    private void fillBus() {
        list.clear();
        Iterable<Bus> c = this.busService.getBusRepository().findAll();
        Iterator<Bus> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Actualizamos el autobús
     *
     * @return
      */
    public String update() {
        
        Bus busUpdated = this.busService.getBusRepository().findOne(this.bus.getId());
        
        busUpdated.update(this.bus);
        this.busService.getBusRepository().save(busUpdated);
        bus = new Bus();
        return toBus();
    }

}
