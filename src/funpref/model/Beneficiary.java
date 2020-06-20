/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.model;

import java.io.Serializable;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author robson
 */
public class Beneficiary implements Serializable {
    
    public enum Sex{ MALE, FEMALE, NULL };    
    
    private int id;
    private int registration;
    private String ordinance;
    private String cpf;    
    private String name;
    private Sex sex;
    private Date birthDate;
    private int idCadastralStatus;
    private int idMaritalStatus;
    private boolean deceased;
    private Date deathDate;    
    private String nationality;
    private int idCityPlaceOfBirth;
    private int idProvincePlaceOfBirth;
    private int idDegreeOfEducation;
    private int idDeficiency;
    private String rg;
    private Date rgEmissionDate;
    private int idRgIssuingBody;
    private int idProvinceRg;
    private String motherName;
    private String fatherName;
    private String motherCpf;
    private String fatherCpf;
    private String pisPasep;
    private int idBenefitType;
    private int idCalculationForm;
    private Date admissionDate;
    private Date inactivationDate;
    private Date applicationDate;
    private Period contributionTime;
    private Period contributionTimeFUNPREF;
    private Period inactivityTime;    
    private String office;
    private int idStockingOrgan;
    private int instituteEnrollment;
    private String invalidityReason;
    private Date invalidityAwardDate;
    private ArrayList<Dependent> dependents;
    private String observations;
    private String bankAgency;
    private String account;
    private double earningsInative;
    private boolean oldPromotion;
    private double oldPromotionValue;
    private boolean chalkPowder;
    private double chalkPowderPercentual;
    private double chalkPowderValue;
    private boolean moreOneYear;
    private double moreOneYearPercentual;
    private double moreOneYearValue;
    private boolean moreFiveYear;
    private double moreFiveYearPercentual;
    private double moreFiveYearValue;   
    private double incomeTaxRate;              // imposto de renda
    private double incomeTaxValue;
    private boolean payrollLoan;
    private double payrollLoanValue;
    private double grossValue;
    private double discounts;
    private double netValue;
    private String address;
    private int idCityAddress;
    private int idProvinceAddress;
    private String zipCode;
    private String phone1;
    private String phone2;
    private String email;
    private int physicalDocumentDrawer;    
    private int indexPhysicalDocument;
    private String votersTitle;
    private int electoralZone;
    private int electoralSection;
    private int idProvinceElectoralZone;
    private int idUserRegistration;
    private int idUserCreate;
    private int idUserUpdate;
    private Date registerDate;
    private Date updateDate;
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegistration() {
        return registration;
    }

    public void setRegistration(int registration) {
        this.registration = registration;
    }

    public String getOrdinance() {
        return ordinance;
    }

