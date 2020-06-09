/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.comboboxmodels;

import funpref.model.dao.CityDAO;
import funpref.model.dao.ProvinceDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author secretaria
 */
public class CityComboBoxModels extends DefaultComboBoxModel {
    
    private ArrayList<CityDAO> city = null;      

    public CityComboBoxModels(ArrayList<CityDAO> city) {
        this.city = city;
    }
    
   @Override  
   public void addElement(Object anObject) {  
      this.city.add((CityDAO)anObject);  
      //notifica o combo que o modelo foi alterado.  
      this.fireIntervalAdded(this, 0, 0);  
   }  
  
    @Override
   public Object getElementAt(int index) {  
      return this.city.get(index);  
   }  
  
    @Override
   public Object getSelectedItem() {  
      return super.getSelectedItem();  
   }  
  
    @Override
   public int getSize() {  
      return this.city.size();  
   }          
    
}
