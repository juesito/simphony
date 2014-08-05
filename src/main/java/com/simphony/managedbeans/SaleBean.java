/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.AssociateService;
import com.simphony.beans.CostService;
import com.simphony.beans.GuideService;
import com.simphony.beans.SaleService;
import com.simphony.beans.SeatService;
import com.simphony.entities.Associate;
import com.simphony.entities.Cost;
import com.simphony.entities.Customer;
import com.simphony.entities.Guide;
import com.simphony.entities.Sale;
import com.simphony.entities.SaleDetail;
import com.simphony.entities.Seat;
import com.simphony.interfases.IConfigurable;
import com.simphony.pojos.ItineraryCost;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class SaleBean implements IConfigurable {

    private Sale sale = new Sale();
    private Cost cost = new Cost();
    private Seat selectedSeat = new Seat();
    
    private ItineraryCost selected = new ItineraryCost();

    private List<Sale> list = new ArrayList();
    private List<Object[]> objects = new ArrayList<Object[]>();
    private List<Seat> seat = new ArrayList();
    private List<ItineraryCost> itineraryCost = new ArrayList<ItineraryCost>();
    private List<Seat> selectedSeats = new ArrayList<Seat>();
    
    private SaleDetail unSelectedDetail = new SaleDetail();
    private List<SaleDetail> saleDetail = new ArrayList<SaleDetail>();

    @ManagedProperty(value = "#{costService}")
    private CostService costService;

    @ManagedProperty(value = "#{saleService}")
    private SaleService saleService;

    @ManagedProperty(value = "#{guideService}")
    private GuideService guideService;

    @ManagedProperty(value = "#{associateService}")
    private AssociateService associateService;

    @ManagedProperty(value = "#{seatService}")
    private SeatService seatService;

    /**
     * Creates a new instance of SaleBean
     */
    public SaleBean() {
    }

    @PostConstruct
    void init() {
        seat = seatService.getRepository().findAllAvailable();
    }

    public CostService getCostService() {
        return costService;
    }

    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public List<Sale> getList() {
        return list;
    }

    public void setList(List<Sale> list) {
        this.list = list;
    }

    public void findItinearies() {
        itineraryCost.clear();
        List<ItineraryCost> itineraryCostTemp = new ArrayList<ItineraryCost>();
        itineraryCost = saleService.getSaleRepository().findItineraryCost(this.sale.getOrigin().getId(), this.sale.getDestiny().getId());
        itineraryCostTemp = saleService.getSaleRepository().findItineraryDetailCost(this.sale.getOrigin().getId(), this.sale.getDestiny().getId());
        sale.setExistRoutes(itineraryCost.size() > 0);
        sale.setAvailability(false);

    }

    public void findAssociate() {
        Associate temp = associateService.getRepository().findByKey(this.sale.getAssociate().getKeyId());

        if (temp != null) {
            this.sale.setAssociate(temp);
        } else {
            this.sale.getAssociate().setKeyId(_BLANK);
            GrowlBean.simplyErrorMessage("No se encontro", "Asociado no encontrado");
        }
    }

    /**
     * buscando dispobinibilidad
     */
    public void findAvailability() {

        if (selected != null) {

            Guide guide = guideService.getRepository().findByItineraryAndDate(selected.getItinerary().getId(), sale.getTripDate());
            if (guide != null) {
                // Validación de asientos
            } else {
                this.sale.setAvailability(true);                
                guide = new Guide();
                guide.setCheckDate(sale.getTripDate());
                guide.setItinerary(this.selected.getItinerary());
                guide.setStatus(_GUIDE_TYPE_CLOSED);
                guide.setGuideReference("Sin Referencia");
                guideService.getRepository().save(guide);
            }
            System.out.println("Availability-->" + sale.isAvailability());
        } else {
            GrowlBean.simplyWarmMessage("No selecciono registro", "Es necesario seleccionar");
        }
        this.sale.setExistRoutes(false);
    }

    public void save() {

    }
    
    public void addSeat(){
        SaleDetail saleDetailTmp = new SaleDetail(this.selected.getCost().getCost(), selectedSeat,  new Customer());
        saleDetail.add(saleDetailTmp);
        System.out.println("Asiento --> " + selectedSeat);
    }
    
    public void removeSeat(){
        saleDetail.remove(unSelectedDetail);
        System.out.println("Total detalle:" + saleDetail.size());
    }

    public List<Seat> getSeat() {
        return seat;
    }

    public void setSeat(List<Seat> seat) {
        this.seat = seat;
    }

    public SeatService getSeatService() {
        return seatService;
    }

    public void setSeatService(SeatService seatService) {
        this.seatService = seatService;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public List<ItineraryCost> getItineraryCost() {
        return itineraryCost;
    }

    public void setItineraryCost(List<ItineraryCost> itineraryCost) {
        this.itineraryCost = itineraryCost;
    }

    public SaleService getSaleService() {
        return saleService;
    }

    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    public ItineraryCost getSelected() {
        return selected;
    }

    public void setSelected(ItineraryCost selected) {
        this.selected = selected;
    }

    public GuideService getGuideService() {
        return guideService;
    }

    public void setGuideService(GuideService guideService) {
        this.guideService = guideService;
    }

    public AssociateService getAssociateService() {
        return associateService;
    }

    public void setAssociateService(AssociateService associateService) {
        this.associateService = associateService;
    }

    public Seat getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(Seat selectedSeat) {
        this.selectedSeat = selectedSeat;
    }

    public List<Seat> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<Seat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }
   
    public List<SaleDetail> getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(List<SaleDetail> saleDetail) {
        this.saleDetail = saleDetail;
    }

    public SaleDetail getUnSelectedDetail() {
        return unSelectedDetail;
    }

    public void setUnSelectedDetail(SaleDetail unSelectedDetail) {
        this.unSelectedDetail = unSelectedDetail;
    }
    

}
