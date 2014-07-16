package com.simphony.managedbeans;

import com.simphony.beans.BusService;
import com.simphony.entities.Bus;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class BusBean {

    private List<Bus> list = new ArrayList();
    private Bus current = new Bus();
    private Bus bus = new Bus();
    private Bus selected = new Bus();

    @ManagedProperty(value = "#{busService}")
    BusService busService;

    /**
     * Creates a new instance of Bus
     */
    public BusBean() {
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

    public Bus getSelected() {
        return selected;
    }

    public void setSelected(Bus selected) {
        this.selected = selected;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public BusService getBusService() {
        return busService;
    }

    public void setBusService(BusService busService) {
        this.busService = busService;
    }

    public String addBus() {
        bus = new Bus();
        this.current.setAction(_ADD);
        return "addBus";
    }

    public String save(User user) {
        Calendar cal = Calendar.getInstance();
        bus.setUser(user);
        bus.setCreateDate(cal.getTime());
        bus.setStatus(IConfigurable._ENABLED);
        this.busService.getBusRepository().save(bus);
        bus = new Bus();
        return "";
    }

    public String toBus() {
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
            Logger.getLogger(BusBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addBus";
    }

    /**
     * deshabilitamos Bus
     *
     * @throws com.simphony.exceptions.PersonException
     */
    public void disableBus() throws PersonException {
        this.selected.setStatus(_DISABLE);

        Bus busUpdated = this.busService.getBusRepository().findOne(selected.getId());

        if (busUpdated == null) {
            throw new PersonException("Autobús no existente");
        }

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
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {
        
        Bus busUpdated = this.busService.getBusRepository().findOne(this.bus.getId());
        
        if(busUpdated == null){
            throw new PersonException("Autobús no existente"); 
        }
        this.bus.setUser(user);
        busUpdated.update(this.bus);
        this.busService.getBusRepository().save(busUpdated);
        bus = new Bus();
        return toBus();
    }

      /**
     * habilitamos Bus
     */
    public void enableBus() {
        this.selected.setStatus(_ENABLED);

        Bus busUpdated = this.busService.getBusRepository().findOne(selected.getId());

        busUpdated.update(selected);
        this.busService.getBusRepository().save(busUpdated);

        this.fillBus();

    }

}
