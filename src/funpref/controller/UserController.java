/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.UserDAO;
import funpref.model.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author robson
 */
public class UserController {
    
    private final FUNPREFController funprefController;
    private UserDAO userDAO;
    
    public UserController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
        userDAO = new DAOFactoryImpl().getUserDAO();
    }
    
    public User findByID( int userID ) {
        return userDAO.findByID(userID);        
    }
    
    public boolean validateLogin(User user) {

        String salt = null;
        String cryptedPassword = null;
        String cryptedTempPassword = null;
        boolean validLoginAndPassword = false;
        boolean validLogin = false;
        
        String sqlGetSalt = "SELECT salt, password\n" + 
                "FROM user\n" +
                " where login = '" + user.getLogin() + "'";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetSalt);

            if( rs.next() ) {            
                salt = rs.getString(1);   
                cryptedPassword = rs.getString(2);
                validLogin = true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LogController.reportException(UserController.class.getName(), ex);
        }         
        
        if( validLogin ) {        
//            String sqlStoreTempPassword = "insert into temp_password (password) \n"
//                            + "values (SHA2(CONCAT( ? , ? ),256))";    
//
//            try {                
//
//                PreparedStatement pstmt = DAOFactoryImpl.getConnection().prepareStatement( sqlStoreTempPassword );
//                pstmt.setString( 1, user.getPassword() );
//                pstmt.setString( 2, salt ); 
//
//                pstmt.execute();
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//                LogController.reportException(UserController.class.getName(), ex);
//            }        
//
//            String sqlTempPassword = "SELECT password \n" +
//                "  FROM temp_password\n";  
//
//            try {                
//                Statement stmt = DAOFactoryImpl.getConnection().createStatement();
//                ResultSet rs = stmt.executeQuery(sqlTempPassword);
//
//                rs.next();            
//                cryptedTempPassword = rs.getString(1);          
//                System.out.println("Password criptografada do banco: " + cryptedPassword );
//                System.out.println("Password criptografada do Java: " + cryptPassword( user.getPassword(), salt ) );
//
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//                LogController.reportException(UserController.class.getName(), ex);
//            }  
//
//            String sqlDeleteTempPassword = "DELETE FROM temp_password";  
//
//            try {                
//                Statement stmt = DAOFactoryImpl.getConnection().createStatement();
//                stmt.executeUpdate(sqlDeleteTempPassword);          
//
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//                LogController.reportException(UserController.class.getName(), ex);
//            }         

            if( cryptedPassword.equals(cryptPassword( user.getPassword(), salt )) ) {
                validLoginAndPassword = true;
                
                String sqlIdLogin = "SELECT id_user, name, office\n" +
                    "FROM funpref.`user`\n" +
                    "where login = '" + user.getLogin() + "'";

                try {                
                    Statement stmt = DAOFactoryImpl.getConnection().createStatement();
                    ResultSet rs = stmt.executeQuery(sqlIdLogin);

                    if( rs.next() ) {
                        user.setId(rs.getInt(1));
                        user.setName(rs.getString(2));
                        user.setOffice(rs.getString(3));                    
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    LogController.reportException(UserController.class.getName(), ex);
                }                   
            }
        }
        
        return validLoginAndPassword;        
    }    

    private String cryptPassword(String password, String salt) {
        String passwordAndSalt = password + salt;
        StringBuilder hexString = null;
        byte[] hash = null;
        MessageDigest digest = null;
                
        try {
            digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(passwordAndSalt.getBytes("UTF-8"));

            hexString = new StringBuilder();
            for (int i: hash) {
                if(Integer.toHexString(0xFF & i).length() == 2)
                    hexString.append(Integer.toHexString(0xFF & i));
                else
                    hexString.append ( 0x00 + Integer.toHexString(0xFF & i));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogController.reportException(UserController.class.getName(), e);
        }
        
        return new String( hexString );
    }
    
}
