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
import com.simphony.beans.PayRollService;
import com.simphony.beans.SaleService;
import com.simphony.beans.SeatService;
import com.simphony.entities.Associate;
import com.simphony.entities.Cost;
import com.simphony.entities.Customer;
import com.simphony.entities.Guide;
import com.simphony.entities.GuideDetail;
import com.simphony.entities.Itinerary;
import com.simphony.entities.PayRoll;
import com.simphony.entities.PayType;
import com.simphony.entities.Payment;
import com.simphony.entities.ReservedSeats;
import com.simphony.entities.Sale;
import com.simphony.entities.SaleDetail;
import com.simphony.entities.Seat;
import com.simphony.entities.User;
import com.simphony.entities.Vendor;
import com.simphony.interfases.IConfigurable;
import com.simphony.models.ItineraryCostModel;
import com.simphony.pojos.ItineraryCost;
import com.simphony.pojos.ReservedSeatInDetailSale;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class SaleBean implements IConfigurable {

    private Sale sale = new Sale();
    private Sale cancelledSale = new Sale();
    private SaleDetail pendingSale = new SaleDetail();
    private Cost cost = new Cost();
    private Guide guide = new Guide();
    private Guide guideRoot = new Guide();
    private Seat selectedSeat = new Seat();
    private Associate associate = new Associate();
    private ItineraryCost selected = new ItineraryCost();
    private SaleDetail unSelectedDetail = new SaleDetail();
    private SaleDetail saleDetailSelected = new SaleDetail();
    private ItineraryCostModel model = new ItineraryCostModel();
    ReservedSeatInDetailSale selectedReservedSeatInDetailSale = new ReservedSeatInDetailSale();

    private List<Seat> seat = new ArrayList();
    private List<Sale> list = new ArrayList();
    private List<PayRoll> payRollList = new ArrayList();
    private List<Seat> selectedSeats = new ArrayList<Seat>();
    List<ReservedSeatInDetailSale> reservedSeatInDetailSale = new ArrayList();
    private List<SaleDetail> saleDetail = new ArrayList<SaleDetail>();
    private List<ItineraryCost> itineraryCost = new ArrayList<ItineraryCost>();

    private boolean existSelectedAssociates = false;
    private Double valeCaja = 0.0;
    private Double cantOriginal = 0.0;

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

    @ManagedProperty(value = "#{payRollService}")
    private PayRollService payRollService;

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
     *
     * @throws java.text.ParseException
     */
    public void findItinearies() throws ParseException {
        Calendar now = Calendar.getInstance();
        int increaseSequence = 0;

        if (_SDF.parse(_SDF.format(this.sale.getTripDate())).compareTo(_SDF.parse(_SDF.format(now.getTime()))) < 0) {
            //     GrowlBean.simplyErrorMessage("Error de fechas", "Rutas fuera de calendario");
            //     return;
        }

        if (this.sale.getPassengers() + this.sale.getRetirees() > 0) {
            itineraryCost.clear();

            itineraryCost = saleService.getSaleRepository().findItineraryCost(this.sale.getOrigin().getId(), this.sale.getDestiny().getId());

            List<ItineraryCost> itineraryCostTemp = saleService.getSaleRepository().findItineraryDetailCost(this.sale.getOrigin().getId(), this.sale.getDestiny().getId());

            if (itineraryCostTemp.size() > 0) {

                for (ItineraryCost it : itineraryCostTemp) {
                    it.getItinerary().setRoute(it.getAlternateItinerary().getRoute());
                    if (this.sale.getDestiny().getId() == it.getItinerary().getDestiny().getId()) {
                        it.getAlternateItinerary().setSequence(it.getAlternateItinerary().getSequence() + 5);
                    }
                    itineraryCost.add(it);
                }
            }

            if (itineraryCost.size() > 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(this.sale.getTripDate());
                Calendar calTimeTmp = Calendar.getInstance();

                calTimeTmp.setTime(sale.getTripDate());

                List<ItineraryCost> lastItinerary = new ArrayList<ItineraryCost>();
                for (ItineraryCost itineraryCost1 : itineraryCost) {

                    Long route = itineraryCost1.getItinerary().getRoute().getId();
                    Itinerary rootItinerary
                            = itineraryService.getItineraryRepository().findOne(route);
                    calTimeTmp.setTime(itineraryCost1.getItinerary().getDepartureTime());

                    Calendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
                            calTimeTmp.get(Calendar.HOUR_OF_DAY), calTimeTmp.get(Calendar.MINUTE), calTimeTmp.get(Calendar.SECOND));
                    itineraryCost1.setDepartureTime(calendar.getTime());

                    rootItinerary.setDepartureTime(itineraryCost1.getDepartureTime());
                    guideRoot = guideService.getRepository().findRootGuide(route, rootItinerary.getDepartureTime());
                    List<ReservedSeats> reservedSeats = saleService.getReservedSeatsRepository().findAllReserved(guideRoot.getRootGuide(), guideRoot.getRootRoute());
                    
                    itineraryCost1.setReservedSeats(reservedSeats.size());
                    itineraryCost1.setFreeSeats(guideRoot.getQuota() - reservedSeats.size());

                    if (guideRoot != null && !guideRoot.getStatus().equals(_GUIDE_TYPE_CLOSED)) {
                        lastItinerary.add(itineraryCost1);
                    }

                }

                if (lastItinerary.size() > 0) {
                    model = new ItineraryCostModel(lastItinerary);

                    sale.setExistRoutes(lastItinerary.size() > 0);
                    sale.setAvailability(false);
                }else{
                    GrowlBean.simplyErrorMessage("No se encontraron rutas", "Rutas no disponibles");
                }
            } else {
                GrowlBean.simplyErrorMessage("No se encontro", "Rutas no encontradas");
            }
        } else {
            GrowlBean.simplyErrorMessage("Sin pasajeros", "Es importante asignar los pasajeros");
        }

    }

    /**
     * Buscamos al agremiado
     *
     * @param innerSaleDetail
     */
    public void findAssociate(SaleDetail innerSaleDetail) {
        Associate temp = associateService.getRepository().findByKey(innerSaleDetail.getAssociateKey());
        Integer index = 0;
        if (this.saleDetail.contains(innerSaleDetail)) {
            index = this.saleDetail.indexOf(innerSaleDetail);
        }

        if (temp != null) {
            innerSaleDetail.setAssociate(temp);
            innerSaleDetail.setCustomerName(temp.getFirstLastName().trim() + " " + temp.getSecondLastName().trim() + " " + temp.getName().trim());
            this.saleDetail.set(index, innerSaleDetail);

        } else {
            innerSaleDetail.setCustomerName(_BLANK);
            innerSaleDetail.setAssociateKey(_BLANK);
            this.saleDetail.set(index, innerSaleDetail);
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

            if (calTime.getTime().compareTo(this.sale.getTripDate()) > 0) {
                calTime.add(Calendar.DAY_OF_MONTH, -1);
            }
            rootItinerary.setDepartureTime(calTime.getTime());

            //Validamos la guia padre
            guideRoot = guideService.getRepository().findRootGuide(route, rootItinerary.getDepartureTime());
            if (guideRoot == null) {
                guideRoot = new Guide();
                guideRoot.setUsrModify(vendor.getNick());
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
                Pageable topTen = new PageRequest(0, maxLimitCarrie);
                seat = seatService.getRepository().findAllAvailable(topTen);
            } else {
                seat = seatService.getRepository().findAllAvailable();
            }

            Seat occupiedPattern = seatService.getRepository().findOccupiedSeatPattern();
            List<ReservedSeats> reservedSeats = saleService.getReservedSeatsRepository().findAllReserved(guideRoot.getRootGuide(), guideRoot.getRootRoute());

            for (ReservedSeats reserved : reservedSeats) {

                if (seat.contains(reserved.getSeat())) {
                    int index = seat.indexOf(reserved.getSeat());

                    if (this.selected.getItinerary().getSequence() == 0
                            && this.selected.isNormalMode()) {
                        seat.set(index, occupiedPattern);
                    } else if (reserved.getFinalSequence() == 0) {  //Parten del mismo origen   
                        seat.set(index, occupiedPattern);
                    } else if (selected.getItinerary().getSequence() == reserved.getInitialSequence()) {
                        seat.set(index, occupiedPattern);
                        //Alt 10  -  Initial 0
                    } else if (!this.selected.isNormalMode()
                            && (selected.getAlternateItinerary().getSequence() < reserved.getFinalSequence()
                            && selected.getAlternateItinerary().getSequence() > reserved.getInitialSequence())) {
                        seat.set(index, occupiedPattern);
                    } else if (!this.selected.isNormalMode()
                            && (reserved.getInitialSequence() >= selected.getItinerary().getSequence()
                            && selected.getAlternateItinerary().getSequence() >= reserved.getFinalSequence())) {
                        seat.set(index, occupiedPattern);
                    } else if (!this.selected.isNormalMode()
                            && (reserved.getInitialSequence() <= selected.getItinerary().getSequence()
                            && selected.getAlternateItinerary().getSequence() >= reserved.getFinalSequence()
                            && reserved.getFinalSequence() > selected.getItinerary().getSequence())) {
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
     * @param payRollList
     * @throws java.lang.CloneNotSupportedException
     */
    public void save(Vendor vendor, List<PayType> payTypeList, List<PayRoll> payRollList) throws CloneNotSupportedException {

        // Guardamos si hubo venta pendiente.
        if (pendingSale.getId() != null) {
            this.modifyPendingSale(vendor);

            sale.setIdRefSale(pendingSale.getId());
            sale.setServiceType(_PENDING);
        }
        sale.setVendor(vendor);
        sale.setStatus(_SALED);
        sale.setCreateDate(new Date());

        //Guardamos la venta
        sale = saleService.getSaleRepository().save(sale);

        // Guardamos la guia
        if (guide.isNewGuide()) {

            guide.setUsrModify(vendor.getNick());
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
                guide.setFinaDestiny(guideRoot.getDestiny());

                guideService.getRepository().save(guide);
                guide.setRootGuide(guide.getId());
                guide.update(guide);
                guide = guideService.getRepository().save(guide);

            } else {

                guide.setOrigin(this.selected.getCost().getOrigin());
                guide.setDestiny(this.selected.getCost().getDestiny());
                guide.setRootRoute(guideRoot.getRootRoute());
                guide.setRootGuide(guideRoot.getId());
                guide.setFinaDestiny(guideRoot.getDestiny());
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

            //Guardamos los asientos ocupados            
            ReservedSeats reservedSeat = new ReservedSeats();
            reservedSeat.setGuideId(guide.getRootGuide());
            reservedSeat.setRouteId(guide.getRootRoute());
            reservedSeat.setRouteType(this.selected.getItinerary().getTypeOfRoute());

            ///Aqui modifique si es local la ruta antes tenia 0 0
            reservedSeat.setInitialSequence(this.selected.getItinerary().getSequence());
            if (this.selected.isNormalMode()) {
                reservedSeat.setFinalSequence(this.selected.getItinerary().getSequence());
            } else {
                reservedSeat.setFinalSequence(this.selected.getAlternateItinerary().getSequence());
            }
            reservedSeat.setSeat(dtSale.getSeat());

            saleService.getReservedSeatsRepository().save(reservedSeat);

            //Guardamos el detalle de venta
            dtSale.setSale(sale);
            dtSale.setStatus(_SALED);

            if (pendingSale.getId() != null) {
                dtSale.setServiceType(_PENDING);
                dtSale.setIdRefSale(sale.getId());
            } else {
                dtSale.setServiceType("");
                dtSale.setIdRefSale(new Long(0));
            }
            if (dtSale.getAssociateKey().isEmpty()) {
                dtSale.setAssociate(associateService.getRepository().getOne(1L));
                dtSale.setType(_SALE_TYPE_PUBLIC);
            } else {
                dtSale.setType(_SALE_TYPE_ASSOCIATE);
            }

            if (dtSale.getBolType().equals(_RETIREE)) {
                dtSale.setDiscount(dtSale.getAmount() * _RETIREE_DISCOUNT);
            }
            dtSale.setIdResSeat(reservedSeat.getId());
            dtSale.setCustomerName(dtSale.getCustomerName().toUpperCase());
            dtSale.update(dtSale);
            saleService.getDetailRepository().save(dtSale);
        }

        //Guardamos los pagos
        for (PayType payType : payTypeList) {
            if (payType.getAmount() > 0.0) {
                Payment payment = new Payment(payType, sale);
                saleService.getPaymentRepository().save(payment);
            }
        }

        //Guardamos el tipo de pago nominal en caso de existir
        for (PayRoll pr : payRollList) {
            if (pr.getAmount() > 0.0) {
                pr.setSale(this.sale);
                this.saleService.getPayrollRepository().save(pr);
            }
        }

        GrowlBean.simplyWarmMessage("Se ha guardado la venta", "Venta guardada con exito!");
        this.clearSale();

    }

    /**
     * *
     *
     * @param vendor
     * @throws java.lang.CloneNotSupportedException
     */
    public String saveReservations(Vendor vendor) throws CloneNotSupportedException {

        sale.setVendor(vendor);
        sale.setStatus(_RESERVATED);
        sale.setCreateDate(new Date());

        //Guardamos la venta
        sale = saleService.getSaleRepository().save(sale);

        // Guardamos la guia
        if (guide.isNewGuide()) {

            guide.setUsrModify(vendor.getNick());
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
                guide.setFinaDestiny(guideRoot.getDestiny());

                guideService.getRepository().save(guide);
                guide.setRootGuide(guide.getId());
                guide.update(guide);
                guide = guideService.getRepository().save(guide);

            } else {

                guide.setOrigin(this.selected.getCost().getOrigin());
                guide.setDestiny(this.selected.getCost().getDestiny());
                guide.setRootRoute(guideRoot.getRootRoute());
                guide.setRootGuide(guideRoot.getId());
                guide.setFinaDestiny(guideRoot.getDestiny());
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
            dtSale.setStatus(_RESERVATED);

            if (dtSale.getAssociateKey().isEmpty()) {
                dtSale.setAssociate(associateService.getRepository().getOne(1L));
                dtSale.setType(_SALE_TYPE_PUBLIC);
            } else {
                dtSale.setType(_SALE_TYPE_ASSOCIATE);
                associateService.getRepository().save(dtSale.getAssociate());
            }

            if (dtSale.getBolType().equals(_RETIREE)) {
                dtSale.setDiscount(dtSale.getAmount() * _RETIREE_DISCOUNT);
            }
            dtSale.setCustomerName(dtSale.getCustomerName().toUpperCase());

            saleService.getDetailRepository().save(dtSale);

            //Guardamos los asientos ocupados            
            ReservedSeats reservedSeat = new ReservedSeats();
            reservedSeat.setGuideId(guide.getRootGuide());
            reservedSeat.setRouteId(guide.getRootRoute());
            reservedSeat.setRouteType(this.selected.getItinerary().getTypeOfRoute());

            ///Aqui modifique si es local la ruta antes tenia 0 0
            reservedSeat.setInitialSequence(this.selected.getItinerary().getSequence());
            if (this.selected.isNormalMode()) {
                reservedSeat.setFinalSequence(this.selected.getItinerary().getSequence());
            } else {
                reservedSeat.setFinalSequence(this.selected.getAlternateItinerary().getSequence());
            }
            reservedSeat.setSeat(dtSale.getSeat());

            saleService.getReservedSeatsRepository().save(reservedSeat);

        }

        GrowlBean.simplyWarmMessage("Se ha guardado la Reservación", "Reservación guardada con exito!");
        this.clearSale();

        return "toReservations";
    }

    /**
     * Buscamos Reservaciones
     *
     * @param mode
     * @return
     */
    public String findReservations(Integer mode) {
        boolean saleExist = false;
        reservedSeatInDetailSale.clear();

        if (mode == 0) {

            for (Sale saleTmp : saleService.getSaleRepository().findRes(this.cancelledSale.getOrigin().getId(),
                    this.cancelledSale.getDestiny().getId(),
                    this.cancelledSale.getTripDate())) {
                for (ReservedSeatInDetailSale reservedSeatInDetailSaleTmp : saleService.getDetailRepository().findSeatsBySale(saleTmp.getId(), "R")) {
                    reservedSeatInDetailSale.add(reservedSeatInDetailSaleTmp);
                    saleExist = true;
                }
            }

            if (!saleExist) {
                GrowlBean.simplyWarmMessage("Sin resultados", "No se ha encotrado una reservación con esos datos");
            }

        } else if (mode == 2) {
            if (!unSelectedDetail.getCustomerName().isEmpty()) {

                for (ReservedSeatInDetailSale reservedSeatInDetailSaleTmp : saleService.getDetailRepository().
                        findSeatsByCustomerName(unSelectedDetail.getCustomerName().trim().toUpperCase(), "R")) {
                    reservedSeatInDetailSale.add(reservedSeatInDetailSaleTmp);
                    saleExist = true;
                }

                if (!saleExist) {
                    GrowlBean.simplyWarmMessage("Sin resultados", "No se ha encotrado una reservación con esos datos");
                }
            }
        }

        return "toCancelRes";
    }

    /**
     * Validamos lo necesario para la venta
     *
     * @param vendor
     * @param payTypeList
     * @param payRollList
     * @return
     * @throws CloneNotSupportedException
     */
    public String validateSale(Vendor vendor, List<PayType> payTypeList, List<PayRoll> payRollList) throws CloneNotSupportedException {
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
            if (retireeSelected == this.sale.getRetirees()) {
                GrowlBean.simplyWarmMessage("Sin seleccion de jubilados", "No se han seleccionado los jubilados solicitados");
                msgNav = "toSaleConfirm";
            }

        }

        //Validamos el monto recibido
        if (amountPayed < sale.getAmount()) {
            GrowlBean.simplyWarmMessage("Monto entregado erroneo", "No es suficiente el monto ingresado");
            msgNav = "toSaleConfirm";
        } else if (amountPayed > sale.getAmount()) {
            GrowlBean.simplyWarmMessage("Monto entregado erroneo", "El monto ingresado es superior al solicitado");
            msgNav = "toSaleConfirm";
        } else {
            this.save(vendor, payTypeList, payRollList);
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
        rootGuide.setFinaDestiny(rootRoute.getDestiny());
        rootGuide.setOrigin(rootRoute.getOrigin());
        rootGuide.setRootRoute(rootRoute.getRoute().getId());

        rootGuide = guideService.getRepository().save(rootGuide);
        rootGuide.setRootGuide(rootGuide.getId());
        rootGuide.update(rootGuide);
        rootGuide = guideService.getRepository().save(rootGuide);

        return rootGuide;
    }

    /**
     * Calculamos el descuento por jubilado
     *
     * @param saleDetail
     */
    public void calculateRetireeDiscount(List<SaleDetail> saleDetail) {
        Integer countRetiree = 0;
        Double subTotal = selected.getCost().getCost() * saleDetail.size();
        for (SaleDetail sl : saleDetail) {
            if (sl.getBolType().equals(_RETIREE)) {
                countRetiree++;
            }
        }
        if (countRetiree > 0) {
            this.sale.setDiscount((selected.getCost().getCost() * countRetiree) * _RETIREE_DISCOUNT);
            Double amount = subTotal - this.sale.getDiscount();

            sale.setSubTotal(subTotal);
            sale.setAmount(amount);
        } else {
            this.sale.setDiscount(0.0);
            sale.setSubTotal(subTotal);
            sale.setAmount(subTotal);
        } //Hay jubilados en el viaje?

    }

    /**
     * Confirmamos la venta
     *
     * @return
     */
    public String toSaleConfirm() {
        String msgNav = "toSaleConfirm";
        if (this.sale.getPassengers() == saleDetail.size()) {

            this.sale.setTripDate(this.selected.getDepartureTime());
            sale.setOrigin(this.selected.getCost().getOrigin());
            sale.setDestiny(this.selected.getCost().getDestiny());

            //Calculamos el total de la venta considerando los descuentos por jubilados
            Double subTotal = selected.getCost().getCost() * this.saleDetail.size();
            Double discount = 0.0;

            Double amount = subTotal - discount;

            sale.setSubTotal(subTotal);
            sale.setDiscount(discount);
            sale.setAmount(amount);
            cantOriginal = sale.getAmount();

        } else {
            GrowlBean.simplyWarmMessage("Asientos incompletos", "No Se han asigando los asientos solicitados");
            msgNav = "toSale";
        }

        return msgNav;
    }

    public String toReservations() {
        cancelledSale = new Sale();
        unSelectedDetail = new SaleDetail();
        reservedSeatInDetailSale = new ArrayList();
        selectedReservedSeatInDetailSale = new ReservedSeatInDetailSale();
        return "toReservations";
    }

    public String toCancelRes() {
        cancelledSale = new Sale();
        unSelectedDetail = new SaleDetail();
        reservedSeatInDetailSale = new ArrayList();
        selectedReservedSeatInDetailSale = new ReservedSeatInDetailSale();
        return "toCancelRes";
    }

    /**
     * Confirmamos la Reservación
     *
     * @return
     */
    public String toReservationsConfirm() {
        String msgNav = "toReservationsConfirm";
        if (this.sale.getPassengers() == saleDetail.size()) {

            this.sale.setTripDate(this.selected.getDepartureTime());
            sale.setOrigin(this.selected.getCost().getOrigin());
            sale.setDestiny(this.selected.getCost().getDestiny());

        } else {
            GrowlBean.simplyWarmMessage("Asientos incompletos", "No Se han asigando los asientos solicitados");
            msgNav = "toRservations";
        }

        return msgNav;
    }

    /**
     * Limpiamos la venta
     */
    public void clearSale() {
        this.sale = new Sale();
        this.sale.setTripDate(new Date());
        this.saleDetail = new ArrayList<SaleDetail>();
        this.guide = new Guide();
        this.guideRoot = new Guide();
        this.selected = new ItineraryCost();
        this.pendingSale = new SaleDetail();
    }

    /**
     * Agregamos el asiento seleccionado
     */
    public void addSeat() {
        String bolType = "";
        boolean exist = false;
        if (saleDetail.size() < this.sale.getPassengers() + this.sale.getRetirees()) {
            if (this.selectedSeat != null) {
                for (SaleDetail sl : saleDetail) {
                    if (this.selectedSeat.getSeat().trim().equals(sl.getSeat().getSeat().trim())) {
                        exist = true;
                    }
                }
                if (!exist) {
                    SaleDetail saleDetailTmp = new SaleDetail(this.selected.getCost().getCost(), selectedSeat, new Customer(), associate, bolType);
                    saleDetail.add(saleDetailTmp);
                }
            }
        } else {
            GrowlBean.simplyWarmMessage("Asientos completos", "Se han asigando todos los asientos solicitados");
        }
    }

    /**
     * Validamos que se haya tecleado algu id del asociado
     *
     */
    public void validateAssociates() {

        boolean exist = false;
        for (SaleDetail sl : saleDetail) {
            if (!sl.getAssociateKey().trim().isEmpty()) {
                exist = true;
                break;
            }
        }

        RequestContext context = RequestContext.getCurrentInstance();

        if (!exist) {
            GrowlBean.simplyInfoMessage("Solo agremiados.", "No existen agremiados seleccionados.");
        } else {
            context.execute("PF('payRollVar').show()");
        }

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

    /**
     * Formateamos los asientos
     *
     * @param value
     * @return
     */
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
     * Cancelamos el asiento
     *
     * @param user
     * @param toReturn
     * @return
     * @throws CloneNotSupportedException
     */
    public String cancelSeat(User user, String toReturn) throws CloneNotSupportedException {

        if (selectedReservedSeatInDetailSale != null) {

            SaleDetail saleDetailCancelled = selectedReservedSeatInDetailSale.getSaleDetail();

            //Cancelamos el detalle de la venta
            selectedReservedSeatInDetailSale.getSaleDetail().setStatus(_CANCELLED);

            saleDetailCancelled.update(selectedReservedSeatInDetailSale.getSaleDetail());

            unSelectedDetail = saleService.getDetailRepository().save(saleDetailCancelled);

            //Borramos el asiento reservado
            saleService.getReservedSeatsRepository().delete(selectedReservedSeatInDetailSale.getReservedSeatId());

            Integer countDetailLeft = saleService.getDetailRepository().countDetailBySale(sale.getId());

            //Cancelamos el monto de la venta
            if (countDetailLeft == 0) {
                sale.setStatus(_CANCELLED);

            }
            sale = saleDetailCancelled.getSale();
            sale.setCancelUser(user.getNick());
            sale.setDiscount(sale.getDiscount() - saleDetailCancelled.getDiscount());
            sale.setAmount(sale.getAmount() - (saleDetailCancelled.getAmount() - saleDetailCancelled.getDiscount()));
            sale.setSubTotal(sale.getSubTotal() - saleDetailCancelled.getDiscount());
            sale.setPassengers(sale.getPassengers() - 1);
//            if ("toCancel".equals(toReturn)){
//                sale.setAmount(sale.getAmount() - (saleDetailCancelled.getAmount()-saleDetailCancelled.getDiscount()));
//                sale.setSubTotal(sale.getSubTotal() - saleDetailCancelled.getAmount());
//            }

            //Actualizamos la venta
            sale.update(sale);
            saleService.getSaleRepository().save(sale);

            //Borramos el elmento de la lista
            this.reservedSeatInDetailSale.remove(selectedReservedSeatInDetailSale);
            GrowlBean.simplyWarmMessage("Se ha cancelado", "Asiento cancelado con exito!");

        } else {
            GrowlBean.simplyWarmMessage("Sin selección", "No se ha seleccionado asiento para cancelar!");
        }
        return toReturn;

    }

    /**
     * Buscamos asiento
     *
     * @param mode
     * @return
     */
    public String findSale(Integer mode) {
        boolean saleExist = false;
        reservedSeatInDetailSale.clear();
        SaleDetail sdTemp = new SaleDetail();
        if (mode == 0) {

            for (Sale saleTmp : saleService.getSaleRepository().findSale(this.cancelledSale.getOrigin().getId(),
                    this.cancelledSale.getDestiny().getId(),
                    this.cancelledSale.getTripDate())) {
                for (ReservedSeatInDetailSale reservedSeatInDetailSaleTmp : saleService.getDetailRepository().findSeatsBySale(saleTmp.getId(), "V")) {
                    reservedSeatInDetailSale.add(reservedSeatInDetailSaleTmp);
                    saleExist = true;
                }
            }

            if (!saleExist) {
                GrowlBean.simplyWarmMessage("Sin resultados", "No se ha encotrado una venta con esos datos");
            }

        } else if (mode == 1) {

            if (cancelledSale != null) {
                sdTemp = saleService.getDetailRepository().findOne(cancelledSale.getId());
                if (sdTemp != null) {
                    saleExist = true;
                    cancelledSale = sdTemp.getSale();
                }

            }

            if (saleExist) {
                reservedSeatInDetailSale = saleService.getDetailRepository().findSeatsBySale(cancelledSale.getId(), "V");
            } else {
                GrowlBean.simplyWarmMessage("Sin resultados", "No se ha encotrado una venta con esos datos");
                cancelledSale = new Sale();
            }//Existe venta
        } else if (mode == 2) {
            if (!unSelectedDetail.getCustomerName().isEmpty()) {

                for (ReservedSeatInDetailSale reservedSeatInDetailSaleTmp : saleService.getDetailRepository().
                        findSeatsByCustomerName(unSelectedDetail.getCustomerName().trim().toUpperCase(), "V")) {
                    reservedSeatInDetailSale.add(reservedSeatInDetailSaleTmp);
                    saleExist = true;
                }

                if (!saleExist) {
                    GrowlBean.simplyWarmMessage("Sin resultados", "No se ha encotrado una venta con esos datos");
                }
            }
        }

        return "toCancel";
    }

    /**
     * A cancelar
     *
     * @return
     */
    public String toCancel() {
        cancelledSale = new Sale();
        unSelectedDetail = new SaleDetail();
        reservedSeatInDetailSale = new ArrayList();
        selectedReservedSeatInDetailSale = new ReservedSeatInDetailSale();
        return "toCancel";
    }

    //Pendiente
    public void confirmPayRoll(List<PayRoll> list) {
        this.payRollList = list;

    }

    public SaleDetail getSaleDetailSelected() {
        return saleDetailSelected;
    }

    public void setSaleDetailSelected(SaleDetail saleDetailSelected) {
        this.saleDetailSelected = saleDetailSelected;
    }

    public PayRollService getPayRollService() {
        return payRollService;
    }

    public void setPayRollService(PayRollService payRollService) {
        this.payRollService = payRollService;
    }

    public List<PayRoll> getPayRollList() {
        return payRollList;
    }

    public void setPayRollList(List<PayRoll> payRollList) {
        this.payRollList = payRollList;
    }

    public boolean isExistSelectedAssociates() {
        return existSelectedAssociates;
    }

    public void setExistSelectedAssociates(boolean existSelectedAssociates) {
        this.existSelectedAssociates = existSelectedAssociates;
    }

    public List<ReservedSeatInDetailSale> getReservedSeatInDetailSale() {
        return reservedSeatInDetailSale;
    }

    public void setReservedSeatInDetailSale(List<ReservedSeatInDetailSale> reservedSeatInDetailSale) {
        this.reservedSeatInDetailSale = reservedSeatInDetailSale;
    }

    public Sale getCancelledSale() {
        return cancelledSale;
    }

    public void setCancelledSale(Sale cancelledSale) {
        this.cancelledSale = cancelledSale;
    }

    public SaleDetail getPendingdSale() {
        return pendingSale;
    }

    public void setPendingSale(SaleDetail pendingSale) {
        this.pendingSale = pendingSale;
    }

    public void onTabChange(TabChangeEvent event) {
        reservedSeatInDetailSale.clear();
    }

    public ReservedSeatInDetailSale getSelectedReservedSeatInDetailSale() {
        return selectedReservedSeatInDetailSale;
    }

    public void setSelectedReservedSeatInDetailSale(ReservedSeatInDetailSale selectedReservedSeatInDetailSale) {
        this.selectedReservedSeatInDetailSale = selectedReservedSeatInDetailSale;
    }

    /**
     * Removemos el asiento seleccionado
     */
    public void removeSeat() {
        if (unSelectedDetail != null) {
            saleDetail.remove(unSelectedDetail);
        }
    }

    /**
     * Buscamos venta pendiente
     */
    public void findPendingSale() {
        boolean saleExist = false;
        boolean saleUsed = false;

        if (pendingSale != null) {
            pendingSale = saleService.getDetailRepository().findOne(pendingSale.getId());
            if (pendingSale != null && pendingSale.getStatus().equals("V")) {
                saleExist = true;
                if (pendingSale.getServiceType().equals("P")) {
                    saleUsed = true;
                }
            }
        }

        if (!saleExist) {
            GrowlBean.simplyWarmMessage("Sin resultados", "No se ha encotrado una venta con esos datos");
            pendingSale = new SaleDetail();
        } else {
            if (saleUsed) {
                GrowlBean.simplyWarmMessage("Folio ya utilizado", "El folio de ese boleto ya fue cambiado anteriormente");
                pendingSale = new SaleDetail();
            } else {
                String status = guideService.getRepository().selectStatus(pendingSale.getSale().getId());
                if (status.equals("CL")) {
                    GrowlBean.simplyWarmMessage("Guía Cerrada", "La Guía de viaje ya fue cerrada...");
                    pendingSale = new SaleDetail();
                }
            }
        }
    }

    public String cancelPendigSale(Integer mode) {
        if (mode == 2) {
            pendingSale.clear();
        } else {
            Double varCalc;
            if (pendingSale.getAmount() >= cantOriginal) {
                varCalc = pendingSale.getAmount() - cantOriginal;
                valeCaja = varCalc;
                this.sale.setAmount(0.0);
            } else {
                varCalc = cantOriginal - pendingSale.getAmount();
                valeCaja = 0.0;
                this.sale.setAmount(varCalc);
            }
            this.sale.setSubTotal(0.0);
        }
        return "toCambioConfirm";
    }

    /**
     * Cancelamos la venta Pendiente
     *
     * @param vendor
     * @throws CloneNotSupportedException
     */
    public void modifyPendingSale(Vendor vendor) throws CloneNotSupportedException {
        Sale saleTmp = pendingSale.getSale();

        //Cancelamos el detalle de la venta
        pendingSale.setServiceType(_PENDING);
        pendingSale.setStatus(_CANCELLED);

        saleService.getDetailRepository().save(pendingSale);

        //Borramos el asiento reservado
        saleService.getReservedSeatsRepository().delete(pendingSale.getIdResSeat());

        Integer countDetailLeft = saleService.getDetailRepository().countDetailBySale(saleTmp.getId());

        //Cancelamos el monto de la venta
        if (countDetailLeft == 0) {
            saleTmp.setStatus(_PENDING);

        }
        saleTmp.setCancelUser(vendor.getNick());
        saleTmp.setPassengers(saleTmp.getPassengers() - 1);
        saleTmp.setServiceType(_ROUNDED);

        //Actualizamos la venta anterior
        saleTmp.update(saleTmp);
        saleService.getSaleRepository().save(saleTmp);
    }

    /**
     * Muestra el diálogo de la venta pendiente
     *
     */
    public void pendingSaleDialog() {

        RequestContext context = RequestContext.getCurrentInstance();

        context.execute("PF('pendingSaleVar').show()");
    }

    /**
     * A venta pendiente
     *
     * @return
     */
    public String toPendingSale() {

        return "toPendingSale";
    }

}
