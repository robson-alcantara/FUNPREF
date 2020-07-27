/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.controller.report.ReportController;
import funpref.model.User;
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
    private final ReportController reportController;
    private final JComboBoxModelController jComboBoxModelController;
    private final UserController userController;
    
    
    private int currentUserID;
    private User user;
    
    
    public FUNPREFController() {
        beneficiaryController = new BeneficiaryController( this );
        dependentController = new DependentController(this);
        searchBeneficiaryController = new SearchBeneficiaryController( this );
        jComboBoxModelController = new JComboBoxModelController(this);
        reportController = new ReportController(this);
        userController = new UserController(this);
    }

    public void run() {
        
        /* Set the GTK+ or Windows Look and Feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            
            boolean hasWindowsUIManager = false;
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ( info.getName().contains("Windows") ) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    hasWindowsUIManager = true;
                    break;
                }
            }
            
            if( !hasWindowsUIManager ) {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ( "GTK+".equals(info.getName()) ) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
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
        //TODO: temporário, remover
        user = userController.findByID(currentUserID);
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

    public ReportController getReportController() {
        return reportController;
    }    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
