/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import funpref.model.Beneficiary;
import funpref.model.Dependent;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author secretaria
 */
public class JDBCController {
    
    Connection connection;
    private final SimpleDateFormat formatDate;
    private int idLogin;
    private int lastInsertId;
    private String nameLogin;
    private String officeLogin;
    private int lastInsertDependentId;
    
    public JDBCController() {
        formatDate = new SimpleDateFormat("dd/MM/yyyy");
    }

    public int getLastInsertId() {
        return lastInsertId;
    }
    
    public void connectDB() {
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            String serverName = "localhost";    //caminho do servidor do BD 
            String mydatabase = "funpref";        //nome do seu banco de dados 
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 
            String username = "funpref";        //nome de um usuário de seu BD  
            String password = "fund0pr3v1";      //sua senha de acesso 
            
        try {        
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if ( connection != null ) { 
            System.out.println("INFO 003 - Conectado com o banco de dados.");
        }        
        
        else {
            System.out.println("ERRO 002 - Não conectado com o banco de dados.");
        }
    }

    public void setAdminPassword(String password) {
        
        Random random = new Random();
        String salt = Integer.toString(random.nextInt(1000000000 - 1 + 1) + 1);

        String sql = "insert into user (ref_id_permition, login, salt, password) \n"
                        + "values (?, ?, ?, SHA2(CONCAT( ? , ? ),256))";        

        try {                
            
            PreparedStatement pstmt = connection.prepareStatement( sql );
            pstmt.setInt( 1, 1 );
            pstmt.setString( 2, "admin" );
            pstmt.setString( 3, salt);
            pstmt.setString( 4, password );
            pstmt.setString( 5, salt ); 
            
            pstmt.execute();

            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
    }

    public void testAdminPassword(String password) {
        
        String salt = null;
        String cryptedPassword = null;
        String cryptedTempPassword = null;
        
        String sqlGetSalt = "SELECT salt, password\n" + 
                "FROM user\n" +
                " where login = 'admin'";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetSalt);

            rs.next();
            
            salt = rs.getString(1);   
            cryptedPassword = rs.getString(2);
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }         
        
        
        String sqlStoreTempPassword = "insert into temp_password (password) \n"
                        + "values (SHA2(CONCAT( ? , ? ),256))";    
        
        try {                
            
            PreparedStatement pstmt = connection.prepareStatement( sqlStoreTempPassword );
            pstmt.setString( 1, password );
            pstmt.setString( 2, salt ); 
            
            pstmt.execute();

            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        String sqlTempPassword = "SELECT password \n" +
            "  FROM temp_password\n";  
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlTempPassword);

            rs.next();            
            cryptedTempPassword = rs.getString(1);            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
        
        String sqlDeleteTempPassword = "DELETE FROM temp_password";  
        
        try {                
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sqlDeleteTempPassword);          
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }         
        
        if( cryptedPassword.compareTo(cryptedTempPassword) == 0 ) {
            JOptionPane.showMessageDialog(null, "Senha confere", "Senha", JOptionPane.INFORMATION_MESSAGE);
        }
        
