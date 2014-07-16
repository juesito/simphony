/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.SalePointService;
import com.simphony.entities.SalePoint;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ADD;
import static com.simphony.interfases.IConfigurable._DISABLE;
import static com.simphony.interfases.IConfigurable._ENABLED;
import static com.simphony.interfases.IConfigurable._MODIFY;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private SalePoint salePoint = new SalePoint();
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

    public SalePoint getSalePoint() {
        return salePoint;
    }

    public void setSalePoint(SalePoint salePoint) {
        this.salePoint = salePoint;
    }

    public SalePointService getSalePointService() {
        return salePointService;
    }

    public void setSalePointService(SalePointService salePointService) {
        this.salePointService = salePointService;
    }

    public String addSalePoint() {
        salePoint = new SalePoint();
        this.current.setAction(_ADD);
        return "addSalePoint";
    }

    public String save(User user) {
        Calendar cal = Calendar.getInstance();
        salePoint.setUser(user);
        salePoint.setCreateDate(cal.getTime());
        salePoint.setStatus(IConfigurable._ENABLED);
        this.salePointService.getSalePointRepository().save(salePoint);
        salePoint = new SalePoint();
        return "";
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
    
       /**
     * Controlador para modificar SalePoint
     *
     * @return
     */
    public String modifySalePoint() {
        this.current.setAction(_MODIFY);
        try {
            this.salePoint = (SalePoint) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(SalePointBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addSalePoint";
    }

    /**
     * deshabilitamos SalePoint
     *
     * @throws com.simphony.exceptions.PersonException
     */
    public void disableSalePoint() throws PersonException {
        this.selected.setStatus(_DISABLE);

        SalePoint salePointUpdated = this.salePointService.getSalePointRepository().findOne(selected.getId());

        if (salePointUpdated == null) {
            throw new PersonException("Punto de venta no existente");
        }

        salePointUpdated.update(selected);
        this.salePointService.getSalePointRepository().save(salePointUpdated);

        this.fillSalePoint();

    }

    public String cancelSalePoint() {
        this.fillSalePoint();
        return "toSalePoint";
    }

    /**
     * Llenamos lista de agremiados
     */
    private void fillSalePoint() {
        list.clear();
        Iterable<SalePoint> c = this.salePointService.getSalePointRepository().findAll();
        Iterator<SalePoint> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }
    
    /**
     * Actualizamos el punto de venta
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {
        
        SalePoint salePointUpdated = this.salePointService.getSalePointRepository().findOne(this.salePoint.getId());
        
        if(salePointUpdated == null){
            throw new PersonException("Punto de venta no existente"); 
        }
        this.salePoint.setUser(user);
        salePointUpdated.update(this.salePoint);
        this.salePointService.getSalePointRepository().save(salePointUpdated);
        salePoint = new SalePoint();
        return toSalePoint();
    }

      /**
     * habilitamos SalePoint
     */
    public void enableSalePoint() {
        this.selected.setStatus(_ENABLED);

        SalePoint salePointUpdated = this.salePointService.getSalePointRepository().findOne(selected.getId());

        salePointUpdated.update(selected);
        this.salePointService.getSalePointRepository().save(salePointUpdated);

        this.fillSalePoint();

    }

}
