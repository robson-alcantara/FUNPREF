/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.concrete;

import funpref.controller.DependentController;
import funpref.controller.LogController;
import funpref.dao.interfaces.DependentDAO;
import funpref.model.Beneficiary;
import funpref.model.Dependent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robson
 */
public class DependentDAOImpl implements DependentDAO {

    private int lastInsertDependentId;
    private final DependentController dependentController;

    public DependentDAOImpl(DependentController dependentController) {
        this.dependentController = dependentController;
        
    }    

    @Override
    public boolean insert(Dependent dependent) {
        String query = "INSERT INTO `funpref`.`dependent`\n" +
            "(`name`,\n" +
            "`cpf`,\n" +
            "`birth_date`,\n" +
            "`ref_id_kinship`,\n" +
            "`ref_if_deficiency`,\n" +
            "`ref_id_beneficiary`,\n" +
            "`sex`,\n" +
            "`phone`,\n" +
            "`ref_id_user_create`,\n" +
            "`ref_id_user_update`,\n" +
            "`created_at`,\n" +
            "`updated_at`)\n" +
            "VALUES\n" +
            "(?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +                
            "?)";
        
        String sqlLastInsertId = "SELECT LAST_INSERT_ID()";        
        boolean result = true;
        
        int row = -1;
        int column = 1;
        
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection(dependentController.getFunprefController().getPropertiesController().getDbHost()).prepareStatement(query);          
            preparedStatement.setString(column++, dependent.getName());
            preparedStatement.setString(column++, dependent.getCpf());
            preparedStatement.setDate(column++, new java.sql.Date( dependent.getBirthDate().getTime() ) );
            preparedStatement.setInt(column++, dependent.getIdKinship());
            preparedStatement.setInt(column++, dependent.getIdDeficiency());
            preparedStatement.setInt(column++, dependent.getIdBeneficiary());
                
            if( dependent.getSex() == Beneficiary.Sex.NULL ) {
                preparedStatement.setString(column, null);
            }

            else if( dependent.getSex() == Beneficiary.Sex.MALE ) {
                preparedStatement.setString(column, "m");
            }

            else {
                preparedStatement.setString(column, "f");
            }
            
            column++;
            
            preparedStatement.setString(column++, dependent.getPhone() );                                  
            preparedStatement.setInt(column++, dependent.getIdUserCreate());
            preparedStatement.setInt(column++, dependent.getIdUserUpdate());
            preparedStatement.setTimestamp(column++, new Timestamp( dependent.getCreateDate().getTime() ));
            preparedStatement.setTimestamp(column++, new Timestamp( dependent.getUpdateDate().getTime() ));            
            
            row = preparedStatement.executeUpdate();      
            
            Statement stmt = DAOFactoryImpl.getConnection(dependentController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
            
            ResultSet rs = stmt.executeQuery(sqlLastInsertId);

            if( rs.next() ) {
                lastInsertDependentId = rs.getInt(1);
            }
            
            else {
                row = -1;
                result = false;
            }            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
            result = false;
        }
        
        return result; 
    }

