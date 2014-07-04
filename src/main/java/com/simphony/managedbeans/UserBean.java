/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import com.simphony.beans.UserService;
import com.simphony.entities.User;
import com.simphony.interfases.IConfigurable;
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
import javax.faces.context.FacesContext;

/**
 *
 * @author root
 */
@ManagedBean
@SessionScoped
public class UserBean implements IConfigurable {

    private User user = new User();
    private User current = new User();
    private User selected = new User();
    private List<User> list = new ArrayList<User>();

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }

    @PostConstruct
    public void postInitialization() {

        Calendar cal = Calendar.getInstance();
        this.current.setCreateDate(cal.getTime());
        this.current.setStatus(_DISABLE);
        this.current.setName(_BLANK);
        checkRootUser();

        //Esta inicializacion sera temporal solo pruebas
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public User getSelected() {
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    /**
     * Controlador Agregamos usuario
     *
     * @return
     */
    public String addUser() {
        user = new User();
        this.current.setAction(_ADD);
        return "addUser";
    }

    /**
     * Controlador para modificar usuario
     *
     * @return
     */
    public String modifyUser() {
        this.current.setAction(_MODIFY);
        try {
            this.user = (User) this.selected.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "addUser";
    }

    /**
     * deshabilitamos usuario
     */
    public void disableUser() {
        Calendar cal = Calendar.getInstance();
        this.selected.setLastUpdate(cal.getTime());
        this.selected.setStatus(_DISABLE);
        this.userService.getUserRepository().save(selected);
        this.fillUsers();

    }

    /**
     * Llenamos lista de usuarios
     */
    private void fillUsers() {
        list.clear();
        Iterable<User> c = this.getUserService().getUserRepository().findAll();
        Iterator<User> cu = c.iterator();
        while (cu.hasNext()) {
            list.add(cu.next());
        }
    }

    /**
     * boton de cancelar
     *
     * @return
     */
    public String cancelUser() {
        this.fillUsers();
        return "toUsers";
    }

    /**
     * Guardamos usuario
     *
     * @return
     */
    public String save() {

        if (!user.getName().equals("")) {

            Calendar cal = Calendar.getInstance();
            user.setCreateDate(cal.getTime());
            user.setLastUpdate(cal.getTime());
            user.setStatus(_ENABLED);

            this.userService.getUserRepository().save(user);
            user = new User();
        }
        return "";
    }

    /**
     * Actualizamos vendedor
     *
     * @return
     */
    public String update() {
        Calendar cal = Calendar.getInstance();
        user.setLastUpdate(cal.getTime());

        this.userService.getUserRepository().save(user);
        user = new User();
        return toUsers();
    }

    /**
     * Controlador listar usuarios
     *
     * @return
     */
    public String toUsers() {
        this.fillUsers();
        return "toUsers";
    }

    /**
     * Autentificamos usuario
     *
     * @return
     */
    public String login() {
        current = this.userService.getUserRepository().login(current.getNick().trim(), current.getPassword().trim());
        if (current != null) {
            return "toindex";
        } else {
            return "toLogin";
        }
    }

    /**
     * Salida del usuario
     *
     * @return
     */
    public String logout() {
        current = new User();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";

    }

    /**
     * validamos si existe usuario
     */
    private void checkRootUser() {
        if (!this.userService.getUserRepository().exists(1L)) {
            Calendar cal = Calendar.getInstance();
            User root = new User();
            root.setName("Administrador");
            root.setFirstLastName("Admon");
            root.setSecondLastName("Admin");
            root.setNick("root");
            root.setPassword("123");
            root.setStatus(_ENABLED);
            root.setEmail("jues40@hotmail.com");
            root.setCreateDate(cal.getTime());
            root.setLastUpdate(cal.getTime());
            this.userService.getUserRepository().save(root);
        }
    }

}
