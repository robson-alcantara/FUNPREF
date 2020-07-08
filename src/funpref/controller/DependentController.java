/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.DependentDAO;
import funpref.model.Dependent;
import funpref.view.dependentView.DependentJInternalFrame;
import java.beans.PropertyVetoException;
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
            dependentJInternalFrame = DependentJInternalFrame.getDependentJInternalFrame( dependent, this);
            dependentJInternalFrame.setEditableFields( crudWrite );
            dependentJInternalFrame.fillJComboBoxes();
            dependentJInternalFrame.fillDependentCRUDJInternalFrame(dependent);
            
            funprefController.getFunprefJFrame().getJDesktopPane().add(dependentJInternalFrame);
            dependentJInternalFrame.setLocation(funprefController.getFunprefJFrame().getJDesktopPane().getLocation().x + ( ( funprefController.getFunprefJFrame().getJDesktopPane().getWidth() - dependentJInternalFrame.getWidth() ) / 2 ),
                    funprefController.getFunprefJFrame().getJDesktopPane().getLocation().y + 10);
            dependentJInternalFrame.toFront();
            dependentJInternalFrame.setSelected(true);
            dependentJInternalFrame.setClosable(true);
            dependentJInternalFrame.setVisible(true);        
        } catch (PropertyVetoException ex) {
            Logger.getLogger(DependentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
