package com.simphony.selectors;

import com.simphony.beans.UserService;
import com.simphony.entities.User;
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
 * @author Soporte IT
 */
@ManagedBean(name = "boxUserService", eager = true)
@ApplicationScoped
public class UserBox {

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    private Map<ComboBox, String> box = new TreeMap<ComboBox, String>(new ComboBoxComparator());
    private final Map<String, ComboBox> reverseComboBoxes = new HashMap<String, ComboBox>();
    private List<User> list = new ArrayList<User>();
    private List<SelectItem> userList = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of UserBox
     */
    public UserBox() {
    }

    @PostConstruct
    public void init() {
         
        List<User> optionList = this.getUserService().getUserRepository().findAllEnabled();

        for (User user : optionList) {
            ComboBox cmb = new ComboBox(user.getId().toString(), user.getNick().trim());
            box.put(cmb, cmb.getKey());
            reverseComboBoxes.put(cmb.getKey(), cmb);
            list.add(user);
            userList.add(new SelectItem(user, user.getNick()));
        }

    }

    public List<SelectItem> getUserList() {
        return userList;
    }

    public void setUserList(List<SelectItem> userList) {
        this.userList = userList;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
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
        userList.clear();
        init();
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

}
