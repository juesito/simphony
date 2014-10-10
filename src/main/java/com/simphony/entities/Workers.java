package com.simphony.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity(name="Workers")
@Table(name="Trabajadores")
public  class Workers extends Person implements Serializable, Cloneable {

    @Column(name="puesto",length=35)
    @Basic
    private String position;

    @Column(name="fechaIngreso")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date incomeDate;

    @Column(name="curp",length=50)
    @Basic
    private String curp;

    @Column(name="rfc",length=15)
    @Basic
    private String rfc;

    @Column(name="nss",length=25)
    @Basic
    private String nss;

    @Column(name = "salarioImss", length = 7)
    @Basic
    private double imssSalary;

     @Column(name = "salarioBase", length = 7)
    @Basic
    private double baseSalary;
   
     @Column(name="domicilio",length=100)
    @Basic
    private String address;

    @Column(name="entidad",length=35)
    @Basic
    private String entidad;

    @Column(name="codigoPostal",length=10)
    @Basic
    private String zipCode;

    @Column(name="lugar de nacimiento",length=35)
    @Basic
    private String birthPlace;
    
    @Column(name="estadoCivil",length=35)
    @Basic
    private String maritalStatus;
   
    @Column(name="telefono",length=10)
    @Basic
    private String phoneId;

    @Column(name="escolaridad",length=10)
    @Basic
    private String degree;

    @Column(name="tipoPago",length=15)
    @Basic
    private String payType;
 
    @Column(name="banco",length=35)
    @Basic
    private String bank;
    
    @Column(name="cuentaBanco",length=35)
    @Basic
    private String bankCount;
   
    @Column(name="clabeBanco",length=35)
    @Basic
    private String bankClabe;
    
    @Column(name="grupoSanguineo",length=20)
    @Basic
    private String bloodType;

    @Column(name="contactoAccidente",length=50)
    @Basic
    private String accidentContact;
    
    @Column(name="telefonoContacto",length=10)
    @Basic
    private String contactPhone;
    
    @Column(name="tipoTrabajador",length=25)
    @Basic
    private String workerType;
 
    @Column(name="expedicionLicencia")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date licenseExpedition;
    
    @Column(name="expiracionLicencia")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date licenseExpiry;

    @Column(name="refrendoLicencia")
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date licenseCountersign;
    
    @Column(name="autobusAsignado",length=6)
    @Basic
    private String assignedBus;

    @Column(name="enfermedad",length=35)
    @Basic
    private String illness;

    @OneToOne(targetEntity=User.class)
    private User user;


    public Workers(){

    }

    public void update(Workers workersUpdated){
        super.update(workersUpdated);
        this.position = workersUpdated.getPosition();
        this.incomeDate = workersUpdated.getIncomeDate();
        this.curp = workersUpdated.getCurp();
        this.rfc = workersUpdated.getRfc();
        this.nss = workersUpdated.getNss();
        this.imssSalary = workersUpdated.getImssSalary();
        this.baseSalary = workersUpdated.getBaseSalary();
        this.address = workersUpdated.getAddress();
        this.entidad = workersUpdated.getEntidad();
        this.zipCode = workersUpdated.zipCode;
        this.birthPlace = workersUpdated.birthPlace;
        this.maritalStatus = workersUpdated.getMaritalStatus();
        this.payType = workersUpdated.getPayType();
        this.bank = workersUpdated.getBank();
        this.bankCount = workersUpdated.getBankCount();
        this.bankClabe = workersUpdated.getBankClabe();
        this.bloodType = workersUpdated.getBloodType();
        this.accidentContact = workersUpdated.getAccidentContact();
        this.contactPhone = workersUpdated.getContactPhone();
        this.phoneId = workersUpdated.getPhoneId();
        this.degree = workersUpdated.getDegree();
        this.licenseExpedition = workersUpdated.getLicenseExpedition();
        this.licenseExpiry = workersUpdated.getLicenseExpiry();
        this.licenseCountersign = workersUpdated.getLicenseCountersign();
        this.assignedBus = workersUpdated.getAssignedBus();
        this.illness = workersUpdated.getIllness();
        this.user = workersUpdated.getUser();
    }
    
    @PreUpdate
    public void preUpdate(){
        super.setLastUpdate(new Date());
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public double getImssSalary() {
        return imssSalary;
    }

    public void setImssSalary(double imssSalary) {
        this.imssSalary = imssSalary;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankCount() {
        return bankCount;
    }

    public void setBankCount(String bankCount) {
        this.bankCount = bankCount;
    }

    public String getBankClabe() {
        return bankClabe;
    }

    public void setBankClabe(String bankClabe) {
        this.bankClabe = bankClabe;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAccidentContact() {
        return accidentContact;
    }

    public void setAccidentContact(String accidentContact) {
        this.accidentContact = accidentContact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public Date getLicenseExpedition() {
        return licenseExpedition;
    }

    public void setLicenseExpedition(Date licenseExpedition) {
        this.licenseExpedition = licenseExpedition;
    }

    public Date getLicenseExpiry() {
        return licenseExpiry;
    }

    public void setLicenseExpiry(Date licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public Date getLicenseCountersign() {
        return licenseCountersign;
    }

    public void setLicenseCountersign(Date licenseCountersign) {
        this.licenseCountersign = licenseCountersign;
    }

    public String getAssignedBus() {
        return assignedBus;
    }

    public void setAssignedBus(String assignedBus) {
        this.assignedBus = assignedBus;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


  public String getFullName(){
      String fullName = super.getFirstLastName().trim() + " "+ super.getSecondLastName().trim() + " " + super.getName().trim();
      return fullName;
  }
  
  

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.payType != null ? this.payType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Workers other = (Workers) obj;
        if ((this.rfc == null) ? (other.rfc != null) : !this.rfc.equals(other.rfc)) {
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

    public String getFormatStatus(){
        String texto = "";
        if(this.getStatus() != null){
            if (this.getStatus().equals("A")){
                texto = "Activo";
            }else texto = "Inactivo";
       
            return texto;
        }else return texto;
    }

}

