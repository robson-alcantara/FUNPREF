/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.concrete;

import funpref.controller.LogController;
import funpref.controller.UserController;
import funpref.dao.interfaces.UserDAO;
import funpref.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robson
 */
public class UserDAOImpl implements UserDAO{

    private int lastInsertDependentId;
    private final UserController userController;

    UserDAOImpl(UserController userController) {
        this.userController = userController;
    }

    @Override
    public User findByID(int userID) {
        User user = new User();
        
        try {
            Statement statement = DAOFactoryImpl.getConnection(userController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE id_user = " + userID + " and active = 1" );

            if (resultSet.next()) {                
                user.setId(userID);
                user.setIdPermition(resultSet.getInt(2));
                user.setName(resultSet.getString(3));
                user.setCpf(resultSet.getString(4));
                user.setOffice(resultSet.getString(5));
                user.setLogin(resultSet.getString(6));
                user.setActive(resultSet.getBoolean(9));                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
        }        
        
        return user;        
    }
    
    @Override
    public List<User> findAll() {
        
        ArrayList<User> result = new ArrayList<>(); 
        
        try {
            Statement statement = DAOFactoryImpl.getConnection(userController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM funpref.user where login <> 'admin' order by name ");

            while (resultSet.next()) {
                
                User user = new User();
                
                user.setId(resultSet.getInt(1));
                user.setIdPermition(resultSet.getInt(2));
                user.setName(resultSet.getString(3));
                user.setCpf(resultSet.getString(4));
                user.setOffice(resultSet.getString(5));
                user.setLogin(resultSet.getString(6));
                user.setActive(resultSet.getBoolean(9));

                result.add(user);
            }
        } catch (SQLException ex) {
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
            return null;
        }        
        
        return result;
    }

    @Override
    public boolean update(User user) {
        String query = "UPDATE `funpref`.`user`\n" +
            "SET\n" +            
            "`ref_id_permition` = ?,\n" +
            "`name` = ?,\n" +                
            "`cpf` = ?,\n" +
            "`office` = ?,\n" +
            "`login` = ?,\n" +
            "`active` = ?,\n" +
            "`updated_at` = ?\n" +
            "WHERE `id_user` = ?";
        
        boolean result = true;
        
        int row = -1;
        int column = 1;
        
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection(userController.getFunprefController().getPropertiesController().getDbHost()).prepareStatement(query);          
            preparedStatement.setInt(column++, user.getIdPermition());
            preparedStatement.setString(column++, user.getName());
            preparedStatement.setString(column++, user.getCpf());
            preparedStatement.setString(column++, user.getOffice() );
            preparedStatement.setString(column++, user.getLogin() );
            preparedStatement.setBoolean(column++, user.isActive());
            preparedStatement.setTimestamp(column++, new Timestamp( (new Date()).getTime() ));
                        
            preparedStatement.setInt(column++, user.getId());
            
            row = preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LogController.reportException(DependentDAOImpl.class.getName(), ex);
            result = false;
        }
        
        if( result && ( user.getPassword() != null ) ) {
            result = savePassword(user);
        }
        
        return result; 
    }

    @Override
    public boolean insert(User user) {
        String query = "INSERT INTO `funpref`.`user`\n" +
            "(`ref_id_permition`,\n" +
            "`name`,\n" +                
            "`cpf`,\n" +
            "`office`,\n" +
            "`login`,\n" +
            "`salt`,\n" +
            "`password`,\n" +                
            "`active`,\n" +
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
            "?)";
        
        String sqlLastInsertId = "SELECT LAST_INSERT_ID()";        
        boolean result = true;
        
        int row = -1;
        int column = 1;
        
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection(userController.getFunprefController().getPropertiesController().getDbHost()).prepareStatement(query);          
            preparedStatement.setInt(column++, user.getIdPermition());
            preparedStatement.setString(column++, user.getName());
            preparedStatement.setString(column++, user.getCpf());
            preparedStatement.setString(column++, user.getOffice() );
            preparedStatement.setString(column++, user.getLogin() );
            preparedStatement.setString(column++, "" );
            preparedStatement.setString(column++, "" );
            preparedStatement.setBoolean(column++, user.isActive());
            preparedStatement.setTimestamp(column++, new Timestamp( (new Date()).getTime() ));
            preparedStatement.setTimestamp(column++, new Timestamp( (new Date()).getTime() ));
            
            row = preparedStatement.executeUpdate();      
            
            Statement stmt = DAOFactoryImpl.getConnection(userController.getFunprefController().getPropertiesController().getDbHost()).createStatement();
            
            ResultSet rs = stmt.executeQuery(sqlLastInsertId);

            if( rs.next() ) {
                lastInsertDependentId = rs.getInt(1);
                user.setId(lastInsertDependentId);
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
        
        if( result ) {
            result = savePassword(user);
        }        
        
        return result; 
    }

    @Override
    public int getId() {
        return lastInsertDependentId;
    }

    private boolean savePassword(User user) {
        Random random = new Random();
        int column = 1;
        int row = -1;
        boolean result = true;
        String salt = Integer.toString(random.nextInt(1000000000 - 1 + 1) + 1);
        
        String query = "UPDATE `funpref`.`user`\n" +
            "SET\n" +            
            "`salt` = ?,\n" +
            "`password` = SHA2(CONCAT( ? , ? ),256)\n" +                
            "WHERE `id_user` = ?";        

        try {            
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection(userController.getFunprefController().getPropertiesController().getDbHost()).prepareStatement(query);          
            preparedStatement.setString(column++, salt);
            preparedStatement.setString(column++, user.getPassword());
            preparedStatement.setString(column++, salt);
                        
            preparedStatement.setInt(column++, user.getId());
            
            row = preparedStatement.executeUpdate();            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            result = false;
        }        
        
        return result;
    }
    
}
