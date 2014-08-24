/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.PayRollService;
import com.simphony.entities.PayRoll;
import com.simphony.interfases.IConfigurable;
import com.simphony.tools.MessageProvider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.springframework.data.domain.Sort;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class PayRollBean implements IConfigurable {

    private final MessageProvider mp;
    private PayRoll payRoll = new PayRoll();
    private PayRoll current = new PayRoll();
    private PayRoll selected = new PayRoll();
    private List<PayRoll> list = new ArrayList<PayRoll>();
    private Calendar cal = Calendar.getInstance();

    @ManagedProperty(value = "#{payRollService}")
    private PayRollService payRollService;

    
    /**
     * Creates a new instance of PayRollBean
     */
    public PayRollBean() {
        mp = new MessageProvider();
    }
    
    

    public PayRoll getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(PayRoll payRoll) {
        this.payRoll = payRoll;
    }

    public PayRollService getPayRollService() {
        return payRollService;
    }

    public void setPayRollService(PayRollService payRollService) {
        this.payRollService = payRollService;
    }

    public List<PayRoll> getList() {
        return list;
    }

    public void setList(List<PayRoll> list) {
        this.list = list;
    }

    public PayRoll getSelected() {
        return selected;
    }

    public void setSelected(PayRoll selected) {
        this.selected = selected;
    }

    public PayRoll getCurrent() {
        return current;
    }

    public void setCurrent(PayRoll current) {
        this.current = current;
    }

    public Double getSumAmount() {
        Double amount = 0.0;
        for (PayRoll pr : list) {
            amount += pr.getAmount();
        }
        return amount;
    }

    /**
     * boton de cancelar
     *
     * @return
     */
    public String cancelPayRoll() {
        this.fillPayRolls();
        return "toPayRolls";
    }

    public String save() {

        this.payRollService.getRepository().save(payRoll);
        payRoll = new PayRoll();

        return "";
    }

    public void fillPayRolls() {
        list.clear();
    }

    /**
     * agregamos un rol de pago
     *
     * @param saleAmount
     */
    public void addPayRoll(Double saleAmount) {
        Boolean exist = true;
        PayRoll payRollTmp;

        if (saleAmount >= getSumAmount() + this.payRoll.getAmount()) {

            try {
                payRollTmp = this.payRollService.getRepository().findByKey(this.payRoll.getIdPayRoll());
                if (payRollTmp == null) {
                    exist = false;
                }
            } catch (Exception ex) {
                exist = false;
            }
            if (exist) {
                GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), "Folio: " + this.payRoll.getIdPayRoll()
                        + " Importe: " + this.payRoll.getAmount() + " Fecha:" + this.payRoll.getSale().getCreateDate()
                        + " " + mp.getValue("error_keyId_Detail"));
            }

            if (this.payRoll.getIdPayRoll() != null) {
                payRollTmp = new PayRoll(this.payRoll.getIdPayRoll(), this.payRoll.getAmount());
                list.add(payRollTmp);
            }
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), " Importe: " + this.payRoll.getAmount() + 
                    " Mayor a la venta:" + saleAmount);
        }
    }

    public String toPayRolls() {
        fillPayRolls();
        return "toPayRolls";
    }

    private Sort sortByKeyId() {
        return new Sort(Sort.Direction.ASC, "IdPayRoll");
    }

}
