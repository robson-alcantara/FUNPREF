/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.model.dao;

/**
 *
 * @author secretaria
 */
public class IssuingBodyDAO {
    private int id;
    private String initials;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitial() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }
    
    @Override
    public String toString() {
        return initials;
    } 
}