        else {
            JOptionPane.showMessageDialog(null, "Senha não confere", "Senha", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createUser(UserDAO user) {
        Random random = new Random();
        String salt = Integer.toString(random.nextInt(1000000000 - 1 + 1) + 1);

        String sql = "insert into user (ref_id_permition, name, cpf, office, login, salt, password) \n"
                        + "values (?, ?, ?, ?,  ?, ?, SHA2(CONCAT( ? , ? ),256))";        

        try {                
            
            PreparedStatement pstmt = connection.prepareStatement( sql );
            pstmt.setInt( 1, 2 );
            pstmt.setString( 2, user.getName() );
            pstmt.setString(3, "" + user.getCpf());
            pstmt.setString( 4, user.getOffice() );
            pstmt.setString( 5, user.getLogin() );
            pstmt.setString( 6, salt);
            pstmt.setString( 7, user.getPassword() );
            pstmt.setString( 8, salt ); 
            
            pstmt.execute();

            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    boolean validateLogin(UserDAO user) {

        String salt = null;
        String cryptedPassword = null;
        String cryptedTempPassword = null;
        boolean validLoginAndPassword = false;
        boolean validLogin = false;
        
        String sqlGetSalt = "SELECT salt, password\n" + 
                "FROM user\n" +
                " where login = '" + user.getLogin() + "'";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetSalt);

            if( rs.next() ) {            
                salt = rs.getString(1);   
                cryptedPassword = rs.getString(2);
                validLogin = true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }         
        
        if( validLogin ) {        
            String sqlStoreTempPassword = "insert into temp_password (password) \n"
                            + "values (SHA2(CONCAT( ? , ? ),256))";    

            try {                

                PreparedStatement pstmt = connection.prepareStatement( sqlStoreTempPassword );
                pstmt.setString( 1, user.getPassword() );
                pstmt.setString( 2, salt ); 

                pstmt.execute();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }        

            String sqlTempPassword = "SELECT password \n" +
                "  FROM temp_password\n";  

            try {                
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlTempPassword);

                rs.next();            
                cryptedTempPassword = rs.getString(1);            

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }  

            String sqlDeleteTempPassword = "DELETE FROM temp_password";  

            try {                
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(sqlDeleteTempPassword);          

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }         

            if( cryptedPassword.compareTo(cryptedTempPassword) == 0 ) {
                validLoginAndPassword = true;
            }

            String sqlIdLogin = "SELECT id_user, name, office\n" +
                "FROM funpref.`user`\n" +
                "where login = '" + user.getLogin() + "'";

            try {                
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sqlIdLogin);

                if( rs.next() ) {            
                    idLogin = rs.getInt(1);            
                    nameLogin = rs.getString(2);
                    officeLogin = rs.getString(3);
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }   
        }
        
        return validLoginAndPassword;        
    }

    public ArrayList<MaritalStatusDAO> getMaritalStatusArrayList() {
        
        ArrayList<MaritalStatusDAO> maritalStatusDAOArrayList = new ArrayList<MaritalStatusDAO>();
        MaritalStatusDAO maritalStatusDAO;
        
        String sql = "SELECT * FROM funpref.marital_status;";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                maritalStatusDAO = new MaritalStatusDAO();
                maritalStatusDAO.setId( rs.getInt(1));
                maritalStatusDAO.setDescription(rs.getString(3));            
                maritalStatusDAOArrayList.add(maritalStatusDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return maritalStatusDAOArrayList;
    }

    public ArrayList<StockingOrganDAO> getStockingOrganArrayList() {
        ArrayList<StockingOrganDAO> stockingOrganDAOArrayList = new ArrayList<StockingOrganDAO>();
        StockingOrganDAO stockingOrganDAO;
        
        String sql = "SELECT * FROM funpref.stocking_organ;";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                stockingOrganDAO = new StockingOrganDAO();
                stockingOrganDAO.setId( rs.getInt(1));
                stockingOrganDAO.setDescription(rs.getString(3));            
                stockingOrganDAOArrayList.add(stockingOrganDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return stockingOrganDAOArrayList;
    }
    
    public ArrayList<KinshipDAO> getKinshipArrayList() {
        ArrayList<KinshipDAO> kinshipDAOArrayList = new ArrayList<KinshipDAO>();
        KinshipDAO kinshipDAO;
        
        String sql = "SELECT * FROM funpref.kinship;";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                kinshipDAO = new KinshipDAO();
                kinshipDAO.setId( rs.getInt(1));
                kinshipDAO.setDescription(rs.getString(3));            
                kinshipDAOArrayList.add(kinshipDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return kinshipDAOArrayList;
    }    

    public ArrayList<IssuingBodyDAO> getIssuingBodyArrayList() {
        ArrayList<IssuingBodyDAO> issuingBodyDAOArrayList = new ArrayList<IssuingBodyDAO>();
        IssuingBodyDAO issuingBodyDAO;
        
        String sql = "SELECT * FROM funpref.issuing_body;";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                issuingBodyDAO = new IssuingBodyDAO();
                issuingBodyDAO.setId( rs.getInt(1));
                issuingBodyDAO.setInitials(rs.getString(2));            
                issuingBodyDAOArrayList.add(issuingBodyDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return issuingBodyDAOArrayList;
    }

    public ArrayList<ProvinceDAO> getProvinceDAOArrayList() {
        ArrayList<ProvinceDAO> provinceDAOArrayList = new ArrayList<ProvinceDAO>();
        ProvinceDAO provinceDAO;
        
        String sql = "SELECT * FROM funpref.province";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                provinceDAO = new ProvinceDAO();
                provinceDAO.setId( rs.getInt(1));
                provinceDAO.setInitial(rs.getString(2));            
                provinceDAO.setName(rs.getString(3)); 
                provinceDAOArrayList.add(provinceDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return provinceDAOArrayList;
    }

    public ArrayList<BenefitTypeDAO> getBenefitTypeDAOArrayList() {
        ArrayList<BenefitTypeDAO> benefitTypeDAOArrayList = new ArrayList<BenefitTypeDAO>();
        BenefitTypeDAO benefitTypeDAO;
        
        String sql = "SELECT * FROM funpref.benefit_type;";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                benefitTypeDAO = new BenefitTypeDAO();
                benefitTypeDAO.setId( rs.getInt(1));
                benefitTypeDAO.setDescription(rs.getString(3));            
                benefitTypeDAOArrayList.add(benefitTypeDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return benefitTypeDAOArrayList;
    }
    
    public ArrayList<CadastralStatusDAO> getCadastralStatusDAOArrayList() {
        ArrayList<CadastralStatusDAO> cadastralStatusDAOArrayList = new ArrayList<CadastralStatusDAO>();
        CadastralStatusDAO cadastralStatusDAO;
        
        String sql = "SELECT * FROM funpref.cadastral_status;";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                cadastralStatusDAO = new CadastralStatusDAO();
                cadastralStatusDAO.setId( rs.getInt(1));
                cadastralStatusDAO.setDescription(rs.getString(2));            
                cadastralStatusDAOArrayList.add(cadastralStatusDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return cadastralStatusDAOArrayList;
    }    
    
    public ArrayList<DegreeEducationDAO> getDegreeEducationArrayList() {
        ArrayList<DegreeEducationDAO> degreeEducationDAOArrayList = new ArrayList<DegreeEducationDAO>();
        DegreeEducationDAO degreeEducationDAO;
        
        String sql = "SELECT * FROM funpref.degree_of_education;";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                degreeEducationDAO = new DegreeEducationDAO();
                degreeEducationDAO.setId( rs.getInt(1));
                degreeEducationDAO.setDescription(rs.getString(3));            
                degreeEducationDAOArrayList.add(degreeEducationDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return degreeEducationDAOArrayList;
    }  
    
    ArrayList<DeficiencyDAO> getDeficiencyArrayList() {
        ArrayList<DeficiencyDAO> deficiencyDAOArrayList = new ArrayList<DeficiencyDAO>();
        DeficiencyDAO deficiencyDAO;
        
        String sql = "SELECT * FROM funpref.deficiency;";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                deficiencyDAO = new DeficiencyDAO();
                deficiencyDAO.setId( rs.getInt(1));
                deficiencyDAO.setDescription(rs.getString(3));            
                deficiencyDAOArrayList.add(deficiencyDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return deficiencyDAOArrayList;
    }    

    public ArrayList<CityDAO> getCityDAOArrayList(int idProvince) {
        ArrayList<CityDAO> cityDAOArrayList = new ArrayList<CityDAO>();
        CityDAO cityDAO;
        
        String sql = "SELECT * FROM funpref.city where id_province = " + idProvince;
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                cityDAO = new CityDAO();
                cityDAO.setId( rs.getInt(1));
                cityDAO.setName(rs.getString(2));            
                cityDAO.setIdProvince(rs.getInt(3));
                cityDAOArrayList.add(cityDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return cityDAOArrayList;
    }    

    ArrayList<ContentSearchTableItensDAO> getContentSearchTableArrayList(String text, boolean evaluateDeath ) {
        ArrayList<ContentSearchTableItensDAO> contentSearcheTableItensDAOArrayList = new ArrayList<ContentSearchTableItensDAO>();
        ContentSearchTableItensDAO contentSearchTableItensDAO;
        
        String sql = "SELECT id_beneficiary, enrollment, name FROM funpref.beneficiary where name like '%" + text + "%'";
        
        if( evaluateDeath ) {
            sql += " and death_date is not null";
        }
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                contentSearchTableItensDAO = new ContentSearchTableItensDAO();
                contentSearchTableItensDAO.setId(rs.getInt(1));
                contentSearchTableItensDAO.setEnrollment( rs.getInt(2));
                contentSearchTableItensDAO.setName(rs.getString(3));
                contentSearcheTableItensDAOArrayList.add(contentSearchTableItensDAO);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return contentSearcheTableItensDAOArrayList;
    }
    
    String getBeneficiaryNameByEnrollment(int instituteEnrollment) {
        String name = "";
        String sql = "SELECT name FROM funpref.beneficiary where enrollment = " + instituteEnrollment;
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if( rs.next() ) {            
                name = rs.getString(1);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return name;
    }

    int getBeneficiaryEnrollmentById(int beneficiaryId) {
        int enrollment = -1;
        String sql = "SELECT enrollment FROM funpref.beneficiary where id_beneficiary = " + beneficiaryId;
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if( rs.next() ) {            
                enrollment = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return enrollment;
    }    

    Beneficiary getBeneficiary(int idBeneficiary) {
        Beneficiary beneficiary = null;
        int column = 1;
        
        String sql = "SELECT * FROM funpref.beneficiary where id_beneficiary = '" + idBeneficiary + "'";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if( rs.next() ) {            
                beneficiary = new Beneficiary();
                beneficiary.setId(rs.getInt(column++));
                beneficiary.setRegistration(rs.getInt(column++));
                beneficiary.setOrdinance(rs.getString(column++));
                beneficiary.setCpf(rs.getString(column++));
                beneficiary.setName(rs.getString(column++));  
                
                if( rs.getString(column) == null ) {
                    beneficiary.setSex(Beneficiary.Sex.NULL);
                }
                
                else if( rs.getString(column).compareTo("m") == 0 ) {
                    beneficiary.setSex(Beneficiary.Sex.MALE);
                }
                
                else {
                    beneficiary.setSex(Beneficiary.Sex.FEMALE);
                }
                
                column++;
                
                beneficiary.setBirthDate(rs.getDate(column++));
                beneficiary.setIdCadastralStatus(rs.getInt(column++));                
                column++; // update number                
                beneficiary.setIdUserRegistration(rs.getInt(column++));// user registration
                column++; // user update
                beneficiary.setNationality(rs.getString(column++));
                beneficiary.setIdCityPlaceOfBirth( rs.getInt(column++) );
                beneficiary.setIdProvincePlaceOfBirth( getProvinceId( beneficiary.getIdCityPlaceOfBirth() ) );
                beneficiary.setDeathDate(rs.getDate(column++));
                
                if( rs.wasNull() ) {
                    beneficiary.setDeceased(false);
                }
                
                else {
                    beneficiary.setDeceased(true);
                }
                
                beneficiary.setAddress(rs.getString(column++));
                beneficiary.setIdCityAddress(rs.getInt(column++));
                beneficiary.setIdProvinceAddress(getProvinceId(beneficiary.getIdCityAddress()));
                beneficiary.setZipCode(rs.getString(column++));
                beneficiary.setPhone1(rs.getString(column++));
                beneficiary.setPhone2(rs.getString(column++));
                beneficiary.setEmail(rs.getString(column++));
                beneficiary.setIdDegreeOfEducation(rs.getInt(column++));
                beneficiary.setIdMaritalStatus(rs.getInt(column++));
                beneficiary.setIdDeficiency(rs.getInt(column++));
                beneficiary.setInvalidityReason(rs.getString(column++));
                beneficiary.setInvalidityAwardDate(rs.getDate(column++));
                beneficiary.setMotherCpf(rs.getString(column++));
                beneficiary.setMotherName(rs.getString(column++));
                beneficiary.setFatherCpf(rs.getString(column++));
                beneficiary.setFatherName(rs.getString(column++));   
                beneficiary.setPisPasep(rs.getString(column++));
                beneficiary.setVotersTitle(rs.getString(column++));                                
                beneficiary.setElectoralZone(rs.getInt(column++));
                beneficiary.setElectoralSection(rs.getInt(column++));
                beneficiary.setIdProvinceElectoralZone(rs.getInt(column++));
                beneficiary.setRg(rs.getString(column++));
                beneficiary.setRgEmissionDate(rs.getDate(column++));
                beneficiary.setIdRgIssuingBody(rs.getInt(column++));
                beneficiary.setIdProvinceRg(rs.getInt(column++));
                beneficiary.setAdmissionDate(rs.getDate(column++));
                beneficiary.setApplicationDate(rs.getDate(column++));
                beneficiary.setInactivationDate(rs.getDate(column++));
                beneficiary.setIdBenefitType(rs.getInt(column++));
                beneficiary.setIdCalculationForm(rs.getInt(column++));
                beneficiary.setIdStockingOrgan(rs.getInt(column++));
                beneficiary.setOffice(rs.getString(column++));
                column++; // previous time
                beneficiary.setInstituteEnrollment(rs.getInt(column++));
                beneficiary.setPhysicalDocumentDrawer(rs.getInt(column++));
                beneficiary.setIndexPhysicalDocument( rs.getInt(column++));
                beneficiary.setBankAgency(rs.getString(column++));
                beneficiary.setAccount(rs.getString(column++));
                column++; // earnings
                column++; // old_promotion
                column++; // chalk powder percentual
                column++; // chalk powder value
                column++; // more one year percentual
                column++; // more one year value
                column++; // more five year percentual
                column++; // more five year value                
                column++; // income tax rate
                column++; // income tax value                  
                column++; // payroll loans value                                
                column++; // payroll loans gross value
                column++; // payroll loans net value
                column++; // active
                column++; // created_at datetime                
                beneficiary.setUpdateDate(rs.getDate(column++));
                //column++; // updated_at datetime
                
                beneficiary.setDependents( loadDependents( beneficiary.getId() ) );
                
                if( ( beneficiary.getAdmissionDate() != null ) && ( beneficiary.getInactivationDate() != null ) ) {
                    beneficiary.setContributionTime( Period.between( new java.util.Date(beneficiary.getAdmissionDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                            new java.util.Date(beneficiary.getInactivationDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));        
                }

                try {
                    if( beneficiary.getInactivationDate() != null ) {
                        beneficiary.setContributionTimeFUNPREF( Period.between(formatDate.parse("01/01/1994").toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                new java.util.Date(beneficiary.getInactivationDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) );
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(JDBCController.class.getName()).log(Level.SEVERE, null, ex);
                }

                if( beneficiary.getInactivationDate() != null ) {
                    beneficiary.setInactivityTime( Period.between(new java.util.Date(beneficiary.getInactivationDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                            (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) );                   
                }
            }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }        
        
        return beneficiary;
    }
    
    private ArrayList<Dependent> loadDependents(int idBeneficiary ) {
        ArrayList<Dependent> dependents = new ArrayList<Dependent>();
        Dependent dependent = null;
        
        String sql = "SELECT * FROM funpref.dependent where ref_id_beneficiary = '" + idBeneficiary + "'";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {            
                dependent = new Dependent();
                dependent.setId(rs.getInt(1));
                dependent.setName(rs.getString(2));
                dependent.setCpf(rs.getString(3));
                dependent.setBirthDate(rs.getDate(4));
                dependent.setIdKinship(rs.getInt(5));
                dependent.setIdDeficiency(rs.getInt(6));
                // idBeneficiary
                
                if( rs.getString(8) == null ) {
                    dependent.setSex(Beneficiary.Sex.NULL);
                }
                
                else if( rs.getString(8).compareTo("m") == 0 ) {
                    dependent.setSex(Beneficiary.Sex.MALE);
                }
                
                else {
                    dependent.setSex(Beneficiary.Sex.FEMALE);
                }
                
                dependent.setPhone(rs.getString(9));
                
                if( dependent.getBirthDate()!= null ) {
                    dependent.setAge(Period.between(new java.util.Date(dependent.getBirthDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                            (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) );                   
                }                
                
                dependents.add(dependent);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }             
        
        return dependents;
    }    

    private int getProvinceId(int idCity ) {
        int provinceId = -1;
        String sql = "SELECT id_province FROM funpref.city WHERE id_city = " + idCity;
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if( rs.next() ) {  
                provinceId = rs.getInt(1);                
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        
        return provinceId;        
    }

    public int saveBeneficiary(Beneficiary beneficiary, UserDAO user) {
        
        int row;
        int dependentRow;
        
        if( beneficiary.getId() >= 0 ) {
            row = updateBeneficiary( beneficiary, user );
        }
        
        else {
            row = insertBeneficiary( beneficiary, user );
        }
        
        if( row >= 0 ) {
            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                if( beneficiary.getDependents().get(i).getId() >= 0 ) {
                    dependentRow = updateDependent( beneficiary.getDependents().get(i), beneficiary.getId(), user );                    
                }
                
                else {
                    dependentRow = insertDependent( beneficiary.getDependents().get(i), beneficiary.getId(), user );                    
                    beneficiary.getDependents().get(i).setId(lastInsertDependentId);
                }                
            }
        }
        
        return row;
    }

    private int updateBeneficiary(Beneficiary beneficiary, UserDAO user) {
        String sqlUpdateBeneficiary = "UPDATE funpref.beneficiary\n" +
            "SET enrollment=?, ordinance=?, cpf=?, name=?, sex=?, birth_date=?, ref_id_cadastral_status=?, "
                + "update_number=?, ref_id_user_registration=?, ref_id_user_update=?, "
                + "nationality=?, ref_id_city_place_of_birth=?, death_date=?, address=?, ref_id_city_address=?, "
                + "zipcode=?, phone1=?, phone2=?, email=?, ref_id_degree_of_education=?, ref_id_marital_status=?, "
                + "ref_id_deficiency=?, deficiency_reason=?, report_deficiency_date=?, mother_cpf=?, "
                + "mother_name=?, father_cpf=?, father_name=?, pis_pasep=?, voters_title=?, electoral_zone=?, "
                + "electoral_section=?, ref_id_province_electoral_zone=?, rg=?, rg_emission_date=?, "
                + "ref_id_issuing_body=?, ref_id_province_rg=?, admission_date=?, aplication_date=?, "
                + "grant_of_benefit_date=?, ref_id_benefity_type=?, ref_id_calculation_form=?, "
                + "ref_id_stocking_organ=?, office=?, previous_time=?, id_institutes_enrollment=?, "
                + "physical_document_drawer=?, index_physical_document=?, bank_agency=?, account=?, "
                + "earnings=?, old_promotion=?, chalk_powder_percentual=?, chalk_powder_value=?, "
                + "more_one_year_percentual=?, more_one_year_value=?, more_five_year_percentual=?, "
                + "more_five_year_value=?, income_tax_rate=?, income_tax_value=?, payroll_loans_value=?, "
                + "payroll_loans_gross_value=?, payroll_loans_net_value=?, active=?, created_at=?, updated_at=?\n" +
            "WHERE id_beneficiary=?";
        
        int row = -1;
        int column = 1;
        
        try {                
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateBeneficiary);
            preparedStatement.setInt(column++, beneficiary.getRegistration());
            preparedStatement.setString(column++, beneficiary.getOrdinance());
            preparedStatement.setString(column++, beneficiary.getCpf());            
            preparedStatement.setString(column++, beneficiary.getName());
                
            if( beneficiary.getSex() == Beneficiary.Sex.NULL ) {
                preparedStatement.setString(column, null);
            }

            else if( beneficiary.getSex() == Beneficiary.Sex.MALE ) {
                preparedStatement.setString(column, "m");
            }

            else {
                preparedStatement.setString(column, "f");
            }
            
            column++;
            
            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getBirthDate().getTime() ) );
            preparedStatement.setInt(column++, beneficiary.getIdCadastralStatus()); 
            preparedStatement.setNull(column++, Types.INTEGER); // update number            
            preparedStatement.setInt(column++, beneficiary.getIdUserRegistration()); // user registration
            preparedStatement.setInt(column++, user.getId()); // user update
            
            preparedStatement.setString(column++, beneficiary.getNationality());
            preparedStatement.setInt(column++, beneficiary.getIdCityPlaceOfBirth()); 
            
            if( beneficiary.getDeathDate() != null ) {
                preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getDeathDate().getTime() ) );
            }
            
            else {
                preparedStatement.setNull(column++, Types.DATE);
            }
            
            preparedStatement.setString(column++, beneficiary.getAddress());
            preparedStatement.setInt(column++, beneficiary.getIdCityAddress());
            preparedStatement.setString(column++, beneficiary.getZipCode());
            preparedStatement.setString(column++, beneficiary.getPhone1());
            preparedStatement.setString(column++, beneficiary.getPhone2());
            preparedStatement.setString(column++, beneficiary.getEmail());
            preparedStatement.setInt(column++, beneficiary.getIdDegreeOfEducation() );
            preparedStatement.setInt(column++, beneficiary.getIdMaritalStatus());
            preparedStatement.setInt(column++, beneficiary.getIdDeficiency());
            preparedStatement.setString(column++, beneficiary.getInvalidityReason());
            
            if( beneficiary.getInvalidityAwardDate() != null ) {
                preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getInvalidityAwardDate().getTime() ) );
            }
            
            else {
                preparedStatement.setNull(column++, Types.DATE);
            }

            preparedStatement.setString(column++, beneficiary.getMotherCpf());
            preparedStatement.setString(column++, beneficiary.getMotherName());
            preparedStatement.setString(column++, beneficiary.getFatherCpf());
            preparedStatement.setString(column++, beneficiary.getFatherName());
            preparedStatement.setString(column++, beneficiary.getPisPasep());
            preparedStatement.setString(column++, beneficiary.getVotersTitle());
            preparedStatement.setInt(column++, beneficiary.getElectoralZone());
            preparedStatement.setInt(column++, beneficiary.getElectoralSection());
            preparedStatement.setInt(column++, beneficiary.getIdProvinceElectoralZone());
            preparedStatement.setString(column++, beneficiary.getRg());
            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getRgEmissionDate().getTime() )  );
            preparedStatement.setInt(column++, beneficiary.getIdRgIssuingBody());
            preparedStatement.setInt(column++, beneficiary.getIdProvinceRg());
            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getAdmissionDate().getTime() ) );
            
            if( beneficiary.getApplicationDate() != null ) {
                preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getApplicationDate().getTime() ) );
            }
            
            else {
                preparedStatement.setNull(column++, Types.DATE);
            }
            
            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getInactivationDate().getTime() ) );
            preparedStatement.setInt(column++, beneficiary.getIdBenefitType());
            preparedStatement.setInt(column++, beneficiary.getIdCalculationForm());
            preparedStatement.setInt(column++, beneficiary.getIdStockingOrgan());
            preparedStatement.setString(column++, beneficiary.getOffice() );
            preparedStatement.setNull(column++, Types.INTEGER); // previous time
            
            if( beneficiary.getInstituteEnrollment() >= 0 ) {
                preparedStatement.setInt(column++, beneficiary.getInstituteEnrollment());                
            }
            
            else {
                preparedStatement.setNull(column++, Types.INTEGER);                
            }            
            
            preparedStatement.setInt(column++, beneficiary.getPhysicalDocumentDrawer());
            preparedStatement.setInt(column++, beneficiary.getIndexPhysicalDocument());
            preparedStatement.setString(column++, beneficiary.getBankAgency());
            preparedStatement.setString(column++, beneficiary.getAccount());            
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(52, beneficiary.getEarningsInative());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(53, beneficiary.getOldPromotionValue());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(54, beneficiary.getChalkPowderPercentual());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(55, beneficiary.getChalkPowderValue());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(56, beneficiary.getMoreOneYearPercentual());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(57, beneficiary.getMoreOneYearValue());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(58, beneficiary.getMoreFiveYearPercentual());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(59, beneficiary.getMoreFiveYearValue());            
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(60, beneficiary.getIncomeTaxRate());            
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(61, beneficiary.getIncomeTaxValue());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(62, beneficiary.getPayrollLoanValue());
            preparedStatement.setNull(column++, Types.DOUBLE); // payroll_loans_gross_value
            preparedStatement.setNull(column++, Types.DOUBLE); // payroll_loans_net_value
            preparedStatement.setBoolean(column++, true);
            preparedStatement.setNull(column++, Types.DATE); // created_at datetime
            preparedStatement.setNull(column++, Types.DATE); // updated_at datetime
            preparedStatement.setInt(column++, beneficiary.getId());            

            
            //beneficiary.setDependents( loadDependents( beneficiary.getId() ) );            
            
            row = preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return row;        
    }

    private int insertBeneficiary(Beneficiary beneficiary, UserDAO user) {
        String sqlInsertBeneficiary = "INSERT INTO funpref.beneficiary\n" +
            "(enrollment, ordinance, cpf, name, sex, birth_date, ref_id_cadastral_status, update_number, "
                + "ref_id_user_registration, ref_id_user_update, nationality, "
                + "ref_id_city_place_of_birth, death_date, address, ref_id_city_address, zipcode, phone1, "
                + "phone2, email, ref_id_degree_of_education, ref_id_marital_status, ref_id_deficiency, "
                + "deficiency_reason, report_deficiency_date, mother_cpf, mother_name, father_cpf, father_name, "
                + "pis_pasep, voters_title, electoral_zone, electoral_section, ref_id_province_electoral_zone, "
                + "rg, rg_emission_date, ref_id_issuing_body, ref_id_province_rg, admission_date, aplication_date, "
                + "grant_of_benefit_date, ref_id_benefity_type, ref_id_calculation_form, ref_id_stocking_organ, "
                + "office, previous_time, id_institutes_enrollment, physical_document_drawer, "
                + "index_physical_document, bank_agency, account, earnings, old_promotion, chalk_powder_percentual, "
                + "chalk_powder_value, more_one_year_percentual, more_one_year_value, "
                + "more_five_year_percentual, more_five_year_value, income_tax_rate, income_tax_value, "
                + "payroll_loans_value, payroll_loans_gross_value, payroll_loans_net_value, active, created_at, updated_at )\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String sqlLastInsertId = "SELECT LAST_INSERT_ID()";
        
        int row = -1;
        int column = 1;
        
        try {                
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertBeneficiary);
            preparedStatement.setInt(column++, beneficiary.getRegistration());
            preparedStatement.setString(column++, beneficiary.getOrdinance());
            preparedStatement.setString(column++, beneficiary.getCpf());            
            preparedStatement.setString(column++, beneficiary.getName());
                
            if( beneficiary.getSex() == Beneficiary.Sex.NULL ) {
                preparedStatement.setString(column, null);
            }

            else if( beneficiary.getSex() == Beneficiary.Sex.MALE ) {
                preparedStatement.setString(column, "m");
            }

            else {
                preparedStatement.setString(column, "f");
            }
            column++;
            
            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getBirthDate().getTime() ) );
            preparedStatement.setInt(column++, beneficiary.getIdCadastralStatus()); 
            preparedStatement.setNull(column++, Types.INTEGER); // update number            
            preparedStatement.setInt(column++, user.getId()); // user registration
            preparedStatement.setInt(column++, user.getId()); // user update
            
            preparedStatement.setString(column++, beneficiary.getNationality());
            preparedStatement.setInt(column++, beneficiary.getIdCityPlaceOfBirth()); 
            
            if( beneficiary.getDeathDate() != null ) {
                preparedStatement.setDate(column, new java.sql.Date( beneficiary.getDeathDate().getTime() ) );
            }
            
            else {
                preparedStatement.setNull(column, Types.DATE);
            }
            
            column++;
            
            preparedStatement.setString(column++, beneficiary.getAddress());
            preparedStatement.setInt(column++, beneficiary.getIdCityAddress());
            preparedStatement.setString(column++, beneficiary.getZipCode());
            preparedStatement.setString(column++, beneficiary.getPhone1());
            preparedStatement.setString(column++, beneficiary.getPhone2());
            preparedStatement.setString(column++, beneficiary.getEmail());
            preparedStatement.setInt(column++, beneficiary.getIdDegreeOfEducation() );
            preparedStatement.setInt(column++, beneficiary.getIdMaritalStatus());
            preparedStatement.setInt(column++, beneficiary.getIdDeficiency());
            preparedStatement.setString(column++, beneficiary.getInvalidityReason());
            
            if( beneficiary.getInvalidityAwardDate() != null ) {
                preparedStatement.setDate(column, new java.sql.Date( beneficiary.getInvalidityAwardDate().getTime() ) );
            }
            
            else {
                preparedStatement.setNull(column, Types.DATE);
            }
            
            column++;

            preparedStatement.setString(column++, beneficiary.getMotherCpf());
            preparedStatement.setString(column++, beneficiary.getMotherName());
            preparedStatement.setString(column++, beneficiary.getFatherCpf());
            preparedStatement.setString(column++, beneficiary.getFatherName());
            preparedStatement.setString(column++, beneficiary.getPisPasep());
            preparedStatement.setString(column++, beneficiary.getVotersTitle());
            preparedStatement.setInt(column++, beneficiary.getElectoralZone());
            preparedStatement.setInt(column++, beneficiary.getElectoralSection());
            preparedStatement.setInt(column++, beneficiary.getIdProvinceElectoralZone());
            preparedStatement.setString(column++, beneficiary.getRg());
            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getRgEmissionDate().getTime() )  );
            preparedStatement.setInt(column++, beneficiary.getIdRgIssuingBody());
            preparedStatement.setInt(column++, beneficiary.getIdProvinceRg());
            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getAdmissionDate().getTime() ) );
            
            if( beneficiary.getApplicationDate() != null ) {
                preparedStatement.setDate(column, new java.sql.Date( beneficiary.getApplicationDate().getTime() ) );
            }
            
            else {
                preparedStatement.setNull(column, Types.DATE);
            }
            
            column++;
            
            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getInactivationDate().getTime() ) );
            preparedStatement.setInt(column++, beneficiary.getIdBenefitType());
            preparedStatement.setInt(column++, beneficiary.getIdCalculationForm());
            preparedStatement.setInt(column++, beneficiary.getIdStockingOrgan());
            preparedStatement.setString(column++, beneficiary.getOffice() );
            preparedStatement.setNull(column++, Types.INTEGER); // previous time
            
