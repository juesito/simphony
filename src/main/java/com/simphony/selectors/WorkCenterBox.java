package com.simphony.selectors;

import com.simphony.beans.WorkCenterService;
import com.simphony.entities.WorkCenter;
import com.simphony.pojos.ComboBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    private Map<ComboBox, String> box = new TreeMap<ComboBox, String>(new ComboBoxComparator());
    private final Map<String, ComboBox> reverseComboBoxes = new HashMap<String, ComboBox>();
    private List<WorkCenter> list = new ArrayList<WorkCenter>();
    private List<SelectItem> workCenterList = new ArrayList<SelectItem>();

    public WorkCenterBox() {
    }
    

    @PostConstruct
    public void init() {
        List<WorkCenter> optionList = this.getWorkCenterService().getWorkCenterRepository().findAllEnabled();

        for (WorkCenter workCenter : optionList) {
            ComboBox cmb = new ComboBox(workCenter.getId().toString(), workCenter.getDescription().trim());
            box.put(cmb, cmb.getKey());
            reverseComboBoxes.put(cmb.getKey(), cmb);
            list.add(workCenter);
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

    public Map<ComboBox, String> getBox() {
        return box;
    }

    public void setBox(Map<ComboBox, String> box) {
        this.box = box;
    }

    public ComboBox getBox(String key) {
        return reverseComboBoxes.get(key);
    }

    public void fillBox() {
        workCenterList.clear();
        init();
    }

    public List<WorkCenter> getList() {
        return list;
    }

    public void setList(List<WorkCenter> list) {
        this.list = list;
    }
}
