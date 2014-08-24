package com.simphony.managedbeans;

import com.simphony.beans.PayTypeService;
import com.simphony.entities.PayRoll;
import com.simphony.entities.PayType;
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
public class PayTypeBean implements IConfigurable {

    private final MessageProvider mp;

    private PayType current = new PayType();
    private PayType payType = new PayType();
    private PayType selected = new PayType();
    private PayType nominalPayType = new PayType();

    private boolean existNominalPayType = false;

    private List<PayType> list = new ArrayList();

    @ManagedProperty(value = "#{payTypeService}")
    PayTypeService payTypeService;

    /**
     * Creates a new instance of PayType
     */
    public PayTypeBean() {
        mp = new MessageProvider();
    }

    @PostConstruct
    public void init() {
        //Validamos que exista el tipo de pago nominal
        if (payTypeService.getPayTypeRepository().countByDescription(_NOMINAL_REFERENCE) > 0) {
            this.existNominalPayType = true;
            this.nominalPayType = payTypeService.getPayTypeRepository().findByDes(_NOMINAL_REFERENCE);
        }
    }

    public List<PayType> getList() {
        return list;
    }

    public void setList(List<PayType> list) {
        this.list = list;
    }

    public PayType getCurrent() {
        return current;
    }

    public void setCurrent(PayType current) {
        this.current = current;
    }

    public PayType getSelected() {
        return selected;
    }

    public void setSelected(PayType selected) {
        this.selected = selected;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PayTypeService getPayTypeService() {
        return payTypeService;
    }

    public void setPayTypeService(PayTypeService payTypeService) {
        this.payTypeService = payTypeService;
    }

    public String addPayType() {
        payType = new PayType();
        this.current.setAction(_ADD);
        return "addPayType";
    }

    public boolean isExistNominalPayType() {
        return existNominalPayType;
    }

    public void setExistNominalPayType(boolean existNominalPayType) {
        this.existNominalPayType = existNominalPayType;
    }

    public PayType getNominalPayType() {
        return nominalPayType;
    }

    public void setNominalPayType(PayType nominalPayType) {
        this.nominalPayType = nominalPayType;
    }

    /**
     * Guardamos el tipo de pago
     *
     * @param user
     * @return
     */
    public String save(User user) {
        Boolean exist = true;
        PayType payTypeTmp;

        try {
            payTypeTmp = this.payTypeService.getPayTypeRepository().findByDes(this.payType.getDescription().trim());
            if (payTypeTmp == null) {
                exist = false;
            }
        } catch (Exception ex) {
            System.out.println("Error");
            exist = false;

        }

        if (!exist) {
            if (this.payType.getDescription() != null) {
                Calendar cal = Calendar.getInstance();
                payType.setUser(user);
                payType.setCreateDate(cal.getTime());
                payType.setStatus(IConfigurable._ENABLED);
                this.payTypeService.getPayTypeRepository().save(payType);
                GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.payType.getDescription() + " " + mp.getValue("msj_record_save"));
                payType = new PayType();

            }
        } else {
            GrowlBean.simplyFatalMessage(mp.getValue("error_keyId"), this.payType.getDescription() + " " + mp.getValue("error_keyId_Detail"));
        }

        return "";
    }

    public String toPayType() {
        this.fillPayType();
        return "toPayType";
    }

    /**
     * Controlador para modificar PayType
     *
     * @return
     */
    public String modifyPayType() {
        if (this.selected != null) {
            this.current.setAction(_MODIFY);
            try {
                this.payType = (PayType) this.selected.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PayTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "addPayType";
        } else {
            return "toPayType";
        }
    }

    /**
     * deshabilitamos PayType
     *
     * @throws com.simphony.exceptions.PersonException
     */
    public void disablePayType() throws PersonException {
        this.selected.setStatus(_DISABLE);

        PayType payTypeUpdated = this.payTypeService.getPayTypeRepository().findOne(selected.getId());

        if (payTypeUpdated == null) {
            throw new PersonException("Tipo de pago no existente");
        }

        payTypeUpdated.update(selected);
        this.payTypeService.getPayTypeRepository().save(payTypeUpdated);

        this.fillPayType();

    }

    public String cancelPayType() {
        this.fillPayType();
        return "toPayType";
    }

    /**
     * Llenamos lista de tipos de pago
     */
    public void fillPayType() {
        list.clear();
        Iterable<PayType> c = this.payTypeService.getPayTypeRepository().findAll(sortByDes());
        Iterator<PayType> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * Actualizamos el tipo de pago
     *
     * @param user
     * @return
     * @throws com.simphony.exceptions.PersonException
     */
    public String update(User user) throws PersonException {

        PayType payTypeUpdated = this.payTypeService.getPayTypeRepository().findOne(this.payType.getId());

        if (payTypeUpdated == null) {
            throw new PersonException("Tipo de pago no existente");
        }
        this.payType.setUser(user);
        payTypeUpdated.update(this.payType);
        this.payTypeService.getPayTypeRepository().save(payTypeUpdated);
        GrowlBean.simplyInfoMessage(mp.getValue("msj_success"), this.payType.getDescription() + " " + mp.getValue("msj_record_update"));
        payType = new PayType();
        return toPayType();
    }

    /**
     * habilitamos PayType
     */
    public void enabledPayType() {
        this.selected.setStatus(_ENABLED);

        PayType payTypeUpdated = this.payTypeService.getPayTypeRepository().findOne(selected.getId());

        payTypeUpdated.update(selected);
        this.payTypeService.getPayTypeRepository().save(payTypeUpdated);

        this.fillPayType();

    }

    private Sort sortByDes() {
        return new Sort(Sort.Direction.ASC, "Description");
    }

    /**
     * Actualizamos el monto del tipo de pago nominal en la lista
     *
     * @param payRollList
     */
    public void updateAmountForNominalPay(List<PayRoll> payRollList) {
        Double amount = 0.0;

        for (PayRoll pr : payRollList) {
            amount += pr.getAmount();
        }
        nominalPayType.setAmount(amount);

    }

    public void updateOther() {
        for (PayType pt : list) {
            if (pt.getDescription().trim().equals(_NOMINAL_REFERENCE)) {
                pt.setAmount(nominalPayType.getAmount());
                Integer index = list.indexOf(pt);
                list.set(index, pt);
            }
        }
    }

    /**
     * Validamos si la descripcion del pago es de tipo nominal
     *
     * @param description
     * @return
     */
    public boolean isNominalPayType(String description) {
        return (description.trim().equals(_NOMINAL_REFERENCE));
    }

}
