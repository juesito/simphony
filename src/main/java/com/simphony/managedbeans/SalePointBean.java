/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.SalePointService;
import com.simphony.entities.SalePoint;
import com.simphony.interfases.IConfigurable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class SalePointBean {

    private List<SalePoint> list = new ArrayList();
    private SalePoint current = new SalePoint();
    private SalePoint selected = new SalePoint();

    @ManagedProperty(value = "#{salePointService}")
    SalePointService salePointService;

    /**
     * Creates a new instance of SalePointBean
     */
    public SalePointBean() {
    }

    public List<SalePoint> getList() {
        return list;
    }

    public void setList(List<SalePoint> list) {
        this.list = list;
    }

    public SalePoint getCurrent() {
        return current;
    }

    public void setCurrent(SalePoint current) {
        this.current = current;
    }

    public SalePoint getSelected() {
        return selected;
    }

    public void setSelected(SalePoint selected) {
        this.selected = selected;
    }

    public SalePointService getSalePointService() {
        return salePointService;
    }

    public void setSalePointService(SalePointService salePointService) {
        this.salePointService = salePointService;
    }

    public String addSalePoint() {
        return "addSalePoint";
    }

    public void save() {
        Calendar cal = Calendar.getInstance();
        this.current.setCreateDate(cal.getTime());
        this.current.setCreateHour(cal.getTime());
        this.current.setStatus(IConfigurable._ENABLED);
        this.getSalePointService().getSalePointRepository().save(current);
        current = new SalePoint();
    }

    public String toSalePoint() {
        list.clear();
        Iterable<SalePoint> c = this.getSalePointService().getSalePointRepository().findAll();
        Iterator<SalePoint> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        return "toSalePoint";
    }
}
