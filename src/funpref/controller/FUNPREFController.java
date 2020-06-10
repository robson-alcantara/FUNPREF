/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.SerialUtils;
import funpref.controller.comboboxmodels.BenefitTypeComboBoxModels;
import funpref.controller.comboboxmodels.CityComboBoxModels;
import funpref.controller.comboboxmodels.DeficiencyComboBoxModels;
import funpref.controller.comboboxmodels.DegreeEducationComboBoxModels;
import funpref.controller.comboboxmodels.IssuingBodyComboBoxModels;
import funpref.controller.comboboxmodels.KinshipComboBoxModels;
import funpref.controller.comboboxmodels.MaritalStatusComboBoxModels;
import funpref.controller.comboboxmodels.ProvinceComboBoxModels;
import funpref.controller.comboboxmodels.StockingOrganComboBoxModels;
import funpref.model.Dependent;
import funpref.model.FUNPREFModel;
import funpref.model.Beneficiary;
import funpref.model.dao.BenefitTypeDAO;
import funpref.model.dao.CadastralStatusDAO;
import funpref.model.dao.CityDAO;
import funpref.model.dao.ContentSearchTableItensDAO;
import funpref.model.dao.DeficiencyDAO;
import funpref.model.dao.DegreeEducationDAO;
import funpref.model.dao.IssuingBodyDAO;
import funpref.model.dao.KinshipDAO;
import funpref.model.dao.MaritalStatusDAO;
import funpref.model.dao.ProvinceDAO;
import funpref.model.dao.StockingOrganDAO;
import funpref.model.dao.UserDAO;
import funpref.view.FUNPREFJFrame;
import funpref.view.LoginScreen;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;

/**
 *
 * @author Robson
 */
public class FUNPREFController {
    
    private FUNPREFJFrame funprefJFrame;
    private JDBCController jdbcController;
    private ReportController reportController;
    private FUNPREFModel funprefModel;
    public PropertiesController propertiesController;
    
    private ArrayList<ProvinceDAO> provinceDAOArrayList;
    private ArrayList<MaritalStatusDAO> maritalStatusDAOArrayList;
    private ArrayList<IssuingBodyDAO> issuingBodyDAOArrayList;
    private ArrayList<BenefitTypeDAO> benefitTypeDAOArrayList;
    private ArrayList<CadastralStatusDAO> cadastralStatusDAOArrayList;
    private ArrayList<StockingOrganDAO> stockingOrganDAOArrayList;
    private ArrayList<CityDAO> cityDAOArrayList;
    private ArrayList<KinshipDAO> kinshipDAOArrayList;
    private ArrayList<DegreeEducationDAO> degreeEducationDAOArrayList;
    private ArrayList<DeficiencyDAO> deficiencyDAOArrayList;
    private UserDAO user;
    
    public void run() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FUNPREFController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FUNPREFController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FUNPREFController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FUNPREFController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        propertiesController = new PropertiesController();
        funprefJFrame = new FUNPREFJFrame();
        jdbcController = new JDBCController();
        reportController = new ReportController();
        reportController.setController( this );
        funprefJFrame.setController( this );
        funprefModel = new FUNPREFModel( jdbcController );
        
        jdbcController.connectDB();
        maritalStatusDAOArrayList = jdbcController.getMaritalStatusArrayList();
        provinceDAOArrayList = jdbcController.getProvinceDAOArrayList();
        issuingBodyDAOArrayList = jdbcController.getIssuingBodyArrayList();
        benefitTypeDAOArrayList = jdbcController.getBenefitTypeDAOArrayList();
        cadastralStatusDAOArrayList = jdbcController.getCadastralStatusDAOArrayList();
        stockingOrganDAOArrayList = jdbcController.getStockingOrganArrayList();
        kinshipDAOArrayList = jdbcController.getKinshipArrayList();
        degreeEducationDAOArrayList = jdbcController.getDegreeEducationArrayList();
        deficiencyDAOArrayList = jdbcController.getDeficiencyArrayList();
        
//        if( isValidRegister() ) {          
            LoginScreen loginScreen = new LoginScreen();                    

