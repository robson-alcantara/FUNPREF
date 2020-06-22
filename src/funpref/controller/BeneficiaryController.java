/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.BeneficiaryDAO;
import funpref.model.Beneficiary;
import funpref.view.FUNPREFJFrame;
import funpref.view.beneficiaryView.BeneficiaryJInternalFrame;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robson
 */
public class BeneficiaryController {
    
    private final BeneficiaryDAO beneficiaryDAO;
    private BeneficiaryJInternalFrame beneficiaryJInternalFrame;
    private FUNPREFJFrame funprefJFrame;
    
    private Beneficiary currentBeneficiary;
    int currentUserID;

    public BeneficiaryController() {
        beneficiaryDAO = new DAOFactoryImpl().getBeneficiaryDAO();
    }    

    public Beneficiary getCurrentBeneficiary() {
        return currentBeneficiary;
    }

    public void setCurrentBeneficiary(Beneficiary currentBeneficiary) {
        this.currentBeneficiary = currentBeneficiary;
    }    

    public int getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(int currentUserID) {
        this.currentUserID = currentUserID;
    }    

    public FUNPREFJFrame getFunprefJFrame() {
        return funprefJFrame;
    }

    public void setFunprefJFrame(FUNPREFJFrame funprefJFrame) {
        this.funprefJFrame = funprefJFrame;
    }
    
    

    public ArrayList<Beneficiary> findByExamplePart(int beneficiaryID, String nameSubstring ) {
        
        ArrayList<Beneficiary> beneficiaries;
        Beneficiary beneficiary = new Beneficiary();
        
        beneficiary.setId(beneficiaryID);
        beneficiary.setName(nameSubstring);
        beneficiaries = new ArrayList<Beneficiary> (beneficiaryDAO.findByExamplePart(beneficiary) );
        
        return beneficiaries;        
    }    
    
    public Beneficiary findById( int beneficiaryID ) {
        return beneficiaryDAO.findByID(beneficiaryID);
    }

    public void fillAndShowBeneficiaryJInternalFrame() {
        try {
            beneficiaryJInternalFrame = new BeneficiaryJInternalFrame( currentBeneficiary, currentUserID, this);
            beneficiaryJInternalFrame.fillFields();
            
            funprefJFrame.getJDesktopPane().add(beneficiaryJInternalFrame);
            beneficiaryJInternalFrame.setLocation(funprefJFrame.getJDesktopPane().getLocation().x + ( ( funprefJFrame.getJDesktopPane().getWidth() - beneficiaryJInternalFrame.getWidth() ) / 2 ),
                    funprefJFrame.getJDesktopPane().getLocation().y + 10);
            beneficiaryJInternalFrame.toFront();
            beneficiaryJInternalFrame.setSelected(true);
            beneficiaryJInternalFrame.setClosable(true);
            beneficiaryJInternalFrame.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(BeneficiaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
