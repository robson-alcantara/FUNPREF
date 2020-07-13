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
    
    public boolean insert(Beneficiary beneficiary);

    public boolean delete(Beneficiary beneficiary);

    public boolean update(Beneficiary beneficiary);

    public Beneficiary findByID(int beneficiaryID);
    
    public String getNameByID(int beneficiaryID);
    
    public String getCadastralStatusDescriptionById(int id);

    public List<Beneficiary> findAll();
    
    public List<Beneficiary> findByExample(Beneficiary beneficiary);    
    
    public List<Beneficiary> findByExamplePart(Beneficiary beneficiary);    

    public int getId();
}
