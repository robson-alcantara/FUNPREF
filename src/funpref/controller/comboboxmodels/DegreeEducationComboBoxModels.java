/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.comboboxmodels;

import funpref.model.dao.DegreeEducationDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author secretaria
 */
public class DegreeEducationComboBoxModels extends DefaultComboBoxModel {
    
    private ArrayList<DegreeEducationDAO> degreeEducation = null;      

    public DegreeEducationComboBoxModels(ArrayList<DegreeEducationDAO> degreeEducation) {
        this.degreeEducation = degreeEducation;
    }
    
   @Override  
   public void addElement(Object anObject) {  
      this.degreeEducation.add((DegreeEducationDAO)anObject);  
      //notifica o combo que o modelo foi alterado.  
      this.fireIntervalAdded(this, 0, 0);  
   }  
  
    @Override
   public Object getElementAt(int index) {  
      return this.degreeEducation.get(index);  
   }  
  
    @Override
   public Object getSelectedItem() {  
      return super.getSelectedItem();  
   }  
  
    @Override
   public int getSize() {  
      return this.degreeEducation.size();  
   }          
    
}
