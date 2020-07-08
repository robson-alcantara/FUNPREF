/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.interfaces;

import funpref.model.Dependent;
import java.util.List;

/**
 *
 * @author robson
 */
public interface DependentDAO {
    
    public List<Dependent> findAllByBeneficiaryID(int beneficiaryID);
    
    public String getKinshipByID(int kinshipID);    
    
}
