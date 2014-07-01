/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.VendorService;
import com.simphony.entities.Vendor;
import com.simphony.interfases.IConfigurable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class VendorBean implements IConfigurable {

    private Vendor vendor = new Vendor();
    private Vendor current = new Vendor();
    private Vendor selected = new Vendor();
    private List<Vendor> list = new ArrayList<Vendor>();

    @ManagedProperty(value = "#{vendorService}")
    private VendorService vendorService;

    /**
     * Creates a new instance of VendorBean
     */
    public VendorBean() {
    }

    @PostConstruct
    public void postInitialization() {

        Calendar cal = Calendar.getInstance();
        this.current.setCreateDate(cal.getTime());
        this.current.setStatus(_DISABLE);
        this.current.setName(_BLANK);

    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Vendor getCurrent() {
        return current;
    }

    public void setCurrent(Vendor current) {
        this.current = current;
    }

    public Vendor getSelected() {
        return selected;
    }

    public void setSelected(Vendor selected) {
        this.selected = selected;
    }

    public List<Vendor> getList() {
        return list;
    }

    public void setList(List<Vendor> list) {
        this.list = list;
    }

    public VendorService getVendorService() {
        return vendorService;
    }

    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    /**
     * Controlador para agregar vendedor
     * @return
     */
    public String addVendor() {
        vendor = new Vendor();
        this.current.setAction(_ADD);
        return "addVendor";
    }

    /**
     * modificamos vendedor
     * @return
     */
    public String modifyVendor() {
        this.current.setAction(_MODIFY);
        try {
            this.vendor = (Vendor) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addVendor";
    }

    /**
     * deshabilitamos vendedor
     */
    public void disableVendor() {
        Calendar cal = Calendar.getInstance();
        this.selected.setLastUpdate(cal.getTime());
        this.selected.setStatus(_DISABLE);
        this.vendorService.getVendorRepository().save(selected);
        this.fillVendors();

    }

    /**
     * boton de cancelar 
     * @return
     */
    public String cancelVendor() {
        this.fillVendors();
        return "toVendors";
    }

    /**
     * Guardamos vendedor
     * @return
     */
    public String save() {

        Calendar cal = Calendar.getInstance();
        vendor.setCreateDate(cal.getTime());
        vendor.setLastUpdate(cal.getTime());
        vendor.setStatus(_ENABLED);

        this.vendorService.getVendorRepository().save(vendor);
        vendor = new Vendor();
        return "";
    }

    /**
     * actualizamos vendedor
     * @return
     */
    public String update() {
        Calendar cal = Calendar.getInstance();
        vendor.setLastUpdate(cal.getTime());

        this.vendorService.getVendorRepository().save(vendor);
        vendor = new Vendor();
        return toVendors();
    }

    /**
     * Controlador listar usuarios
     *
     * @return
     */
    public String toVendors() {
        this.fillVendors();
        return "toVendors";
    }

    /**
     * Llenamos lista de vendedores
     */
    private void fillVendors() {
        list.clear();
        Iterable<Vendor> c = this.vendorService.getVendorRepository().findAll();
        Iterator<Vendor> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }

    }

    /**
     * Logueamos vendedor
     * @return
     */
    public String login() {
        current = this.vendorService.getVendorRepository().login(current.getNick().trim(), current.getPassword().trim());
        if (current != null) {
        }
        return "toindex";
    }

    /**
     * Salida del vendedor
     * @return
     */
    public String logout() {
        current = new Vendor();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";

    }

}
