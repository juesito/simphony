package com.simphony.selectors;

import com.simphony.beans.WorkCenterService;
import com.simphony.entities.WorkCenter;
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
@ManagedBean(name = "boxWorkCenterService", eager = true)
@ApplicationScoped
public class WorkCenterBox {

    @ManagedProperty(value = "#{workCenterService}")
    private WorkCenterService workCenterService;

    private List<SelectItem> workCenterList = new ArrayList<SelectItem>();

    public WorkCenterBox() {
    }
    

    @PostConstruct
    public void init() {
        List<WorkCenter> optionList = this.getWorkCenterService().getWorkCenterRepository().findAllEnabled();

        for (WorkCenter workCenter : optionList) {
            workCenterList.add(new SelectItem(workCenter, workCenter.getDescription()));
        }
    }

    public List<SelectItem> getWorkCenterList() {
        return workCenterList;
    }

    public void setWorkCenterList(List<SelectItem> workCenterList) {
        this.workCenterList = workCenterList;
    }

    public WorkCenterService getWorkCenterService() {
        return workCenterService;
    }

    public void setWorkCenterService(WorkCenterService workCenterService) {
        this.workCenterService = workCenterService;
    }

    public void fillBox() {
        workCenterList.clear();
        init();
    }


}
