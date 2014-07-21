/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Administrador
 */

public class GrowlBean {

    private static FacesContext context;

    /**
     * Creates a new instance of GrowlBean
     */
    public GrowlBean() {
        

    }

    public static FacesContext getContext() {
        return context;
    }

    public static void simplyInfoMessage(String title, String body) {
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, title, body));
    }

    public static void simplyErrorMessage(String title, String body) {
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, body));
    }

    public static void simplyWarmMessage(String title, String body) {
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, title, body));
    }

    public static void simplyFatalMessage(String title, String body) {
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, title, body));
    }

}
