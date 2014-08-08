/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.GuideService;
import com.simphony.entities.User;
import com.simphony.entities.Guide;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
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
public class GuideBean implements IConfigurable {

    private final MessageProvider mp;
    private Guide guide = new Guide();
    private Guide current = new Guide();
    private Guide selected = new Guide();
    private List<Guide> list = new ArrayList<Guide>();
    private List<Guide> listDetail = new ArrayList<Guide>();

    @ManagedProperty(value = "#{guideService}")
    private GuideService guideService;
    private Calendar cal = Calendar.getInstance();

    /**
     * Creates a new instance of GuideBean
     */
    public GuideBean() {
        mp = new MessageProvider();
    }

    @PostConstruct
    public void postInitialization() {

    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public GuideService getGuideService() {
        return guideService;
    }

    public void setGuideService(GuideService guideService) {
        this.guideService = guideService;
    }

    public List<Guide> getList() {
        return list;
    }

    public void setList(List<Guide> list) {
        this.list = list;
    }

    public Guide getSelected() {
        return selected;
    }

    public void setSelected(Guide selected) {
        this.selected = selected;
    }

    public Guide getCurrent() {
        return current;
    }

    public void setCurrent(Guide current) {
        this.current = current;
    }

    /**
     * Controlador para modificar Guide
     *
     * @return
     */
    public String modifyGuide() {
        if (this.selected != null ) {
            this.current.setAction(_MODIFY);
            try {
                this.guide = (Guide) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GuideBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addGuide";
        }else
            return "toGuide";
    }

    public String cancelGuide() {
        this.fillGuide();
        return "toGuide";
    }

    /**
     * Llenamos lista de agremiados
     */
    private void fillGuide() {
        list.clear();
        Iterable<Guide> c = this.guideService.getRepository().findAll();
        Iterator<Guide> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    
    /**
     * Actualizamos el usuario
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {
        
        Guide guideUpdated = this.guideService.getRepository().findOne(this.guide.getId());
        
        if(guideUpdated == null){
            throw new PersonException("Estación de trabajo no existente"); 
        }
        guide.setVendor(user);
        guide.setLastUpdate(cal.getTime());
        guideUpdated.update(this.guide);
        this.guideService.getRepository().save(guideUpdated);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.guide.getId()+ " "+mp.getValue("msj_record_update"));
        guide = new Guide();
        return toGuide();
    }
 
    /**
     * Controlador listar Guide
     *
     * @return
     */
    public String toGuide() {
        list.clear();
        Iterable<Guide> c = this.getGuideService().getRepository().findAll();
        Iterator<Guide> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toGuide";
    }

}
