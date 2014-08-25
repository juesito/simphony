/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.beans;

import com.simphony.repositories.PayRollRepository;
import com.simphony.repositories.PaymentRepository;
import com.simphony.repositories.ReservedSeatsRepository;
import com.simphony.repositories.SaleDetailRepository;
import com.simphony.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jueser
 */
@Component
public class SaleService {
    
    @Autowired
    SaleRepository saleRepository;
    
    @Autowired
    SaleDetailRepository detailRepository;
    
    @Autowired
    ReservedSeatsRepository reservedSeatsRepository;
    
    @Autowired
    PaymentRepository paymentRepository;
    
    @Autowired
    PayRollRepository payrollRepository;
    
    public SaleRepository getSaleRepository() {
        return saleRepository;
    }

    public void setSaleRepository(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public SaleDetailRepository getDetailRepository() {
        return detailRepository;
    }

    public void setDetailRepository(SaleDetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public ReservedSeatsRepository getReservedSeatsRepository() {
        return reservedSeatsRepository;
    }

    public void setReservedSeatsRepository(ReservedSeatsRepository reservedSeatsRepository) {
        this.reservedSeatsRepository = reservedSeatsRepository;
    }

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PayRollRepository getPayrollRepository() {
        return payrollRepository;
    }

    public void setPayrollRepository(PayRollRepository payrollRepository) {
        this.payrollRepository = payrollRepository;
    }
}
