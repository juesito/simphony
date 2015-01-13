/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.PrinterRepository;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author root
 */
@Component
public class PrinterService {
    
    @Autowired
    PrinterRepository printerRepository;

    public PrinterRepository getPrinterRepository() {
        return printerRepository;
    }

    public void setPrinterRepository(PrinterRepository printerRepository) {
        this.printerRepository = printerRepository;
    }
    
    
    
}
