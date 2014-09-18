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

        if (this.sale.getVendor() != null && this.sale.getCreateDate() != null) {
            listDailySales.clear();

            listDailySales = reportsService.getReportsRepository().dailySales(this.sale.getVendor().getId());

                modelDS = new DailySalesModel(listDailySales);

            } else {
                GrowlBean.simplyErrorMessage("Error de datos", "Falta vendedor o fecha...");
            }

    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    
}
