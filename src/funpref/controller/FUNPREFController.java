/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.view.FUNPREFJFrame;
import java.time.Period;

/**
 *
 * @author robson
 */
public class FUNPREFController {
    
    private FUNPREFJFrame funprefJFrame;
    private final BeneficiaryController beneficiaryController;
    private final DependentController dependentController;
    private final SearchBeneficiaryController searchBeneficiaryController;
    private JComboBoxModelController jComboBoxModelController;
    
    int currentUserID;
    
    public FUNPREFController() {
        beneficiaryController = new BeneficiaryController( this );
        dependentController = new DependentController(this);
        searchBeneficiaryController = new SearchBeneficiaryController( this );
        jComboBoxModelController = new JComboBoxModelController(this);
    }

    public void run() {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FUNPREFController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FUNPREFController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FUNPREFController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FUNPREFController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }      
        
        if( validLogin() ) {
            funprefJFrame = new FUNPREFJFrame( this );            
            funprefJFrame.setVisible(true);
        }        
    }
    
    private boolean validLogin() {
        return true;
    }    

    public FUNPREFJFrame getFunprefJFrame() {
        return funprefJFrame;
    }    

    public int getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(int currentUserID) {
        this.currentUserID = currentUserID;
    }    

    public BeneficiaryController getBeneficiaryController() {
        return beneficiaryController;
    }

    public DependentController getDependentController() {
        return dependentController;
    }
    
    public SearchBeneficiaryController getSearchBeneficiaryController() {
        return searchBeneficiaryController;
    }

    public JComboBoxModelController getjComboBoxModelController() {
        return jComboBoxModelController;
    }

    public void setjComboBoxModelController(JComboBoxModelController jComboBoxModelController) {
        this.jComboBoxModelController = jComboBoxModelController;
    }    
    
    public String decodePeriod(Period period) {
        
        String periodString = "";
        
        if( period.getYears() > 0 ) {
            periodString += "" + period.getYears() + " anos";
        }
        
        if( period.getMonths() > 0 ) {
            
            if( !periodString.isEmpty() ) {
                
                if( ( period.getYears() > 0 ) && ( period.getDays() == 0 ) ) {
                    periodString += " e ";
                }
                
                else {                
                    periodString += ", ";
                }
            }
            
            periodString += "" + period.getMonths() + " meses";
        }    
        
        if( period.getDays() > 0 ) {
            
            if( !periodString.isEmpty() ) {
                periodString += " e ";
            }
            
            periodString += "" + period.getDays() + " dias";
        }         
        
        return periodString;
    }        
}
