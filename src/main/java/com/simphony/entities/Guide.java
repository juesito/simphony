/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.entities;

import com.simphony.interfases.IConfigurable;
import static com.simphony.interfases.IConfigurable._DMA;
import static com.simphony.interfases.IConfigurable._SHM;
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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Jueser
 */
@Entity(name = "Guide")
@Table(name = "Guias")
public class Guide implements Serializable, Cloneable, IConfigurable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "referencia")
    @Basic(optional = true)
    private String guideReference;

    @Column(name = "fechaSalida")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date departureDate;

    @Column(name = "horaSalida")
    @Basic
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date departureTime;

    @ManyToOne(targetEntity = Population.class)
    private Population origin;

    @ManyToOne(targetEntity = Population.class)
    private Population destiny;

    @ManyToOne(targetEntity = Bus.class)
    private Bus bus;

    @ManyToOne(targetEntity = DriverMan.class)
    private DriverMan driverMan1;

    @ManyToOne(targetEntity = DriverMan.class)
    private DriverMan driverMan2;

    @Column(name = "rutaPadre")
    private Long rootRoute;

    @Column(name = "guiaPadre")
    private Long rootGuide;

    @Column(name = "fechaCreacion")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createDate;

    @Column(name = "estatus")
    @Basic
    private String status;    
    
    @Column(name="cupo")
    @Basic
    private Integer quota;
    
    @Column(name="tipoGuia")
    @Basic
    private String guideType;

    @OneToOne(targetEntity = Vendor.class)
    private Person vendor;
    
    @Column(name="ultimaModificacion")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastUpdate;

    @Transient
    private boolean newGuide = false;
    
    @Transient
    private String action;

    public Guide() {
        quota = _DEFAULT_SEAT_NUMBER;
    }

    public Guide(boolean newGuide) {
        this.quota = _DEFAULT_SEAT_NUMBER;
        this.newGuide = newGuide;
    }

    @PreUpdate
    public void preUpdate() {
        this.setLastUpdate(new Date());
    }

    public void update(Guide guideUpdated) {
        this.id = guideUpdated.getId();
        this.vendor = guideUpdated.getVendor();
        this.rootGuide = guideUpdated.getRootGuide();
        this.rootRoute = guideUpdated.getRootRoute();
        this.origin = guideUpdated.getOrigin();
        this.destiny = guideUpdated.getDestiny();
        this.departureDate = guideUpdated.getDepartureDate();
        this.guideReference = guideUpdated.getGuideReference();
        this.status = guideUpdated.getStatus();
                
        this.bus = guideUpdated.getBus();
        this.driverMan1 = guideUpdated.getDriverMan1();
        this.driverMan2 = guideUpdated.getDriverMan2();
        this.quota = guideUpdated.getQuota();
    }

    public String getGuideReference() {
        return guideReference;
    }

    public void setGuideReference(String guideReference) {
        this.guideReference = guideReference;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public DriverMan getDriverMan1() {
        return driverMan1;
    }

    public void setDriverMan1(DriverMan driverMan1) {
        this.driverMan1 = driverMan1;
    }

    public DriverMan getDriverMan2() {
        return driverMan2;
    }

    public void setDriverMan2(DriverMan driverMan2) {
        this.driverMan2 = driverMan2;
    }

    public boolean isNewGuide() {
        return newGuide;
    }

    public void setNewGuide(boolean newGuide) {
        this.newGuide = newGuide;
    }

    public Population getOrigin() {
        return origin;
    }

    public void setOrigin(Population origin) {
        this.origin = origin;
    }

    public Population getDestiny() {
        return destiny;
    }

    public void setDestiny(Population destiny) {
        this.destiny = destiny;
    }

    public Long getRootRoute() {
        return rootRoute;
    }

    public void setRootRoute(Long rootRoute) {
        this.rootRoute = rootRoute;
    }

    public Long getRootGuide() {
        return rootGuide;
    }

    public void setRootGuide(Long rootGuide) {
        this.rootGuide = rootGuide;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Person getVendor() {
        return vendor;
    }

    public void setVendor(Person vendor) {
        this.vendor = vendor;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
  
    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getGuideType() {
        return guideType;
    }

    public void setGuideType(String guideType) {
        this.guideType = guideType;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getFormatDepartureTime(){
        if(this.departureDate != null){
            return _SHM.format(this.departureDate.getTime());
        }else return "";
   }
    
    public String getFormatStatus(){
    String texto = "";
       if(this.getStatus() != null){
            if (this.getStatus().equals("OP")){
                texto = "Abierta";
            }else 
                 if (this.getStatus().equals("CL")){
                     texto = "Cerrada";
                 } else texto = "Cancelada";
       
            return texto;
        }else return texto;
    }
    
    public String getFormatDepartureDate(){
        if(this.departureDate != null){
            return _DMA.format(this.departureDate);
        }else return "";
   }

    public String getFullName1(){
        if(this.driverMan1 != null){
            String fullName = this.driverMan1.getName().trim() + " "+ this.driverMan1.getFirstLastName().trim() + " " + this.driverMan1.getSecondLastName().trim();
            return fullName;
        }else return "";
    }

    public String getFullName2(){
        if(this.driverMan1 != null){
            String fullName = this.driverMan2.getName().trim() + " "+ this.driverMan2.getFirstLastName().trim() + " " + this.driverMan2.getSecondLastName().trim();
            return fullName;
        }else return "";
    }

}
