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
import javax.persistence.PreUpdate;
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

    @Column(name="usuarioCancel")
    @Basic
    private String cancelUser;

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

    @Column(name = "fechaCreacion")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createDate;
    
    @Column(name = "fechaModificacion")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modifyDate;
    
    @Column(name = "descuento")
    private Double discount;

    @Column(name = "montoVenta")
    @Basic
    private Double amount;

    @Column(name = "pasajeros")
    @Basic
    private Integer passengers;
    
    @Column(name = "subtotal")
    private Double subTotal;
    
    @Column(name = "jubilados")
    @Basic
    private Integer retirees;
    
    @Column(name = "estatus")
    @Basic
    private String status;
    
    @Column(name = "ventaReferenciada")
    @Basic
    private Long idRefSale;

    @Column(name = "TipoServicio")
    @Basic
    private String serviceType;

    @Transient
    private boolean partner;

    @Transient
    private boolean availability;

    @Transient
    private boolean existRoutes;
    
    @Transient
    private String seat;

    public Sale() {
        this.setPartner(false);
        this.setAvailability(false);
        this.setExistRoutes(false);
        this.passengers = 0;
        this.amount = 0.0;
        this.retirees = 0;
        this.subTotal = 0.0;
        this.status = _SALED;
        this.createDate = new Date();

    }

    public void clear() {
        this.setPartner(false);
        this.setAvailability(false);
        this.setExistRoutes(false);

        this.amount = 0.0;
        this.retirees = 0;
        this.subTotal = 0.0;
 
    }
    
    /**
     * Campos a actualizar
     * @param saleUpdated 
     */
    public void update(Sale saleUpdated){
        this.amount = saleUpdated.getAmount();
        this.retirees = saleUpdated.getRetirees();
        this.subTotal = saleUpdated.getSubTotal();
        this.cancelUser = saleUpdated.getCancelUser();
        this.vendor = saleUpdated.getVendor();
        this.tripDate = saleUpdated.getTripDate();
        this.passengers = saleUpdated.getPassengers();
        this.origin = saleUpdated.getOrigin();
        this.status = saleUpdated.getStatus();
     }

    @PreUpdate
    public void preUpdate() {
        this.modifyDate = new Date();
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCancelUser() {
        return this.cancelUser;
    }

    public void setCancelUser(String cancelUser) {
        if (cancelUser != null) {
            this.cancelUser= cancelUser;
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

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getFormatCreateTime() {
        if (this.createDate != null) {
            return _SHM.format(this.createDate);
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
            return _SHM.format(this.tripDate);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getIdRefSale() {
        return idRefSale;
    }

    public void setIdRefSale(Long idRefSale) {
        this.idRefSale = idRefSale;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
        
}
