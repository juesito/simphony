package com.simphony.managedbeans;

import com.simphony.beans.DriverManService;
import com.simphony.entities.DriverMan;
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
public class DriverManBean implements IConfigurable {

    private DriverMan driverMan = new DriverMan();
    private DriverMan current = new DriverMan();
    private DriverMan selected = new DriverMan();
    private List<DriverMan> list = new ArrayList<DriverMan>();

    @ManagedProperty(value = "#{driverManService}")
    private DriverManService driverManService;

    /**
     * Creates a new instance of DriverManBean
     */
    public DriverManBean() {
    }

    @PostConstruct
    public void postInitialization() {

    }

    public DriverMan getDriverMan() {
        return driverMan;
    }

    public void setDriverMan(DriverMan driverMan) {
        this.driverMan = driverMan;
    }

    public DriverManService getDriverManService() {
        return driverManService;
    }

    public void setDriverManService(DriverManService driverManService) {
        this.driverManService = driverManService;
    }

    public List<DriverMan> getList() {
        return list;
    }

    public void setList(List<DriverMan> list) {
        this.list = list;
    }

    public DriverMan getSelected() {
        return selected;
    }

    public void setSelected(DriverMan selected) {
        this.selected = selected;
    }

    public DriverMan getCurrent() {
        return current;
    }

    public void setCurrent(DriverMan current) {
        this.current = current;
    }

    public String addDriverMan() {
        driverMan = new DriverMan();
        this.current.setAction(_ADD);
        return "addDriverMan";
    }

    /**
     * Controlador para modificar DriverMan
     *
     * @return
     */
    public String modifyDriverMan() {
        this.current.setAction(_MODIFY);
        try {
            this.driverMan = (DriverMan) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(DriverManBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addDriverMan";
    }

    /**
     * deshabilitamos DriverMan
    */
    public void disableDriverMan() {
        this.selected.setStatus(_DISABLE);

        DriverMan driverManUpdated = this.driverManService.getDriverManRepository().findOne(selected.getId());

        driverManUpdated.update(selected);
        this.driverManService.getDriverManRepository().save(driverManUpdated);

        this.fillDriverMan();

    }

     /**
     * habilitamos DriverMan
     */
    public void enableDriverMan() {
        this.selected.setStatus(_ENABLED);

        DriverMan driverManUpdated = this.driverManService.getDriverManRepository().findOne(selected.getId());

        driverManUpdated.update(selected);
        this.driverManService.getDriverManRepository().save(driverManUpdated);

        this.fillDriverMan();

    }

    public String cancelDriverMan() {
        this.fillDriverMan();
        return "toDriverMan";
    }

    /**
     * Llenamos lista de DriverMan
     */
    private void fillDriverMan() {
        list.clear();
        Iterable<DriverMan> c = this.driverManService.getDriverManRepository().findAll();
        Iterator<DriverMan> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Guardamos el DriverMan
     * @return
     */
    public String save() {
            Calendar cal = Calendar.getInstance();
            driverMan.setCreateDate(cal.getTime());
            driverMan.setLastUpdate(cal.getTime());
            driverMan.setStatus(_ENABLED);
            
            this.driverManService.getDriverManRepository().save(driverMan);
            driverMan = new DriverMan();

            return "";
    }
    
    /**
     * Actualizamos DriverMan
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update() throws PersonException {
        
        DriverMan driverManUpdated = this.driverManService.getDriverManRepository().findOne(this.driverMan.getId());
        
        if(driverManUpdated == null){
            throw new PersonException("Operador no existente"); 
        }
        
        driverManUpdated.update(this.driverMan);
        this.driverManService.getDriverManRepository().save(driverManUpdated);
        driverMan = new DriverMan();
        return toDriverMan();
    }

    /**
     * Controlador listar DriverMan
     *
     * @return
     */
    public String toDriverMan() {
        list.clear();
        Iterable<DriverMan> c = this.getDriverManService().getDriverManRepository().findAll();
        Iterator<DriverMan> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toDriverMan";
    }

}
