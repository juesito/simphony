/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.tools;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author Administrador
 */
public class MessageProvider implements Serializable{
    private static final long serialVersionUID = 1L;
    private ResourceBundle bundle;

    public MessageProvider() {
    }

    /**
     * Obtenemos el bundle
     * @return
     */
    public ResourceBundle getBundle() {
         if (bundle == null) {
	            FacesContext context = FacesContext.getCurrentInstance();
	            bundle = context.getApplication().getResourceBundle(context, "msgs");
	        }
        return bundle;
    }
    
    /**
     * Obtenemos el valor de la llave
     * @param key
     * @return
     */
    public String getValue(String key){
        String result = null;
        try{
            result =  getBundle().getString(key);
        }catch(MissingResourceException e){
            result = "???" + key + "??? not found";
        }
        return result;
    }
    
    
    
}
