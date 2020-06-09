/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.model;

import funpref.controller.JDBCController;
import funpref.model.dao.UserDAO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author Robson
 */
public class FUNPREFModel {
    
    private Beneficiary currentBeneficiary;
    private final SimpleDateFormat formatDate;
    
    private ArrayList<Double> incomeTaxRates;
    private ArrayList<Double> incomeTaxValues;    
    private JDBCController jdbcController;
    
    public FUNPREFModel() {
        formatDate = new SimpleDateFormat("dd/MM/yyyy");
        
        incomeTaxRates = new ArrayList<Double>();
        incomeTaxValues = new ArrayList<Double>();         
    }

    public FUNPREFModel(JDBCController jdbcController) {
        this.jdbcController = jdbcController;
        
        formatDate = new SimpleDateFormat("dd/MM/yyyy");
        
        incomeTaxRates = new ArrayList<Double>();
        incomeTaxValues = new ArrayList<Double>();          
    }

    public void loadBeneficiary(int index) {
        //currentBeneficiary = jdb
        
//        try {        
//            currentBeneficiary.ordinance = 356;
//            currentBeneficiary.name = "Maria de Lourdes Santana";
//            currentBeneficiary.sex = Beneficiary.Sex.FEMALE;
//            currentBeneficiary.birthDate = formatDate.parse("16/10/1949");
//            currentBeneficiary.maritalStatus = Beneficiary.MaritalStatus.SINGLE;
//            currentBeneficiary.deceased = false;
//            //currentInative.deathDate = formatDate.parse("21/10/1949");
//            currentBeneficiary.rg = "906371 SSP/PE";
//            currentBeneficiary.rgEmissionDate = formatDate.parse("01/10/1960");
//            currentBeneficiary.cpf = 2501228472L;
//            currentBeneficiary.motherName = "Maria Carmelita Santana";
//            currentBeneficiary.fatherName = "Espedito Santana de Lima";
//            currentBeneficiary.rule = Beneficiary.Rule.SERVICE_CONTRIBUTION_AGE;
//            currentBeneficiary.form = Beneficiary.Form.YEARS;
//            currentBeneficiary.admissionDate = formatDate.parse("01/10/1965");
//            currentBeneficiary.inactivationDate = formatDate.parse("01/04/1992");
//            currentBeneficiary.applicationDate = formatDate.parse("03/01/1992");
//            currentBeneficiary.contributionTime = Period.between(currentBeneficiary.admissionDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
//                    currentBeneficiary.inactivationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//            currentBeneficiary.contributionTimeFUNPREF = Period.between(formatDate.parse("01/01/1994").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
//                    currentBeneficiary.inactivationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//            currentBeneficiary.inactivityTime = Period.between(currentBeneficiary.inactivationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
//                    (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//            currentBeneficiary.registration = 410;
//            currentBeneficiary.office = "Auxiliar Administrativo";
//            currentBeneficiary.stockingOrgan = Beneficiary.StockingOrgan.ADMINISTRATION_SECRETARY;
//            currentBeneficiary.invalidityReason = "";
//            currentBeneficiary.invalidityAwardDate = null;
//            
//            Dependent dependent = new Dependent();
//            dependent.code = 1;
//            dependent.name = "Robson Alcântara Santana";
//            dependent.degreeOfKinship = Dependent.DegreeOfKinship.OTHERS;
//            dependent.sex = Dependent.Sex.MALE;
//            dependent.birthDate = formatDate.parse("06/01/1983");
//            dependent.age = Period.between(dependent.birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
//                    (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//            dependent.phone = "(81)99649-2372";
//            
//            currentBeneficiary.dependents = new ArrayList<Dependent>();
//            currentBeneficiary.dependents.add(dependent);
//            
//            currentBeneficiary.bankAgency = "1060-X";
//            currentBeneficiary.account = "6000-8";
//            currentBeneficiary.earningsInative = 2000.0f;
//            currentBeneficiary.oldPromotion = true;
//            currentBeneficiary.oldPromotionValue = 100.0f;
//            currentBeneficiary.chalkPowder = false;
//            currentBeneficiary.chalkPowderValue = 0.0f;
//            currentBeneficiary.moreOneYear = true;
//            currentBeneficiary.moreOneYearPercentual = calculateMoreOneYearPercentual( currentBeneficiary.contributionTime, currentBeneficiary.moreOneYear );
//            currentBeneficiary.moreOneYearValue = currentBeneficiary.moreOneYearPercentual * currentBeneficiary.earningsInative;
//            currentBeneficiary.moreFiveYear = false;  
//            currentBeneficiary.moreFiveYearPercentual = calculateMoreFiveYearPercentual( currentBeneficiary.contributionTime, currentBeneficiary.moreFiveYear );
//            currentBeneficiary.moreFiveYearValue = currentBeneficiary.moreFiveYearPercentual * currentBeneficiary.earningsInative;            
//            currentBeneficiary.incomeTaxRate = calculateIncomeTaxRate(currentBeneficiary.earningsInative);
//            currentBeneficiary.incomeTaxValue = (double)currentBeneficiary.earningsInative * currentBeneficiary.incomeTaxRate;
//            currentBeneficiary.payrollLoan = false;
//            currentBeneficiary.payrollLoanValue = 0.0f;
//            currentBeneficiary.grossValue = currentBeneficiary.earningsInative + currentBeneficiary.oldPromotionValue +
//                    currentBeneficiary.moreOneYearValue + currentBeneficiary.moreFiveYearValue +
//                    currentBeneficiary.chalkPowderValue;
//            currentBeneficiary.discounts = (float)currentBeneficiary.incomeTaxValue + currentBeneficiary.payrollLoanValue;
//            currentBeneficiary.netValue = currentBeneficiary.grossValue - currentBeneficiary.discounts;
//            
//            currentBeneficiary.address = "Rua Dr. Santana Filho, nº 61, Centro";
//            currentBeneficiary.city = "Flores-PE";
//            currentBeneficiary.zipCode = 56850000;
//            currentBeneficiary.phone1 = "(87) 3857-1145";
//            currentBeneficiary.phone2 = "";
//            currentBeneficiary.email = "mariadelourdessantana@hotmail.com";
//            
//            currentBeneficiary.physicalDocumentDrawer = 5; 
//            currentBeneficiary.indexPhysicalDocument = "65";
//        } catch (ParseException ex) {
//            Logger.getLogger(FUNPREFModel.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private double calculateMoreOneYearPercentual(Period contributionTime, boolean hasBenefit) {
        double percentual = 0.0;
        
        if (hasBenefit) {
            percentual = 0.01;
        }
            
        return percentual;
    }

