/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.view;

import funpref.model.dao.UserDAO;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author secretaria
 */
class CreateUserJFrame {

    UserDAO getUser() {
        
        UserDAO user = new UserDAO();
        int option;

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel labelPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        labelPanel.add(new JLabel("login", SwingConstants.RIGHT));
        labelPanel.add(new JLabel("nome", SwingConstants.RIGHT));
        labelPanel.add(new JLabel("cpf", SwingConstants.RIGHT));
        labelPanel.add(new JLabel("cargo", SwingConstants.RIGHT));
        labelPanel.add(new JLabel("senha", SwingConstants.RIGHT));
        panel.add(labelPanel, BorderLayout.WEST); 
        
        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField login = new JTextField();
        controls.add(login);
        JTextField name = new JTextField();
        controls.add(name);        
        JTextField cpf = new JTextField();
        controls.add(cpf);                
        JTextField office = new JTextField();
        controls.add(office);                        
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);  
        
        option = JOptionPane.showConfirmDialog(null, panel, "Criação de usuário", JOptionPane.OK_CANCEL_OPTION);
        
        if( option == 0 ) {
            user.setLogin(login.getText());
            user.setName(name.getText());
            user.setCpf( Long.parseLong(cpf.getText()));
            user.setOffice(office.getText());
            user.setPassword(new String( password.getPassword() ));            
        }
        
        return user;
        
    }
    
}
