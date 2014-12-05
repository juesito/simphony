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
import java.io.Serializable;
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
public class SaleBean implements IConfigurable, Serializable {

    private Sale sale = new Sale();
    private Sale saleToBack = new Sale();
    private Sale cancelledSale = new Sale();

    private Cost cost = new Cost();
    private Guide guide = new Guide();
    private Guide guideRoot = new Guide();
    private Guide guideToBack = new Guide();
    private Guide guideRootToBack = new Guide();

    private Associate associate = new Associate();

    private ItineraryCostModel model = new ItineraryCostModel();
    private ItineraryCostModel modelToBack = new ItineraryCostModel();

    List<ReservedSeatInDetailSale> reservedSeatInDetailSale = new ArrayList();
    ReservedSeatInDetailSale selectedReservedSeatInDetailSale = new ReservedSeatInDetailSale();

    private Seat selectedSeat = new Seat();
    private Seat selectedSeatToBack = new Seat();
    private List<Sale> list = new ArrayList();
    private List<Seat> seats = new ArrayList();
    private List<Seat> seatsToBack = new ArrayList();
    private List<Seat> selectedSeats = new ArrayList<Seat>();

    private List<PayRoll> payRollList = new ArrayList();

    private SaleDetail pendingSale = new SaleDetail();
    private SaleDetail unSelectedDetail = new SaleDetail();
    private SaleDetail saleDetailSelected = new SaleDetail();
    private List<SaleDetail> saleDetail = new ArrayList<SaleDetail>();
    private List<SaleDetail> saleDetailToBack = new ArrayList<SaleDetail>();

    private ItineraryCost selected = new ItineraryCost();
    private ItineraryCost selectedToBack = new ItineraryCost();
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
     * Buscamos los itinerarios disponibles
     *
     * @param dateFind
     * @param origin
     * @param destiny
     * @return
     * @throws ParseException
     */
    public ItineraryCostModel findAvailabilities(Date dateFind, Long origin, Long destiny) throws ParseException {
        Calendar now = Calendar.getInstance();


        ItineraryCostModel currentModel = new ItineraryCostModel();

        itineraryCost.clear();
        itineraryCost = saleService.getSaleRepository().findItineraryCost(origin, destiny);

        List<ItineraryCost> itineraryCostTemp = saleService.getSaleRepository().findItineraryDetailCost(origin, destiny);

        if (itineraryCostTemp.size() > 0) {

            for (ItineraryCost it : itineraryCostTemp) {
                it.getItinerary().setRoute(it.getAlternateItinerary().getRoute());
                if (destiny == it.getItinerary().getDestiny().getId()) {
                    it.getAlternateItinerary().setSequence(it.getAlternateItinerary().getSequence() + 5);
                }
                itineraryCost.add(it);

            }
        }

        if (itineraryCost.size() > 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFind);
            Calendar calTimeTmp = Calendar.getInstance();

            calTimeTmp.setTime(dateFind);

            List<ItineraryCost> lastItinerary = new ArrayList<ItineraryCost>();
            for (ItineraryCost itineraryCost1 : itineraryCost) {

                Long route = itineraryCost1.getItinerary().getRoute().getId();
                Itinerary rootItinerary
                        = itineraryService.getItineraryRepository().findOne(route);

                Calendar calTime = (Calendar) cal.clone();

                calTime.setTime(rootItinerary.getDepartureTime());
                calTime.set(calTimeTmp.get(Calendar.YEAR), calTimeTmp.get(Calendar.MONTH), calTimeTmp.get(Calendar.DAY_OF_MONTH));

                calTimeTmp.setTime(itineraryCost1.getItinerary().getDepartureTime());

                Calendar calendar = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
                        calTimeTmp.get(Calendar.HOUR_OF_DAY), calTimeTmp.get(Calendar.MINUTE), calTimeTmp.get(Calendar.SECOND));
                itineraryCost1.setDepartureTime(calendar.getTime());

                rootItinerary.setDepartureTime(calTime.getTime());

                guideRoot = guideService.getRepository().findRootGuide(route, rootItinerary.getDepartureTime());

                if (guideRoot != null) {
                    if (!guideRoot.getStatus().equals(_GUIDE_TYPE_CLOSED)) {
                        List<ReservedSeats> reservedSeats = saleService.getReservedSeatsRepository().findAllReserved(guideRoot.getRootGuide(), guideRoot.getRootRoute());

                        int occupieds = 0;
                        for (ReservedSeats reserved : reservedSeats) {

                            if (itineraryCost1.getItinerary().getSequence() == 0
                                    && itineraryCost1.isNormalMode()) {
                                occupieds++;
                            } else if (reserved.getFinalSequence() == 0) {  //Parten del mismo origen   
                                occupieds++;
                            } else if (itineraryCost1.getItinerary().getSequence() == reserved.getInitialSequence()) {
                                occupieds++;
                                //Alt 10  -  Initial 0
                            } else if (!itineraryCost1.isNormalMode()
                                    && (itineraryCost1.getAlternateItinerary().getSequence() < reserved.getFinalSequence()
                                    && itineraryCost1.getAlternateItinerary().getSequence() > reserved.getInitialSequence())) {
                                occupieds++;
                            } else if (!itineraryCost1.isNormalMode()
                                    && (reserved.getInitialSequence() >= itineraryCost1.getItinerary().getSequence()
                                    && itineraryCost1.getAlternateItinerary().getSequence() >= reserved.getFinalSequence())) {
                                occupieds++;
                            } else if (!itineraryCost1.isNormalMode()
                                    && (reserved.getInitialSequence() <= itineraryCost1.getItinerary().getSequence()
                                    && itineraryCost1.getAlternateItinerary().getSequence() >= reserved.getFinalSequence()
                                    && reserved.getFinalSequence() > itineraryCost1.getItinerary().getSequence())) {
                                occupieds++;
                            }

                        }

                        itineraryCost1.setReservedSeats(occupieds);
                        itineraryCost1.setFreeSeats(guideRoot.getQuota() - occupieds);
                        lastItinerary.add(itineraryCost1);
                    }
                } else {
                    itineraryCost1.setReservedSeats(0);
                    itineraryCost1.setFreeSeats(_DEFAULT_SEAT_NUMBER);

                    lastItinerary.add(itineraryCost1);
                }

            }

