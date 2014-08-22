/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.AssociateService;
import com.simphony.beans.CostService;
import com.simphony.beans.GuideService;
import com.simphony.beans.ItineraryService;
import com.simphony.beans.SaleService;
import com.simphony.beans.SeatService;
import com.simphony.entities.Associate;
import com.simphony.entities.Cost;
import com.simphony.entities.Customer;
import com.simphony.entities.Guide;
import com.simphony.entities.GuideDetail;
import com.simphony.entities.Itinerary;
import com.simphony.entities.PayType;
import com.simphony.entities.Payment;
import com.simphony.entities.ReservedSeats;
import com.simphony.entities.Sale;
import com.simphony.entities.SaleDetail;
import com.simphony.entities.Seat;
import com.simphony.entities.Vendor;
import com.simphony.interfases.IConfigurable;
import com.simphony.models.ItineraryCostModel;
import com.simphony.pojos.ItineraryCost;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private Guide guide = new Guide();
    private Guide guideRoot = new Guide();
    private Associate associate = new Associate();
    private Sale sale = new Sale();
    private Cost cost = new Cost();
    private Seat selectedSeat = new Seat();
    private SaleDetail saleDetailSelected = new SaleDetail();

    private List<Seat> seat = new ArrayList();
    private List<Sale> list = new ArrayList();
    private ItineraryCost selected = new ItineraryCost();
    private ItineraryCostModel model = new ItineraryCostModel();
    private List<Seat> selectedSeats = new ArrayList<Seat>();
    private List<ItineraryCost> itineraryCost = new ArrayList<ItineraryCost>();

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

    @ManagedProperty(value = "#{itineraryService}")
    private ItineraryService itineraryService;

    /**
     * Creates a new instance of SaleBean
     */
    public SaleBean() {
    }

    @PostConstruct
    void init() {

    }

    /**
     * Buscamos itinerarios
     */
    public void findItinearies() throws ParseException {
        Calendar now = Calendar.getInstance();

        if (_SDF.parse(_SDF.format(this.sale.getTripDate())).compareTo(_SDF.parse(_SDF.format(now.getTime()))) < 0) {
            GrowlBean.simplyErrorMessage("Error de fechas", "Rutas fuera de calendario");
            return;
        }

        if (this.sale.getPassengers() + this.sale.getRetirees() > 0) {
            itineraryCost.clear();

            itineraryCost = saleService.getSaleRepository().findItineraryCost(this.sale.getOrigin().getId(), this.sale.getDestiny().getId());

            List<ItineraryCost> itineraryCostTemp = saleService.getSaleRepository().findItineraryDetailCost(this.sale.getOrigin().getId(), this.sale.getDestiny().getId());

            if (itineraryCostTemp.size() > 0) {

                for (ItineraryCost it : itineraryCostTemp) {
                    it.getItinerary().setTypeOfRoute(it.getAlternateItinerary().getTypeOfRoute());
                    it.getItinerary().setRoute(it.getAlternateItinerary().getRoute());
                    itineraryCost.add(it);
                }
            }

            if (itineraryCost.size() > 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(this.sale.getTripDate());

                for (ItineraryCost itineraryCost1 : itineraryCost) {

                    Calendar calTimeTmp = Calendar.getInstance();
                    calTimeTmp.setTime(itineraryCost1.getItinerary().getDepartureTime());
                    itineraryCost1.setDepartureTime(new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
                            calTimeTmp.get(Calendar.HOUR_OF_DAY), calTimeTmp.get(Calendar.MINUTE), calTimeTmp.get(Calendar.SECOND)));
                }

                model = new ItineraryCostModel(itineraryCost);

                sale.setExistRoutes(itineraryCost.size() > 0);
                sale.setAvailability(false);
            } else {
                GrowlBean.simplyErrorMessage("No se encontro", "Rutas no encontradas");
            }
        } else {
            GrowlBean.simplyErrorMessage("Sin pasajeros", "Es importante asignar los pasajeros");
        }

    }

    /**
     * Buscamos al agremiado
     */
    public void findAssociate(SaleDetail innerSaleDetail) {
        Associate temp = associateService.getRepository().findByKey(innerSaleDetail.getAssociateKey());

        if (temp != null) {
            try {
                associate = (Associate) temp.clone();
                innerSaleDetail.setAssociate(associate);
                innerSaleDetail.setCustomerName(associate.getName());
                Integer index = 0;
                if (this.saleDetail.contains(innerSaleDetail)) {
                    index = this.saleDetail.indexOf(innerSaleDetail);
                    this.saleDetail.set(index, innerSaleDetail);

                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(SaleBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.associate.setKeyId(_BLANK);
            GrowlBean.simplyErrorMessage("No se encontro", "Asociado no encontrado");
        }
    }

    /**
     * buscando disponibilidad
     *
     * @param vendor
     * @throws java.lang.CloneNotSupportedException
     */
    public void findAvailability(Vendor vendor) throws CloneNotSupportedException {

        if (selected != null) {

            //Asignamos la fecha de la venta
            this.sale.setTripDate(this.selected.getDepartureTime());

            Long ori = selected.getCost().getOrigin().getId();
            Long des = selected.getCost().getDestiny().getId();
            Long route = selected.getItinerary().getRoute().getId();

            //Obtenemos el itinerario padre
            Itinerary rootItinerary
                    = itineraryService.getItineraryRepository().findOne(route);

            Calendar cal = Calendar.getInstance();
            Calendar calTime = (Calendar) cal.clone();
            Calendar calTimeTmp = Calendar.getInstance();

            calTimeTmp.setTime(sale.getTripDate());
            calTime.setTime(rootItinerary.getDepartureTime());
            calTime.set(calTimeTmp.get(Calendar.YEAR), calTimeTmp.get(Calendar.MONTH), calTimeTmp.get(Calendar.DAY_OF_MONTH));
            rootItinerary.setDepartureTime(calTime.getTime());

            /*
             -- Validacion del cambio de dia --
            
             cal = Calendar.getInstance();
             cal.setTime(this.sale.getTripDate());

             if (selected.isNormalMode()) {
             calTime = (Calendar) cal.clone();
             calTimeTmp = Calendar.getInstance();

             calTimeTmp.setTime(rootItinerary.getDepartureTime());
             calTime.add(Calendar.HOUR, calTimeTmp.get(Calendar.HOUR));
             calTime.add(Calendar.MINUTE, calTimeTmp.get(Calendar.MINUTE));
             calTime.add(Calendar.SECOND, calTimeTmp.get(Calendar.SECOND));
             rootItinerary.setDepartureTime(calTime.getTime());
             } else {
             calTime = (Calendar) cal.clone();
             calTimeTmp = Calendar.getInstance();

             calTimeTmp.setTime(rootItinerary.getDepartureTime());
             calTime.add(Calendar.SECOND, -calTimeTmp.get(Calendar.SECOND));
             calTime.add(Calendar.MINUTE, -calTimeTmp.get(Calendar.MINUTE));
             calTime.add(Calendar.HOUR, -calTimeTmp.get(Calendar.HOUR));
             rootItinerary.setDepartureTime(calTime.getTime());
             }
            
             */
            //Validamos la guia padre
            guideRoot = guideService.getRepository().findRootGuide(route, rootItinerary.getDepartureTime());
            if (guideRoot == null) {
                guideRoot = new Guide();
                guideRoot.setVendor(vendor);
                guideRoot.setGuideType(_LOCAL);
                guideRoot.setCreateDate(new Date());
                guideRoot.setStatus(_GUIDE_TYPE_OPEN);
                guideRoot.setGuideReference("Sin Referencia");
                guideRoot.setDepartureDate(rootItinerary.getDepartureTime());
                guideRoot = this.createRootGuide(guideRoot, rootItinerary);
            }

            guide = guideService.getRepository().findByItineraryAndDate(ori, des, sale.getTripDate(), route);
            Integer maxLimitCarrie = 0;

            if (guide != null) {
                // Validación de asientos
                guide.setNewGuide(false);
                maxLimitCarrie = guide.getQuota();

            } else {
                //seat = seatService.getRepository().findAllAvailable();
                guide = new Guide(true);
            }

            seat.clear();

            //Obtenemos el limite de pasajeros en esa guia
            if (maxLimitCarrie > 0) {
                seat = seatService.getRepository().findAllAvailable();
            } else {
                seat = seatService.getRepository().findAllAvailable();
            }

            Seat occupiedPattern = seatService.getRepository().findOccupiedSeatPattern();
            List<ReservedSeats> reservedSeats = saleService.getReservedSeatsRepository().findAllReserved(guideRoot.getRootGuide(), guideRoot.getRootRoute());

            for (ReservedSeats reserved : reservedSeats) {

                if (seat.contains(reserved.getSeat())) {
                    int index = seat.indexOf(reserved.getSeat());
                    //Parten del mismo origen   
                    if (reserved.getFinalSequence() == 0) {
                        seat.set(index, occupiedPattern);
                    } else if (selected.getItinerary().getSequence() == reserved.getInitialSequence()) {
                        seat.set(index, occupiedPattern);
                    } else if (selected.getAlternateItinerary().getSequence() > reserved.getInitialSequence()) {
                        seat.set(index, occupiedPattern);
                    }

                }
            }

            this.sale.setAvailability(true);
            this.sale.setExistRoutes(false);
        } else {
            GrowlBean.simplyWarmMessage("No selecciono registro", "Es necesario seleccionar");
        }

    }

    /**
     * *
     *
     * @param vendor
     * @param payTypeList
     * @throws java.lang.CloneNotSupportedException
     */
    public void save(Vendor vendor, List<PayType> payTypeList) throws CloneNotSupportedException {

        sale.setVendor(vendor);
        sale.setCreateDate(new Date());
        sale.setOrigin(this.selected.getCost().getOrigin());
        sale.setDestiny(this.selected.getCost().getDestiny());

        //Guardamos la venta
        sale = saleService.getSaleRepository().save(sale);

        // Guardamos la guia
        if (guide.isNewGuide()) {

            guide.setVendor(vendor);
            guide.setCreateDate(new Date());
            guide.setStatus(_GUIDE_TYPE_OPEN);
            guide.setGuideType(_FOREING);
            guide.setGuideReference("Sin Referencia");
            guide.setDepartureDate(sale.getTripDate());

            if (this.selected.isNormalMode()) {

                guide.setRootGuide(0L);
                guide.setRootRoute(this.selected.getItinerary().getId());
                guide.setOrigin(this.selected.getCost().getOrigin());
                guide.setDestiny(this.selected.getCost().getDestiny());

                guideService.getRepository().save(guide);
                guide.setRootGuide(guide.getId());
                guide.update(guide);
                guide = guideService.getRepository().save(guide);

            } else {

                guide.setOrigin(this.selected.getCost().getOrigin());
                guide.setDestiny(this.selected.getCost().getDestiny());
                guide.setRootRoute(guideRoot.getRootRoute());
                guide.setRootGuide(guideRoot.getId());
                guideService.getRepository().save(guide);

            }
        }

        /**
         * Guardamos el detalle de la guia
         */
        if (guide != null) {

            GuideDetail guideDetail = new GuideDetail();
            guideDetail.setGuide(guide);
            guideDetail.setSale(sale);
            guideService.getDetailRepository().save(guideDetail);

        }

        //Guardamos el detalle de la venta
        for (SaleDetail dtSale : this.saleDetail) {

            //Guardamos el detalle de venta
            dtSale.setSale(sale);
            dtSale.setStatus("V");

            if (dtSale.getAssociateKey().isEmpty()) {
                dtSale.setAssociate(associateService.getRepository().getOne(1L));
                dtSale.setType(_SALE_TYPE_PUBLIC);
            } else {
                dtSale.setType(_SALE_TYPE_ASSOCIATE);
            }

            saleService.getDetailRepository().save(dtSale);

            //Guardamos los asientos ocupados            
            ReservedSeats reservedSeat = new ReservedSeats();
            reservedSeat.setGuideId(guide.getRootGuide());
            reservedSeat.setRouteId(guide.getRootRoute());
            reservedSeat.setRouteType(this.selected.getItinerary().getTypeOfRoute());

            if (reservedSeat.getRouteType().equals(_LOCAL)) {
                reservedSeat.setInitialSequence(0);
                reservedSeat.setFinalSequence(0);
            } else {
                reservedSeat.setInitialSequence(this.selected.getItinerary().getSequence());
                reservedSeat.setFinalSequence(this.selected.getAlternateItinerary().getSequence());
            }
            reservedSeat.setSeat(dtSale.getSeat());

            saleService.getReservedSeatsRepository().save(reservedSeat);
        }

        for (PayType payType : payTypeList) {
            if (payType.getAmount() > 0.0) {
                Payment payment = new Payment(payType, sale);
                saleService.getPaymentRepository().save(payment);
            }

        }

        GrowlBean.simplyWarmMessage("Se ha guardado la venta", "Venta guardada con exito!");
        this.clearSale();

    }

    /**
     * Validamos lo necesario para la venta
     *
     * @param vendor
     * @param payTypeList
     * @return
     * @throws CloneNotSupportedException
     */
    public String validateSale(Vendor vendor, List<PayType> payTypeList) throws CloneNotSupportedException {
        Double amountPayed = 0.0;
        String msgNav = "toSale";

        for (PayType payType : payTypeList) {
            amountPayed = amountPayed + payType.getAmount();
        }

        if (this.sale.getRetirees() > 0) {
            Integer retireeSelected = 0;
            for (SaleDetail sl : this.saleDetail) {
                if (sl.getBolType().equals(_RETIREE)) {
                    retireeSelected++;
                }
            }
            if (this.sale.getRetirees() == retireeSelected) {
                GrowlBean.simplyWarmMessage("Sin seleccion de jubilados", "No se han seleccionado los jubilados solicitados");
                msgNav = "toSaleConfirm";
            }

        }

        //Validamos el monto recibido
        if (amountPayed < sale.getAmount()) {
            GrowlBean.simplyWarmMessage("Monto entregado erroneo", "No es suficiente el monto indresado");
            msgNav = "toSaleConfirm";
        } else if (amountPayed > sale.getAmount()) {
            GrowlBean.simplyWarmMessage("Monto entregado erroneo", "El monto ingresado es superior al solicitado");
            msgNav = "toSaleConfirm";
        } else {
            this.save(vendor, payTypeList);
        }

        return msgNav;
    }

    /**
     * Creamos la guia padre
     *
     * @param guide
     * @param rootRoute
     * @return
     */
    private Guide createRootGuide(Guide rootGuide, Itinerary rootRoute) {

        rootGuide.setDestiny(rootRoute.getDestiny());
        rootGuide.setOrigin(rootRoute.getOrigin());
        rootGuide.setRootRoute(rootRoute.getRoute().getId());

        rootGuide = guideService.getRepository().save(rootGuide);
        rootGuide.setRootGuide(rootGuide.getId());
        rootGuide.update(rootGuide);
        rootGuide = guideService.getRepository().save(rootGuide);

        return rootGuide;
    }

    public void clearSale() {
        this.sale = new Sale();
        this.saleDetail = new ArrayList<SaleDetail>();
        this.guide = new Guide();
        this.guideRoot = new Guide();
        this.selected = new ItineraryCost();
    }

    public String toSaleConfirm() {
        String msgNav = "toSaleConfirm";
        if (this.sale.getPassengers() + this.sale.getRetirees() == saleDetail.size()) {

            //Calculamos el total de la venta considerando los descuentos por jubilados
            Double subTotal = selected.getCost().getCost() * this.saleDetail.size();
            Double discount = 0.0;

            if (this.sale.getRetirees() > 0) {
                discount = (selected.getCost().getCost() * this.sale.getRetirees()) * _RETIREE_DISCOUNT;
            } //Hay jubilados en el viaje?

            Double amount = subTotal - discount;

            sale.setSubTotal(subTotal);
            sale.setDiscount(discount);
            sale.setAmount(amount);

            if (sale.isPartner()) {
                saleDetail.get(0).setCustomerName(associate.getFullName());
            }
        } else {
            GrowlBean.simplyWarmMessage("Asientos incompletos", "No Se han asigando los asientos solicitados");
            msgNav = "toSale";
        }

        return msgNav;
    }

    /**
     * Agregamos el asiento seleccionado
     */
    public void addSeat() {
        String bolType = "";
        if (saleDetail.size() < this.sale.getPassengers() + this.sale.getRetirees()) {
            if (this.selectedSeat != null) {
                SaleDetail saleDetailTmp = new SaleDetail(this.selected.getCost().getCost(), selectedSeat, new Customer(), associate, bolType);
                if (!saleDetail.contains(saleDetailTmp)) {
                    saleDetail.add(saleDetailTmp);
                }
            }
        } else {
            GrowlBean.simplyWarmMessage("Asientos completos", "Se han asigando todos los asientos solicitados");
        }
    }

    /**
     * Removemos el asiento seleccionado
     */
    public void removeSeat() {
        if (unSelectedDetail != null) {
            saleDetail.remove(unSelectedDetail);
        }
    }

    public void fillSeats() {
        init();
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

    public ItineraryService getItineraryService() {
        return itineraryService;
    }

    public void setItineraryService(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    public ItineraryCostModel getModel() {
        return model;
    }

    public void setModel(ItineraryCostModel model) {
        this.model = model;
    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
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

    public String columnClass(int value) {
        Integer[] arraRight = new Integer[]{2, 6, 10, 14, 18, 22, 26, 30, 34, 38, 42};
        Integer[] arraLeft = new Integer[]{3, 7, 11, 15, 19, 23, 27, 31, 35, 39, 43};

        String customClass = "window";
        if (Arrays.asList(arraRight).contains(value)) {
            customClass = "aisler";
        } else if (Arrays.asList(arraLeft).contains(value)) {
            customClass = "aislel";
        }

        return customClass;
    }

    /**
     * Buscamos asiento
     */
    public String findSeat(String seat) {
        System.out.println("Fecha " + this.sale.getTripDate());
        unSelectedDetail = saleService.getSaleRepository().findSeat(this.sale.getOrigin().getId(), this.sale.getDestiny().getId(), this.sale.getTripDate(), seat);

        if (unSelectedDetail != null) {
            this.sale.setSeat(seat);
        } else {
            this.sale.setSeat(null);
            GrowlBean.simplyErrorMessage("Error", "Asiento no encontrado");
        }
        return "toCancel";
    }

    public String cancelSeat(Vendor vendor) throws CloneNotSupportedException {

        unSelectedDetail.setStatus("C");
        unSelectedDetail = saleService.getDetailRepository().save(unSelectedDetail);
        sale = unSelectedDetail.getSale();
        sale.setCancelVendor(vendor);
        sale.setAmount(sale.getAmount() - unSelectedDetail.getAmount());
        sale.setSubTotal(sale.getSubTotal() - unSelectedDetail.getAmount());
        sale.setPassengers(sale.getPassengers() - 1);
        sale = saleService.getSaleRepository().save(sale);
        GrowlBean.simplyWarmMessage("Se ha cancelado", "Asiento cancelad0 con exito!");
        this.sale.setSeat(null);

        return "toCancel";

    }

    public String toCancel() {
        String msgNav = "";
        if (this.sale.getSeat() != "") {
            msgNav = "toCancel";
        } else {
            GrowlBean.simplyWarmMessage("Asiento no capturado", "No Se ha capturado el asiento solicitado");
            msgNav = "toCancel";
        }

        return msgNav;
    }

    public SaleDetail getSaleDetailSelected() {
        return saleDetailSelected;
    }

    public void setSaleDetailSelected(SaleDetail saleDetailSelected) {
        this.saleDetailSelected = saleDetailSelected;
    }

}