            boolean validLogin = false;            

            do {
                user = loginScreen.login(funprefJFrame);
                if( !user.getLogin().isEmpty() ) {
                    validLogin = jdbcController.validateLogin(user);                    

                    if( validLogin ) {
                        user.setId( jdbcController.getIdLogin() );
                        user.setName( jdbcController.getNameLogin() );
                        user.setOffice(jdbcController.getOfficeLogin() );
                        funprefJFrame.setVisible(true);
                    }
                    
                    else {
                        JOptionPane.showMessageDialog(null, "'login' ou 'senha' inválido(a)", "FUNPREF", JOptionPane.ERROR_MESSAGE);
                    }
                }

                else {
                    if( user.getLoginScreenResult() == 0 ) {
                        JOptionPane.showMessageDialog(null, "'login' vazio", "FUNPREF", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } while( (!validLogin) && (user.getLoginScreenResult() == 0) );
            
            if( !validLogin ) {
                funprefJFrame.dispose();
            }
                

                
            //loginScreen.
            
            
//        }
        
//        else {
//            
//            boolean validKey;
//            
//            validKey = checkKey();
//            
//            if( validKey ) {            
//                boolean registerStatus;
//                registerStatus = registerProgram();            
//                
//                if( registerStatus ) {                    
//                    LoginScreen loginScreen = new LoginScreen();                    
//                    loginScreen.login(funprefJFrame);
//                    
//                    funprefJFrame.setVisible(true); 
//                }                
//                
//                else {
//                    JOptionPane.showMessageDialog(null, "Erro ao tentar registrar", "Erro", JOptionPane.ERROR_MESSAGE );                    
//                }
//            }
//            
//            else {
//                JOptionPane.showMessageDialog(null, "Chave de registro inválida", "Informação", JOptionPane.ERROR_MESSAGE );
//            }
//        }
    }

    private static boolean hasValidOpenFile() {
        File autenticationFile = new File("./resources/config");
        boolean valid = false;
        
        if(autenticationFile.exists()) {            
            try {
                FileInputStream fileInputStream = new FileInputStream("./resources/config");
                InputStreamReader inputStreamReader = new InputStreamReader( fileInputStream, StandardCharsets.ISO_8859_1 );
                BufferedReader bufferedReader = new BufferedReader( inputStreamReader ); 
                
                try {
                    String line = bufferedReader.readLine();
                    if( line.compareTo("9e107d9d372bb6826bd81d3542a419d6") == 0) {
                        valid = true;                        
                    }
                    
                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();                    
                } catch (IOException ex) {
                    Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        return valid;
    }

    private static void writeInvalidFile() {
                       
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("./resources/config");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( fileOutputStream, StandardCharsets.ISO_8859_1 );
            BufferedWriter writer = new BufferedWriter( outputStreamWriter );  

            try {
                writer.append("d41d8cd98f00b204e9800998ocf8427e");
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public void readAndFillBeneficiaryCRUDJInternalFrame(int idBeneficiary, FUNPREFJFrame.BeneficiaryCRUD inativeCRUDMode) {
        // stub
        funprefModel.setBeneficiary( jdbcController.getBeneficiary( idBeneficiary ) );                
        
        if( inativeCRUDMode == FUNPREFJFrame.BeneficiaryCRUD.READ ) {
            funprefJFrame.setEditableBeneficiaryCRUDJInternalFrame(false);
        }
        
        else {
            funprefJFrame.setEditableBeneficiaryCRUDJInternalFrame(true);
        }
        
        funprefJFrame.fillBeneficiaryCRUDJInternalFrame( funprefModel.getBeneficiary() );
    }

    public void saveBeneficiary(Beneficiary inative) {
        funprefModel.saveBeneficiary(inative, user );
    }

    public Dependent getDependentByCode(int code) {
        return funprefModel.getDependentByCode(code);
    }

    public void saveDependent(Dependent dependent) {
        funprefModel.saveDependent(dependent);
    }

    public void fillIncomeTaxJInternalFrame() {
        ArrayList<Double> incomeTaxRates;
        ArrayList<Double> incomeTaxValues;
        
        funprefModel.readIncomeTax();
        incomeTaxRates = funprefModel.getIncomeTaxRates();
        incomeTaxValues = funprefModel.getIncomeTaxValues();
        
        funprefJFrame.fillIncomeTaxJInternalFrame(incomeTaxValues,incomeTaxRates);
    }

    public int saveIncomeTax(JTable incomeTaxJTable) {
        return funprefModel.saveIncomeTax(incomeTaxJTable);
    }

    private boolean isValidRegister() {
        String fileString = "./resources/config3";
        File autenticationFile = new File(fileString);
        boolean valid = false;
      
        if(autenticationFile.exists()) {            
            try {
                FileInputStream fileInputStream = new FileInputStream(fileString);
               
                try {
                    String decriptedLine = decriptToString( Files.readAllBytes(new File(fileString).toPath()) );    
                    
                    if( decriptedLine.compareTo("2017" + SerialUtils.getCPUSerial() ) == 0) {
                        valid = true;                        
                    }
                    
                    fileInputStream.close();                    
                } catch (IOException ex) {
                    Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        else {
            JOptionPane.showMessageDialog(null, "Erro ao tentar carregar chave do registro", "Erro", JOptionPane.ERROR_MESSAGE );            
        }
        
        return valid;
    }

    private boolean registerProgram() {
        
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR); 

        boolean valid = false;
        String fileString = "./resources/config3";
        File autenticationFile = new File(fileString);
        
        if(autenticationFile.exists()) {            
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileString);                
                
                try {
                    fileOutputStream.write( criptFromString( "" + year + SerialUtils.getCPUSerial() ) );  
                    
                    valid = true;                    
                    fileOutputStream.close();                    
                } catch (IOException ex) {
                    Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
        
        return valid;
    }


    private byte[] criptKey(String externalKeyString) {
        Cipher cripter;
        String IV = "AAAAAAAAAAAAAAAA";
        String keyString = externalKeyString;
        byte[] criptedKey = new byte[] { '0' };
        
        while(keyString.length() < 32) {
            keyString = keyString + keyString;
        }
        
        keyString = keyString.substring(0, 32);
        
        try {
            cripter = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key;
            key = new SecretKeySpec(keyString.getBytes("UTF-8"), "AES");

            cripter.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
            
            criptedKey = cripter.doFinal(externalKeyString.getBytes("UTF-8"));
            
            System.out.print("cripted key: " );
            
            for( int j = 0; j < criptedKey.length; j++ ) {
                System.out.print(""  + criptedKey[j] + ";" );
            }
            
            System.out.println("");
            
            
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);                    
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);                  
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return criptedKey;
    }

    private String decriptKey(byte[] criptedKey, String candidateKey) {
        Cipher decripter;
        String IV = "AAAAAAAAAAAAAAAA";
        String keyString = candidateKey;
        String decriptedKey = "";
        
        while(keyString.length() < 32) {
            keyString = keyString + keyString;
        }        
        
        keyString = keyString.substring(0, 32);
        
        try {
            decripter = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key;
            key = new SecretKeySpec(keyString.getBytes("UTF-8"), "AES");
            decripter.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));            
            decriptedKey = new String( decripter.doFinal(criptedKey), "UTF-8" );            
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);                    
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);                  
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return decriptedKey;
    }

    private boolean checkKey() {
        String key;
        String decriptedKey;
        boolean valid = false;
        
        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*'); 
        
        JLabel label = new JLabel("Qual a chave de registro do programa?");
        
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(password);
        
        JOptionPane.showMessageDialog(null, panel, "Registro", JOptionPane.QUESTION_MESSAGE);        
        
        key = new String( password.getPassword() );        

        decriptedKey = decriptKey(loadCriptedKey(),key);
        
        if( key.compareTo(decriptedKey) == 0 ) {
            valid = true;
        }
        
        return valid;
    }

    private byte[] criptFromString(String decriptedString) {
        Cipher cripter;
        String IV = "AAAAAAAAAAAAAAAA";
        String keyString = "9e107d9d372bb6826bd81d3542a419d6";
        byte[] criptedByteArray = new byte[] { '0' };
     
        
        try {
            cripter = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key;
            key = new SecretKeySpec(keyString.getBytes("UTF-8"), "AES");

            cripter.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));  
            criptedByteArray = cripter.doFinal(decriptedString.getBytes("UTF-8"));
            
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);                    
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);                  
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return criptedByteArray;
    }
    
    private String decriptToString(byte[] criptedByteArray ) {
        Cipher decripter;
        String IV = "AAAAAAAAAAAAAAAA";
        String keyString = "9e107d9d372bb6826bd81d3542a419d6";
        String decriptedString = "";        
        
        try {
            decripter = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key;
            key = new SecretKeySpec(keyString.getBytes("UTF-8"), "AES");
            decripter.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));              
            decriptedString = new String( decripter.doFinal(criptedByteArray), "UTF-8" );            
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);                    
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);                  
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return decriptedString;
    }
    

