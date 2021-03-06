/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.view;

import funpref.model.User;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Robson
 */
public class LoginScreen {
    
    public User login(JFrame frame) {
        return login(frame, "");
    }    
    
    public User login(JFrame frame, String username ) {
        User loginInformation = new User();

        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("login", SwingConstants.RIGHT));
        label.add(new JLabel("senha", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField usernameJTextField = new JTextField();
        
        if(!username.isEmpty()) {
            usernameJTextField.setText(username);
            usernameJTextField.setEditable(false);
        }
        
        controls.add(usernameJTextField);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);        
    
        loginInformation.setLoginScreenResult( JOptionPane.showConfirmDialog(frame, panel, "FUNPREF", JOptionPane.OK_CANCEL_OPTION) );
        
        loginInformation.setLogin( usernameJTextField.getText() );
        loginInformation.setPassword(new String(password.getPassword()));


        return loginInformation;                
    }
}