            if (lastItinerary.size() > 0) {
                currentModel = new ItineraryCostModel(lastItinerary);

            } else {
                GrowlBean.simplyErrorMessage("No se encontro", "Rutas no encontradas");
            }
        }

        return currentModel;

    }

    /**
     * Buscamos itinerarios
     *
     * @throws java.text.ParseException
     */
    public void findItinearies() throws ParseException {

        Calendar now = Calendar.getInstance();

        if (_SDF.parse(_SDF.format(this.sale.getTripDate())).compareTo(_SDF.parse(_SDF.format(now.getTime()))) < 0) {
            //     GrowlBean.simplyErrorMessage("Error de fechas", "Rutas fuera de calendario");
            //     return;
        }

        if (this.sale.getTravelService().equals(_SALE_SINGLE_TRAVEL)) {
            model = findAvailabilities(this.sale.getTripDate(), this.sale.getOrigin().getId(), this.sale.getDestiny().getId());

            if (model.getRowCount() > 0) {
                sale.setExistRoutes(true);
                sale.setAvailability(false);

            } else {

                sale.setExistRoutes(false);
                sale.setAvailability(true);
                GrowlBean.simplyErrorMessage("No se encontraron rutas", "Rutas no disponibles");
            }
        } else {
            model = findAvailabilities(this.sale.getTripDate(), this.sale.getOrigin().getId(), this.sale.getDestiny().getId());

            if (model.getRowCount() > 0) {
                sale.setExistRoutes(true);
                sale.setAvailability(false);

                modelToBack = findAvailabilities(this.sale.getBackDate(), this.sale.getDestiny().getId(), this.sale.getOrigin().getId());

                if (modelToBack.getRowCount() == 0) {
                    sale.setExistRoutes(false);
                    sale.setAvailability(true);
                    GrowlBean.simplyErrorMessage("No se encontraron rutas de regreso", "Rutas regreso no disponibles");
                }

            } else {
                sale.setExistRoutes(false);
                sale.setAvailability(true);
                GrowlBean.simplyErrorMessage("No se encontraron rutas", "Rutas no disponibles");
            }
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

            if (this.sale.getTravelService().equals(_SALE_ROUNDED_TRAVEL)) {
                if (selectedToBack != null) {
                    seats = findAvailableSeats(this.selected, this.sale.getTripDate(), vendor.getNick(), _TO_ORIGIN);

                    if (!seats.isEmpty()) {
                        seatsToBack = findAvailableSeats(this.selectedToBack, this.sale.getBackDate(), vendor.getNick(), _TO_BACK);
                    }
                } else {
                    GrowlBean.simplyWarmMessage("No selecciono registro destino", "Es necesario seleccionar");
                }
            } else {
                seats = findAvailableSeats(this.selected, this.sale.getTripDate(), vendor.getNick(), _TO_ORIGIN);
            }

            this.sale.setAvailability(true);
            this.sale.setExistRoutes(false);
        } else {
            GrowlBean.simplyWarmMessage("No selecciono registro", "Es necesario seleccionar");
        }
    }

    /**
     * Buscamos los asientos disponibles
     *
     * @param selectedItinerary
     * @param dateFounded
     * @param vendorNick
     * @param travelType
     * @return
     * @throws java.lang.CloneNotSupportedException
     */
    public List<Seat> findAvailableSeats(ItineraryCost selectedItinerary, Date dateFounded,
            String vendorNick, String travelType) throws CloneNotSupportedException {

        List<Seat> currentSeat;
        Guide innerGuide = new Guide();
        Guide innerRootGuide = new Guide();

        //Asignamos la fecha de la venta            
        this.sale.setTripDate(selectedItinerary.getDepartureTime());

        Long ori = selectedItinerary.getCost().getOrigin().getId();
        Long des = selectedItinerary.getCost().getDestiny().getId();
        Long route = selectedItinerary.getItinerary().getRoute().getId();

        //Obtenemos el itinerario padre
        Itinerary rootItinerary
                = itineraryService.getItineraryRepository().findOne(route);

        Calendar cal = Calendar.getInstance();
        Calendar calTime = (Calendar) cal.clone();
        Calendar calTimeTmp = Calendar.getInstance();

        calTimeTmp.setTime(dateFounded);
        calTime.setTime(rootItinerary.getDepartureTime());
        calTime.set(calTimeTmp.get(Calendar.YEAR), calTimeTmp.get(Calendar.MONTH), calTimeTmp.get(Calendar.DAY_OF_MONTH));

        if (calTime.getTime().compareTo(dateFounded) > 0) {
            calTime.add(Calendar.DAY_OF_MONTH, -1);
        }
        rootItinerary.setDepartureTime(calTime.getTime());

        //Validamos la guia padre
        innerRootGuide = guideService.getRepository().findRootGuide(route, rootItinerary.getDepartureTime());
        if (innerRootGuide == null) {
            innerRootGuide = new Guide();
            innerRootGuide.setUsrModify(vendorNick.toUpperCase().trim());
            innerRootGuide.setGuideType(_LOCAL);
            innerRootGuide.setCreateDate(new Date());
            innerRootGuide.setStatus(_GUIDE_TYPE_OPEN);
            innerRootGuide.setGuideReference("Sin Referencia");
            innerRootGuide.setDepartureDate(rootItinerary.getDepartureTime());
            innerRootGuide = this.createRootGuide(innerRootGuide, rootItinerary);
        }

        innerGuide = guideService.getRepository().findByItineraryAndDate(ori, des, dateFounded, route);
        Integer maxLimitCarrie = 0;

        if (innerGuide != null) {
            // Validacion de asientos
            innerGuide.setNewGuide(false);
            maxLimitCarrie = guide.getQuota();

        } else {
            //seat = seatService.getRepository().findAllAvailable();
            innerGuide = new Guide(true);
        }

        //Obtenemos el limite de pasajeros en esa guia
        if (maxLimitCarrie > 0) {
            Pageable topTen = new PageRequest(0, maxLimitCarrie);
            currentSeat = seatService.getRepository().findAllAvailable(topTen);
        } else {
            currentSeat = seatService.getRepository().findAllAvailable();
        }

        Seat occupiedPattern = seatService.getRepository().findOccupiedSeatPattern();
        List<ReservedSeats> reservedSeats = saleService.getReservedSeatsRepository().findAllReserved(innerRootGuide.getRootGuide(), innerRootGuide.getRootRoute());

        for (ReservedSeats reserved : reservedSeats) {

            if (currentSeat.contains(reserved.getSeat())) {
                int index = currentSeat.indexOf(reserved.getSeat());

                if (selectedItinerary.getItinerary().getSequence() == 0
                        && selectedItinerary.isNormalMode()) {
                    currentSeat.set(index, occupiedPattern);
                } else if (reserved.getFinalSequence() == 0) {  //Parten del mismo origen   
                    currentSeat.set(index, occupiedPattern);
                } else if (selectedItinerary.getItinerary().getSequence() == reserved.getInitialSequence()) {
                    currentSeat.set(index, occupiedPattern);
                    //Alt 10  -  Initial 0
                } else if (!selectedItinerary.isNormalMode()
                        && (selectedItinerary.getAlternateItinerary().getSequence() < reserved.getFinalSequence()
                        && selectedItinerary.getAlternateItinerary().getSequence() > reserved.getInitialSequence())) {
                    currentSeat.set(index, occupiedPattern);
                } else if (!selectedItinerary.isNormalMode()
                        && (reserved.getInitialSequence() >= selectedItinerary.getItinerary().getSequence()
                        && selectedItinerary.getAlternateItinerary().getSequence() >= reserved.getFinalSequence())) {
                    currentSeat.set(index, occupiedPattern);
                } else if (!selectedItinerary.isNormalMode()
                        && (reserved.getInitialSequence() <= selectedItinerary.getItinerary().getSequence()
                        && selectedItinerary.getAlternateItinerary().getSequence() >= reserved.getFinalSequence()
                        && reserved.getFinalSequence() > selectedItinerary.getItinerary().getSequence())) {
                    currentSeat.set(index, occupiedPattern);
                }

            }
        }
        
        //Asignamos las guias correspondientes
        if(travelType.equals(_TO_ORIGIN)){
            guideRoot = (Guide)innerRootGuide.clone();
            guide = guideRoot = (Guide)innerGuide.clone();
        }else{
            guideRootToBack = (Guide)innerRootGuide.clone();
            guideToBack = guideRoot = (Guide)innerGuide.clone();
        }

        //Asignamos las guias correspondientes
        if (travelType.equals(_TO_ORIGIN)) {
            guideRoot = (Guide) innerRootGuide.clone();
            guide = (Guide) innerGuide.clone();
        } else {
            guideRootToBack = (Guide) innerRootGuide.clone();
            guideToBack = (Guide) innerGuide.clone();
        }

        return currentSeat;
    }

    /**
     * *
     *
     * @param vendor
     * @param payTypeList
     * @param payRollList
     * @param saveSale
     * @param saveSelected
     * @param saveGuide
     * @param saveRootGuide
     * @param saveDetail
     * @param type
     * @throws java.lang.CloneNotSupportedException
     */
    public void save(Vendor vendor, List<PayType> payTypeList,
            List<PayRoll> payRollList, Sale saveSale, ItineraryCost saveSelected,
            Guide saveGuide, Guide saveRootGuide, List<SaleDetail> saveDetail,
            String type) throws CloneNotSupportedException {

        // Guardamos si hubo venta pendiente.
        if (pendingSale.getId() != null) {
            this.modifyPendingSale(vendor);

            saveSale.setIdRefSale(pendingSale.getId());
            saveSale.setServiceType(_PENDING);
        }
        saveSale.setVendor(vendor);
        saveSale.setStatus(_SALED);
        saveSale.setCreateDate(new Date());

        //Guardamos la venta
        saveSale = saleService.getSaleRepository().saveAndFlush(saveSale);

        // Guardamos la guia
        if (saveGuide.isNewGuide()) {

            saveGuide.setUsrModify(vendor.getNick());
            saveGuide.setCreateDate(new Date());
            saveGuide.setStatus(_GUIDE_TYPE_OPEN);
            saveGuide.setGuideType(_FOREING);
            saveGuide.setGuideReference("Sin Referencia");
            saveGuide.setDepartureDate(saveSale.getTripDate());

            if (saveSelected.isNormalMode()) {

                saveGuide.setRootGuide(0L);
                saveGuide.setRootRoute(saveSelected.getItinerary().getId());
                saveGuide.setOrigin(saveSelected.getCost().getOrigin());
                saveGuide.setDestiny(saveSelected.getCost().getDestiny());
                saveGuide.setFinaDestiny(saveRootGuide.getDestiny());

                guideService.getRepository().saveAndFlush(saveGuide);
                saveGuide.setRootGuide(saveGuide.getId());
                saveGuide.update(saveGuide);
                saveGuide = guideService.getRepository().saveAndFlush(saveGuide);

            } else {

                saveGuide.setOrigin(saveSelected.getCost().getOrigin());
                saveGuide.setDestiny(saveSelected.getCost().getDestiny());
                saveGuide.setRootRoute(saveRootGuide.getRootRoute());
                saveGuide.setRootGuide(saveRootGuide.getId());
                saveGuide.setFinaDestiny(saveRootGuide.getDestiny());
                guideService.getRepository().saveAndFlush(saveGuide);

            }
        }

        /**
         * Guardamos el detalle de la guia
         */
        if (saveGuide != null) {

            GuideDetail guideDetail = new GuideDetail();
            guideDetail.setGuide(saveGuide);
            guideDetail.setSale(saveSale);
            guideService.getDetailRepository().saveAndFlush(guideDetail);

        }
    
        //Guardamos el detalle de la venta
        saveDetailSale(saveDetail, saveSale, saveSelected, saveGuide,
                saveRootGuide);

        //Guardamos los pagos
        if (this.sale.getTravelService().equals(_SALE_ROUNDED_TRAVEL) && type.equals(_TO_ORIGIN)) {
            for (PayType payType : payTypeList) {
                if (payType.getAmount() > 0.0) {
                    Payment payment = new Payment(payType, saveSale);
                    saleService.getPaymentRepository().saveAndFlush(payment);
                }
            }

            //Guardamos el tipo de pago nominal en caso de existir
            for (PayRoll pr : payRollList) {
                if (pr.getAmount() > 0.0) {
                    pr.setSale(saveSale);
                    this.saleService.getPayrollRepository().saveAndFlush(pr);
                }
            }

        } else if (this.sale.getTravelService().equals(_SALE_SINGLE_TRAVEL) && type.equals(_TO_ORIGIN)) {

            for (PayType payType : payTypeList) {
                if (payType.getAmount() > 0.0) {
                    Payment payment = new Payment(payType, saveSale);
                    saleService.getPaymentRepository().saveAndFlush(payment);
                }
            }

            //Guardamos el tipo de pago nominal en caso de existir
            for (PayRoll pr : payRollList) {
                if (pr.getAmount() > 0.0) {
                    pr.setSale(saveSale);
                    this.saleService.getPayrollRepository().saveAndFlush(pr);
                }
            }

        }

        if (this.sale.getTravelService().equals(_SALE_ROUNDED_TRAVEL) && 
                type.equals(_TO_BACK)) {
            GrowlBean.simplyWarmMessage(
                    "Se ha guardado la venta", "Venta guardada con exito!");
        } else if (this.sale.getTravelService().equals(_SALE_SINGLE_TRAVEL) && 
                type.equals(_TO_ORIGIN)) {
            GrowlBean.simplyWarmMessage(
                    "Se ha guardado la venta", "Venta guardada con exito!");
        }

    }

    /**
     *
     * @param innerDetail
     * @param saveSale
     * @param saveSelected
     * @param saveGuide
     * @param saveRootGuide
     */
    public void saveDetailSale(List<SaleDetail> innerDetail, Sale saveSale, ItineraryCost saveSelected,
            Guide saveGuide, Guide saveRootGuide) {

        //Guardamos el detalle de la venta
        for (SaleDetail dtSale : innerDetail) {

            //Guardamos los asientos ocupados            
            ReservedSeats reservedSeat = new ReservedSeats();
            reservedSeat.setGuideId(saveGuide.getRootGuide());
            reservedSeat.setRouteId(saveGuide.getRootRoute());
            reservedSeat.setRouteType(saveSelected.getItinerary().getTypeOfRoute());

            ///Aqui modifique si es local la ruta antes tenia 0 0
            reservedSeat.setInitialSequence(saveSelected.getItinerary().getSequence());
            if (saveSelected.isNormalMode()) {
                reservedSeat.setFinalSequence(saveSelected.getItinerary().getSequence());
            } else {
                reservedSeat.setFinalSequence(saveSelected.getAlternateItinerary().getSequence());
            }
            reservedSeat.setSeat(dtSale.getSeat());

            saleService.getReservedSeatsRepository().saveAndFlush(reservedSeat);

            //Guardamos el detalle de venta
            dtSale.setSale(sale);
            dtSale.setStatus(_SALED);

            if (pendingSale.getId() != null) {
                dtSale.setServiceType(_PENDING);
                dtSale.setIdRefSale(saveSale.getId());
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
            saleService.getDetailRepository().saveAndFlush(dtSale);
        }
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

        if (sale.getTravelService().equals(_SALE_SINGLE_TRAVEL)) {

            //Validamos el monto recibido
            if (amountPayed < sale.getAmount()) {
                GrowlBean.simplyWarmMessage("Monto entregado erroneo", "No es suficiente el monto ingresado");
                msgNav = "toSaleConfirm";
            } else if (amountPayed > sale.getAmount()) {
                GrowlBean.simplyWarmMessage("Monto entregado erroneo", "El monto ingresado es superior al solicitado");
                msgNav = "toSaleConfirm";
            } else {
                this.save(vendor, payTypeList, payRollList, sale, this.selected, guide, guideRoot, saleDetail, _TO_ORIGIN);
                this.clearSale();
            }
        } else if (sale.getTravelService().equals(_SALE_ROUNDED_TRAVEL)) {

            //Validamos el monto recibido de ambos viajes
            if (amountPayed < sale.getAmount() + saleToBack.getAmount()) {
                GrowlBean.simplyWarmMessage("Monto entregado erroneo", "No es suficiente el monto ingresado");
                msgNav = "toSaleConfirm";
            } else if (amountPayed > sale.getAmount() + saleToBack.getAmount()) {
                GrowlBean.simplyWarmMessage("Monto entregado erroneo", "El monto ingresado es superior al solicitado");
                msgNav = "toSaleConfirm";
            } else {
                this.save(vendor, payTypeList, payRollList, sale, this.selected, this.guide, this.guideRoot, saleDetail, _TO_ORIGIN);

                //Actualizamos los valores del detalle de regreso
                for (int i = 0; i < saleDetailToBack.size(); i++) {
                    saleDetailToBack.get(i).updateToRounded(saleDetail.get(i));
                }

                this.save(vendor, payTypeList, payRollList, saleToBack, this.selectedToBack, this.guideToBack,
                        this.guideRootToBack, saleDetailToBack, _TO_BACK);
                this.clearSale();
            }
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
        boolean isError = false;

        if (this.sale.getTravelService().equals(_SALE_ROUNDED_TRAVEL)) {

            if (saleDetail.size() > 0 && saleDetailToBack.size() > 0) {

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
                sale.setPassengers(this.saleDetail.size());
                cantOriginal = sale.getAmount();

                //Now we configure the sale to return
                this.saleToBack.setTripDate(this.selectedToBack.getDepartureTime());
                this.saleToBack.setOrigin(this.selectedToBack.getCost().getOrigin());
                this.saleToBack.setOrigin(this.selectedToBack.getCost().getDestiny());

                subTotal = this.selectedToBack.getCost().getCost() * this.saleDetailToBack.size();
                amount = subTotal - discount;

                this.saleToBack.setSubTotal(subTotal);
                this.saleToBack.setDiscount(discount);
                this.saleToBack.setAmount(amount);
                this.saleToBack.setPassengers(this.saleDetailToBack.size());

            } else {
                isError = true;
                msgNav = "toSale";
            }

        } else if (this.sale.getTravelService().equals(_SALE_SINGLE_TRAVEL)) {

            if (saleDetail.size() > 0) {

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
                isError = true;
                msgNav = "toSale";
            }
        }

        if (isError) {
            GrowlBean.simplyWarmMessage("Asientos incompletos", "No Se han asigando los asientos solicitados");
        }

        return msgNav;
    }

    /**
     * Limpiamos la venta
     */
    public void clearSale() {
        this.sale = new Sale();
        this.saleToBack = new Sale();
        this.sale.setTripDate(new Date());
        this.saleDetail = new ArrayList<SaleDetail>();
        this.saleDetailToBack = new ArrayList<SaleDetail>();
        this.guide = new Guide();
        this.guideToBack = new Guide();
        this.guideRoot = new Guide();
        this.guideRootToBack = new Guide();
        this.selected = new ItineraryCost();
        this.selectedToBack = new ItineraryCost();
        this.pendingSale = new SaleDetail();
    }

    /**
     * Removemos el asiento seleccionado
     *
     * @param type
     * @param selectedInnerSeat
     */
    public void removeSeat(String type, SaleDetail selectedInnerSeat) {

        if (type.equals(_TO_ORIGIN)) {
            if (selectedInnerSeat != null) {
                saleDetail.remove(selectedInnerSeat);
            }
        } else if (type.equals(_TO_BACK)) {
            if (saleDetailToBack != null) {
                saleDetailToBack.remove(selectedInnerSeat);
            }
        }
    }

    /**
     * Agregamos el asiento seleccionado
     *
     * @param type
     * @param selectedInnerSeat
     */
    public void addSeat(String type, Seat selectedInnerSeat) {
        String bolType = "";
        boolean exist = false;

        if (type.equals(_TO_ORIGIN)) {
            if (selectedInnerSeat != null) {
                for (SaleDetail sl : saleDetail) {
                    if (selectedInnerSeat.getSeat().trim().equals(sl.getSeat().getSeat().trim())) {
                        exist = true;
                    }
                }
                if (!exist) {
                    SaleDetail saleDetailTmp = new SaleDetail(this.selected.getCost().getCost(), selectedInnerSeat, new Customer(), associate, bolType);
                    saleDetail.add(saleDetailTmp);
                }
            }
        } else if (type.equals(_TO_BACK)) {
            if (this.selectedSeatToBack != null) {
                for (SaleDetail sl : saleDetailToBack) {
                    if (selectedInnerSeat.getSeat().trim().equals(sl.getSeat().getSeat().trim())) {
                        exist = true;
                    }
                }
                if (!exist) {
                    SaleDetail saleDetailTmp = new SaleDetail(this.selectedToBack.getCost().getCost(), selectedInnerSeat, new Customer(), associate, bolType);
                    saleDetailToBack.add(saleDetailTmp);
                }
            }
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeat(List<Seat> seats) {
        this.seats = seats;
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
        Integer[] arraRight = new Integer[]{2, 6, 10, 14, 18, 22, 26, 30, 34, 38, 42, 46, 50};
        Integer[] arraLeft = new Integer[]{3, 7, 11, 15, 19, 23, 27, 31, 35, 39, 43, 47, 51};

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

            // Descontamos en pagos de la venta
            List<Payment> pmList = saleService.getPaymentRepository().findbySale(sale.getId());
            boolean cobrado = false;
            for (Payment payment : pmList) {
                if (payment.getAmount() >= saleDetailCancelled.getAmount() - saleDetailCancelled.getDiscount() && !cobrado) {
                    payment.setAmount(payment.getAmount() - (saleDetailCancelled.getAmount() - saleDetailCancelled.getDiscount()));
                    saleService.getPaymentRepository().save(payment);
                    cobrado = true;
                }
            }
            //Borramos el elmento de la lista
            this.reservedSeatInDetailSale.remove(selectedReservedSeatInDetailSale);
            GrowlBean.simplyWarmMessage("Se ha cancelado", "Asiento cancelado con exito!");

        } else {
            GrowlBean.simplyWarmMessage("Sin selecciÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³n", "No se ha seleccionado asiento para cancelar!");
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
                    GrowlBean.simplyWarmMessage("GuÃƒÂ­a Cerrada", "La GuÃ­a de viaje ya fue cerrada...");
                    pendingSale = new SaleDetail();
                }
            }
        }
    }

    /**
     * Cancelamos la venta pendiente
     *
     * @param mode
     * @return
     */
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
     * Muestra el diÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡logo de la venta pendiente
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

    public ItineraryCostModel getModelToBack() {
        return modelToBack;
    }

    public void setModelToBack(ItineraryCostModel modelToBack) {
        this.modelToBack = modelToBack;
    }

    public ItineraryCost getSelectedToBack() {
        return selectedToBack;
    }

    public void setSelectedToBack(ItineraryCost selectedToBack) {
        this.selectedToBack = selectedToBack;
    }

    public List<Seat> getSeatsToBack() {
        return seatsToBack;
    }

    public void setSeatsToBack(List<Seat> seatToBack) {
        this.seatsToBack = seatToBack;
    }

    public Seat getSelectedSeatToBack() {
        return selectedSeatToBack;
    }

    public void setSelectedSeatToBack(Seat selectedSeatToBack) {
        this.selectedSeatToBack = selectedSeatToBack;
    }

    public List<SaleDetail> getSaleDetailToBack() {
        return saleDetailToBack;
    }

    public void setSaleDetailToBack(List<SaleDetail> saleDetailToBack) {
        this.saleDetailToBack = saleDetailToBack;
    }

    public Sale getSaleToBack() {
        return saleToBack;
    }

    public void setSaleToBack(Sale saleToBack) {
        this.saleToBack = saleToBack;
    }

}
