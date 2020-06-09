/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.comboboxmodels;

import funpref.model.dao.KinshipDAO;
import funpref.model.dao.MaritalStatusDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author secretaria
 */
public class KinshipComboBoxModels extends DefaultComboBoxModel {
    private ArrayList<KinshipDAO> kinship = null;  
     
   public KinshipComboBoxModels(ArrayList<KinshipDAO> kinship) {  
      this.kinship = kinship;  
   }  
     
   @Override  
   public void addElement(Object anObject) {  
      this.kinship.add((KinshipDAO)anObject);  
      //notifica o combo que o modelo foi alterado.  
      this.fireIntervalAdded(this, 0, 0);  
   }  
  
    @Override
   public Object getElementAt(int index) {  
      return this.kinship.get(index);  
   }  
  
    @Override
   public Object getSelectedItem() {  
      return super.getSelectedItem();  
   }  
  
    @Override
   public int getSize() {  
      return this.kinship.size();  
   }      
}
