package com.simphony.entities;

import com.simphony.interfases.IConfigurable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity(name = "Sale")
@Table(name = "Ventas")
public class Sale implements Serializable, IConfigurable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Vendor.class)
    private Vendor cancelVendor;

    @ManyToOne(targetEntity = Vendor.class)
    private Vendor vendor;

    @Column(name = "fechaSalida")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date tripDate;

    @ManyToOne(targetEntity = Population.class)
    private Population origin;

    @ManyToOne(targetEntity = Population.class)
    private Population destiny;

    @Column(name = "tipo", length = 2)
    @Basic
    private String type;

    @Column(name = "fechaCreacion")
    @Basic
    private Date createDate;

    @ManyToOne(targetEntity = Associate.class)
    private Associate associate;

    @Column(name = "montoVenta")
    @Basic
    private Double amount;

    @Column(name = "pasajeros")
    @Basic
    private Integer passengers;
    
    @Column(name = "jubilados")
    @Basic
    private Integer retirees;
    
    @Transient
    private boolean partner;

    @Transient
    private boolean availability;

    @Transient
    private boolean existRoutes;
    
    @Column(name = "subtotal")
    private Double subTotal;
    
    @Column(name = "descuento")
    private Double discount;

    @Transient
    private String seat;

    public Sale() {
        this.setPartner(false);
        this.setAvailability(false);
        this.setExistRoutes(false);
        this.associate = new Associate();
        this.passengers = 0;
        this.amount = 0.0;
        this.retirees = 0;
        this.subTotal = 0.0;
        this.discount = 0.0;

    }

    public void clear() {
        this.setPartner(false);
        this.setAvailability(false);
        this.setExistRoutes(false);

        this.amount = 0.0;
        this.retirees = 0;
        this.subTotal = 0.0;
        this.discount = 0.0;
        this.associate = new Associate();
        this.type = _SALE_TYPE_PUBLIC;

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getCancelVendor() {
        return this.cancelVendor;
    }

    public void setCancelVendor(Vendor cancelVendor) {
        if (cancelVendor != null) {
            this.cancelVendor = cancelVendor;
        }
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Population getOrigin() {
        return this.origin;
    }

    public void setOrigin(Population origin) {
        this.origin = origin;
    }

    public Date getTripDate() {
        return this.tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public Population getDestiny() {
        return this.destiny;
    }

    public void setDestiny(Population destiny) {
        this.destiny = destiny;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Associate getAssociate() {
        return this.associate;
    }

    public void setAssociate(Associate associate) {
        this.associate = associate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public boolean isPartner() {
        return partner;
    }

    public void setPartner(boolean partner) {
        this.partner = partner;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean isExistRoutes() {
        return existRoutes;
    }

    public void setExistRoutes(boolean existRoutes) {
        this.existRoutes = existRoutes;
    }

    public String getFormatTripTime() {
        if (this.tripDate != null) {
            return _DMA.format(this.tripDate);
        } else {
            return "";
        }

    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public Integer getRetirees() {
        return retirees;
    }

    public void setRetirees(Integer retirees) {
        this.retirees = retirees;
    }
    
    
    public String getFormatDateTime() {
        if (this.tripDate != null) {
            return _DMA.format(this.tripDate);
        } else {
            return "";
        }
    }

    public String getFormatTime(){
        if(this.tripDate != null){
            return _SHM.format(this.tripDate.getTime());
        }else return "";
   }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sale other = (Sale) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getSeat() {
        return this.seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

}
