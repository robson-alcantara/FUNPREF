/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.comboboxmodels;

import funpref.model.dao.IssuingBodyDAO;
import funpref.model.dao.StockingOrganDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author secretaria
 */
public class IssuingBodyComboBoxModels extends DefaultComboBoxModel {
    
    private ArrayList<IssuingBodyDAO> issuingBody = null;      

    public IssuingBodyComboBoxModels(ArrayList<IssuingBodyDAO> issuingBody) {
        this.issuingBody = issuingBody;
    }
    
   @Override  
   public void addElement(Object anObject) {  
      this.issuingBody.add((IssuingBodyDAO)anObject);  
      //notifica o combo que o modelo foi alterado.  
      this.fireIntervalAdded(this, 0, 0);  
   }  
  
    @Override
   public Object getElementAt(int index) {  
      return this.issuingBody.get(index);  
   }  
  
    @Override
   public Object getSelectedItem() {  
      return super.getSelectedItem();  
   }  
  
    @Override
   public int getSize() {  
      return this.issuingBody.size();  
   }          
    
}
