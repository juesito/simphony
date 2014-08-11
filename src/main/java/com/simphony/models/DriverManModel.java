/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.models;

import com.simphony.entities.DriverMan;
import com.simphony.pojos.ItineraryCost;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Jesus Garcia
 */
public class DriverManModel extends ListDataModel<DriverMan> implements SelectableDataModel<DriverMan>{

    public DriverManModel() {
    }

    public DriverManModel(List<DriverMan> list) {
        super(list);
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public Object getRowKey(DriverMan t) {
        return t.getId();
    }

    /**
     *
     * @param rowKey
     * @return
     */
    @Override
    public DriverMan getRowData(String rowKey) {
        List<DriverMan> driverManList = (List<DriverMan>) getWrappedData();  
        
       for(DriverMan dm : driverManList) {
            if(dm.getId().toString().equals(rowKey))  
                return dm;  
        }  
          
        return null;  
    }

      
    
}