    @Override
    public boolean delete(Dependent dependent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Dependent dependent) {
        String sqlUpdateDependent = "UPDATE `funpref`.`dependent`\n" +
            "SET\n" +            
            "`name` = ?,\n" +
            "`cpf` = ?,\n" +
            "`birth_date` = ?,\n" +
            "`ref_id_kinship` = ?,\n" +
            "`ref_if_deficiency` = ?,\n" +
            "`ref_id_beneficiary` = ?,\n" +
            "`sex` = ?,\n" +
            "`phone` = ?,\n" +
            "`ref_id_user_create` = ?,\n" +
            "`ref_id_user_update` = ?,\n" +                
            "`created_at` = ?,\n" +
            "`updated_at` = ?\n" +                
            "WHERE `id_dependent` = ?";
        
        boolean result = true;
        
        int row = -1;
        int column = 1;
        
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection(dependentController.getFunprefController().getPropertiesController().getDbHost()).prepareStatement(sqlUpdateDependent);          
            preparedStatement.setString(column++, dependent.getName());
            preparedStatement.setString(column++, dependent.getCpf());
            preparedStatement.setDate(column++, new java.sql.Date( dependent.getBirthDate().getTime() ) );
            preparedStatement.setInt(column++, dependent.getIdKinship());
            preparedStatement.setInt(column++, dependent.getIdDeficiency());
            preparedStatement.setInt(column++, dependent.getIdBeneficiary());
                
            if( dependent.getSex() == Beneficiary.Sex.NULL ) {
                preparedStatement.setString(column, null);
            }

            else if( dependent.getSex() == Beneficiary.Sex.MALE ) {
                preparedStatement.setString(column, "m");
            }

            else {
                preparedStatement.setString(column, "f");
            }
            
            column++;
            
            preparedStatement.setString(column++, dependent.getPhone() );
            preparedStatement.setInt(column++, dependent.getIdUserCreate());
            preparedStatement.setInt(column++, dependent.getIdUserUpdate());
            preparedStatement.setTimestamp(column++, new Timestamp( dependent.getCreateDate().getTime() ));
            preparedStatement.setTimestamp(column++, new Timestamp( dependent.getUpdateDate().getTime() ));
                        
            preparedStatement.setInt(column++, dependent.getId());
            
            row = preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
            result = false;
        }
        
        return result; 
    }    
    
    public List<Dependent> findAllByBeneficiaryID(int beneficiaryID) {
        ArrayList<Dependent> result = new ArrayList<>(); 
        int column;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection(dependentController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
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
                dependent.setIdBeneficiary(resultSet.getInt(column++));
                
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
                dependent.setKinship(loadKinship(dependent.getIdKinship()));
                dependent.setDeficiency(loadDeficiency(dependent.getIdDeficiency()));

                result.add(dependent);
            }
        } catch (SQLException ex) {
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
            return null;
        }
        return result;
        
    }

    @Override
    public String getKinshipByID(int kinshipID) {
        String description = "";
        
        String query = "SELECT description FROM funpref.kinship where id_kinship = '" + kinshipID + "'";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection(dependentController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if( resultSet.next() ) {            
                description = resultSet.getString(1);
            }

        } catch (SQLException ex) {            
            System.out.println(ex.getMessage());
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
        }        
        
        return description;
    }    

    private String loadKinship(int idKinship) {
        String kinship = null;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection(dependentController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM kinship WHERE id_kinship = " + idKinship );

            if (resultSet.next()) {                
                kinship = resultSet.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
        }        
        
        return kinship;
    }
    
    private String loadDeficiency(int idDeficiency) {
        String deficiency = null;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection(dependentController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM deficiency WHERE id_deficiency = " + idDeficiency );

            if (resultSet.next()) {                
                deficiency = resultSet.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
        }        
        
        return deficiency;
    }    
    
    public ArrayList<ArrayList<Object>> getReportDependentData() {
        ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> dependentData;
        
        String sql = "SELECT funpref.dependent.name, funpref.dependent.cpf, funpref.dependent.phone, "
                + "funpref.beneficiary.name, funpref.kinship.description\n "
                + "FROM funpref.beneficiary\n "
                + "inner join funpref.dependent on funpref.dependent.ref_id_beneficiary = funpref.beneficiary.id_beneficiary\n "
                + "inner join funpref.kinship on funpref.kinship.id_kinship = funpref.dependent.ref_id_kinship\n "
                + "order by funpref.beneficiary.name, funpref.dependent.name";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection(dependentController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {  
                dependentData = new ArrayList<Object>();
                dependentData.add( rs.getString(1) );
                dependentData.add( rs.getString(2) );
                dependentData.add( rs.getString(3) );
                dependentData.add( rs.getString(4) );
                dependentData.add( rs.getString(5) );
                data.add(dependentData);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
        }        
        
        return data;
    }        
}
