/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.ReportsService;
import com.simphony.entities.Sale;
import com.simphony.models.DailySalesModel;
import com.simphony.pojos.DailySales;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ReportsBean  {

    private Sale sale = new Sale();
    private DailySales selectedDS = new DailySales();
    private DailySalesModel modelDS = new DailySalesModel();
    private List<DailySales> listDailySales = new ArrayList<DailySales>();
    
    private Long totMov;
    private Long totAgr;
    private Long totPub;
    private Long totRet;
    private Double  totVta;
    private Double  totEfe;
    private Double  totNom;
    private Double  totCor;
    private Double  totPag;

    @ManagedProperty(value = "#{reportsService}")
    private ReportsService reportsService;

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

        return "toDailySales";
    }

        /**
     * Buscamos itinerarios
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
        finDate.setTime(this.sale.getCreateDate());
        finDate.add(Calendar.HOUR, 23);
        finDate.add(Calendar.MINUTE, 59);
        Date finD = finDate.getTime();

        if (this.sale.getVendor() != null && this.sale.getCreateDate() != null) {
            listDailySales.clear();
            
            listDailySales = reportsService.getReportsRepository().dailySales(this.sale.getVendor().getId(),
                            this.sale.getCreateDate(), finD);

            modelDS = new DailySalesModel(listDailySales);
            Long idAnt = new Long(0);
            for (DailySales dl : listDailySales) {
                totMov += 1;
                totVta = totVta + dl.getPayment().getAmount();
                if(dl.getSale().getId() != idAnt){
                    totAgr = totAgr + dl.getDetAssociates();
                    totPub = totPub + dl.getDetPublico();
                    totRet = totRet + dl.getDetRetires();
                    idAnt = dl.getSale().getId();
                } 
                if(dl.getPayment().getPayType().getId() == 1){
                    totEfe = totEfe + dl.getPayment().getAmount();
                }
                if(dl.getPayment().getPayType().getId() == 2){
                    totNom = totNom + dl.getPayment().getAmount();
                }
                if(dl.getPayment().getPayType().getId() == 3){
                    totPag = totPag + dl.getPayment().getAmount();
                }
                if(dl.getPayment().getPayType().getId() == 4){
                    totCor = totCor + dl.getPayment().getAmount();
                }            }      
        } else {
                GrowlBean.simplyErrorMessage("Error de datos", "Falta Asesor de Venta o fecha...");
        }

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

    
}