    public void setOrdinance(String ordinance) {
        this.ordinance = ordinance;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getIdCadastralStatus() {
        return idCadastralStatus;
    }

    public void setIdCadastralStatus(int idCadastralStatus) {
        this.idCadastralStatus = idCadastralStatus;
    }

    public int getIdMaritalStatus() {
        return idMaritalStatus;
    }

    public void setIdMaritalStatus(int idMaritalStatus) {
        this.idMaritalStatus = idMaritalStatus;
    }

    public boolean isDeceased() {
        return deceased;
    }

    public void setDeceased(boolean deceased) {
        this.deceased = deceased;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getIdCityPlaceOfBirth() {
        return idCityPlaceOfBirth;
    }

    public void setIdCityPlaceOfBirth(int idCityPlaceOfBirth) {
        this.idCityPlaceOfBirth = idCityPlaceOfBirth;
    }

    public int getIdProvincePlaceOfBirth() {
        return idProvincePlaceOfBirth;
    }

    public void setIdProvincePlaceOfBirth(int idProvincePlaceOfBirth) {
        this.idProvincePlaceOfBirth = idProvincePlaceOfBirth;
    }

    public int getIdDegreeOfEducation() {
        return idDegreeOfEducation;
    }

    public void setIdDegreeOfEducation(int idDegreeOfEducation) {
        this.idDegreeOfEducation = idDegreeOfEducation;
    }

    public int getIdDeficiency() {
        return idDeficiency;
    }

    public void setIdDeficiency(int idDeficiency) {
        this.idDeficiency = idDeficiency;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getRgEmissionDate() {
        return rgEmissionDate;
    }

    public void setRgEmissionDate(Date rgEmissionDate) {
        this.rgEmissionDate = rgEmissionDate;
    }

    public int getIdRgIssuingBody() {
        return idRgIssuingBody;
    }

    public void setIdRgIssuingBody(int idRgIssuingBody) {
        this.idRgIssuingBody = idRgIssuingBody;
    }

    public int getIdProvinceRg() {
        return idProvinceRg;
    }

    public void setIdProvinceRg(int idProvinceRg) {
        this.idProvinceRg = idProvinceRg;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherCpf() {
        return motherCpf;
    }

    public void setMotherCpf(String motherCpf) {
        this.motherCpf = motherCpf;
    }

    public String getFatherCpf() {
        return fatherCpf;
    }

    public void setFatherCpf(String fatherCpf) {
        this.fatherCpf = fatherCpf;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public int getIdBenefitType() {
        return idBenefitType;
    }

    public void setIdBenefitType(int idBenefitType) {
        this.idBenefitType = idBenefitType;
    }

    public int getIdCalculationForm() {
        return idCalculationForm;
    }

    public void setIdCalculationForm(int idCalculationForm) {
        this.idCalculationForm = idCalculationForm;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getInactivationDate() {
        return inactivationDate;
    }

    public void setInactivationDate(Date inactivationDate) {
        this.inactivationDate = inactivationDate;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Period getContributionTime() {
        return contributionTime;
    }

    public void setContributionTime(Period contributionTime) {
        this.contributionTime = contributionTime;
    }

    public Period getContributionTimeFUNPREF() {
        return contributionTimeFUNPREF;
    }

    public void setContributionTimeFUNPREF(Period contributionTimeFUNPREF) {
        this.contributionTimeFUNPREF = contributionTimeFUNPREF;
    }

    public Period getInactivityTime() {
        return inactivityTime;
    }

    public void setInactivityTime(Period inactivityTime) {
        this.inactivityTime = inactivityTime;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public int getIdStockingOrgan() {
        return idStockingOrgan;
    }

    public void setIdStockingOrgan(int idStockingOrgan) {
        this.idStockingOrgan = idStockingOrgan;
    }

    public int getInstituteEnrollment() {
        return instituteEnrollment;
    }

    public void setInstituteEnrollment(int instituteEnrollment) {
        this.instituteEnrollment = instituteEnrollment;
    }

    public String getInvalidityReason() {
        return invalidityReason;
    }

    public void setInvalidityReason(String invalidityReason) {
        this.invalidityReason = invalidityReason;
    }

    public Date getInvalidityAwardDate() {
        return invalidityAwardDate;
    }

    public void setInvalidityAwardDate(Date invalidityAwardDate) {
        this.invalidityAwardDate = invalidityAwardDate;
    }

    public ArrayList<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<Dependent> dependents) {
        this.dependents = dependents;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getBankAgency() {
        return bankAgency;
    }

    public void setBankAgency(String bankAgency) {
        this.bankAgency = bankAgency;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getEarningsInative() {
        return earningsInative;
    }

    public void setEarningsInative(double earningsInative) {
        this.earningsInative = earningsInative;
    }

    public boolean isOldPromotion() {
        return oldPromotion;
    }

    public void setOldPromotion(boolean oldPromotion) {
        this.oldPromotion = oldPromotion;
    }

    public double getOldPromotionValue() {
        return oldPromotionValue;
    }

    public void setOldPromotionValue(double oldPromotionValue) {
        this.oldPromotionValue = oldPromotionValue;
    }

    public boolean isChalkPowder() {
        return chalkPowder;
    }

    public void setChalkPowder(boolean chalkPowder) {
        this.chalkPowder = chalkPowder;
    }

    public double getChalkPowderPercentual() {
        return chalkPowderPercentual;
    }

    public void setChalkPowderPercentual(double chalkPowderPercentual) {
        this.chalkPowderPercentual = chalkPowderPercentual;
    }

    public double getChalkPowderValue() {
        return chalkPowderValue;
    }

    public void setChalkPowderValue(double chalkPowderValue) {
        this.chalkPowderValue = chalkPowderValue;
    }

    public boolean isMoreOneYear() {
        return moreOneYear;
    }

    public void setMoreOneYear(boolean moreOneYear) {
        this.moreOneYear = moreOneYear;
    }

    public double getMoreOneYearPercentual() {
        return moreOneYearPercentual;
    }

    public void setMoreOneYearPercentual(double moreOneYearPercentual) {
        this.moreOneYearPercentual = moreOneYearPercentual;
    }

    public double getMoreOneYearValue() {
        return moreOneYearValue;
    }

    public void setMoreOneYearValue(double moreOneYearValue) {
        this.moreOneYearValue = moreOneYearValue;
    }

    public boolean isMoreFiveYear() {
        return moreFiveYear;
    }

    public void setMoreFiveYear(boolean moreFiveYear) {
        this.moreFiveYear = moreFiveYear;
    }

    public double getMoreFiveYearPercentual() {
        return moreFiveYearPercentual;
    }

    public void setMoreFiveYearPercentual(double moreFiveYearPercentual) {
        this.moreFiveYearPercentual = moreFiveYearPercentual;
    }

    public double getMoreFiveYearValue() {
        return moreFiveYearValue;
    }

    public void setMoreFiveYearValue(double moreFiveYearValue) {
        this.moreFiveYearValue = moreFiveYearValue;
    }

    public double getIncomeTaxRate() {
        return incomeTaxRate;
    }

    public void setIncomeTaxRate(double incomeTaxRate) {
        this.incomeTaxRate = incomeTaxRate;
    }

    public double getIncomeTaxValue() {
        return incomeTaxValue;
    }

    public void setIncomeTaxValue(double incomeTaxValue) {
        this.incomeTaxValue = incomeTaxValue;
    }

    public boolean isPayrollLoan() {
        return payrollLoan;
    }

    public void setPayrollLoan(boolean payrollLoan) {
        this.payrollLoan = payrollLoan;
    }

    public double getPayrollLoanValue() {
        return payrollLoanValue;
    }

    public void setPayrollLoanValue(double payrollLoanValue) {
        this.payrollLoanValue = payrollLoanValue;
    }

    public double getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(double grossValue) {
        this.grossValue = grossValue;
    }

    public double getDiscounts() {
        return discounts;
    }

    public void setDiscounts(double discounts) {
        this.discounts = discounts;
    }

    public double getNetValue() {
        return netValue;
    }

    public void setNetValue(double netValue) {
        this.netValue = netValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdCityAddress() {
        return idCityAddress;
    }

    public void setIdCityAddress(int idCityAddress) {
        this.idCityAddress = idCityAddress;
    }

    public int getIdProvinceAddress() {
        return idProvinceAddress;
    }

    public void setIdProvinceAddress(int idProvinceAddress) {
        this.idProvinceAddress = idProvinceAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhysicalDocumentDrawer() {
        return physicalDocumentDrawer;
    }

    public void setPhysicalDocumentDrawer(int physicalDocumentDrawer) {
        this.physicalDocumentDrawer = physicalDocumentDrawer;
    }

    public int getIndexPhysicalDocument() {
        return indexPhysicalDocument;
    }

    public void setIndexPhysicalDocument(int indexPhysicalDocument) {
        this.indexPhysicalDocument = indexPhysicalDocument;
    }

    public String getVotersTitle() {
        return votersTitle;
    }

    public void setVotersTitle(String votersTitle) {
        this.votersTitle = votersTitle;
    }

    public int getElectoralZone() {
        return electoralZone;
    }

    public void setElectoralZone(int electoralZone) {
        this.electoralZone = electoralZone;
    }

    public int getElectoralSection() {
        return electoralSection;
    }

    public void setElectoralSection(int electoralSection) {
        this.electoralSection = electoralSection;
    }

    public int getIdProvinceElectoralZone() {
        return idProvinceElectoralZone;
    }

    public void setIdProvinceElectoralZone(int idProvinceElectoralZone) {
        this.idProvinceElectoralZone = idProvinceElectoralZone;
    }

    public int getIdUserRegistration() {
        return idUserRegistration;
    }

    public void setIdUserRegistration(int idUserRegistration) {
        this.idUserRegistration = idUserRegistration;
    }

    public int getIdUserCreate() {
        return idUserCreate;
    }

    public void setIdUserCreate(int idUserCreate) {
        this.idUserCreate = idUserCreate;
    }

    public int getIdUserUpdate() {
        return idUserUpdate;
    }

    public void setIdUserUpdate(int idUserUpdate) {
        this.idUserUpdate = idUserUpdate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    
}
