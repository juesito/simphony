/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.UserService;
import com.simphony.beans.VendorService;
import com.simphony.entities.User;
import com.simphony.entities.Vendor;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import com.simphony.tools.MessageProvider;
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

    private final MessageProvider mp;
    private Vendor vendor = new Vendor();
    private Vendor current = new Vendor();
    private Vendor selected = new Vendor();
    private List<Vendor> list = new ArrayList<Vendor>();
    Calendar cal = Calendar.getInstance();
   
    @ManagedProperty(value = "#{vendorService}")
    private VendorService vendorService;
    
    @ManagedProperty(value = "#{userBean}")
    private UserBean userBean;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;
        /**
     * Creates a new instance of VendorBean
     */
    public VendorBean() {
        mp = new MessageProvider();
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

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
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
        if (this.selected != null ) {
            this.current.setAction(_MODIFY);
            try {
                this.vendor = (Vendor) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addVendor";
        }else
            return "toVendors";
    }

    /**
     * deshabilitamos vendedor
     * @throws com.simphony.exceptions.PersonException
     */
    public void disableVendor() throws PersonException {
        this.selected.setStatus(_DISABLE);
        Vendor vendorUpdated = this.vendorService.getVendorRepository().findOne(selected.getId());
        
        if(vendorUpdated == null){
            throw new PersonException("Vendedor no existente"); 
        }
        
        vendorUpdated.update(selected);
        this.vendorService.getVendorRepository().save(vendorUpdated);
        this.fillVendors();

    }

        /**
     * habilitamos vendedor
     * @throws com.simphony.exceptions.PersonException
     */
    public void enabledVendor() throws PersonException {
        this.selected.setStatus(_ENABLED);
        Vendor vendorUpdated = this.vendorService.getVendorRepository().findOne(selected.getId());
        
        if(vendorUpdated == null){
            throw new PersonException("Vendedor no existente"); 
        }
        
        vendorUpdated.update(selected);
        this.vendorService.getVendorRepository().save(vendorUpdated);
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
         Boolean exist = true;
         Vendor vendorTmp;

        try {
            vendorTmp = this.vendorService.getVendorRepository().findByNick(this.vendor.getNick().trim());
            if(vendorTmp == null){
                exist = false;
            }
        } catch (Exception ex) {
            System.out.println("Error");
            exist = false;

        }

        if (!exist) {
            if (this.vendor.getNick() != null ) {
                vendor.setCreateDate(cal.getTime());
                vendor.setStatus(_ENABLED);

                this.vendorService.getVendorRepository().save(vendor);
                GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.vendor.getNick()+" "+mp.getValue("msj_record_save"));
                vendor = new Vendor();
            }
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), this.vendor.getNick()+" "+mp.getValue("error_keyId_Detail"));
        }

         return "";
    }

    /**
     * actualizamos vendedor
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update() throws PersonException {
        
        Vendor vendorUpdated = this.vendorService.getVendorRepository().findOne(vendor.getId());
        
        if(vendorUpdated == null){
            throw new PersonException("Vendedor no existente"); 
        }
         
        vendorUpdated.update(vendor);
        this.vendorService.getVendorRepository().save(vendorUpdated);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"),  this.vendor.getNick()+" "+mp.getValue("msj_record_update"));
  
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
            current.setLooged(true);
            GrowlBean.simplyInfoMessage(mp.getValue("msj_welcome"), this.vendor.getNick() );
            Long idTmp = (long) 1;
            User usrTmp = this.userService.getUserRepository().findOne(idTmp);
            usrTmp.setNick(vendor.getNick());
            usrTmp.setName(vendor.getName());
            usrTmp.setFirstLastName(vendor.getFirstLastName());
            usrTmp.setSecondLastName(vendor.getSecondLastName());
            this.userBean.setAlternativeUser(usrTmp);
            return "toSale";
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_login"), mp.getValue("error_user"));
            current = new Vendor();
            return "toindex";
        }
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
