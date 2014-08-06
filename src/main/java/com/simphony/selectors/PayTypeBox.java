/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.PayTypeService;
import com.simphony.entities.PayType;
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
@ManagedBean(name = "boxPayTypeService", eager = true)
@ApplicationScoped
public class PayTypeBox {

    @ManagedProperty(value = "#{payTypeService}")
    private PayTypeService payTypeService;
    
    private List<PayType> list = new ArrayList<PayType>();
    private List<SelectItem> payTypeList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of PayTypeBox
     */
    public PayTypeBox() {
    }

    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<PayType> optionList = this.getPayTypeService().getPayTypeRepository().findAllEnabled();

        for (PayType payType : optionList) {
            list.add(payType);
            payTypeList.add(new SelectItem(payType, payType.getDescription()));
        }

    }
    
    public List<SelectItem> getPayTypeList() {
        return payTypeList;
    }

    public void setPayTypeList(List<SelectItem> payTypeList) {
        this.payTypeList = payTypeList;
    }

    public PayTypeService getPayTypeService() {
        return payTypeService;
    }

    public void setPayTypeService(PayTypeService payTypeService) {
        this.payTypeService = payTypeService;
    }

    public void fillBox() {
        payTypeList.clear();
        init();
    }

    public List<PayType> getList() {
        return list;
    }

    public void setList(List<PayType> list) {
        this.list = list;
    }

}
