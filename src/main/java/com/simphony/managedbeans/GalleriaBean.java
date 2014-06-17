/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.managedbeans;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.simphony.interfases.IConfigurable;
/**
 *
 * @author ga25rciaj
 */
@ManagedBean
@SessionScoped
public class GalleriaBean implements Serializable, IConfigurable{

    private static final long SerialVersionUID = 1L;
    //protected Logger log = Logger.getLogger( "org.jafra.tools.GalleriaBean" );
    private List<String> images;   
    
    private String effect = "turndown";    
   
    @PostConstruct
    public void init() {
         images = new ArrayList<String>();       

        FacesContext context = FacesContext.getCurrentInstance();
        String realPath = context.getExternalContext().getRealPath("/images/galleria/");
        
        File dir = new File(realPath);
        String[] ficheros = dir.list();

        if (ficheros != null){
             for (String fichero : ficheros) {
                 File archivo = new File(realPath + "/" + fichero);
                 if(archivo.getName().startsWith("c"))
                     images.add(archivo.getName());
                 archivo =null;
             }
        }
     }
    
    
     public List<String> getImages() {
         return images;
     }

     public String getEffect() {
         return effect;
     }

     public void setEffect(String effect) {
         this.effect = effect;
     }    

     
}
