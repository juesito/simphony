/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.models;

import com.simphony.entities.Associate;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Jesus Garcia
 */
public class AssociateModel extends ListDataModel<Associate> implements SelectableDataModel<Associate>{

    public AssociateModel() {
    }

    public AssociateModel(List<Associate> list) {
        super(list);
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public Object getRowKey(Associate t) {
        return t.getId();
    }

    /**
     *
     * @param rowKey
     * @return
     */
    @Override
    public Associate getRowData(String rowKey) {
        List<Associate> associateList = (List<Associate>) getWrappedData();  
        
       for(Associate dm : associateList) {
            if(dm.getId().toString().equals(rowKey))  
                return dm;  
        }  
          
        return null;  
    }

      
    
}
