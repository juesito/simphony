/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.CostService;
import com.simphony.entities.Cost;
import com.simphony.entities.Population;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
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
public class CostBean implements IConfigurable {

    private final MessageProvider mp;
    private Cost cost = new Cost();
    private Cost current = new Cost();
    private Cost selected = new Cost();
    private List<Cost> list = new ArrayList<Cost>();
    private Population population = new Population();
    private Calendar cal = Calendar.getInstance();

    @ManagedProperty(value = "#{costService}")
    private CostService costService;

    /**
     * Creates a new instance of CostBean
     */
    public CostBean() {
       mp = new MessageProvider();
    }

    @PostConstruct
    public void postInitialization() {
        
        this.cost.setCreateDate(cal.getTime());
       
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public CostService getCostService() {
        return costService;
    }

    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    public List<Cost> getList() {
        return list;
    }

    public void setList(List<Cost> list) {
        this.list = list;
    }

    public Cost getSelected() {
        return selected;
    }

    public void setSelected(Cost selected) {
        this.selected = selected;
    }

    public Cost getCurrent() {
        return current;
    }

    public void setCurrent(Cost current) {
        this.current = current;
    }

    public String addCost() {
        cost = new Cost();
        this.current.setAction(_ADD); 
        return "addCost";
    }
    
    /**
     * boton de cancelar
     *
     * @return
     */
    public String cancelCost() {
        this.fillCosts();
        return "toCosts";
    }

    public String save(User user) {
 
         Boolean exist = true;
         Cost costTmp;

        try {
            costTmp = this.costService.getCostRepository().findByOriDes(this.cost.getOrigin().getId(), this.cost.getDestiny().getId());
            if(costTmp == null){
                exist = false;
            }
        } catch (Exception ex) {
            System.out.println("Error");
            exist = false;

        }

        if (!exist) {
            if (this.cost.getOrigin() != null ) {
                cost.setUser(user);
                cost.setCreateDate(cal.getTime());
                cost.setStatus(_ENABLED);

                this.costService.getCostRepository().save(cost);
                GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.cost.getOrigin().getDescription()+"-"+this.cost.getDestiny().getDescription()+" "+mp.getValue("msj_record_save") );
                cost = new Cost();

            }
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), this.cost.getOrigin().getDescription()+"-"+this.cost.getDestiny().getDescription()+" "+mp.getValue("error_keyId_Detail"));
        }

        return "";
    }

    private void fillCosts(){
        list.clear();
        Iterable<Cost> c = this.getCostService().getCostRepository().findAll(sortByOrigin());
        Iterator<Cost> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }
    /**
     * Controlador listar tarifas
     *
     * @return
     */
    public String toCosts() {
        fillCosts();
        return "toCosts";
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    /**
     * Controlador para modificar tarifas
     *
     * @return
     */
    public String modifyCost() {
        if (this.selected != null ) {
             this.current.setAction(_MODIFY);
            try {
                this.cost = (Cost) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(CostBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addCost";
        }else
            return "toCosts";
    }

    /**
     * deshabilitamos Tarifas
     *
     */
    public void disableCost() {
        this.selected.setStatus(_DISABLE);

        Cost costUpdated = this.costService.getCostRepository().findOne(selected.getId());

        costUpdated.update(selected);
        this.costService.getCostRepository().save(costUpdated);

        this.fillCosts();

    }

     /**
     * habilitamos tarifas
     */
    public void enabledCost() {
        this.selected.setStatus(_ENABLED);

        Cost costUpdated = this.costService.getCostRepository().findOne(selected.getId());

        costUpdated.update(selected);
        this.costService.getCostRepository().save(costUpdated);

        this.fillCosts();

    }

    /**
     * Actualizamos tarifa
     *
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {
        
        Cost costUpdated = this.costService.getCostRepository().findOne(this.cost.getId());
        
        if(costUpdated == null){
            throw new PersonException("Tarifa no existente"); 
        }

        cost.setLastUpdate(cal.getTime());
        cost.setUser(user);
        costUpdated.update(this.cost);
        this.costService.getCostRepository().save(costUpdated);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.cost.getOrigin().getDescription()+"-"+this.cost.getDestiny().getDescription()+" "+mp.getValue("msj_record_update"));
        cost = new Cost();
        return toCosts();
    }

    private Sort sortByOrigin(){
        return new Sort(Sort.Direction.ASC, "origin.description");
    }

}
