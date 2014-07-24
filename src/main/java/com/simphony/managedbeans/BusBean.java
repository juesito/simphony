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
import com.simphony.tools.MessageProvider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.springframework.data.domain.Sort;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class BusBean {

    private final MessageProvider mp;
    private List<Bus> list = new ArrayList();
    private Bus current = new Bus();
    private Bus bus = new Bus();
    private Bus selected = new Bus();
    private Calendar cal = Calendar.getInstance();

    @ManagedProperty(value = "#{busService}")
    BusService busService;

    /**
     * Creates a new instance of Bus
     */
    public BusBean() {
       mp = new MessageProvider();
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
         Boolean exist = true;
         Bus busTmp;

        try {
            busTmp = this.busService.getBusRepository().findByNum(this.bus.getNumber().trim());
            if(busTmp == null){
                exist = false;
            }
        } catch (Exception ex) {
            System.out.println("Error");
            exist = false;

        }

        if (!exist) {
            if (this.bus.getNumber() != null) {
            bus.setUser(user);
            bus.setCreateDate(cal.getTime());
            bus.setStatus(IConfigurable._ENABLED);
            this.busService.getBusRepository().save(bus);
            GrowlBean.simplyInfoMessage(mp.getValue("msj_save"), mp.getValue("msj_record_save") + this.bus.getId());
            bus = new Bus();

            }
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_keyId") + mp.getValue("cat_keyId"), mp.getValue("error_keyId_Detail") + this.bus.getNumber());
        }

        return "";
      }

    public String toBus() {
        this.fillBus();
        return "toBus";
    }
    
       /**
     * Controlador para modificar Bus
     *
     * @return
     */
    public String modifyBus() {
        if (this.selected != null ) {
            this.current.setAction(_MODIFY);
            try {
                this.bus = (Bus) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(BusBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addBus";
        }else
            return "toBus";
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
        Iterable<Bus> c = this.busService.getBusRepository().findAll(sortByNumber());
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
        GrowlBean.simplyInfoMessage(mp.getValue("msj_update"), mp.getValue("msj_record_update") + this.bus.getId());
        bus = new Bus();
        return toBus();
    }

      /**
     * habilitamos Bus
     */
    public void enabledBus() {
        this.selected.setStatus(_ENABLED);

        Bus busUpdated = this.busService.getBusRepository().findOne(selected.getId());

        busUpdated.update(selected);
        this.busService.getBusRepository().save(busUpdated);

        this.fillBus();

    }

        private Sort sortByNumber(){
        return new Sort(Sort.Direction.ASC, "number");
    }

}
