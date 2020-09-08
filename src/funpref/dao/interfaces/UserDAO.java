/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.interfaces;

import funpref.model.User;
import java.util.List;

/**
 *
 * @author robson
 */
public interface UserDAO {
    
    public User findByID(int userID);
    
    public List<User> findAll();    

    public boolean update(User currentUser);

    public boolean insert(User currentUser);

    public int getId();
}
