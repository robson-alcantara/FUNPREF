/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.interfaces;

import funpref.model.User;

/**
 *
 * @author robson
 */
public interface UserDAO {
    
    public User findByID(int userID);
    
}
