/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.GuideService;
import com.simphony.entities.Guide;
import com.simphony.entities.SaleDetail;
import com.simphony.entities.User;
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
import org.springframework.data.domain.Sort;

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
    private List<SaleDetail> listDetail = new ArrayList<SaleDetail>();

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

    public List<SaleDetail> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<SaleDetail> listDetail) {
        this.listDetail = listDetail;
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
        if (this.selected != null) {
            this.current.setAction(_MODIFY);
            try {
                this.guide = (Guide) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GuideBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "modifyGuide";
        } else {
            return "toGuide";
        }
    }

    public String cancelGuide() {
        this.fillGuide();
        return "toGuide";
    }

    /**
     * Llenamos lista
     */
    private void fillGuide() {
        list.clear();
//        Iterable<Guide> c = this.guideService.getRepository().findAllLocal();
        Iterable<Guide> c = this.guideService.getRepository().findAll(sortByDate());
        if (c != null ){
            Iterator<Guide> cu = c.iterator();
            while (cu.hasNext()) {
                list.add(cu.next());
            }
        }
    }

    /**
     * Controlador para el Deatlle
     *
     * @return
     */
    public String guideDetail() {
        if (this.selected != null) {
            listDetail.clear();
//            listDetail = guideService.getRepository().qryGuideDetailLocal(this.selected.getDepartureDate(), this.selected.getOrigin().getId());
            listDetail = guideService.getRepository().qryGuideDetail(this.selected.getId());
            return "toGuideDetail";
        } else {
            return "toGuide";
        }
    }

    /**
     * Llenamos lista
     */
    private void fillDetail(Long guideId) {
        listDetail.clear();
        Iterable<SaleDetail> c = this.guideService.getRepository().qryGuideDetail(guideId);
        Iterator<SaleDetail> cu = c.iterator();
        while (cu.hasNext()) {
            listDetail.add(cu.next());
        }
    }

    /**
     * Controlador listar Detail
     *
     * @return
     */
    public String toGuideDetail() {
        this.fillDetail(this.selected.getId());
        return "toGuideDetail";
    }

    /**
     * Actualizamos el usuario
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {

        Guide guideUpdated = this.guideService.getRepository().findOne(this.guide.getId());

        if (guideUpdated == null) {
            throw new PersonException("Guía no existente");
        }
        guide.setUsrModify(user.getNick());
        guide.setLastUpdate(cal.getTime());
        guideUpdated.update(this.guide);
        this.guideService.getRepository().save(guideUpdated);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), " " + mp.getValue("msj_record_update"));
        guide = new Guide();
        return toGuide();
    }

    /**
     * Controlador listar Guide
     *
     * @return
     */
    public String toGuide() {
        this.fillGuide();
        return "toGuide";
    }

    private Sort sortByDate() {
    return new Sort(Sort.Direction.ASC, "departureDate");
    }

}
