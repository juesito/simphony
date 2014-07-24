/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.managedbeans;

import com.simphony.beans.CostService;
import com.simphony.entities.Cost;
import com.simphony.entities.Sale;
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
    
    @ManagedProperty(value = "#{costService}")
    private CostService costService;
    
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
    
    public void findItinearies(){
        cost = costService.getCostRepository().findByOriDes(sale.getOrigin().getId(), sale.getDestiny().getId());
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }
    
    
    
}
