/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.models;

import com.simphony.pojos.DailySales;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Jesus Garcia
 */
public class DailySalesModel extends ListDataModel<DailySales> implements SelectableDataModel<DailySales>{

    public DailySalesModel() {
    }

    public DailySalesModel(List<DailySales> list) {
        super(list);
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public Object getRowKey(DailySales t) {
        return t.getRowId();
    }

    /**
     *
     * @param rowKey
     * @return
     */
    @Override
    public DailySales getRowData(String rowKey) {
        List<DailySales> dailySalesList = (List<DailySales>) getWrappedData();  
        
       for(DailySales ic : dailySalesList) {
            if(ic.getRowId()== Integer.parseInt(rowKey))  
                return ic;  
        }  
          
        return null;  
    }

      
    
}
