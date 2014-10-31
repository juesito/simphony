/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.GuideService;
import com.simphony.beans.SalePointService;
import com.simphony.entities.Guide;
import com.simphony.entities.SaleDetail;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._MODIFY;
import com.simphony.pojos.DailySales;
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
    private DailySales selected = new DailySales();
    private List<DailySales> list = new ArrayList<DailySales>();
    private List<SaleDetail> listDetail = new ArrayList<SaleDetail>();
    private List<DailySales> listTemporal = new ArrayList<DailySales>();

    @ManagedProperty(value = "#{guideService}")
    private GuideService guideService;
    
    @ManagedProperty(value = "#{salePointService}")
    private SalePointService salePointService;

    private Calendar cal = Calendar.getInstance();
    private String tit1 = "";
    private String tit2 = "";

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

    public SalePointService getSalePointService() {
        return salePointService;
    }

    public void setSalePointService(SalePointService salePointService) {
        this.salePointService = salePointService;
    }

    public List<DailySales> getList() {
        return list;
    }

    public void setList(List<DailySales> list) {
        this.list = list;
    }

    public List<SaleDetail> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<SaleDetail> listDetail) {
        this.listDetail = listDetail;
    }

    public DailySales getSelected() {
        return selected;
    }

    public void setSelected(DailySales selected) {
        this.selected = selected;
    }

    public Guide getCurrent() {
        return current;
    }

    public void setCurrent(Guide current) {
        this.current = current;
    }

    public String getTit1() {
        return tit1;
    }

    public void setTit1(String tit1) {
        this.tit1 = tit1;
    }

    public String getTit2() {
        return tit2;
    }

    public void setTit2(String tit2) {
        this.tit2 = tit2;
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
                this.guide = (Guide) this.selected.getGuide().clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GuideBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "modifyGuide";
        } else {
            return "toGuide";
        }
    }

    public String cancelGuide() throws CloneNotSupportedException {
        this.fillGuide();
        return "toGuide";
    }

    /**
     * Llenamos lista
     */
    private void fillGuide() throws CloneNotSupportedException {
        list.clear();
//        Iterable<Guide> c = this.guideService.getRepository().findAllLocal();
//        Iterable<Guide> c = this.guideService.getRepository().findAll(sortByDate());
//        if (c != null ){
//            Iterator<Guide> cu = c.iterator();
//            while (cu.hasNext()) {
//                list.add(cu.next());
//            }
//        }
//        List<Guide> c = this.guideService.getRepository().findAll(sortByDate());
        Long totPas = new Long(0);
        Double totIng = 0.0;
        List<DailySales> c = this.guideService.getRepository().selectAllGuide();
        listTemporal = c;
        if (c.size() > 0) {
            Date depDate = new Date();
            Long idOrigin = (long) 0;
            Long idRoute = (long) 0;
            boolean unaVez = true;
            DailySales gClone = new DailySales();
            for (DailySales gd : c) {
                if (unaVez) {
                    depDate = gd.getGuide().getDepartureDate();
                    idOrigin = gd.getGuide().getOrigin().getId();
                    idRoute = gd.getGuide().getRootRoute();
                    gClone = (DailySales) gd.clone();
                    unaVez = false;
                }
                if (!gd.getGuide().getDepartureDate().equals(depDate) || !gd.getGuide().getOrigin().getId().equals(idOrigin) || !gd.getGuide().getRootRoute().equals(idRoute)) {
                    gClone.setDetIncome(totIng);
                    gClone.setDetAssociates(totPas);
                    list.add(gClone);
                    gClone = (DailySales) gd.clone();
                    totPas = new Long(0);
                    totIng = 0.0;
                    depDate = gd.getGuide().getDepartureDate();
                    idOrigin = gd.getGuide().getOrigin().getId();
                    idRoute = gd.getGuide().getRootRoute();
                }
                if (gd.getDetAssociates() != null) {
                    totPas = totPas + gd.getDetAssociates();
                }
                if (gd.getDetIncome() != null) {
                    totIng = totIng + gd.getDetIncome();
                }
            }
            gClone.setDetIncome(totIng);
            gClone.setDetAssociates(totPas);
            list.add(gClone);
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
            for (DailySales gd : listTemporal) {
                if (gd.getGuide().getDepartureDate().equals(this.selected.getGuide().getDepartureDate())
                        && gd.getGuide().getOrigin().getId().equals(this.selected.getGuide().getOrigin().getId())
                        && gd.getGuide().getRootRoute().equals(this.selected.getGuide().getRootRoute())) {
                    Iterable<SaleDetail> c = this.guideService.getRepository().qryGuideDetail(gd.getGuide().getId());
                    Iterator<SaleDetail> cu = c.iterator();
                    while (cu.hasNext()) {
                        listDetail.add(cu.next());
                    }
                }
            }
            tit1 = this.salePointService.getSalePointRepository().tit1(this.selected.getGuide().getOrigin().getId());
            tit2 = this.salePointService.getSalePointRepository().tit2(this.selected.getGuide().getOrigin().getId());
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
        for (DailySales gd : listTemporal) {
                if(gd.getGuide().getDepartureDate().equals(depDate)&& gd.getGuide().getOrigin().getId().equals(idOrigin) && gd.getGuide().getRootRoute().equals(idRoute)){
                    Iterable<SaleDetail> c = this.guideService.getRepository().qryGuideDetail(gd.getGuide().getId());
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
        this.fillDetail(this.selected.getGuide().getDepartureDate(),this.selected.getGuide().getOrigin().getId(),this.selected.getGuide().getRootRoute());
        return "toGuideDetail";
    }

    /**
     * Actualizamos el usuario
     *
     * @param user
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String updateG(User user) throws PersonException, CloneNotSupportedException {

        // Verifica cuantos boletos tiene vendidos anets de modificar el cupo
        int pasVend = 0;
        for (DailySales gd : listTemporal) {
            if(gd.getGuide().getDepartureDate().equals(this.selected.getGuide().getDepartureDate())&& 
                gd.getGuide().getOrigin().getId().equals(this.selected.getGuide().getOrigin().getId()) && 
                gd.getGuide().getRootRoute().equals(this.selected.getGuide().getRootRoute())){
                Iterable<SaleDetail> c = this.guideService.getRepository().qryGuideDetail(gd.getGuide().getId());
                Iterator<SaleDetail> cu = c.iterator();
                while (cu.hasNext()) {
                    pasVend += 1;
                    cu.next();
                }
            }
        }
        if(pasVend != 0 && this.guide.getStatus().equals("C")){
                 GrowlBean.simplyErrorMessage("Error de cambio", "La ruta ya tiene boletos vendidos.");
        }else{
            if (pasVend <= this.guide.getQuota()) {
                Guide guideUpdated = this.guideService.getRepository().findOne(this.guide.getId());

                if (guideUpdated == null) {
                    throw new PersonException("Guía no existente");
                }
                guide.setUsrModify(user.getNick());
                guide.setLastUpdate(cal.getTime());
                guideUpdated.update(this.guide);

                this.guideService.getRepository().updateGuide(guideUpdated.getRootGuide(), guideUpdated.getBus().getId(), 
                        guideUpdated.getDriverMan1().getId(), guideUpdated.getDriverMan2().getId(), guideUpdated.getQuota(), 
                        guideUpdated.getStatus(), guideUpdated.getDepartureDate());

                GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), " " + mp.getValue("msj_record_update"));
                guide = new Guide();
            }else{
                 GrowlBean.simplyErrorMessage("Error de cupo", "El cupo no puede ser menor a los boletos vendidos.");
            }
            }
        return toGuide();
    }

        /**
     * cerramos la Guía para su venta.
     *
     * @throws com.simphony.exceptions.PersonException
     */
    public void closeGuide() throws PersonException, CloneNotSupportedException {
        this.selected.getGuide().setStatus(_GUIDE_TYPE_CLOSED);

        Guide guideUpdated = this.guideService.getRepository().findOne(selected.getGuide().getId());

        if (guideUpdated == null) {
            throw new PersonException("Guía no existente");
        }
        Guide guide = this.selected.getGuide();
        guideUpdated.update(guide);
        this.guideService.getRepository().save(guideUpdated);

        this.fillGuide();

    }

    /**
     * Controlador listar Guide
     *
     * @return
     */
    public String toGuide() throws CloneNotSupportedException {
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
