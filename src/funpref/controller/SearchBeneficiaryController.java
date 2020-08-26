/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.view.beneficiaryView.SearchBeneficiaryJInternalFrame;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author robson
 */
public class SearchBeneficiaryController {

    private final FUNPREFController funprefController;
    private SearchBeneficiaryJInternalFrame searchJInternalFrame;

    public SearchBeneficiaryController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
    }

    public FUNPREFController getFunprefController() {
        return funprefController;
    }
    
    

    public void show( boolean updatingSourceBeneficiary ) {
        try {
            
            boolean isToAdd = false;
            
            if( searchJInternalFrame == null ) {
                isToAdd = true;
                
                searchJInternalFrame = SearchBeneficiaryJInternalFrame.getSearchBeneficiaryJInternalFrame( this, updatingSourceBeneficiary);

                //if(isToAdd) {
                    funprefController.getFunprefJFrame().getJDesktopPane().add(searchJInternalFrame);
                //}

                searchJInternalFrame.setLocation(funprefController.getFunprefJFrame().getJDesktopPane().getLocation().x + ( ( funprefController.getFunprefJFrame().getJDesktopPane().getWidth() - searchJInternalFrame.getWidth() ) / 2 ),
                        funprefController.getFunprefJFrame().getJDesktopPane().getLocation().y + 10);
                
                searchJInternalFrame.toFront();                
            }
            
            else {
                searchJInternalFrame.setUpdatingSourceBeneficiary(updatingSourceBeneficiary);
                searchJInternalFrame.reset();            
            }

            searchJInternalFrame.setSelected(true);
            searchJInternalFrame.setClosable(true);
            searchJInternalFrame.setVisible(true);
            searchJInternalFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(SearchBeneficiaryController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(SearchBeneficiaryController.class.getName(), ex);
        }
    }    
}


