/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.concrete;

import funpref.dao.interfaces.UserDAO;
import funpref.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robson
 */
public class UserDAOImpl implements UserDAO{

    @Override
    public User findByID(int userID) {
        User user = new User();
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE id_user = " + userID );

            if (resultSet.next()) {   
                user.setId(userID);
                user.setName(resultSet.getString(3));
                user.setOffice(resultSet.getString(5));                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return user;        
    }
    
}
