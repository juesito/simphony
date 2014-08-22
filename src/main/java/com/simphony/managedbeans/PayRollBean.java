/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.PayRollService;
import com.simphony.entities.PayRoll;
import com.simphony.entities.Population;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ADD;
import static com.simphony.interfases.IConfigurable._DISABLE;
import static com.simphony.interfases.IConfigurable._ENABLED;
import static com.simphony.interfases.IConfigurable._MODIFY;
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
    private Population population = new Population();
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

    public String addPayRoll() {
        payRoll = new PayRoll();
        return "addPayRoll";
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

    public String save(User user) {
 
         Boolean exist = true;
         PayRoll payRollTmp;

        try {
            payRollTmp = this.payRollService.getRepository().findByKey(this.payRoll.getIdPayRoll());
            if(payRollTmp == null){
                exist = false;
            }
        } catch (Exception ex) {
            System.out.println("Error");
            exist = false;
        }
        if (!exist) {
                GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.payRoll.getIdPayRoll()+" "+mp.getValue("msj_record_save") );
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), this.payRoll.getIdPayRoll()+""+this.payRoll.getAmount()+" "+this.payRoll.getSale().getCreateDate()+" "+mp.getValue("error_keyId_Detail"));
        }
        this.payRollService.getRepository().save(payRoll);
        payRoll = new PayRoll();

        return "";
    }

    private void fillPayRolls(){
        list.clear();
        Iterable<PayRoll> c = this.getPayRollService().getRepository().findAll(sortByKeyId());
        Iterator<PayRoll> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }
    /**
     * Controlador listar tarifas
     *
     * @return
     */
    public String toPayRolls() {
        fillPayRolls();
        return "toPayRolls";
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    private Sort sortByKeyId(){
        return new Sort(Sort.Direction.ASC, "IdPayRoll");
    }

}
