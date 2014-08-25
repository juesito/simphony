
package com.simphony.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author oj19edal
 * Tabla para guardar los folio de nómina con que pagan, pueden ser
 * varios en una sola venta.
 */
@Entity(name="PayRoll")
@Table(name="Nomina")
public class PayRoll implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="folio")
    @Basic
    private String idPayRoll;

    @Column(name="monto")
    @Basic
    private Double amount;

    @ManyToOne(targetEntity = Sale.class)
    private Sale sale;

    public PayRoll() {

    }
    
    public PayRoll(String folio, Double amount) {
        this.idPayRoll = folio;
        this.amount = amount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdPayRoll() {
        return idPayRoll;
    }

    public void setIdPayRoll(String idPayRoll) {
        this.idPayRoll = idPayRoll;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayRoll)) {
            return false;
        }
        PayRoll other = (PayRoll) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }

    @Override
    public String toString() {
        return "com.simphony.entities.PayRoll[ id=" + id + " ]";
    }
    
}
