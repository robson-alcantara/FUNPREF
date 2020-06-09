/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robson
 */
public class PropertiesController {
    
    private Properties properties;
    public boolean debugMenu;
    public boolean useDB;
    
    public PropertiesController() {
        try {
            properties = getProp();
            debugMenu = Boolean.parseBoolean( properties.getProperty( "prop.frame.debugmenu" ) );
            useDB = Boolean.parseBoolean( properties.getProperty( "prop.model.usedatabase" ) );
        } catch (IOException ex) {
            Logger.getLogger(PropertiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Properties getProp() throws IOException {
            Properties props = new Properties();
            FileInputStream file = new FileInputStream(
                            "./properties/program.properties");
            props.load(file);
            return props;
    }    
    
    
}
