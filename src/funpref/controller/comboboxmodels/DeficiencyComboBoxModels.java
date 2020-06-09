/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.comboboxmodels;

import funpref.model.dao.DeficiencyDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author secretaria
 */
public class DeficiencyComboBoxModels extends DefaultComboBoxModel {
    
    private ArrayList<DeficiencyDAO> deficiency = null;      

    public DeficiencyComboBoxModels(ArrayList<DeficiencyDAO> deficiency) {
        this.deficiency = deficiency;
    }
    
   @Override  
   public void addElement(Object anObject) {  
      this.deficiency.add((DeficiencyDAO)anObject);  
      //notifica o combo que o modelo foi alterado.  
      this.fireIntervalAdded(this, 0, 0);  
   }  
  
    @Override
   public Object getElementAt(int index) {  
      return this.deficiency.get(index);  
   }  
  
    @Override
   public Object getSelectedItem() {  
      return super.getSelectedItem();  
   }  
  
    @Override
   public int getSize() {  
      return this.deficiency.size();  
   }          
    
}
