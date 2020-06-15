/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.comboboxmodels;

import funpref.model.dao.StockingOrganDAO;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author secretaria
 */
public class StockingOrganComboBoxModels extends DefaultComboBoxModel {
    
    private ArrayList<StockingOrganDAO> stockingOrgan = null;      

    public StockingOrganComboBoxModels(ArrayList<StockingOrganDAO> stockingOrgan) {
        this.stockingOrgan = stockingOrgan;
    }
    
   @Override  
   public void addElement(Object anObject) {  
      this.stockingOrgan.add((StockingOrganDAO)anObject);  
      //notifica o combo que o modelo foi alterado.  
      this.fireIntervalAdded(this, 0, 0);  
   }  
  
    @Override
   public Object getElementAt(int index) {  
      return this.stockingOrgan.get(index);  
   }  
  
    @Override
   public Object getSelectedItem() {  
      return super.getSelectedItem();  
   }  
  
    @Override
   public int getSize() {  
      return this.stockingOrgan.size();  
   }          
    
}