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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * Buscamos ingresos por autobÃºs
     *
     * @throws java.text.ParseException
     */
    public void findBusIncome() throws ParseException, CloneNotSupportedException {
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
     * Buscamos ingresos por operador
     *
     * @throws java.text.ParseException
     */
    public void findDriverManIncome() throws ParseException, CloneNotSupportedException {
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

    public void viewSalesReport() throws JRException, ClassNotFoundException {
        jasperService.setReportName("Tarifas.jasper");
        jasperService.builtReport(_REPORT_PDF);
    }

    public void viewGuidesReport() throws JRException, ClassNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.sql.Date frm_dte = null;
        java.sql.Time frm_time = null;
        
        try {
            frm_dte = (java.sql.Date) sdf.parse("2014-10-15 05:00:00");
            frm_time = (java.sql.Time) sdf.parse("05:00:00");
        } catch (ParseException ex) {
            Logger.getLogger(ReportsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fechaSalida", frm_dte);
        parameters.put("hora", frm_time);
        
        jasperService.setReportName("guide.jasper");
        jasperService.builtReport(parameters, _REPORT_PDF);
    }

    public void viewSaleDetailReport(Long vendorId, Date fIni, Date fFin) throws JRException, ClassNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.sql.Date frm_dte1 = null;
        java.sql.Date frm_dte2 = null;
        java.sql.Time frm_time = null;
        
 //       try {
 //           frm_dte1 = (java.sql.Date) sdf.parse(fIni.toString());
 //           frm_dte2 = (java.sql.Date) sdf.parse(fFin.toString());
 //           frm_time = (java.sql.Time) sdf.parse("05:00:00");
 //       } catch (ParseException ex) {
 //           Logger.getLogger(ReportsBean.class.getName()).log(Level.SEVERE, null, ex);
 //       }
        
        Calendar finDate = Calendar.getInstance();
        finDate.setTime(this.fecFin);
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();
        BigDecimal id = new BigDecimal(vendorId);

        if (this.sale.getVendor().getId() != null && this.sale.getCreateDate() != null) {
        
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("user", id);
            parameters.put("fecIni", fIni);
            parameters.put("fecFin", fFin );

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
