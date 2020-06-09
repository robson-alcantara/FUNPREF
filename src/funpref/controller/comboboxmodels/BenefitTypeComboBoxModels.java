/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.comboboxmodels;

import funpref.model.dao.BenefitTypeDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author secretaria
 */
public class BenefitTypeComboBoxModels extends DefaultComboBoxModel {
    
    private ArrayList<BenefitTypeDAO> benefitType = null;      

    public BenefitTypeComboBoxModels(ArrayList<BenefitTypeDAO> benefitType) {
        this.benefitType = benefitType;
    }
    
   @Override  
   public void addElement(Object anObject) {  
      this.benefitType.add((BenefitTypeDAO)anObject);  
      //notifica o combo que o modelo foi alterado.  
      this.fireIntervalAdded(this, 0, 0);  
   }  
  
    @Override
   public Object getElementAt(int index) {  
      return this.benefitType.get(index);  
   }  
  
    @Override
   public Object getSelectedItem() {  
      return super.getSelectedItem();  
   }  
  
    @Override
   public int getSize() {  
      return this.benefitType.size();  
   }          
    
}
