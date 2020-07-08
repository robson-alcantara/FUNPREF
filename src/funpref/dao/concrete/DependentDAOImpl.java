/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.concrete;

import funpref.dao.interfaces.DependentDAO;
import funpref.model.Beneficiary;
import funpref.model.Dependent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author robson
 */
public class DependentDAOImpl implements DependentDAO {

    public DependentDAOImpl() {
        
    }
    
    public List<Dependent> findAllByBeneficiaryID(int beneficiaryID) {
        ArrayList<Dependent> result = new ArrayList<>(); 
        int column;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM funpref.dependent "
                    + "WHERE ref_id_beneficiary = " + beneficiaryID + " ORDER BY name");

            while (resultSet.next()) {
                
                Dependent dependent = new Dependent();
                
                column = 1;
                dependent = new Dependent();
                dependent.setId(resultSet.getInt(column++));
                dependent.setName(resultSet.getString(column++));
                dependent.setCpf(resultSet.getString(column++));
                dependent.setBirthDate(resultSet.getDate(column++));
                dependent.setIdKinship(resultSet.getInt(column++));
                dependent.setIdDeficiency(resultSet.getInt(column++));
                column++; // idBeneficiary
                
                if( resultSet.getString(column) == null ) {
                    dependent.setSex(Beneficiary.Sex.NULL);
                }
                
                else if( resultSet.getString(column).compareTo("m") == 0 ) {
                    dependent.setSex(Beneficiary.Sex.MALE);
                }
                
                else {
                    dependent.setSex(Beneficiary.Sex.FEMALE);
                }
                
                column++;
                
                dependent.setPhone(resultSet.getString(column++));
                
                if( dependent.getBirthDate()!= null ) {
                    dependent.setAge(Period.between(new java.util.Date(dependent.getBirthDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                            (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) );                   
                }                
                
                dependent.setIdUserCreate(resultSet.getInt(column++));
                dependent.setIdUserUpdate(resultSet.getInt(column++));
                dependent.setCreateDate(resultSet.getTimestamp(column++));
                dependent.setUpdateDate(resultSet.getTimestamp(column++));

                result.add(dependent);
            }
        } catch (SQLException ex) {
            return null;
        }
        return result;
        
    }

    @Override
    public String getKinshipByID(int kinshipID) {
        String description = "";
        
        String query = "SELECT description FROM funpref.kinship where id_kinship = '" + kinshipID + "'";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if( resultSet.next() ) {            
                description = resultSet.getString(1);
            }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }        
        
        return description;
    }    
}
