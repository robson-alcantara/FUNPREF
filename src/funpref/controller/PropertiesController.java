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
    private String dbHost;
    private FUNPREFController funprefController;
    
    public PropertiesController(FUNPREFController funprefController) {
        try {
            this.funprefController = funprefController;
            properties = getProp();
            dbHost = properties.getProperty("prop.model.databasehost");
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

    public String getDbHost() {
        return dbHost;
    }
    
    
    
    
}
