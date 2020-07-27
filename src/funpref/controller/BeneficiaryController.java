/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.BeneficiaryDAO;
import funpref.model.Beneficiary;
import funpref.model.Dependent;
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
    private final FUNPREFController funprefController;
    
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

    public boolean isCrudWrite() {
        return crudWrite;
    }

    public void setCrudWrite(boolean crudWrite) {
        this.crudWrite = crudWrite;
    }    

    public ArrayList<Beneficiary> findByExamplePart(int beneficiaryID, String nameSubstring, boolean updatingSourceBeneficiary, int currentBeneficiaryID ) {
        
        ArrayList<Beneficiary> beneficiaries;
        Beneficiary beneficiary = new Beneficiary();
        
        beneficiary.setId(beneficiaryID);
        beneficiary.setName(nameSubstring);
        beneficiaries = new ArrayList<Beneficiary> (beneficiaryDAO.findByExamplePart(beneficiary, updatingSourceBeneficiary, currentBeneficiaryID) );
        
        return beneficiaries;        
    }    
    
    public Beneficiary findById( int beneficiaryID ) {
        Beneficiary beneficiary = beneficiaryDAO.findByID(beneficiaryID);
        beneficiary.setDependents( new ArrayList<Dependent> (funprefController.getDependentController().findAllByBeneficiaryID(beneficiaryID) ) );
        return beneficiary;
    }
    
    public ArrayList<Object> findAll() {
        return new ArrayList<Object>( beneficiaryDAO.findAll() );        
    }
    
    public ArrayList<ArrayList<Object>> getReportBeneficiaryData( boolean retrieveDeceasedBeneficiaries, boolean retrievePendingBeneficiaries ) {        
        return beneficiaryDAO.getReportBeneficiaryData(retrieveDeceasedBeneficiaries, retrievePendingBeneficiaries);
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
                    funprefController.getFunprefJFrame().getJDesktopPane().getLocation().y - 40 );
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

    public String getCadastralStatusDescriptionById(int id) {
        return beneficiaryDAO.getCadastralStatusDescriptionById(id);
    }
    
    public Dependent getDependentById(int id) {
        int index = -1;
        
        for( int i = 0; i < currentBeneficiary.getDependents().size(); i++ ) {
            if( currentBeneficiary.getDependents().get(i).getId() == id ) {
                index = i;
                i = currentBeneficiary.getDependents().size();
            }
        }        
        
        return currentBeneficiary.getDependents().get(index);
    }    

    public void updateDependentsJTable() {
        beneficiaryJInternalFrame.updateDependentsJTable();
    }

    public void showBeneficiaryJInternalFrame() {
        beneficiaryJInternalFrame.setVisible(true);
    }

    public boolean save() {
        
        boolean validSave;
        
        if( currentBeneficiary.getId() > 0 ) { // atualiza o beneficiário
            validSave = beneficiaryDAO.update(currentBeneficiary);
        }
        
        else {  // insere o beneficiário
            validSave = beneficiaryDAO.insert(currentBeneficiary);
            currentBeneficiary.setId( beneficiaryDAO.getId() );
        }
        
        if( validSave ) {
            validSave = funprefController.getDependentController().save(currentBeneficiary.getDependents(), currentBeneficiary);        
        }
        
        return validSave;
    }

    public void setAndFillSourceBeneficiary(Beneficiary beneficiary) {        
        beneficiaryJInternalFrame.getjTextField48().setText("" + beneficiary.getRegistration());
        beneficiaryJInternalFrame.getjTextField49().setText(beneficiary.getName());
    }

    public int restartCadastralStatus() {
        return beneficiaryDAO.restartCadastralStatus();
    }
}
