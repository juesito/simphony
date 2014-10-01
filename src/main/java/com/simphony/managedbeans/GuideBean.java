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
import java.util.Date;
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
    private List<Guide> listTemporal = new ArrayList<Guide>();

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
//        Iterable<Guide> c = this.guideService.getRepository().findAll(sortByDate());
//        if (c != null ){
//            Iterator<Guide> cu = c.iterator();
//            while (cu.hasNext()) {
//                list.add(cu.next());
//            }
//        }
        List<Guide> c = this.guideService.getRepository().findAll(sortByDate());
        listTemporal = c;
        if (c.size() > 0) {
            Date depDate = new Date();
            Long idOrigin = (long) 0 ;
            Long idRoute  = (long) 0;
            for (Guide gd : c) {
                if(gd.getDepartureDate().equals(depDate)&& gd.getOrigin().getId().equals(idOrigin) && gd.getRootRoute().equals(idRoute)){
                }else{
                    depDate = gd.getDepartureDate();
                    idOrigin = gd.getOrigin().getId();
                    idRoute = gd.getRootRoute();
                    list.add(gd);
                }
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
//            listDetail = guideService.getRepository().qryGuideDetail(this.selected.getId());
            for (Guide gd : listTemporal) {
                if (gd.getDepartureDate().equals(this.selected.getDepartureDate())
                        && gd.getOrigin().getId().equals(this.selected.getOrigin().getId())
                        && gd.getRootRoute().equals(this.selected.getRootRoute())) {
                    Iterable<SaleDetail> c = this.guideService.getRepository().qryGuideDetail(gd.getId());
                    Iterator<SaleDetail> cu = c.iterator();
                    while (cu.hasNext()) {
                        listDetail.add(cu.next());
                    }
                }
            }
            this.ordenaAsi();
            return "toGuideDetail";
        } else {
            return "toGuide";
        }
    }

    /**
     * Llenamos lista
     */
    private void fillDetail(Date depDate, Long idOrigin, Long idRoute) {
        listDetail.clear();
        for (Guide gd : listTemporal) {
                if(gd.getDepartureDate().equals(depDate)&& gd.getOrigin().getId().equals(idOrigin) && gd.getRootRoute().equals(idRoute)){
                    Iterable<SaleDetail> c = this.guideService.getRepository().qryGuideDetail(gd.getId());
                    Iterator<SaleDetail> cu = c.iterator();
                    while (cu.hasNext()) {
                        listDetail.add(cu.next());
                    }
                }
            }
        
   
        
    }

    /**
     * Controlador listar Detail
     *
     * @return
     */
    public String toGuideDetail() {
        this.fillDetail(this.selected.getDepartureDate(),this.selected.getOrigin().getId(),this.selected.getRootRoute());
        return "toGuideDetail";
    }

    /**
     * Actualizamos el usuario
     *
     * @param user
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {

        // Verifica cuantos boletos tiene vendidos anets de modificar el cupo
        int pasVend = 0;
        for (Guide gd : listTemporal) {
            if(gd.getDepartureDate().equals(this.selected.getDepartureDate())&& 
                gd.getOrigin().getId().equals(this.selected.getOrigin().getId()) && 
                gd.getRootRoute().equals(this.selected.getRootRoute())){
                Iterable<SaleDetail> c = this.guideService.getRepository().qryGuideDetail(gd.getId());
                Iterator<SaleDetail> cu = c.iterator();
                while (cu.hasNext()) {
                    pasVend += 1;
                    cu.next();
                }
            }
        }
        if (pasVend <= this.guide.getQuota()) {
            Guide guideUpdated = this.guideService.getRepository().findOne(this.guide.getId());

            if (guideUpdated == null) {
                throw new PersonException("Guía no existente");
            }
            guide.setUsrModify(user.getNick());
            guide.setLastUpdate(cal.getTime());
            guideUpdated.update(this.guide);
            //this.guideService.getRepository().save(guideUpdated);
            this.guideService.getRepository().updateGuide(guideUpdated.getQuota());

            GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), " " + mp.getValue("msj_record_update"));
            guide = new Guide();
        }else
             GrowlBean.simplyErrorMessage("Error de cupo", "El cupo no puede ser menor a los boletos vendidos.");

        return toGuide();
    }

        /**
     * cerramos la Guía para su venta.
     *
     * @throws com.simphony.exceptions.PersonException
     */
    public void closeGuide() throws PersonException {
        this.selected.setStatus(_GUIDE_TYPE_CLOSED);

        Guide guideUpdated = this.guideService.getRepository().findOne(selected.getId());

        if (guideUpdated == null) {
            throw new PersonException("Guía no existente");
        }

        guideUpdated.update(selected);
        this.guideService.getRepository().save(guideUpdated);

        this.fillGuide();

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
    return new Sort(Sort.Direction.ASC, "departureDate","rootRoute");
    }

    public void ordenaAsi() {
        int i, j, cont;
        cont = listDetail.size();

        SaleDetail temp;
        SaleDetail opc1;
        SaleDetail opc2;
        for (i = (cont - 1); i >= 0; i--) {
            for (j = 1; j <= i; j++) {
                opc1 = (SaleDetail) listDetail.get(j - 1);
                opc2 = (SaleDetail) listDetail.get(j);
                if ((opc2.getSeat().getId().compareTo(opc1.getSeat().getId())) < 0) {
                    temp = opc1;
                    listDetail.set(j - 1, opc2);
                    listDetail.set(j, temp);
                }
            }
        }
    }
}
