/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.model.combobox;

/**
 *
 * @author Robson Santana
 */
public class ComboBoxItem {
    
    int id;
    String description;
    
    public ComboBoxItem() {
        
    }

    public ComboBoxItem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return description;
    }    

//    @Override
//    public boolean equals(Object o) {
//        return this.id == ((ComboBoxItem)o).id; 
//    }
    
    
    
    
    
}
