/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.interfaces;

import funpref.model.combobox.ComboBoxItem;
import java.util.ArrayList;

/**
 *
 * @author robson
 */
public interface JComboBoxItensDAO {
    public ArrayList<ComboBoxItem> findAll( String table );    

    public ArrayList<ComboBoxItem> findCities(int idProvince);
}
