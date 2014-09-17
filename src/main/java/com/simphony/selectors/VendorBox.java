/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.selectors;

import com.simphony.beans.VendorService;
import com.simphony.entities.Vendor;
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
@ManagedBean(name = "boxVendorService", eager = true)
@ApplicationScoped
public class VendorBox {

    @ManagedProperty(value = "#{vendorService}")
    private VendorService vendorService;
    
    private List<Vendor> list = new ArrayList<Vendor>();
    private List<SelectItem> vendorList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of VendorBox
     */
    public VendorBox() {
    }

    @PostConstruct
    public void init() {
         
        //Llena solo poblaciones activas
        List<Vendor> optionList = this.getVendorService().getVendorRepository().findAllEnabled();

        for (Vendor vendor : optionList) {
            list.add(vendor);
            String fullName = vendor.getNick()+"-"+vendor.getFirstLastName()+" "+ vendor.getSecondLastName()+" "+vendor.getName();
            vendorList.add(new SelectItem(vendor, fullName));
        }

    }
    
    public List<SelectItem> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<SelectItem> vendorList) {
        this.vendorList = vendorList;
    }

    public VendorService getVendorService() {
        return vendorService;
    }

    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    public void fillBox() {
        vendorList.clear();
        init();
    }

    public List<Vendor> getList() {
        return list;
    }

    public void setList(List<Vendor> list) {
        this.list = list;
    }

}
