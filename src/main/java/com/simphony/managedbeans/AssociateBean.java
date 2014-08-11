/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.AssociateService;
import com.simphony.entities.Associate;
import com.simphony.entities.User;
import com.simphony.exceptions.PersonException;
import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._ENABLED;
import static com.simphony.interfases.IConfigurable._MODIFY;
import com.simphony.models.AssociateModel;
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
public class AssociateBean implements IConfigurable {

    private final MessageProvider mp;
    private Associate associate = new Associate();
    private Associate current = new Associate();
    private Associate selected = new Associate();
    private AssociateModel model = new AssociateModel();
    private List<Associate> list = new ArrayList<Associate>();

    @ManagedProperty(value = "#{associateService}")
    private AssociateService service;
    private Calendar cal = Calendar.getInstance();

    /**
     * Creates a new instance of AssociateBean
     */
    public AssociateBean() {
        mp = new MessageProvider();
        
    }

    @PostConstruct
    public void postInitialization() {
        this.checkRootAssociate();

    }

    public Associate getAssociate() {
        return associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public AssociateService getService() {
        return service;
    }

    public void setService(AssociateService service) {
        this.service = service;
    }

    public List<Associate> getList() {
        return list;
    }

    public void setList(List<Associate> list) {
        this.list = list;
    }

    public Associate getSelected() {
        return selected;
    }

    public void setSelected(Associate selected) {
        this.selected = selected;
    }

    public Associate getCurrent() {
        return current;
    }

    public void setCurrent(Associate current) {
        this.current = current;
    }

    public String addAssociate() {
        associate = new Associate();
        this.current.setAction(_ADD);
        return "addAssociate";
    }

    /**
     * Controlador para modificar agremiado
     *
     * @return
     */
    public String modifyAssociate() {
        if (this.selected != null) {
            this.current.setAction(_MODIFY);
            try {
                this.associate = (Associate) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(AssociateBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addAssociate";
        } else {
            return "toAssociates";
        }
    }

    /**
     * deshabilitamos agremiado
     *
     */
    public void disableAssociate() {
        this.selected.setStatus(_DISABLE);

        Associate associateUpdated = this.service.getRepository().findOne(selected.getId());

        associateUpdated.update(selected);
        this.service.getRepository().save(associateUpdated);

        this.fillAssociates();

    }

    /**
     * habilitamos agremiado
     */
    public void enabledAssociate() {
        this.selected.setStatus(_ENABLED);

        Associate associateUpdated = this.service.getRepository().findOne(selected.getId());

        associateUpdated.update(selected);
        this.service.getRepository().save(associateUpdated);

        this.fillAssociates();

    }

    public String cancelAssociate() {
        this.fillAssociates();
        return "toAssociates";
    }

    /**
     * Llenamos lista de agremiados
     */
    private void fillAssociates() {
        list.clear();
        Iterable<Associate> c = this.service.getRepository().findAll(sortByKeyId());
        Iterator<Associate> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
        model = new AssociateModel(list);
    }

    /**
     * Guardamos el agremiado
     *
     * @param user
     * @return
     */
    public String save(User user) {
         Boolean exist = true;
         Associate associateTmp;

        try {
            associateTmp = this.service.getRepository().findByKey(this.associate.getKeyId().trim());
            if(associateTmp == null){
                exist = false;
            }
        } catch (Exception ex) {
            System.out.println("Error");
            exist = false;
 
        }

        if (!exist) {
            if (this.associate.getKeyId() != null && this.associate.getName() != null) {
                associate.setUser(user);
                associate.setCreateDate(cal.getTime());
                associate.setStatus(_ENABLED);

                this.service.getRepository().save(associate);
                GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.associate.getName()+ " "+mp.getValue("msj_record_save") );
                associate = new Associate();

            }
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), this.associate.getKeyId() + " " + mp.getValue("error_keyId_Detail"));
        }

        return "";
    }

    /**
     * Actualizamos el usuario
     *
     * @param user
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {

        Associate associateUpdated = this.service.getRepository().findOne(this.associate.getId());

        if (associateUpdated == null) {
            throw new PersonException("Agremiado no existente");
        }

        associate.setLastUpdate(cal.getTime());
        associate.setUser(user);
        associateUpdated.update(this.associate);
        this.service.getRepository().save(associateUpdated);
       GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.associate.getName() + " " + mp.getValue("msj_record_update"));
        associate = new Associate();
        return toAssociates();
    }

    /**
     * Controlador listar Associate
     *
     * @return
     */
    public String toAssociates() {
        this.fillAssociates();
        return "toAssociates";
    }

    private Sort sortByKeyId() {
        return new Sort(Sort.Direction.ASC, "keyId");
    }

    public AssociateModel getModel() {
        return model;
    }

    public void setModel(AssociateModel model) {
        this.model = model;
    }
    
    
    
    /**
     * validamos si existe usuario
     */
    private void checkRootAssociate() {
        if (!this.service.getRepository().exists(1L)) {
            Calendar cal = Calendar.getInstance();
            Associate root = new Associate();
            root.setName("System");
            root.setFirstLastName("Administrator");
            root.setSecondLastName("Default");
            root.setStatus(_DISABLE);
            root.setCreateDate(cal.getTime());
            root.setLastUpdate(cal.getTime());
            this.service.getRepository().save(root);
        }
    }
}
