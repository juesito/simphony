/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.CostService;
import com.simphony.beans.SaleService;
import com.simphony.entities.Cost;
import com.simphony.entities.Sale;
import com.simphony.pojos.ItineraryCost;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class SaleBean {

    private Sale sale = new Sale();
    private Cost cost = new Cost();

    private List<Sale> list = new ArrayList();
    private List<Object[]> objects = new ArrayList<Object[]>();
    private List<ItineraryCost> itineraryCost = new ArrayList<ItineraryCost>();

    @ManagedProperty(value = "#{costService}")
    private CostService costService;

    @ManagedProperty(value = "#{saleService}")
    private SaleService saleService;

    /**
     * Creates a new instance of SaleBean
     */
    public SaleBean() {
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
    
    

}
