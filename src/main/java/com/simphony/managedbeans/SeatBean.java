/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.SeatService;
import com.simphony.entities.Seat;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import com.simphony.tools.MessageProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Soporte IT
 */
@ManagedBean
@SessionScoped
public class SeatBean implements IConfigurable {

    private final MessageProvider mp;
    private Seat seat = new Seat();
    private Seat selected = new Seat();
    private List<Seat> list = new ArrayList();

    @ManagedProperty(value = "#{seatService}")
    private SeatService service;

    /**
     * Creates a new instance of SeatBean
     */
    public SeatBean() {
        mp = new MessageProvider();
    }

    public String addSeat() {
        seat = new Seat();
        this.seat.setAction(_ADD);
        return "addSeat";
    }

    public String cancelSeat() {
        return toSeats();
    }

    /**
     * Llenamos lista de agremiados
     */
    private void fillSeats() {
        list.clear();
        Iterable<Seat> c = this.service.getRepository().findAll();
        Iterator<Seat> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * deshabilitamos agremiado
     *
     */
    public void disableSeat() {
        this.selected.setStatus(_DISABLE);

        Seat seatUpdated = this.service.getRepository().findOne(selected.getId());

        seatUpdated.update(selected);
        this.service.getRepository().save(seatUpdated);

        this.fillSeats();

    }

    /**
     * habilitamos agremiado
     */
    public void enabledSeat() {
        this.selected.setStatus(_ENABLED);

        Seat seatUpdated = this.service.getRepository().findOne(selected.getId());

        seatUpdated.update(selected);
        this.service.getRepository().save(seatUpdated);

        this.fillSeats();

    }
    
    public String modifySeat() {
        if (this.selected != null) {
            this.seat.setAction(_MODIFY);
            try {
                this.seat = (Seat) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(SeatBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addSeat";
        } else {
            return "toSeats";
        }
    }
    
    /**
     * Guardamos el asiento
     * @param user
     */
    public void save(User user) {
        Boolean exist = true;

        //Se espera validacion
        if (exist) {
            seat.setUser(user);
            seat.setCreateDate(new Date());
            seat.setStatus(_ENABLED);

            this.service.getRepository().save(seat);
            GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), seat.getSeat() + " " + mp.getValue("msj_record_save"));
            seat = new Seat();
            seat.setAction(_ADD);

        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), this.seat.getSeat() + " " + mp.getValue("error_keyId_Detail"));
        }

    }

    /**
     * Actualizamos el asiento
     * @param user
     * @throws PersonException
     */
    public void update(User user) throws PersonException {

        Seat seatUpdated = this.service.getRepository().findOne(this.seat.getId());

        if (seatUpdated == null) {
            throw new PersonException("Asiento no existente");
        }

        seat.setUser(user);
        seatUpdated.update(this.seat);
        this.service.getRepository().save(seatUpdated);

        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.seat.getSeat()+ " " + mp.getValue("msj_record_update"));
        seat = new Seat();
        seat.setAction(_MODIFY);
    }

    public String toSeats() {
        this.fillSeats();
        return "toSeats";
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Seat getSelected() {
        return selected;
    }

    public void setSelected(Seat selected) {
        this.selected = selected;
    }

    public List<Seat> getList() {
        return list;
    }

    public void setList(List<Seat> list) {
        this.list = list;
    }

    public SeatService getService() {
        return service;
    }

    public void setService(SeatService service) {
        this.service = service;
    }

}
