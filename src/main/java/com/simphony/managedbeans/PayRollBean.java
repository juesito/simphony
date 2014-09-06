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
     * @throws java.lang.CloneNotSupportedException
     */
    public void addPayRoll(Double saleAmount) throws CloneNotSupportedException {
        Boolean exist = true;
        PayRoll payRollTmp;

        if (this.payRoll.getIdPayRoll() != null && this.payRoll.getAmount() > 0) {
            if (saleAmount >= getSumAmount() + this.payRoll.getAmount()) {

                try {

                    payRollTmp = this.payRollService.getRepository().findByKey(this.payRoll.getIdPayRoll());
                    if (payRollTmp == null) {
                        exist = false;
                    }
                } catch (Exception ex) {
                    exist = false;
                }
                payRollTmp = (PayRoll) this.payRoll.clone();
                list.add(payRollTmp);
                this.payRoll.setIdPayRoll("");
               this.payRoll.setAmount(0.0);
                if (exist) {
                GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), " Ya utilizado con: " + payRollTmp.getAmount());
                      
//                    GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), "Folio: " + this.payRoll.getIdPayRoll()
//                            + " Importe: " + this.payRoll.getAmount() + " Fecha: " + this.payRoll.getSale().getFormatDateTime() + mp.getValue("error_keyId_Detail"));
                    
                } 
            } else {
                GrowlBean.simplyFatalMessage(mp.getValue("error_amount"), " Importe: " + this.payRoll.getAmount()
                        + " Mayor a la venta:" + saleAmount);
            }
            this.payRoll.setIdPayRoll("");
            this.payRoll.setAmount(0.0);
        }

    }

    public String toPayRolls() {
        fillPayRolls();
        return "toPayRolls";
    }

    
    public void deleteRowTable(PayRoll pr) {

        for(PayRoll payRollList : this.list){
            if(pr.getIdPayRoll().equals(payRollList.getIdPayRoll().trim())){
                int index = this.list.indexOf(payRollList);
                this.list.remove(index);
            }
        }
 
    }
    
    private Sort sortByKeyId() {
        return new Sort(Sort.Direction.ASC, "IdPayRoll");
    }

}
