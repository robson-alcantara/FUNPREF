/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.concrete;

import funpref.dao.interfaces.JComboBoxItensDAO;
import funpref.model.combobox.ComboBoxItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author robson
 */
public class JComboBoxItensDAOImpl implements JComboBoxItensDAO {

    @Override
    public ArrayList<ComboBoxItem> findAll(String table) {
        ArrayList<ComboBoxItem> comboBoxItens = new ArrayList<ComboBoxItem>();
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table );

            while (resultSet.next()) {
                
                ComboBoxItem comboBoxItem = new ComboBoxItem();
                comboBoxItem.setId(resultSet.getInt(1));
                
                if( table.equals("benefit_type") ||
                        table.equals("deficiency") ||
                        table.equals("degree_of_education") ||
                        table.equals("kinship") ||
                        table.equals("marital_status") || 
                        table.equals("stocking_organ")) {
                    comboBoxItem.setDescription(resultSet.getString(3));                
                }
                
                else {
                    comboBoxItem.setDescription(resultSet.getString(2));                
                }

                comboBoxItens.add(comboBoxItem);
            }
        } catch (SQLException ex) {
            return null;
        }        
        
        return comboBoxItens;                
    }

    @Override
    public ArrayList<ComboBoxItem> findCities(int idProvince) {
        ArrayList<ComboBoxItem> comboBoxItens = new ArrayList<ComboBoxItem>();
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM city WHERE id_province = " + idProvince );

            while (resultSet.next()) {
                
                ComboBoxItem comboBoxItem = new ComboBoxItem();
                comboBoxItem.setId(resultSet.getInt(1));                
                comboBoxItem.setDescription(resultSet.getString(2));    
                comboBoxItens.add(comboBoxItem);
            }
        } catch (SQLException ex) {
            return null;
        }        
        
        return comboBoxItens;                
    }
    
}
