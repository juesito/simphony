/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.JasperService;
import com.simphony.beans.ReportsService;
import com.simphony.entities.Bus;
import com.simphony.entities.Guide;
import com.simphony.entities.Sale;
import com.simphony.entities.SalePoint;
import com.simphony.entities.Vendor;
import com.simphony.interfases.IConfigurable;
import com.simphony.models.DailySalesModel;
import com.simphony.pojos.DailySales;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ReportsBean implements IConfigurable {

    private Sale sale = new Sale();
    private Guide guide = new Guide();
    private DailySales selectedDS = new DailySales();
    private DailySalesModel modelDS = new DailySalesModel();
    private List<DailySales> listDailySales = new ArrayList<DailySales>();
    private List<DailySales> listTemp = new ArrayList<DailySales>();
    private SalePoint salePointTmp;
    private Bus bus = new Bus();

    private Date fecIni = new Date();
    private Date fecFin = new Date();
    private Long totMov;
    private Long totAgr;
    private Long totPub;
    private Long totRet;
    private Double totVta;
    private Double totEfe;
    private Double totNom;
    private Double totCor;
    private Double totPag;
    private int totKms;
    private Long totPassengers = (long) 0;

    @ManagedProperty(value = "#{reportsService}")
    private ReportsService reportsService;

    @ManagedProperty(value = "#{jasperService}")
    private JasperService jasperService;

    /**
     * Creates a new instance of SaleBean
     */
    public ReportsBean() {
    }

    @PostConstruct
    void init() {

    }

    public ReportsService getReportsService() {
        return reportsService;
    }

    public void setReportsService(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    public List<DailySales> getListDailySales() {
        return listDailySales;
    }

    public void setListDailySales(List<DailySales> listDailySales) {
        this.listDailySales = listDailySales;
    }

    public DailySales getSelectedDS() {
        return selectedDS;
    }

    public void setSelectedDS(DailySales selectedDS) {
        this.selectedDS = selectedDS;
    }

    public DailySalesModel getModelDS() {
        return modelDS;
    }

    public void setModelDS(DailySalesModel modelDS) {
        this.modelDS = modelDS;
    }

    public String toDailySales() {
        this.clearReports();
        return "toDailySales";
    }

    public String toDailySalesPoint() {
        this.clearReports();
        return "toDailySalesPoint";
    }

    public String toBusIncome() {
        this.clearReports();
        return "toBusIncome";
    }

    public String toDriverManIncome() {
        this.clearReports();
        return "toDriverManIncome";
    }

    public String toTotalSales() {
        this.clearReports();
        return "toTotalSales";
    }

    public String toWeekSales() {
        this.clearReports();
        return "toWeekSales";
    }

    /**
     * Buscamos ventas diarias
     *
     * @throws java.text.ParseException
     */
    public void findDailySales() throws ParseException {
        totMov = new Long(0);
        totAgr = new Long(0);
        totPub = new Long(0);
        totRet = new Long(0);
        totVta = 0.0;
        totEfe = 0.0;
        totNom = 0.0;
        totCor = 0.0;
        totPag = 0.0;
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(this.fecFin);
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();

        if (this.sale.getVendor() != null && this.sale.getCreateDate() != null) {
            listDailySales.clear();

            listDailySales = reportsService.getReportsRepository().dailySales(this.sale.getVendor().getId(),
                    this.sale.getCreateDate(), finD);

            modelDS = new DailySalesModel(listDailySales);
            Long idAnt = (long) 0;
            for (DailySales dl : listDailySales) {
                totMov += 1;
                totVta = totVta + dl.getPayment().getAmount();
                if (dl.getSale().getId() != idAnt) {
                    totAgr = totAgr + dl.getDetAssociates();
                    totPub = totPub + dl.getDetPublico();
                    totRet = totRet + dl.getDetRetires();
                    idAnt = dl.getSale().getId();
                }
                if (dl.getPayment().getPayType().getId() == 1) {
                    totNom = totNom + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 2) {
                    totEfe = totEfe + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 3) {
                    totCor = totCor + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 4) {
                    totPag = totPag + dl.getPayment().getAmount();
                }
            }
        } else {
            GrowlBean.simplyErrorMessage("Error de datos", "Falta Asesor de Venta o fecha...");
        }

    }

    /**
     * Buscamos ventas diarias
     *
     * @throws java.text.ParseException
     */
    public void findDailySalesPoint() throws ParseException {
        totMov = new Long(0);
        totAgr = new Long(0);
        totPub = new Long(0);
        totRet = new Long(0);
        totVta = 0.0;
        totEfe = 0.0;
        totNom = 0.0;
        totCor = 0.0;
        totPag = 0.0;
        Double usrEfe = 0.0;
        Double usrNom = 0.0;
        Double usrCor = 0.0;
        Double usrPag = 0.0;
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(this.sale.getCreateDate());
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();

        if (this.salePointTmp.getId() != null && this.sale.getCreateDate() != null) {
            listDailySales.clear();

            listDailySales = reportsService.getReportsRepository().dailySalesPoint(this.salePointTmp.getId(),
                    this.sale.getCreateDate(), finD);

            listTemp.clear();
            Long idVendor = (long) 0;
            Long idAnt = (long) 0;
            DailySales dx = new DailySales();
            Sale s = new Sale();
            Vendor v = new Vendor();
            boolean unaVez = true;
            String vrNick = "";
            for (DailySales dl : listDailySales) {
                if (unaVez) {
                    idVendor = dl.getSale().getVendor().getId();
                    vrNick = dl.getSale().getVendor().getNick();
                    dx = dl;
                    unaVez = false;
                }
                if (idVendor != dl.getSale().getVendor().getId()) {
                    s = dx.getSale();
                    s.setAmount(usrEfe);
                    s.setDiscount(usrNom);
                    s.setSubTotal(usrPag);
                    listTemp.add(dx);
                    idVendor = dl.getSale().getVendor().getId();
                    vrNick = dl.getSale().getVendor().getNick();
                    dx = dl;
                    usrEfe = 0.0;
                    usrNom = 0.0;
                    usrPag = 0.0;
                }
                totMov += 1;
                totVta = totVta + dl.getPayment().getAmount();
                if (dl.getSale().getId() != idAnt) {
                    totAgr = totAgr + dl.getDetAssociates();
                    totPub = totPub + dl.getDetPublico();
                    totRet = totRet + dl.getDetRetires();
                    idAnt = dl.getSale().getId();
                }
                if (dl.getPayment().getPayType().getId() == 1) {
                    usrNom = usrNom + dl.getPayment().getAmount();
                    totNom = totNom + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 2) {
                    usrEfe = usrEfe + dl.getPayment().getAmount();
                    totEfe = totEfe + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 3) {
                    totCor = totCor + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 4) {
                    usrPag = usrPag + dl.getPayment().getAmount();
                    totPag = totPag + dl.getPayment().getAmount();
                }
            }
            s = dx.getSale();
            s.setAmount(usrEfe);
            s.setDiscount(usrNom);
            s.setSubTotal(usrPag);
            dx.setSale(s);
            listTemp.add(dx);
            modelDS = new DailySalesModel(listTemp);
        } else {
            GrowlBean.simplyErrorMessage("Error de datos", "Falta Punto de Venta o fecha...");
        }

    }

    /**
     * Buscamos ingresos por autobÃƒÂºs
     *
     * @throws java.text.ParseException
     */
    public void findBusIncome() throws ParseException, CloneNotSupportedException {
        clearVar();
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(this.fecFin);
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();

        if (this.guide.getBus().getId() != null && this.fecIni != null && this.fecFin != null) {
            listDailySales.clear();
            List<DailySales> c = reportsService.getReportsRepository().busIncome(this.guide.getBus().getId(),
                    this.fecIni, finD);
            if (c.size() > 0) {
                Date depDate = new Date();
                Long idOrigin = (long) 0;
                Long idRoute = (long) 0;
                Double income = 0.0;
                DailySales dsAnt = new DailySales();
                int vrIndex = 0;
                for (DailySales ds : c) {
                    if (ds.getGuide().getDepartureDate().equals(depDate) && ds.getGuide().getOrigin().getId().equals(idOrigin)
                            && ds.getGuide().getRootRoute().equals(idRoute)) {
                        if (ds.getDetIncome() != null) {
                            income = income + ds.getDetIncome();
                        }
                    } else {
                        depDate = ds.getGuide().getDepartureDate();
                        idOrigin = ds.getGuide().getOrigin().getId();
                        idRoute = ds.getGuide().getRootRoute();
                        if (vrIndex != 0) {
                            dsAnt.setDetIncome(income);
                            listDailySales.set(vrIndex - 1, dsAnt);
                            totVta = totVta + income;
                            income = 0.0;
                        }
                        dsAnt = (DailySales) ds.clone();
                        if (ds.getDetIncome() != null) {
                            income = income + ds.getDetIncome();
                        }
                        vrIndex += 1;
                        listDailySales.add(ds);
                    }
                }
                dsAnt.setDetIncome(income);
                totVta = totVta + income;
                listDailySales.set(vrIndex - 1, dsAnt);
            }
            modelDS = new DailySalesModel(listDailySales);
        } else {
            GrowlBean.simplyErrorMessage("Error de datos", "Falta AutobÃºs o fechas...");
        }

    }

    /**
     * Buscamos ingresos Totales
     *
     * @throws java.text.ParseException
     */
    public void findTotalSales() throws ParseException, CloneNotSupportedException {
        clearVar();
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(this.fecFin);
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();

        if (this.fecIni != null && this.fecFin != null) {
            listDailySales.clear();
            DailySales dsAnt = new DailySales();
            List<DailySales> c = reportsService.getReportsRepository().totalSales(this.fecIni, finD);
            if (c.size() > 0) {
                int lDay = 0;
                Double detNom = 0.0;
                Double detEfe = 0.0;
                Double detCor = 0.0;
                Double detPag = 0.0;
                Double detTot = 0.0;
                Long detPass = (long) 0;
                totVta = 0.0;
                totEfe = 0.0;
                totPassengers = new Long(0);
                int vrIndex = 0;
                for (DailySales ds : c) {
                    if (ds.getrDay() != lDay && vrIndex != 0) {
                        dsAnt.setrDay(lDay);
                        dsAnt.setrTotNom(detNom);
                        dsAnt.setrTotEfe(detEfe);
                        dsAnt.setrTotCor(detCor);
                        dsAnt.setrTotPag(detPag);
                        dsAnt.setrTotVta(detTot);
                        dsAnt.setPassengers(detPass);
                        listDailySales.add(dsAnt);
                        dsAnt = new DailySales();
                        lDay = ds.getrDay();
                        detNom = 0.0;
                        detEfe = 0.0;
                        detCor = 0.0;   
                        detPag = 0.0;
                        detTot = 0.0;
                        detPass = new Long(0);
                   }
                    switch (ds.getDetAssociates().intValue()) {
                        case 1:
                            detNom = detNom + ds.getDetNom();
                            totNom = totNom + ds.getDetNom();
                            break;
                        case 2:
                            detEfe = detEfe + ds.getDetNom();
                            totEfe = totEfe + ds.getDetNom();
                            break;
                        case 3:
                            detCor = detCor + ds.getDetNom();
                            totCor = totCor + ds.getDetNom();
                            break;
                        case 4:
                            detPag = detPag + ds.getDetNom();
                            totPag = totPag + ds.getDetNom();
                            break;
                    }
                    detTot = detTot + ds.getDetNom();
                    totVta = totVta + ds.getDetNom();
                    detPass = detPass + ds.getPassengers();
                    totPassengers = totPassengers + ds.getPassengers();
                    if (vrIndex == 0) {
                        lDay = ds.getrDay();
                        vrIndex += 1;
                    }
                }

                dsAnt.setrDay(lDay);
                dsAnt.setrTotNom(detNom);
                dsAnt.setrTotEfe(detEfe);
                dsAnt.setrTotCor(detCor);
                dsAnt.setrTotPag(detPag);
                dsAnt.setrTotVta(detTot);
                dsAnt.setPassengers(detPass);
                listDailySales.add(dsAnt);
            }
            modelDS = new DailySalesModel(listDailySales);
        } else {
            GrowlBean.simplyErrorMessage("Error de datos", "Falta fechas...");
        }

    }

    /**
     * Buscamos ingresos Totales
     *
     * @throws java.text.ParseException
     */
    public void findWeekSales() throws ParseException, CloneNotSupportedException {
        clearVar();
        Calendar fecQuery = Calendar.getInstance();
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(this.fecFin);
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();
        int dayWeek = 0;

        if (this.fecIni != null && this.fecFin != null) {
            listDailySales.clear();
            DailySales dsAnt = new DailySales();
            listDailySales.add(new DailySales());
            listDailySales.add(new DailySales());
            listDailySales.add(new DailySales());
            listDailySales.add(new DailySales());
            listDailySales.add(new DailySales());
            listDailySales.add(new DailySales());
            listDailySales.add(new DailySales());
            List<DailySales> c = reportsService.getReportsRepository().weekSales(this.fecIni, finD);
            if (c.size() > 0) {
                int lDay = 0;
                for (DailySales ds : c) {
                    finDate.setTime(ds.getRFec());
                    dayWeek = finDate.get(Calendar.DAY_OF_WEEK) - 1;
                    if (dayWeek == 0) {
                        dayWeek = 7;
                    }
                    dsAnt = (DailySales) listDailySales.get(dayWeek-1).clone();
                    switch (ds.getDetAssociates().intValue()) {
                        case 1:
                            dsAnt.setrTotNom(dsAnt.getrTotNom() + ds.getDetNom());
                            totNom = totNom + ds.getDetNom();
                            break;
                        case 2:
                            dsAnt.setrTotEfe(dsAnt.getrTotEfe() + ds.getDetNom());
                            totEfe = totEfe + ds.getDetNom();
                            break;
                        case 3:
                            dsAnt.setrTotCor(dsAnt.getrTotCor() + ds.getDetNom());
                            totCor = totCor + ds.getDetNom();
                            break;
                        case 4:
                            dsAnt.setrTotPag(dsAnt.getrTotPag() + ds.getDetNom());
                            totPag = totPag + ds.getDetNom();
                            break;
                    }
                    totVta = totVta + ds.getDetNom();
                    dsAnt.setPassengers(dsAnt.getPassengers() + ds.getPassengers());
                    totPassengers = totPassengers + ds.getPassengers();
                    dsAnt.setrDay(dayWeek);
                    dsAnt.setrTotVta(dsAnt.getrTotVta() + ds.getDetNom());
                    listDailySales.set(dayWeek-1, dsAnt);
                }
            }
            modelDS = new DailySalesModel(listDailySales);
        } else {
            GrowlBean.simplyErrorMessage("Error de datos", "Falta fecha...");
        }

    }

    /**
     * Buscamos ingresos por operador
     *
     * @throws java.text.ParseException
     */
    public void findDriverManIncome() throws ParseException, CloneNotSupportedException {
        clearVar();
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(this.fecFin);
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();

        if (this.guide.getDriverMan1().getId() != null && this.fecIni != null && this.fecFin != null) {
            listDailySales.clear();
            List<DailySales> c = reportsService.getReportsRepository().driverManIncome(this.guide.getDriverMan1().getId(),
                    this.fecIni, finD);
            if (c.size() > 0) {
                Date depDate = new Date();
                Long idOrigin = (long) 0;
                Long idRoute = (long) 0;
                Double income = 0.0;
                totKms = 0;
                DailySales dsAnt = new DailySales();
                int vrIndex = 0;
                for (DailySales ds : c) {
                    if (ds.getGuide().getDepartureDate().equals(depDate) && ds.getGuide().getOrigin().getId().equals(idOrigin)
                            && ds.getGuide().getRootRoute().equals(idRoute)) {
                        if (ds.getDetIncome() != null) {
                            income = income + ds.getDetIncome();
                        }
                    } else {
                        depDate = ds.getGuide().getDepartureDate();
                        idOrigin = ds.getGuide().getOrigin().getId();
                        idRoute = ds.getGuide().getRootRoute();
                        if (vrIndex != 0) {
                            dsAnt.setDetIncome(income);
                            listDailySales.set(vrIndex - 1, dsAnt);
                            totVta = totVta + income;
                            income = 0.0;
                        }
                        dsAnt = (DailySales) ds.clone();
                        if (ds.getDetIncome() != null) {
                            income = income + ds.getDetIncome();
                        }
                        vrIndex += 1;
                        totKms = totKms + ds.getKms();
                        listDailySales.add(ds);
                    }
                }
                dsAnt.setDetIncome(income);
                totVta = totVta + income;
                totKms = totKms + dsAnt.getKms();
                listDailySales.set(vrIndex - 1, dsAnt);
            }
            modelDS = new DailySalesModel(listDailySales);
        } else {
            GrowlBean.simplyErrorMessage("Error de datos", "Falta AutobÃºs o fechas...");
        }

    }

    /**
     * Buscamos ventas diarias
     *
     * @throws java.text.ParseException
     */
    public void findDailySalesMonth() throws ParseException {
        clearVar();
        totMov = new Long(0);
        totAgr = new Long(0);
        totPub = new Long(0);
        totRet = new Long(0);
        totVta = 0.0;
        totEfe = 0.0;
        totNom = 0.0;
        totCor = 0.0;
        totPag = 0.0;
        Double usrEfe = 0.0;
        Double usrNom = 0.0;
        Double usrCor = 0.0;
        Double usrPag = 0.0;
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(this.sale.getCreateDate());
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();

        if (this.salePointTmp.getId() != null && this.sale.getCreateDate() != null) {
            listDailySales.clear();

            listDailySales = reportsService.getReportsRepository().dailySalesPoint(this.salePointTmp.getId(),
                    this.sale.getCreateDate(), finD);

            listTemp.clear();
            Long idVendor = (long) 0;
            Long idAnt = (long) 0;
            DailySales dx = new DailySales();
            Sale s = new Sale();
            Vendor v = new Vendor();
            boolean unaVez = true;
            String vrNick = "";
            for (DailySales dl : listDailySales) {
                if (unaVez) {
                    idVendor = dl.getSale().getVendor().getId();
                    vrNick = dl.getSale().getVendor().getNick();
                    dx = dl;
                    unaVez = false;
                }
                if (idVendor != dl.getSale().getVendor().getId()) {
                    s = dx.getSale();
                    s.setAmount(usrEfe);
                    s.setDiscount(usrNom);
                    s.setSubTotal(usrPag);
                    listTemp.add(dx);
                    idVendor = dl.getSale().getVendor().getId();
                    vrNick = dl.getSale().getVendor().getNick();
                    dx = dl;
                    usrEfe = 0.0;
                    usrNom = 0.0;
                    usrPag = 0.0;
                }
                totMov += 1;
                totVta = totVta + dl.getPayment().getAmount();
                if (dl.getSale().getId() != idAnt) {
                    totAgr = totAgr + dl.getDetAssociates();
                    totPub = totPub + dl.getDetPublico();
                    totRet = totRet + dl.getDetRetires();
                    idAnt = dl.getSale().getId();
                }
                if (dl.getPayment().getPayType().getId() == 1) {
                    usrEfe = usrEfe + dl.getPayment().getAmount();
                    totEfe = totEfe + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 2) {
                    usrNom = usrNom + dl.getPayment().getAmount();
                    totNom = totNom + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 3) {
                    totCor = totCor + dl.getPayment().getAmount();
                }
                if (dl.getPayment().getPayType().getId() == 4) {
                    usrPag = usrPag + dl.getPayment().getAmount();
                    totPag = totPag + dl.getPayment().getAmount();
                }
            }
            s = dx.getSale();
            s.setAmount(usrEfe);
            s.setDiscount(usrNom);
            s.setSubTotal(usrPag);
            dx.setSale(s);
            listTemp.add(dx);
            modelDS = new DailySalesModel(listTemp);
        } else {
            GrowlBean.simplyErrorMessage("Error de datos", "Falta Punto de Venta o fecha...");
        }

    }

    public void clearReports() {
        listTemp.clear();
        listDailySales.clear();
        sale = new Sale();
        salePointTmp = new SalePoint();
        totMov = new Long(0);
        totAgr = new Long(0);
        totPub = new Long(0);
        totRet = new Long(0);
        totPassengers = new Long(0);
        totVta = 0.0;
        totEfe = 0.0;
        totNom = 0.0;
        totCor = 0.0;
        totPag = 0.0;
        totKms = 0;
        guide = new Guide();
        fecIni = new Date();
        fecFin = new Date();
    }
    
    public void clearVar() {
        totMov = new Long(0);
        totAgr = new Long(0);
        totPub = new Long(0);
        totRet = new Long(0);
        totPassengers = new Long(0);
        totVta = 0.0;
        totEfe = 0.0;
        totNom = 0.0;
        totCor = 0.0;
        totPag = 0.0;
        totKms = 0;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Long getTotMov() {
        return totMov;
    }

    public void setTotMov(Long totMov) {
        this.totMov = totMov;
    }

    public Long getTotAgr() {
        return totAgr;
    }

    public void setTotAgr(Long totAgr) {
        this.totAgr = totAgr;
    }

    public Long getTotPub() {
        return totPub;
    }

    public void setTotPub(Long totPub) {
        this.totPub = totPub;
    }

    public Double getTotVta() {
        return totVta;
    }

    public void setTotVta(Double totVta) {
        this.totVta = totVta;
    }

    public Double getTotEfe() {
        return totEfe;
    }

    public void setTotEfe(Double totEfe) {
        this.totEfe = totEfe;
    }

    public Double getTotNom() {
        return totNom;
    }

    public void setTotNom(Double totNom) {
        this.totNom = totNom;
    }

    public Double getTotCor() {
        return totCor;
    }

    public void setTotCor(Double totCor) {
        this.totCor = totCor;
    }

    public Double getTotPag() {
        return totPag;
    }

    public void setTotPag(Double totPag) {
        this.totPag = totPag;
    }

    public Long getTotRet() {
        return totRet;
    }

    public void setTotRet(Long totRet) {
        this.totRet = totRet;
    }

    public List<DailySales> getListTemp() {
        return listTemp;
    }

    public void setListTemp(List<DailySales> listTemp) {
        this.listTemp = listTemp;
    }

    public SalePoint getSalePointTmp() {
        return salePointTmp;
    }

    public void setSalePointTmp(SalePoint salePointTmp) {
        this.salePointTmp = salePointTmp;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public int getTotKms() {
        return totKms;
    }

    public void setTotKms(int totKms) {
        this.totKms = totKms;
    }

    public Long getTotPassengers() {
        return totPassengers;
    }

    public void setTotPassengers(Long totPassengers) {
        this.totPassengers = totPassengers;
    }

    public void viewSalesReport() throws JRException, ClassNotFoundException {
        jasperService.setReportName("Tarifas.jasper");
        jasperService.builtReport(_REPORT_PDF);
    }

    public void viewGuidesReport(Date fecSal, Long rootId) throws JRException, ClassNotFoundException {
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("fechaSalida", fecSal);
            parameters.put("rootId", rootId);

            jasperService.setReportName("guide.jasper");
            jasperService.builtReport(parameters, _REPORT_PDF);
    }
    
    public void reportTest() throws JRException, ClassNotFoundException{
        jasperService.setReportName("ticket.jasper");
        Long id_vta = 23L;
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id_vta", id_vta);
        //jasperService.fillTicket();
        jasperService.printTicket(parameters);
    }
    

    public void viewSaleDetailReport(Long vendorId, Date fIni, Date fFin) throws JRException, ClassNotFoundException {
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(fFin);
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();

        if (this.sale.getVendor().getId() != null && this.sale.getCreateDate() != null) {

            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("user", vendorId);
            parameters.put("fecIni", fIni);
            parameters.put("fecFin", finD);

            jasperService.setReportName("DetailSale.jasper");
            jasperService.builtReport(parameters, _REPORT_PDF);
        } else {
            GrowlBean.simplyErrorMessage("Error de datos", "Falta Vendedor o fecha...");
        }
    }

    public void setJasperService(JasperService jasperService) {
        this.jasperService = jasperService;
    }

}
