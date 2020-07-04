/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.interfaces;

import funpref.model.Beneficiary;
import java.util.List;

/**
 *
 * @author robson
 */
public interface BeneficiaryDAO {
    
    public boolean insertBeneficiary(Beneficiary beneficiary);

    public boolean deleteBeneficiary(Beneficiary beneficiary);

    public boolean updateBeneficiary(Beneficiary beneficiary);

    public Beneficiary findByID(int beneficiaryID);
    
    public String getNameByID(int beneficiaryID);

    public List<Beneficiary> findAll();
    
    public List<Beneficiary> findByExample(Beneficiary beneficiary);    
    
    public List<Beneficiary> findByExamplePart(Beneficiary beneficiary);
    
}
