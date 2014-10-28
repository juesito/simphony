/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.AssociateService;
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
import com.simphony.entities.Payment;
import com.simphony.entities.ReservedSeats;
import com.simphony.entities.Sale;
import com.simphony.entities.SaleDetail;
import com.simphony.entities.Seat;
import com.simphony.entities.User;
import com.simphony.entities.Vendor;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._CANCELLED;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.TabChangeEvent;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author oj19edal
 */
@ManagedBean
@SessionScoped
public class ReservationsBean implements IConfigurable {
 
    private Sale sale = new Sale();
    private Sale cancelledSale = new Sale();
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
    private List<Seat> selectedSeats = new ArrayList<Seat>();
    List<ReservedSeatInDetailSale> reservedSeatInDetailSale = new ArrayList();
    private List<SaleDetail> saleDetail = new ArrayList<SaleDetail>();
    private List<ItineraryCost> itineraryCost = new ArrayList<ItineraryCost>();

    @ManagedProperty(value = "#{saleService}")
    private SaleService saleService;
   
    @ManagedProperty(value = "#{itineraryService}")
    private ItineraryService itineraryService;

    @ManagedProperty(value = "#{guideService}")
    private GuideService guideService;

    @ManagedProperty(value = "#{seatService}")
    private SeatService seatService;

    @ManagedProperty(value = "#{associateService}")
    private AssociateService associateService;

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

                Calendar calTime = (Calendar) cal.clone();

                calTime.setTime(rootItinerary.getDepartureTime());
                calTime.set(calTimeTmp.get(Calendar.YEAR), calTimeTmp.get(Calendar.MONTH), calTimeTmp.get(Calendar.DAY_OF_MONTH));

//                if (calTime.getTime().compareTo(this.sale.getTripDate()) > 0) {
//                    calTime.add(Calendar.DAY_OF_MONTH, -1);
//                }

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
                model = new ItineraryCostModel(lastItinerary);

                sale.setExistRoutes(lastItinerary.size() > 0);
                sale.setAvailability(false);
            } else {
                GrowlBean.simplyErrorMessage("No se encontraron rutas", "Rutas no disponibles");
            }
        } else {
            GrowlBean.simplyErrorMessage("No se encontro", "Rutas no encontradas");
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

    public void removeSeat() {
        if (unSelectedDetail != null) {
            saleDetail.remove(unSelectedDetail);
        }
    }

        /**
     * *
     *
     * @param vendor
     * @throws java.lang.CloneNotSupportedException
     */
    public String saveReservations(User user) throws CloneNotSupportedException {

//        sale.setVendor(user);
        sale.setStatus(_RESERVATED);
        sale.setCreateDate(new Date());

        //Guardamos la venta
        sale = saleService.getSaleRepository().save(sale);

        // Guardamos la guia
        if (guide.isNewGuide()) {

            guide.setUsrModify(user.getNick());
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
     * Limpiamos la venta
     */
    public void clearSale() {
        this.sale = new Sale();
        this.sale.setTripDate(new Date());
        this.saleDetail = new ArrayList<SaleDetail>();
        this.guide = new Guide();
        this.guideRoot = new Guide();
        this.selected = new ItineraryCost();
    }

    public String toCancelRes() {
        cancelledSale = new Sale();
        unSelectedDetail = new SaleDetail();
        reservedSeatInDetailSale = new ArrayList();
        selectedReservedSeatInDetailSale = new ReservedSeatInDetailSale();
        return "toCancelRes";
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

        public String toReservations() {
        sale = new Sale();
        cancelledSale = new Sale();
        unSelectedDetail = new SaleDetail();
        reservedSeatInDetailSale = new ArrayList();
        selectedReservedSeatInDetailSale = new ReservedSeatInDetailSale();
        return "toReservations";
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
//            sale.setDiscount(sale.getDiscount() - saleDetailCancelled.getDiscount());
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
            GrowlBean.simplyWarmMessage("Sin selección", "No se ha seleccionado asiento para cancelar!");
        }
        return toReturn;

    }

    /**
     * Agregamos el asiento seleccionado
     */
    public void addSeat() {
        String bolType = "";
        boolean exist = false;
        if (this.selectedSeat != null) {
            for (SaleDetail sl : saleDetail) {
                if (this.selectedSeat.getSeat().trim().equals(sl.getSeat().getSeat().trim())) {
                    exist = true;
                }
            }
            if (!exist) {
                SaleDetail saleDetailTmp = new SaleDetail(this.selected.getCost().getCost(), selectedSeat, new Customer(), associate, bolType);
                saleDetail.add(saleDetailTmp);
                this.sale.setPassengers(this.sale.getPassengers() + 1);
            }
        }
    }

    public void onTabChange(TabChangeEvent event) {
       reservedSeatInDetailSale.clear();
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Sale getCancelledSale() {
        return cancelledSale;
    }

    public void setCancelledSale(Sale cancelledSale) {
        this.cancelledSale = cancelledSale;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public Guide getGuideRoot() {
        return guideRoot;
    }

    public void setGuideRoot(Guide guideRoot) {
        this.guideRoot = guideRoot;
    }

    public Seat getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(Seat selectedSeat) {
        this.selectedSeat = selectedSeat;
    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public ItineraryCost getSelected() {
        return selected;
    }

    public void setSelected(ItineraryCost selected) {
        this.selected = selected;
    }

    public SaleDetail getUnSelectedDetail() {
        return unSelectedDetail;
    }

    public void setUnSelectedDetail(SaleDetail unSelectedDetail) {
        this.unSelectedDetail = unSelectedDetail;
    }

    public SaleDetail getSaleDetailSelected() {
        return saleDetailSelected;
    }

    public void setSaleDetailSelected(SaleDetail saleDetailSelected) {
        this.saleDetailSelected = saleDetailSelected;
    }

    public ItineraryCostModel getModel() {
        return model;
    }

    public void setModel(ItineraryCostModel model) {
        this.model = model;
    }

    public ReservedSeatInDetailSale getSelectedReservedSeatInDetailSale() {
        return selectedReservedSeatInDetailSale;
    }

    public void setSelectedReservedSeatInDetailSale(ReservedSeatInDetailSale selectedReservedSeatInDetailSale) {
        this.selectedReservedSeatInDetailSale = selectedReservedSeatInDetailSale;
    }

    public List<Seat> getSeat() {
        return seat;
    }

    public void setSeat(List<Seat> seat) {
        this.seat = seat;
    }

    public List<Sale> getList() {
        return list;
    }

    public void setList(List<Sale> list) {
        this.list = list;
    }

    public List<Seat> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<Seat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public List<ReservedSeatInDetailSale> getReservedSeatInDetailSale() {
        return reservedSeatInDetailSale;
    }

    public void setReservedSeatInDetailSale(List<ReservedSeatInDetailSale> reservedSeatInDetailSale) {
        this.reservedSeatInDetailSale = reservedSeatInDetailSale;
    }

    public List<SaleDetail> getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(List<SaleDetail> saleDetail) {
        this.saleDetail = saleDetail;
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

    public ItineraryService getItineraryService() {
        return itineraryService;
    }

    public void setItineraryService(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    public GuideService getGuideService() {
        return guideService;
    }

    public void setGuideService(GuideService guideService) {
        this.guideService = guideService;
    }

    public SeatService getSeatService() {
        return seatService;
    }

    public void setSeatService(SeatService seatService) {
        this.seatService = seatService;
    }

    public AssociateService getAssociateService() {
        return associateService;
    }

    public void setAssociateService(AssociateService associateService) {
        this.associateService = associateService;
    }

    
}