            if( beneficiary.getInstituteEnrollment() >= 0 ) {
                preparedStatement.setInt(column, beneficiary.getInstituteEnrollment());                
            }
            
            else {
                preparedStatement.setNull(column, Types.INTEGER);                
            }
            
            column++;
            
            preparedStatement.setInt(column++, beneficiary.getPhysicalDocumentDrawer());
            preparedStatement.setInt(column++, beneficiary.getIndexPhysicalDocument());
            preparedStatement.setString(column++, beneficiary.getBankAgency());
            preparedStatement.setString(column++, beneficiary.getAccount());            
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(52, beneficiary.getEarningsInative());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(53, beneficiary.getOldPromotionValue());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(54, beneficiary.getChalkPowderPercentual());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(55, beneficiary.getChalkPowderValue());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(56, beneficiary.getMoreOneYearPercentual());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(57, beneficiary.getMoreOneYearValue());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(58, beneficiary.getMoreFiveYearPercentual());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(59, beneficiary.getMoreFiveYearValue());            
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(60, beneficiary.getIncomeTaxRate());            
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(61, beneficiary.getIncomeTaxValue());
            preparedStatement.setNull(column++, Types.DOUBLE); //preparedStatement.setDouble(62, beneficiary.getPayrollLoanValue());
            preparedStatement.setNull(column++, Types.DOUBLE); // payroll_loans_gross_value
            preparedStatement.setNull(column++, Types.DOUBLE); // payroll_loans_net_value
            preparedStatement.setBoolean(column++, true);                      
            preparedStatement.setNull(column++, Types.DATE); // created_at datetime
            preparedStatement.setNull(column++, Types.DATE); // updated_at datetime

            
            //beneficiary.setDependents( loadDependents( beneficiary.getId() ) );            
            
