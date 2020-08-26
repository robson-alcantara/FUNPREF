/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.DependentDAO;
import funpref.model.Beneficiary;
import funpref.model.Dependent;
import funpref.view.dependentView.DependentJInternalFrame;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robson
 */
public class DependentController {
    
    private final DependentDAO dependentDAO;
    private DependentJInternalFrame dependentJInternalFrame;
    private final FUNPREFController funprefController;
    private boolean crudWrite;
    
    public DependentController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
        dependentDAO = new DAOFactoryImpl().getDependentDAO();
    }
    
    public FUNPREFController getFunprefController() {
        return funprefController;
    }
    
    public List<Dependent> findAllByBeneficiaryID( int beneficiaryId ) {
        return dependentDAO.findAllByBeneficiaryID(beneficiaryId);
    }

    public String getKinshipById( int id ) {
        return dependentDAO.getKinshipByID(id);
    }

    public boolean isCrudWrite() {
        return crudWrite;
    }

    public void setCrudWrite(boolean crudWrite) {
        this.crudWrite = crudWrite;
    }    

    public void fillAndShowDependentCRUDJInternalFrame(Dependent dependent, boolean crudWrite ) {
        try {  
            
            if( dependentJInternalFrame == null ) {
                dependentJInternalFrame = DependentJInternalFrame.getDependentJInternalFrame( dependent, this);
                funprefController.getFunprefJFrame().getJDesktopPane().add(dependentJInternalFrame);
                dependentJInternalFrame.setLocation(funprefController.getFunprefJFrame().getJDesktopPane().getLocation().x + ( ( funprefController.getFunprefJFrame().getJDesktopPane().getWidth() - dependentJInternalFrame.getWidth() ) / 2 ),
                        funprefController.getFunprefJFrame().getJDesktopPane().getLocation().y + 10);                
            }
            
            else {
                dependentJInternalFrame.setCurrentDependent(dependent);
                dependentJInternalFrame.reset();                
            }
            
            dependentJInternalFrame.setEditableFields( crudWrite );
            dependentJInternalFrame.fillJComboBoxes();
            dependentJInternalFrame.fillFields(dependent);
            

            dependentJInternalFrame.toFront();
            dependentJInternalFrame.setSelected(true);
            dependentJInternalFrame.setClosable(true);
            dependentJInternalFrame.setVisible(true);        
        } catch (PropertyVetoException ex) {
            Logger.getLogger(DependentController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(DependentController.class.getName(), ex);
        }
    }

    public void add(Dependent dependent) {
        Beneficiary beneficiary = funprefController.getBeneficiaryController().getCurrentBeneficiary();
        if( has( dependent, beneficiary ) ) {
            update( dependent, beneficiary );
        }
        
        else {            
            beneficiary.getDependents().add(dependent);
        }
    }
    
    private boolean has(Dependent dependent, Beneficiary beneficiary) {
        boolean has = false;
        
        for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
            if( beneficiary.getDependents().get(i).getId() == dependent.getId() ) {
                has = true;
                i = beneficiary.getDependents().size();
            }
        }        
        
        return has;
    }

    private void update(Dependent dependent, Beneficiary beneficiary) {
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

    private int getDependentIndexByCode(Dependent dependent, Beneficiary beneficiary) {
        int index = -1;
        
        for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
            if( beneficiary.getDependents().get(i).getId() == dependent.getId() ) {
                index = i;
                i = beneficiary.getDependents().size();
            }
        }        
        
        return index;
    }    

    boolean save(ArrayList<Dependent> dependents, Beneficiary beneficiary) {
        
        boolean validSave = true;
        
        for(Dependent dependent : dependents ) {
            dependent.setIdBeneficiary( beneficiary.getId() );
            validSave &= saveDependent(dependent);
        }
        
        return validSave;
    }

    private boolean saveDependent(Dependent dependent) {
        
        boolean validSave = false;
        
        if( dependent.getId() > 0 ) { // atualiza
            validSave = dependentDAO.update(dependent);
        }
        
        else { // insere
            validSave = dependentDAO.insert(dependent);
        }
        
        return validSave;
    }

    public ArrayList<ArrayList<Object>> getReportDependentData() {
        return dependentDAO.getReportDependentData();
    }

    
    
}
