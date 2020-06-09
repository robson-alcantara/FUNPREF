/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.comboboxmodels;

import funpref.model.dao.MaritalStatusDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author secretaria
 */
public class MaritalStatusComboBoxModels extends DefaultComboBoxModel {
private ArrayList<MaritalStatusDAO> maritalStatus = null;  
     
   public MaritalStatusComboBoxModels(ArrayList<MaritalStatusDAO> maritalStatus) {  
      this.maritalStatus = maritalStatus;  
   }  
     
   @Override  
   public void addElement(Object anObject) {  
      this.maritalStatus.add((MaritalStatusDAO)anObject);  
      //notifica o combo que o modelo foi alterado.  
      this.fireIntervalAdded(this, 0, 0);  
   }  
  
    @Override
   public Object getElementAt(int index) {  
      return this.maritalStatus.get(index);  
   }  
  
    @Override
   public Object getSelectedItem() {  
      return super.getSelectedItem();  
   }  
  
    @Override
   public int getSize() {  
      return this.maritalStatus.size();  
   }      
}
