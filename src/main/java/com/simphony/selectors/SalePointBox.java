/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.SalePointService;
import com.simphony.entities.SalePoint;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

/**
 *
 * @author Soporte IT
 */
@ManagedBean(name = "boxSalePointService", eager = true)
@ApplicationScoped
public class SalePointBox {

    @ManagedProperty(value = "#{salePointService}")
    private SalePointService salePointService;
    
    private List<SalePoint> list = new ArrayList<SalePoint>();
    private List<SelectItem> salePointList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of SalePointBox
     */
    public SalePointBox() {
    }

    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<SalePoint> optionList = this.getSalePointService().getSalePointRepository().findAllEnabled();

        for (SalePoint salePoint : optionList) {
            list.add(salePoint);
            salePointList.add(new SelectItem(salePoint, salePoint.getDescription()));
        }

    }
    

    public List<SelectItem> getSalePointList() {
        return salePointList;
    }

    public void setSalePointList(List<SelectItem> salePointList) {
        this.salePointList = salePointList;
    }

    public SalePointService getSalePointService() {
        return salePointService;
    }

    public void setSalePointService(SalePointService salePointService) {
        this.salePointService = salePointService;
    }

    public void fillBox() {
        salePointList.clear();
        init();
    }

    public List<SalePoint> getList() {
        return list;
    }

    public void setList(List<SalePoint> list) {
        this.list = list;
    }

  
}
