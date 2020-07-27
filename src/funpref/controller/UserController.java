/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.dao.concrete.DAOFactoryImpl;
import funpref.dao.interfaces.UserDAO;
import funpref.model.User;

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
    
}
