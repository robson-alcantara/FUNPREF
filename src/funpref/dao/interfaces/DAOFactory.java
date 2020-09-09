package funpref.dao.interfaces;

import funpref.controller.BeneficiaryController;
import funpref.controller.DependentController;
import funpref.controller.JComboBoxModelController;
import funpref.controller.UserController;

public interface DAOFactory {

    public BeneficiaryDAO getBeneficiaryDAO( BeneficiaryController beneficiaryController );

    public DependentDAO getDependentDAO( DependentController dependentController );
    
    public UserDAO getUserDAO( UserController userController );
    
    public JComboBoxItensDAO getJComboBoxItensDAO( JComboBoxModelController jComboBoxModelController);
}
