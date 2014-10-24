/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.models;

import com.simphony.entities.Workers;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Jesus Garcia
 */
public class WorkersModel extends ListDataModel<Workers> implements SelectableDataModel<Workers>{

    public WorkersModel() {
    }

    public WorkersModel(List<Workers> list) {
        super(list);
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public Object getRowKey(Workers t) {
        return t.getId();
    }

    /**
     *
     * @param rowKey
     * @return
     */
    @Override
    public Workers getRowData(String rowKey) {
        List<Workers> workersList = (List<Workers>) getWrappedData();  
        
       for(Workers dm : workersList) {
            if(dm.getId().toString().equals(rowKey))  
                return dm;  
        }  
          
        return null;  
    }

      
    
}