    private double calculateMoreFiveYearPercentual(Period contributionTime, boolean hasBenefit) {
        double percentual = 0.0;
        
        if (hasBenefit) {
            percentual = 0.01;
        }
            
        return percentual;
    }    

    private double calculateIncomeTaxRate(double earningsInative) {
        double incomeTaxRate =  0.0;
        
        if ( earningsInative >= 4664.68 ) {
            incomeTaxRate = 0.275;
        }
        
        else if ( earningsInative >= 3751.06 ) {
            incomeTaxRate = 0.225;
        }
        
        else if ( earningsInative >= 2826.66 ) {
            incomeTaxRate = 0.15;
        } 
        
        else if ( earningsInative >= 1903.98 ) {
            incomeTaxRate = 0.075;
        }         
        
        return incomeTaxRate;
    }

    public Beneficiary getBeneficiary() {
        return currentBeneficiary;
    }

    public void saveBeneficiary(Beneficiary beneficiary, UserDAO user) {
        jdbcController.saveBeneficiary( beneficiary, user );
    }

    public Dependent getDependentByCode(int id) {
        int index = -1;
        
        for( int i = 0; i < currentBeneficiary.getDependents().size(); i++ ) {
            if( currentBeneficiary.getDependents().get(i).getId() == id ) {
                index = i;
                i = currentBeneficiary.getDependents().size();
            }
        }        
        
        return currentBeneficiary.getDependents().get(index);
    }

    public void saveDependent(Dependent dependent) {
        if( hasDependent( dependent, currentBeneficiary ) ) {
            updateDependent( dependent, currentBeneficiary );
        }
        
        else {
            //dependent.setId( generateCode(currentBeneficiary.getDependents()) );
            currentBeneficiary.getDependents().add(dependent);
        }
    }

    private boolean hasDependent(Dependent dependent, Beneficiary beneficiary) {
        boolean has = false;
        
        for( int i = 0; i < currentBeneficiary.getDependents().size(); i++ ) {
            if( beneficiary.getDependents().get(i).getId() == dependent.getId() ) {
                has = true;
                i = beneficiary.getDependents().size();
            }
        }        
        
        return has;
    }

