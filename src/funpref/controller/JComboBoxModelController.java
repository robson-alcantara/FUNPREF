/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.JComboBoxItensDAO;
import funpref.model.Beneficiary;
import funpref.model.combobox.ComboBoxItem;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author robson
 */
public class JComboBoxModelController {
    
    private FUNPREFController funprefController;
    private final JComboBoxItensDAO jComboBoxItensDAO;
    private ArrayList<ComboBoxItem> comboBoxItemsMaritalStatus;
    private ArrayList<ComboBoxItem> comboBoxItemsIssuingBody;
    private ArrayList<ComboBoxItem> comboBoxItemsProvince;
    private ArrayList<ComboBoxItem> comboBoxItemsBenefitType;
    private ArrayList<ComboBoxItem> comboBoxItemsStockingOrgan;
    private ArrayList<ComboBoxItem> comboBoxItemsDeficiency;
    private ArrayList<ComboBoxItem> comboBoxItemsDegreeOfEducation;
    private ArrayList<ComboBoxItem> comboBoxItemsCities;

    JComboBoxModelController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
        jComboBoxItensDAO = new DAOFactoryImpl().getJComboBoxItensDAO();
    }

    public ComboBoxModel<Object> getModel(String table ) {
        
        ArrayList<ComboBoxItem> comboBoxItems = null;
        
        switch (table) {
            case "marital_status":
                if( comboBoxItemsMaritalStatus == null ) {
                    comboBoxItemsMaritalStatus = jComboBoxItensDAO.findAll(table);
                }
                
                comboBoxItems = comboBoxItemsMaritalStatus;
                break;
            case "issuing_body":
                if( comboBoxItemsIssuingBody == null ) {
                    comboBoxItemsIssuingBody = jComboBoxItensDAO.findAll(table);                    
                }
                
                comboBoxItems = comboBoxItemsIssuingBody;
                break;
            case "province":
                if( comboBoxItemsProvince == null ) {
                    comboBoxItemsProvince = jComboBoxItensDAO.findAll(table);                    
                }
                
                comboBoxItems = comboBoxItemsProvince;
                break;
            case "benefit_type":
                if( comboBoxItemsBenefitType == null ) {
                    comboBoxItemsBenefitType = jComboBoxItensDAO.findAll(table);                    
                }
                
                comboBoxItems = comboBoxItemsBenefitType;                
                break;
            case "stocking_organ":
                if( comboBoxItemsStockingOrgan == null ) {
                    comboBoxItemsStockingOrgan = jComboBoxItensDAO.findAll(table);                    
                }
                
                comboBoxItems = comboBoxItemsStockingOrgan;                
                break;                
            case "deficiency":
                if( comboBoxItemsDeficiency == null ) {
                    comboBoxItemsDeficiency = jComboBoxItensDAO.findAll(table);                    
                }
                
                comboBoxItems = comboBoxItemsDeficiency;                
                break;                                
            case "degree_of_education":
                if( comboBoxItemsDegreeOfEducation == null ) {
                    comboBoxItemsDegreeOfEducation = jComboBoxItensDAO.findAll(table);                    
                }
                
                comboBoxItems = comboBoxItemsDegreeOfEducation;                
                break;                        
            default:
                break;
        }
        
        if( comboBoxItems == null ) {
            comboBoxItems = jComboBoxItensDAO.findAll(table);  
        }
        
        return new DefaultComboBoxModel( new Vector( comboBoxItems ) );        
    }
    
    public ComboBoxModel<Object> getCitiesModel(int idProvince) {
        comboBoxItemsCities = jComboBoxItensDAO.findCities(idProvince);
        
        return new DefaultComboBoxModel( new Vector( comboBoxItemsCities ) );        
    }    

    public ComboBoxItem getComboBoxItem(int id, String table) {
        ArrayList<ComboBoxItem> comboBoxItems = null;
        
        switch (table) {
            case "marital_status":
                comboBoxItems = comboBoxItemsMaritalStatus;
                break;
            case "issuing_body":
                comboBoxItems = comboBoxItemsIssuingBody;            
                break;
            case "province":
                comboBoxItems = comboBoxItemsProvince;
                break;
            case "benefit_type":
                comboBoxItems = comboBoxItemsBenefitType;
                break;
            case "stocking_organ":
                comboBoxItems = comboBoxItemsStockingOrgan;
                break;                
            case "deficiency":
                comboBoxItems = comboBoxItemsDeficiency;
                break;                      
            case "degree_of_education":
                comboBoxItems = comboBoxItemsDegreeOfEducation;
                break;                                      
            case "city":
                comboBoxItems = comboBoxItemsCities;
                break;                                                      
            default:
                break;
        }
        
        return comboBoxItems.get( findComboBoxItemIndex( id, comboBoxItems ) );
    }    

    private int findComboBoxItemIndex(int id, ArrayList<ComboBoxItem> comboBoxItems) {
        int index = -1;
        
        for( int i = 0; i < comboBoxItems.size(); i++ ) {
            if( comboBoxItems.get(i).getId() == id ) {
                index = i;
                i = comboBoxItems.size();
            }
        }
        
        return index;
    }
}
