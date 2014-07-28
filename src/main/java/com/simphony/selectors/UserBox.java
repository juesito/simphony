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



    public void fillBox() {
        userList.clear();
        init();
    }

}
