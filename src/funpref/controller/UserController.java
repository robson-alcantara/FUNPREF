/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.UserDAO;
import funpref.model.User;
import funpref.view.LoginScreen;
import funpref.view.userView.UserJInternalFrame;
import java.beans.PropertyVetoException;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author robson
 */
public class UserController {
    
    private final FUNPREFController funprefController;
    private final UserDAO userDAO;
    private User user;
    private UserJInternalFrame userJInternalFrame;
    
    public UserController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
        userDAO = new DAOFactoryImpl().getUserDAO(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    } 

    public FUNPREFController getFunprefController() {
        return funprefController;
    }    
    
    public User findByID( int userID ) {
        return userDAO.findByID(userID);        
    }
    
    public ArrayList<User> findAll() {
        return (ArrayList<User>) userDAO.findAll();
    }
    
    public boolean validateLogin(User user) {

        String salt = null;
        String cryptedPassword = null;
//        String cryptedTempPassword = null;
        boolean validLoginAndPassword = false;
        boolean validLogin = false;
        
        String sqlGetSalt = "SELECT salt, password\n" + 
                "FROM user\n" +
                " where login = '" + user.getLogin() + "'";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection(funprefController.getPropertiesController().getDbHost()).createStatement();
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
                
                String sqlIdLogin = "SELECT *\n" +
                    "FROM funpref.`user`\n" +
                    "where login = '" + user.getLogin() + "'";

                try {                
                    Statement stmt = DAOFactoryImpl.getConnection(funprefController.getPropertiesController().getDbHost()).createStatement();
                    ResultSet resultSet = stmt.executeQuery(sqlIdLogin);

                    if( resultSet.next() ) {                        
                        user.setId(resultSet.getInt(1));
                        user.setIdPermition(resultSet.getInt(2));
                        user.setName(resultSet.getString(3));
                        user.setCpf(resultSet.getString(4));
                        user.setOffice(resultSet.getString(5));                        
                        user.setActive(resultSet.getBoolean(9));                        
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
    
    public boolean validLogin() {
        return validLogin("");        
    }

    public boolean validLogin(String login) {
        LoginScreen loginScreen = new LoginScreen();   
        User userLocal;

        boolean validLogin = false;            

        do {
            userLocal = loginScreen.login(funprefController.getFunprefJFrame(), login);
            
            if( !userLocal.getLogin().isEmpty() && ( userLocal.getLoginScreenResult() != 2 ) ) {
                validLogin = validateLogin(userLocal);

                if( !validLogin ) {
                    JOptionPane.showMessageDialog(null, "'login' ou 'senha' inválido(a)", "FUNPREF", JOptionPane.ERROR_MESSAGE);                    
                }
            }

            else {
                if( userLocal.getLoginScreenResult() == 0 ) {
                    JOptionPane.showMessageDialog(null, "'login' vazio", "FUNPREF", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while( (!validLogin) && (userLocal.getLoginScreenResult() == 0) );
        
        if( validLogin && (!userLocal.getLogin().equals("admin")) ) {
            user = userLocal;
        }
        
        return validLogin;
    }

    public void showUserJInternalFrame() {
        showUserJInternalFrame(null);
    }    

    public void showUserJInternalFrame(User user) {
        try {            
            if( userJInternalFrame == null ) {
                userJInternalFrame = UserJInternalFrame.getUserJInternalFrame(this);
                funprefController.getFunprefJFrame().getJDesktopPane().add(userJInternalFrame);
                userJInternalFrame.setLocation(funprefController.getFunprefJFrame().getJDesktopPane().getLocation().x +
                        ( ( funprefController.getFunprefJFrame().getJDesktopPane().getWidth() - userJInternalFrame.getWidth() ) / 2 ),
                        funprefController.getFunprefJFrame().getJDesktopPane().getLocation().y + 10);                
                userJInternalFrame.reset();
            }
                                   
            if( user == null ) {
                userJInternalFrame.populate( findAll() );
                userJInternalFrame.setManageProfile(false);
            }
            
            else {
                ArrayList<User> userArrayList = new ArrayList<User>();
                userArrayList.add(user);
                userJInternalFrame.populate( userArrayList );                
                userJInternalFrame.setManageProfile(true);
            }
            
            
            userJInternalFrame.toFront();
            userJInternalFrame.setSelected(true);
            userJInternalFrame.setClosable(true);
            userJInternalFrame.setVisible(true);        
        } catch (PropertyVetoException ex) {
            Logger.getLogger(DependentController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(DependentController.class.getName(), ex);
        }
    }

    public boolean save(User currentUser) {
        boolean validSave;
        
        if( currentUser.getId() > 0 ) { // atualiza o usuário
            validSave = userDAO.update(currentUser);
        }
        
        else {  // insere o usuário
            validSave = userDAO.insert(currentUser);
            currentUser.setId( userDAO.getId() );
        }
        
        return validSave;
    }
}
