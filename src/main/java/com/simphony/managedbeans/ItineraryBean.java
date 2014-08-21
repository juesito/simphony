/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.CostService;
import com.simphony.beans.ItineraryService;
import com.simphony.entities.Cost;
import com.simphony.entities.Itinerary;
import com.simphony.entities.User;
import com.simphony.exceptions.ItineraryException;
import com.simphony.interfases.IConfigurable;
import com.simphony.selectors.MainItineraryBox;
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
public class ItineraryBean implements IConfigurable {

    private final MessageProvider mp;
    private Itinerary itinerary = new Itinerary();
    private Itinerary selected = new Itinerary();
    private List<Itinerary> list = new ArrayList<Itinerary>();

    @ManagedProperty(value = "#{itineraryService}")
    private ItineraryService itineraryService;

    @ManagedProperty(value = "#{boxMainItineraryService}")
    private MainItineraryBox boxItineraryService;

    @ManagedProperty(value = "#{costService}")
    private CostService costService;

    private Calendar cal = Calendar.getInstance();

    /**
     * Creates a new instance of ItineraryBean
     */
    public ItineraryBean() {
        mp = new MessageProvider();
    }

    @PostConstruct
    public void postInitialization() {

    }

    public void setItineraryService(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    public ItineraryService getItineraryService() {
        return itineraryService;
    }

    public MainItineraryBox getBoxItineraryService() {
        return boxItineraryService;
    }

    public void setBoxItineraryService(MainItineraryBox boxItineraryService) {
        this.boxItineraryService = boxItineraryService;
    }

    public CostService getCostService() {
        return costService;
    } 

    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Itinerary getSelected() {
        return selected;
    }

    public void setSelected(Itinerary selected) {
        this.selected = selected;
    }

    public List<Itinerary> getList() {
        return list;
    }

    public void setList(List<Itinerary> list) {
        this.list = list;
    }

    public String addItinerary() {
        itinerary = new Itinerary();
        this.itinerary.setAction(_ADD);
        return "addItinerary";
    }

    /**
     * Controlador para modificar itinerario
     *
     * @return
     */
    public String modifyItinerary() {
        if (this.selected != null) {
            try {
                this.itinerary = (Itinerary) this.selected.clone();
                this.itinerary.setAction(_MODIFY);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ItineraryBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addItinerary";
        } else {
            return "toItineraries";
        }
    }

    /**
     * deshabilitamos itinerario
     *
     */
    public void disableItinerary() {
        this.selected.setStatus(_DISABLE);

        Itinerary itineraryUpdated = this.itineraryService.getItineraryRepository().findOne(selected.getId());

        itineraryUpdated.update(selected);
        this.itineraryService.getItineraryRepository().save(itineraryUpdated);

        this.fillItineraries();

    }

    /**
     * habilitamos agremiado
     */
    public void enabledItinerary() {
        this.selected.setStatus(_ENABLED);

        Itinerary itineraryUpdated = this.itineraryService.getItineraryRepository().findOne(selected.getId());

        itineraryUpdated.update(selected);
        this.itineraryService.getItineraryRepository().save(itineraryUpdated);

        this.fillItineraries();

    }

    public String cancelItinerary() {
        this.fillItineraries();
        return "toItineraries";
    }

    /**
     * Llenamos lista de itinerarios
     */
    private void fillItineraries() {
        list.clear();
        Iterable<Itinerary> c = this.itineraryService.getItineraryRepository().findAll(sortByKeyId());
        Iterator<Itinerary> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Guardamos el itinerario
     *
     * @param user
     * @return
     */
    public void save(User user) {
        Boolean exist = true;
        if (this.itinerary.getOrigin() != null && this.itinerary.getDestiny() != null) {

        try {
            Itinerary itineratyTemp = this.itineraryService.getItineraryRepository().findByOriginAndDestiny(
                    this.itinerary.getDepartureTime(),this.itinerary.getOrigin().getId(), this.itinerary.getDestiny().getId());
            if(itineratyTemp == null){
                exist = false;
            }
        } catch (Exception ex) {
            System.out.println("Error");
            exist = false;

        }
            if (!exist) {
                itinerary.setUser(user);
                itinerary.setCreateDate(cal.getTime());
                itinerary.setStatus(_ENABLED);

                Cost c = this.costService.getCostRepository().findByOriDes(this.itinerary.getOrigin().getId(), this.itinerary.getDestiny().getId());

                if (c != null) {
                    Calendar calItinerary = Calendar.getInstance();
                    calItinerary.setTime(itinerary.getDepartureTime());

                    Calendar calCost = Calendar.getInstance();
                    calCost.setTime(c.getRouteTime());
                    calItinerary.add(Calendar.HOUR_OF_DAY, calCost.get(Calendar.HOUR_OF_DAY));
                    this.itinerary.setCheckTime(calItinerary.getTime());
                    if (itinerary.getRoute() == null){
                        this.itinerary.setTypeOfRoute("L");
                    }else this.itinerary.setTypeOfRoute("P");

                    this.itineraryService.getItineraryRepository().save(itinerary);
                    if (this.itinerary.getTypeOfRoute().equals("L")){
                       itinerary.setRoute(itinerary);
                       this.itineraryService.getItineraryRepository().save(itinerary);
                     }
                    GrowlBean.simplyInfoMessage(mp.getValue("msj_save"), mp.getValue("msj_record_save") + this.itinerary.getOrigin().getDescription());
                    itinerary = new Itinerary();
                    itinerary.setAction(_ADD);
                    boxItineraryService.fillBox();
                } else {
                    GrowlBean.simplyErrorMessage(mp.getValue("error_cost_title"), mp.getValue("error_cost"));
                } //Existe tarifa?
            } else {
                GrowlBean.simplyErrorMessage(mp.getValue("error_keyId"), mp.getValue("error_keyId_Detail"));
            }
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("msj_save"), mp.getValue("msj_record_save") + this.itinerary.getOrigin().getDescription());
        }
    }

    /**
     * Actualizamos el usuario
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws ItineraryException {

        Cost c = this.costService.getCostRepository().findByOriDes(this.itinerary.getOrigin().getId(), this.itinerary.getDestiny().getId());

        if (c != null) {
            Calendar calItinerary = Calendar.getInstance();
            calItinerary.setTime(this.itinerary.getDepartureTime());

            Calendar calCost = Calendar.getInstance();
            calCost.setTime(c.getRouteTime());
            calItinerary.add(Calendar.HOUR_OF_DAY, calCost.get(Calendar.HOUR_OF_DAY));
            Itinerary itineraryUpdated = this.itineraryService.getItineraryRepository().findOne(this.itinerary.getId());

            if (itineraryUpdated == null) {
                throw new ItineraryException(mp.getValue("cat_itinerary") + " " + mp.getValue("not_founded"));
            }

            this.itinerary.setUser(user);
            this.itinerary.setCheckTime(calItinerary.getTime());
            if (itinerary.getRoute() == null){
                this.itinerary.setTypeOfRoute("L");
            }else this.itinerary.setTypeOfRoute("P");
            itineraryUpdated.update(this.itinerary);
            this.itineraryService.getItineraryRepository().save(itineraryUpdated);

            GrowlBean.simplyInfoMessage(mp.getValue("msj_modified"), mp.getValue("msj_record_modified") + this.itinerary.getOrigin().getDescription());
      
            itinerary = new Itinerary();
        } else {        //Existe tarifa?
            GrowlBean.simplyErrorMessage(mp.getValue("error_cost_title"), mp.getValue("error_cost"));
        } 
        return toItineraries();
    }

    /**
     * Controlador listar Itinerario
     *
     * @return
     */
    public String toItineraries() {
        this.fillItineraries();
        return "toItineraries";
    }

    /**
     * Controlador listar Itinerario
     *
     * @return
     */
    public String toItineraryDetail() {
        if (this.selected != null) {
            try {
                this.itinerary = (Itinerary) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ItineraryBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "toItineraryDetail";
        } else {
            return "toItineraries";
        }
    }

    private Sort sortByKeyId() {
        return new Sort(Sort.Direction.ASC, "route.id", "sequence");
    }

}
