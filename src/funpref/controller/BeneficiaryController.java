/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.BeneficiaryDAO;
import funpref.model.Beneficiary;
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
    private FUNPREFController funprefController;
    
    private Beneficiary currentBeneficiary;    
    private boolean crudWrite;
    

    BeneficiaryController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
        beneficiaryDAO = new DAOFactoryImpl().getBeneficiaryDAO();
        crudWrite = false;
    }

    public Beneficiary getCurrentBeneficiary() {
        return currentBeneficiary;
    }

    public void setCurrentBeneficiary(Beneficiary currentBeneficiary) {
        this.currentBeneficiary = currentBeneficiary;
    } 

    public FUNPREFController getFunprefController() {
        return funprefController;
    }

    public void setFunprefController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
    }

    public boolean isCrudWrite() {
        return crudWrite;
    }

    public void setCrudWrite(boolean crudWrite) {
        this.crudWrite = crudWrite;
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
            beneficiaryJInternalFrame = BeneficiaryJInternalFrame.getBeneficiaryJInternalFrame( currentBeneficiary, funprefController.getCurrentUserID(), this);
            beneficiaryJInternalFrame.setEditableFields( crudWrite );
            //beneficiaryJInternalFrame.prepareFields();
            
            if( currentBeneficiary.getId() != -1 ) {
                beneficiaryJInternalFrame.fillFields();
            }
            
            funprefController.getFunprefJFrame().getJDesktopPane().add(beneficiaryJInternalFrame);
            beneficiaryJInternalFrame.setLocation(funprefController.getFunprefJFrame().getJDesktopPane().getLocation().x + ( ( funprefController.getFunprefJFrame().getJDesktopPane().getWidth() - beneficiaryJInternalFrame.getWidth() ) / 2 ),
                    funprefController.getFunprefJFrame().getJDesktopPane().getLocation().y + 10);
            beneficiaryJInternalFrame.toFront();
            beneficiaryJInternalFrame.setSelected(true);
            beneficiaryJInternalFrame.setClosable(true);
            beneficiaryJInternalFrame.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(BeneficiaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNameById(int id) {
        return beneficiaryDAO.getNameByID(id);
    }
}
