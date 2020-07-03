package funpref.dao.interfaces;

public interface DAOFactory {

    public BeneficiaryDAO getBeneficiaryDAO();

    public DependentDAO getDependentDAO();
    
    public JComboBoxItensDAO getJComboBoxItensDAO();
}
