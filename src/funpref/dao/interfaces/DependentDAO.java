/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.interfaces;

import funpref.model.Dependent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robson
 */
public interface DependentDAO {
    
    public boolean insert(Dependent dependent);

    public boolean delete(Dependent dependent);

    public boolean update(Dependent dependent);    
    
    public List<Dependent> findAllByBeneficiaryID(int beneficiaryID);
    
    public String getKinshipByID(int kinshipID);    
    
    public ArrayList<ArrayList<Object>> getReportDependentData();
    
}
