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
 * @author root
 */
@ManagedBean(name = "boxPayTypeService", eager = true)
@ApplicationScoped
public class PayTypeBox {

    @ManagedProperty(value = "#{payTypeService}")
    private PayTypeService payTypeService;

    private List<SelectItem> payTypeList = new ArrayList<SelectItem>();

    public PayTypeBox() {
    }
    

    @PostConstruct
    public void init() {
        List<PayType> optionList = this.getPayTypeService().getPayTypeRepository().findAll();

        for (PayType payType : optionList) {
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


}