    private void updateDependent(Dependent dependent, Beneficiary beneficiary) {
        int dependentIndex;
        
        dependentIndex = getDependentIndexByCode(dependent, beneficiary);
        beneficiary.getDependents().get(dependentIndex).setName( dependent.getName() ) ;
        beneficiary.getDependents().get(dependentIndex).setCpf(dependent.getCpf()) ;
        beneficiary.getDependents().get(dependentIndex).setIdKinship( dependent.getIdKinship() );
        beneficiary.getDependents().get(dependentIndex).setIdDeficiency(dependent.getIdDeficiency());
        beneficiary.getDependents().get(dependentIndex).setSex( dependent.getSex() );
        beneficiary.getDependents().get(dependentIndex).setBirthDate( dependent.getBirthDate() );
        beneficiary.getDependents().get(dependentIndex).setPhone( dependent.getPhone() );
        beneficiary.getDependents().get(dependentIndex).setAge(dependent.getAge());
        
    }

    private int getDependentIndexByCode(Dependent dependent, Beneficiary inative) {
        int index = -1;
        
        for( int i = 0; i < currentBeneficiary.getDependents().size(); i++ ) {
            if( inative.getDependents().get(i).getId() == dependent.getId() ) {
                index = i;
                i = inative.getDependents().size();
            }
        }        
        
        return index;
    }
    
    private boolean hasThisNumberInDependents(int id, ArrayList<Dependent> dependents) {
        boolean foundAId = false;
        for( int i = 0; i < dependents.size(); i++ ) {
            if( id == dependents.get(i).getId() ) {
                foundAId = true;
            }
        }
        return foundAId;
    }   

    private int generateCode(ArrayList<Dependent> dependents) {
        int code = 0;
        boolean foundACode = false;
        
        while(!foundACode) {
            code++;
            foundACode = !hasThisNumberInDependents( code, dependents );
        }        
        
        return code; 
    }

    public ArrayList<Double> getIncomeTaxRates() {
        return incomeTaxRates;
    }

    public ArrayList<Double> getIncomeTaxValues() {
        return incomeTaxValues;
    }

    public void readIncomeTax() {
        String line;        
        String[] fields;        
        String incomeTaxFilePath = "./resources/income_tax";
        
        incomeTaxRates.clear();
        incomeTaxValues.clear();
        
        try {     
            
            FileInputStream fileInputStream = new FileInputStream(incomeTaxFilePath);
            InputStreamReader inputStreamReader = new InputStreamReader( fileInputStream, StandardCharsets.ISO_8859_1 );
            BufferedReader bufferedReader = new BufferedReader( inputStreamReader );             
                   
            try {                
                while ((line = bufferedReader.readLine()) != null) {
                    fields = line.split(";");
                    incomeTaxValues.add(Double.parseDouble(fields[0]));
                    incomeTaxRates.add(Double.parseDouble(fields[1]));
                }
                
                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FUNPREFModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FUNPREFModel.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }   

    public int saveIncomeTax(JTable incomeTaxJTable) {
        int status = 1;
        int counter = 0;
        ArrayList<Double> incomeTaxRates;
        ArrayList<Double> incomeTaxValues;
        
        incomeTaxRates = new ArrayList<Double>();
        incomeTaxValues = new ArrayList<Double>();         
        
        do {
            if(( incomeTaxJTable.getValueAt( counter, 0 ) != null ) && (!incomeTaxJTable.getValueAt( counter, 0 ).toString().isEmpty())) {
                incomeTaxValues.add( Double.parseDouble(incomeTaxJTable.getValueAt( counter, 0 ).toString()));
                if(( incomeTaxJTable.getValueAt( counter, 1 ) != null ) && (!incomeTaxJTable.getValueAt( counter, 1 ).toString().isEmpty())) {
                    incomeTaxRates.add( Double.parseDouble(incomeTaxJTable.getValueAt( counter, 1 ).toString())/100.0);
                }
                
                else {
                    status = -1;
                }
            }
            
            else {
                if(( incomeTaxJTable.getValueAt( counter, 1 ) != null ) && (!incomeTaxJTable.getValueAt( counter, 1 ).toString().isEmpty())) {
                    status = -1;
                }
                
                else {
                    status = 0;
                }
            }
            
            counter++;
        }while(status>0);
        
        try {
            
            FileOutputStream fileOutputStream = new FileOutputStream("./resources/income_tax");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( fileOutputStream, StandardCharsets.ISO_8859_1 );
            BufferedWriter writer = new BufferedWriter( outputStreamWriter );            
            
            try {
                for( int i = 0; i < incomeTaxValues.size(); i++) {                   
                    writer.append(incomeTaxValues.get(i) + ";");
                    writer.append(incomeTaxRates.get(i) + "\n");                    
                }            
                writer.close(); 
                outputStreamWriter.close();
                fileOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FUNPREFModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FUNPREFModel.class.getName()).log(Level.SEVERE, null, ex);
        }        
                
        return status;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.currentBeneficiary = beneficiary;
    }

    public int getLastInsertId() {
        return jdbcController.getLastInsertId();
    }
}