    private byte[] loadCriptedKey() {
        String fileString = "./resources/config2";
        File autenticationFile = new File(fileString);
        String[] tokens;
        byte[] criptedKey = new byte[] {'0'};
        
        if(autenticationFile.exists()) {            
            try {
                FileInputStream fileInputStream = new FileInputStream(fileString);
                InputStreamReader inputStreamReader = new InputStreamReader( fileInputStream, StandardCharsets.ISO_8859_1 );
                BufferedReader bufferedReader = new BufferedReader( inputStreamReader ); 
                
                try {
                    tokens = bufferedReader.readLine().split(";");
                    
                    criptedKey = new byte[tokens.length];
                    
                    for ( int i = 0; i < tokens.length; i++ ) {
                        criptedKey[i] = Byte.valueOf(tokens[i]);
                    }
                    
                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();                    
                } catch (IOException ex) {
                    Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FUNPREFController.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        return criptedKey;
    }

    public boolean revalidateLogin() {
        
        LoginScreen loginScreen = new LoginScreen();
        boolean validLogin = false;
        UserDAO tryUser = null;

        do {
            tryUser = loginScreen.login(funprefJFrame);
            if( !tryUser.getLogin().isEmpty() ) {
                validLogin = jdbcController.validateLogin(tryUser);                    

                if( validLogin ) {
                    if( !tryUser.getLogin().equals(user.getLogin())) {
                        validLogin = false;
                    }
                }

                else {
                    JOptionPane.showMessageDialog(null, "'login' ou 'senha' inválido(a)", "FUNPREF", JOptionPane.ERROR_MESSAGE);
                }
            }

            else {
                if( tryUser.getLoginScreenResult() == 0 ) {
                    JOptionPane.showMessageDialog(null, "'login' vazio", "FUNPREF", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while( (!validLogin) && (tryUser.getLoginScreenResult() == 0) );
        
        return validLogin;
    }
    
    public JDBCController getJdbcController() {
        return jdbcController;
    }    

    public MaritalStatusComboBoxModels getMaritalStatusModel() {                  
        return new MaritalStatusComboBoxModels(maritalStatusDAOArrayList);        
    }    
    
    public int encodeMaritalStatus(String maritalStatusString) {
        
        int maritalStatusId = -1;
        
        for( int i = 0; i < maritalStatusDAOArrayList.size(); i++ ) {
            if( maritalStatusString.compareTo(maritalStatusDAOArrayList.get(i).getDescription()) == 0 ) {
                maritalStatusId = maritalStatusDAOArrayList.get(i).getId();
            }
        }        
        
        return maritalStatusId;
    }    
    
    
    //TODO: build
    
    public Object decodeMaritalStatus(int maritalStatusId) {
        
        Object maritalStatus = null;
        
        for( int i = 0; i < maritalStatusDAOArrayList.size(); i++ ) {
            if( maritalStatusDAOArrayList.get(i).getId() == maritalStatusId ) {
                maritalStatus = maritalStatusDAOArrayList.get(i);
            }
        }         
        
        return maritalStatus;
    } 
    
    public Object decodeRgIssuingBody(int idRgIssuingBody) {
        Object rgIssuingBody = null;
        
        for( int i = 0; i < issuingBodyDAOArrayList.size(); i++ ) {
            if( issuingBodyDAOArrayList.get(i).getId() == idRgIssuingBody ) {
                rgIssuingBody = issuingBodyDAOArrayList.get(i);
            }
        }         
        
        return rgIssuingBody;
    }    
    
    public Object decodeProvince(int idProvince) {
        Object province = null;
        
        for( int i = 0; i < provinceDAOArrayList.size(); i++ ) {
            if( provinceDAOArrayList.get(i).getId() == idProvince ) {
                province = provinceDAOArrayList.get(i);
            }
        }         
        
        return province;
    }  
    
    public Object decodeCity( int idCity, int idProvince ) {
        Object city = null;
        ArrayList<CityDAO> cityDAOArrayList = jdbcController.getCityDAOArrayList(idProvince);
        
        for( int i = 0; i < cityDAOArrayList.size(); i++ ) {
            if( cityDAOArrayList.get(i).getId() == idCity ) {
                city = cityDAOArrayList.get(i);
            }
        }         
        
        return city;
    } 
    
    public Object decodeCity( int idCity ) {
        Object city = null;
        //ArrayList<CityDAO> cityDAOArrayList = jdbcController.getCityDAOArrayList(idProvince);
        
        for( int i = 0; i < cityDAOArrayList.size(); i++ ) {
            if( cityDAOArrayList.get(i).getId() == idCity ) {
                city = cityDAOArrayList.get(i);
            }
        }         
        
        return city;
    }     
    
    public Object decodeBenefitType(int idBenefitType) {
        Object benefitType = null;
        
        for( int i = 0; i < benefitTypeDAOArrayList.size(); i++ ) {
            if( benefitTypeDAOArrayList.get(i).getId() == idBenefitType ) {
                benefitType = benefitTypeDAOArrayList.get(i);
            }
        }         
        
        return benefitType;
    }  
    
    public Object decodeCadastralStatus(int idCadastralStatus) {
        Object cadastralStatus = null;
        
        for( int i = 0; i < cadastralStatusDAOArrayList.size(); i++ ) {
            if( cadastralStatusDAOArrayList.get(i).getId() == idCadastralStatus ) {
                cadastralStatus = cadastralStatusDAOArrayList.get(i);
            }
        }         
        
        return cadastralStatus;
    }    
    
    public Object decodeStockingOrgan(int idStockingOrgan) {
        Object stockingOrgan = null;
        
        for( int i = 0; i < stockingOrganDAOArrayList.size(); i++ ) {
            if( stockingOrganDAOArrayList.get(i).getId() == idStockingOrgan ) {
                stockingOrgan = stockingOrganDAOArrayList.get(i);
            }
        }         
        
        return stockingOrgan;
    }    
    
    public Object decodeKinship(int idKinship) {
        Object kinship = null;
        
        for( int i = 0; i < kinshipDAOArrayList.size(); i++ ) {
            if( kinshipDAOArrayList.get(i).getId() == idKinship ) {
                kinship = kinshipDAOArrayList.get(i);
            }
        }         
        
        return kinship;
    }  
    
    public Object decodeDegreeEducation(int idDegreeOfEducation) {
        Object degreeEducation = null;
        
        for( int i = 0; i < degreeEducationDAOArrayList.size(); i++ ) {
            if( degreeEducationDAOArrayList.get(i).getId() == idDegreeOfEducation ) {
                degreeEducation = degreeEducationDAOArrayList.get(i);
            }
        }         
        
        return degreeEducation;
    }  
    
    public Object decodeDeficiency(int idDeficiency) {
        Object deficiency = null;
        
        for( int i = 0; i < deficiencyDAOArrayList.size(); i++ ) {
            if( deficiencyDAOArrayList.get(i).getId() == idDeficiency ) {
                deficiency = deficiencyDAOArrayList.get(i);
            }
        }         
        
        return deficiency;
    }    

    public ComboBoxModel<Object> getStockingOrganModel() {
        //ArrayList<StockingOrganDAO> stockingOrganDAOArrayList = jdbcController.getStockingOrganArrayList();        
        StockingOrganComboBoxModels aModel = new StockingOrganComboBoxModels(stockingOrganDAOArrayList);

        return aModel;  
    }

    public ComboBoxModel<Object> getIssuingBodyModel() {
        //ArrayList<IssuingBodyDAO> issuingBodyDAOArrayList = jdbcController.getIssuingBodyArrayList();        
        IssuingBodyComboBoxModels aModel = new IssuingBodyComboBoxModels(issuingBodyDAOArrayList);

        return aModel;  
    }

    public ComboBoxModel<Object> getProvinceModel() {
        return new ProvinceComboBoxModels(provinceDAOArrayList);         
    }

    public ComboBoxModel<Object> getBenefitTypeModel() {    
        //ArrayList<BenefitTypeDAO> benefitTypeDAOArrayList = jdbcController.getBenefitTypeDAOArrayList();        
        BenefitTypeComboBoxModels aModel = new BenefitTypeComboBoxModels(benefitTypeDAOArrayList);

        return aModel; 
    }
    
    public ComboBoxModel<Object> getDegreeOfEducationModel() {
        return new DegreeEducationComboBoxModels(degreeEducationDAOArrayList);        
    }    

    public ComboBoxModel<Object> getCitiesModel(int idProvince) {
        cityDAOArrayList = jdbcController.getCityDAOArrayList(idProvince);        
        CityComboBoxModels aModel = new CityComboBoxModels(cityDAOArrayList);

        return aModel;        
    }
    
    public ComboBoxModel<Object> getKinshipModel() {
        return new KinshipComboBoxModels(kinshipDAOArrayList);
    }  
    
    public ComboBoxModel<Object> getDeficiencyModel() {
        return new DeficiencyComboBoxModels(deficiencyDAOArrayList);
    }    

    public int getProvinceId(String provinceInitial) {
        int id = -1;
        
        for( int i = 0; i < provinceDAOArrayList.size(); i++ ) {
            if( provinceInitial.compareTo( provinceDAOArrayList.get(i).getInitial()) == 0 ) {
                id = provinceDAOArrayList.get(i).getId();
            }                
        }
        
        return id;        
    }
    
    public ArrayList<ContentSearchTableItensDAO> findText(String text, boolean evaluateDeath ) {
        return jdbcController.getContentSearchTableArrayList( text, evaluateDeath );                
    }  
    
    public String getBeneficiaryNameByEnrollment(int instituteEnrollment) {
        return jdbcController.getBeneficiaryNameByEnrollment(instituteEnrollment);
    }

    public int getBeneficiaryEnrollmentById(int beneficiaryId) {
        return jdbcController.getBeneficiaryEnrollmentById(beneficiaryId);
    }

    public int getLastInsertId() {
        return funprefModel.getLastInsertId();
    }

    public void printBeneficiaryCensusVoucher(Beneficiary beneficiary, boolean printRegisterDate ) {
        reportController.printBeneficiaryCensusVoucher(beneficiary, printRegisterDate );
    }

    public UserDAO getUser() {
        return user;
    }

    public void setUser(UserDAO user) {
        this.user = user;
    }

    public void generateReportBeneficiary() {
        reportController.generateReportBeneficiary();
    }

    public void generateReportDependent() {
        reportController.generateReportDependent();
    }

    public void generateReportBeneficiaryPending() {
        reportController.generateReportBeneficiaryPending();
    }

    public void generateReportBeneficiaryDeceased() {
        reportController.generateReportBeneficiaryDeceased();
    }

    public int restartCadastralStatus() {
        return jdbcController.restartCadastralStatus();
    }
}

