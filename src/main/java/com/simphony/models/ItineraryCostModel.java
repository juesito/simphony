/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.models;

import com.simphony.pojos.ItineraryCost;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Jesus Garcia
 */
public class ItineraryCostModel extends ListDataModel<ItineraryCost> implements SelectableDataModel<ItineraryCost>{

    public ItineraryCostModel() {
    }

    public ItineraryCostModel(List<ItineraryCost> list) {
        super(list);
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public Object getRowKey(ItineraryCost t) {
        return t.getRowId();
    }

    /**
     *
     * @param rowKey
     * @return
     */
    @Override
    public ItineraryCost getRowData(String rowKey) {
        List<ItineraryCost> itineraryCostList = (List<ItineraryCost>) getWrappedData();  
        
       for(ItineraryCost ic : itineraryCostList) {
            if(ic.getRowId().equals(rowKey))  
                return ic;  
        }  
          
        return null;  
    }

      
    
}