            row = preparedStatement.executeUpdate();  
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlLastInsertId);

            if( rs.next() ) {
                lastInsertId = rs.getInt(1);
            }
            
            else {
                row = -1;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
        
        return row;
    }    
    
    public int getIdLogin() {
        return idLogin;
    }

    public String getNameLogin() {
        return nameLogin;
    }

    public String getOfficeLogin() {
        return officeLogin;
    }

    private int updateDependent(Dependent dependent, int id, UserDAO user) {
        String sqlUpdateDependent = "UPDATE `funpref`.`dependent`\n" +
            "SET\n" +            
            "`name` = ?,\n" +
            "`cpf` = ?,\n" +
            "`birth_date` = ?,\n" +
            "`ref_id_kinship` = ?,\n" +
            "`ref_if_deficiency` = ?,\n" +
            "`ref_id_beneficiary` = ?,\n" +
            "`sex` = ?,\n" +
            "`phone` = ?\n" +
            "WHERE `id_dependent` = ?";
        
        int row = -1;
        
        try {                
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateDependent);          
            preparedStatement.setString(1, dependent.getName());
            preparedStatement.setString(2, dependent.getCpf());
            preparedStatement.setDate(3, new java.sql.Date( dependent.getBirthDate().getTime() ) );
            preparedStatement.setInt(4, dependent.getIdKinship());
            preparedStatement.setInt(5, dependent.getIdDeficiency());
            preparedStatement.setInt(6, id);
                
            if( dependent.getSex() == Beneficiary.Sex.NULL ) {
                preparedStatement.setString(7, null);
            }

            else if( dependent.getSex() == Beneficiary.Sex.MALE ) {
                preparedStatement.setString(7, "m");
            }

            else {
                preparedStatement.setString(7, "f");
            }
            
            preparedStatement.setString(8, dependent.getPhone() );
            preparedStatement.setInt(9, dependent.getId());                       
            
            row = preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return row; 
    }

    private int insertDependent(Dependent dependent, int id, UserDAO user) {
        String sqlInsertDependent = "INSERT INTO `funpref`.`dependent`\n" +
            "(`name`,\n" +
            "`cpf`,\n" +
            "`birth_date`,\n" +
            "`ref_id_kinship`,\n" +
            "`ref_if_deficiency`,\n" +
            "`ref_id_beneficiary`,\n" +
            "`sex`,\n" +
            "`phone`)\n" +
            "VALUES\n" +
            "(?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?,\n" +
            "?)";
        
        String sqlLastInsertId = "SELECT LAST_INSERT_ID()";        
        
        int row = -1;
        
        try {                
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertDependent);          
            preparedStatement.setString(1, dependent.getName());
            preparedStatement.setString(2, dependent.getCpf());
            preparedStatement.setDate(3, new java.sql.Date( dependent.getBirthDate().getTime() ) );
            preparedStatement.setInt(4, dependent.getIdKinship());
            preparedStatement.setInt(5, dependent.getIdDeficiency());
            preparedStatement.setInt(6, id);
                
            if( dependent.getSex() == Beneficiary.Sex.NULL ) {
                preparedStatement.setString(7, null);
            }

            else if( dependent.getSex() == Beneficiary.Sex.MALE ) {
                preparedStatement.setString(7, "m");
            }

            else {
                preparedStatement.setString(7, "f");
            }
            
            preparedStatement.setString(8, dependent.getPhone() );                                  
            
            row = preparedStatement.executeUpdate();      
            
            Statement stmt = connection.createStatement();
            
            ResultSet rs = stmt.executeQuery(sqlLastInsertId);

            if( rs.next() ) {
                lastInsertDependentId = rs.getInt(1);
            }
            
            else {
                row = -1;
            }            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return row; 
    }

    ArrayList<ArrayList<Object>> getReportBeneficiaryData() {
        ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> beneficiaryData;
        
        String sql = "SELECT enrollment as matricula, ordinance as portaria, cpf, name as nome, "
                + "address as endereco, phone1 as fone1, phone2 as fone2, funpref.cadastral_status.status\n "                
                + "FROM funpref.beneficiary\n "
                + "inner join funpref.cadastral_status on funpref.cadastral_status.id_cadastral_status = funpref.beneficiary.ref_id_cadastral_status\n "
                + "order by name";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {  
                beneficiaryData = new ArrayList<Object>();
                beneficiaryData.add( rs.getInt(1) );
                beneficiaryData.add( rs.getString(2) );
                beneficiaryData.add( rs.getString(3) );
                beneficiaryData.add( rs.getString(4) );
                beneficiaryData.add( rs.getString(5) );
                beneficiaryData.add( rs.getString(6) );
                beneficiaryData.add( rs.getString(7) );
                beneficiaryData.add( rs.getString(8) );
                data.add(beneficiaryData);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return data;
    }
    
    ArrayList<ArrayList<Object>> getReportDependentData() {
        ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> dependentData;
        
        String sql = "SELECT funpref.dependent.name, funpref.dependent.cpf, funpref.dependent.phone, "
                + "funpref.beneficiary.name, funpref.kinship.description\n "
                + "FROM funpref.beneficiary\n "
                + "inner join funpref.dependent on funpref.dependent.ref_id_beneficiary = funpref.beneficiary.id_beneficiary\n "
                + "inner join funpref.kinship on funpref.kinship.id_kinship = funpref.dependent.ref_id_kinship\n "
                + "order by funpref.beneficiary.name, funpref.dependent.name";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {  
                dependentData = new ArrayList<Object>();
                dependentData.add( rs.getString(1) );
                dependentData.add( rs.getString(2) );
                dependentData.add( rs.getString(3) );
                dependentData.add( rs.getString(4) );
                dependentData.add( rs.getString(5) );
                data.add(dependentData);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return data;
    }    

    ArrayList<ArrayList<Object>> getReportBeneficiaryPendingData() {
        ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> beneficiaryData;
        
        String sql = "SELECT enrollment as matricula, ordinance as portaria, cpf, name as nome, "
                + "address as endereco, phone1 as fone1, phone2 as fone2, funpref.cadastral_status.status\n "                
                + "FROM funpref.beneficiary\n "
                + "inner join funpref.cadastral_status on funpref.cadastral_status.id_cadastral_status = funpref.beneficiary.ref_id_cadastral_status\n "
                + "where funpref.cadastral_status.id_cadastral_status <> 2\n "
                + "order by name";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {  
                beneficiaryData = new ArrayList<Object>();
                beneficiaryData.add( rs.getInt(1) );
                beneficiaryData.add( rs.getString(2) );
                beneficiaryData.add( rs.getString(3) );
                beneficiaryData.add( rs.getString(4) );
                beneficiaryData.add( rs.getString(5) );
                beneficiaryData.add( rs.getString(6) );
                beneficiaryData.add( rs.getString(7) );
                beneficiaryData.add( rs.getString(8) );
                data.add(beneficiaryData);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return data;
    }

    ArrayList<ArrayList<Object>> getReportBeneficiaryDeceasedData() {
        ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> beneficiaryData;
        
        String sql = "SELECT enrollment as matricula, ordinance as portaria, cpf, name as nome, "
                + "address as endereco, phone1 as fone1, phone2 as fone2, death_date\n "                
                + "FROM funpref.beneficiary\n "
                + "inner join funpref.cadastral_status on funpref.cadastral_status.id_cadastral_status = funpref.beneficiary.ref_id_cadastral_status\n "
                + "where death_date is not null\n "
                + "order by name";
        
        try {                
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while( rs.next() ) {  
                beneficiaryData = new ArrayList<Object>();
                beneficiaryData.add( rs.getInt(1) );
                beneficiaryData.add( rs.getString(2) );
                beneficiaryData.add( rs.getString(3) );
                beneficiaryData.add( rs.getString(4) );
                beneficiaryData.add( rs.getString(5) );
                beneficiaryData.add( rs.getString(6) );
                beneficiaryData.add( rs.getString(7) );
                beneficiaryData.add( rs.getString(8) );
                data.add(beneficiaryData);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }        
        
        return data;
    }

    int restartCadastralStatus() {
        
        String sql = "UPDATE funpref.beneficiary\n" +
            "SET ref_id_cadastral_status=1";
        int row = -1;
       
        try {                
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            row = preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return row;         

    }






    
}
