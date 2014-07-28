/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.RouteService;
import com.simphony.entities.Route;
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
import javax.annotation.PostConstruct;
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
public class RouteBean implements IConfigurable {

    private final MessageProvider mp;
    private Route route = new Route();
    private Route current = new Route();
    private Route selected = new Route();
    private List<Route> list = new ArrayList<Route>();

    @ManagedProperty(value = "#{routeService}")
    private RouteService service;
    private Calendar cal = Calendar.getInstance();

    /**
     * Creates a new instance of AssociateBean
     */
    public RouteBean() {
        mp = new MessageProvider();
    }

    @PostConstruct
    public void postInitialization() {

    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public RouteService getService() {
        return service;
    }

    public void setService(RouteService service) {
        this.service = service;
    }

    public List<Route> getList() {
        return list;
    }

    public void setList(List<Route> list) {
        this.list = list;
    }

    public Route getSelected() {
        return selected;
    }

    public void setSelected(Route selected) {
        this.selected = selected;
    }

    public Route getCurrent() {
        return current;
    }

    public void setCurrent(Route current) {
        this.current = current;
    }

    /**
     * Controlador para modificar Route
     *
     * @return
     */
    public String modifyRoute() {
        if (this.selected != null) {
            this.current.setAction(_MODIFY);
            try {
                this.route = (Route) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(AssociateBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addRoute";
        } else {
            return "toRoute";
        }
    }

    public String cancelRoute() {
        this.fillRoute();
        return "toRoute";
    }

    /**
     * Llenamos lista de Route
     */
    private void fillRoute() {
        list.clear();
        Iterable<Route> c = this.service.getRepository().findAll(sortByKeyId());
        Iterator<Route> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Actualizamos Route
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {

        Route routeUpdated = this.service.getRepository().findOne(this.route.getId());

        if (routeUpdated == null) {
            throw new PersonException("Guía no existente");
        }

//        route.setUser(user);
        routeUpdated.update(this.route);
        this.service.getRepository().save(routeUpdated);
       GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.route.getHour() + " " + mp.getValue("msj_record_update"));
        route = new Route();
        return toRoute();
    }

    /**
     * Controlador listar Associate
     *
     * @return
     */
    public String toRoute() {
        this.fillRoute();
        return "toRoute";
    }

    private Sort sortByKeyId() {
        return new Sort(Sort.Direction.ASC, "startDate", "Hour");
    }
}
