/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.WorkersService;
import com.simphony.entities.Workers;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ADD;
import static com.simphony.interfases.IConfigurable._DISABLE;
import static com.simphony.interfases.IConfigurable._ENABLED;
import static com.simphony.interfases.IConfigurable._MODIFY;
import com.simphony.models.WorkersModel;
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

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class WorkersBean implements IConfigurable {

    private final MessageProvider mp;
    private Workers workers = new Workers();
    private Workers current = new Workers();
    private Workers selected = new Workers();
    private WorkersModel model = new WorkersModel();
    private List<Workers> list = new ArrayList<Workers>();

    @ManagedProperty(value = "#{workersService}")
    private WorkersService service;
    private Calendar cal = Calendar.getInstance();

    /**
     * Creates a new instance of WorkersBean
     */
    public WorkersBean() {
        mp = new MessageProvider();
        
    }

    public Workers getWorkers() {
        return workers;
    }

    public void setWorkers(Workers workers) {
        this.workers = workers;
    }

    public WorkersService getService() {
        return service;
    }

    public void setService(WorkersService service) {
        this.service = service;
    }

    public List<Workers> getList() {
        return list;
    }

    public void setList(List<Workers> list) {
        this.list = list;
    }

    public Workers getSelected() {
        return selected;
    }

    public void setSelected(Workers selected) {
        this.selected = selected;
    }

    public Workers getCurrent() {
        return current;
    }

    public void setCurrent(Workers current) {
        this.current = current;
    }

    public String addWorkers() {
        workers = new Workers();
        this.current.setAction(_ADD);
        return "addWorkers";
    }

    /**
     * Controlador para modificar trabajador
     *
     * @return
     */
    public String modifyWorkers() {
        if (this.selected != null) {
            this.current.setAction(_MODIFY);
            try {
                this.workers = (Workers) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(WorkersBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addWorkers";
        } else {
            return "toWorkers";
        }
    }

    /**
     * deshabilitamos trabajador
     *
     */
    public void disableWorkers() {
        this.selected.setStatus(_DISABLE);

        Workers workersUpdated = this.service.getRepository().findOne(selected.getId());

        workersUpdated.update(selected);
        this.service.getRepository().save(workersUpdated);

        this.fillWorkers();

    }

    /**
     * habilitamos trabajador
     */
    public void enabledWorkers() {
        this.selected.setStatus(_ENABLED);

        Workers workersUpdated = this.service.getRepository().findOne(selected.getId());

        workersUpdated.update(selected);
        this.service.getRepository().save(workersUpdated);

        this.fillWorkers();

    }

    public String cancelWorkers() {
        this.fillWorkers();
        return "toWorkers";
    }

    /**
     * Llenamos lista de trabajadors
     */
    private void fillWorkers() {
        list.clear();
        Iterable<Workers> c = this.service.getRepository().findAll();
        Iterator<Workers> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        model = new WorkersModel(list);
    }

    /**
     * Guardamos el trabajador
     *
     * @param user
     * @return
     */
    public String save(User user) {
        workers.setUser(user);
        workers.setCreateDate(cal.getTime());
        workers.setStatus(_ENABLED);

        this.service.getRepository().save(workers);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.workers.getName() + " " + mp.getValue("msj_record_save"));
        workers = new Workers();

        return "";
    }

    /**
     * Actualizamos el usuario
     *
     * @param user
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {

        Workers workersUpdated = this.service.getRepository().findOne(this.workers.getId());

        if (workersUpdated == null) {
            throw new PersonException("Agremiado no existente");
        }

        workers.setLastUpdate(cal.getTime());
        workers.setUser(user);
        workersUpdated.update(this.workers);
        this.service.getRepository().save(workersUpdated);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.workers.getName() + " " + mp.getValue("msj_record_update"));
        workers = new Workers();
        return toWorkers();
    }
   
    /**
     * Controlador listar Workers
     *
     * @return
     */
    public String toWorkers() {
        this.fillWorkers();
        return "toWorkers";
    }

    public WorkersModel getModel() {
        return model;
    }

    public void setModel(WorkersModel model) {
        this.model = model;
    }
    
 }
