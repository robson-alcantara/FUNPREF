/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.concrete;

import funpref.controller.LogController;
import funpref.dao.interfaces.BeneficiaryDAO;
import funpref.model.Beneficiary;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robson
 */
public class BeneficiaryDAOImpl implements BeneficiaryDAO {

    private final SimpleDateFormat formatDate;
    private int lastInsertDependentId;
    private int columnGlobal;
    
    public BeneficiaryDAOImpl() {
        formatDate = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public boolean insert(Beneficiary beneficiary) {
        String query = "INSERT INTO funpref.beneficiary\n" +
            "(enrollment, ordinance, cpf, name, sex, birth_date, ref_id_cadastral_status, update_number, "
                + "ref_id_user_registration, ref_id_user_create, ref_id_user_update, nationality, "
                + "ref_id_city_place_of_birth, death_date, address, ref_id_city_address, zipcode, phone1, "
                + "phone2, email, ref_id_degree_of_education, ref_id_marital_status, ref_id_deficiency, "
                + "deficiency_reason, report_deficiency_date, mother_cpf, mother_name, father_cpf, father_name, "
                + "pis_pasep, voters_title, electoral_zone, electoral_section, ref_id_province_electoral_zone, "
                + "rg, rg_emission_date, ref_id_issuing_body, ref_id_province_rg, admission_date, aplication_date, "
                + "grant_of_benefit_date, ref_id_benefity_type, ref_id_calculation_form, ref_id_stocking_organ, "
                + "office, previous_time, id_institutes_enrollment, physical_document_drawer, "
                + "index_physical_document, observations, bank_agency, account, earnings, old_promotion, chalk_powder_percentual, "
                + "chalk_powder_value, more_one_year_percentual, more_one_year_value, "
                + "more_five_year_percentual, more_five_year_value, income_tax_rate, income_tax_value, "
                + "payroll_loans_value, payroll_loans_gross_value, payroll_loans_net_value, active, registered_at, created_at, updated_at )\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";        
        
        String sqlLastInsertId = "SELECT LAST_INSERT_ID()";
        boolean result = true;
        
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection().prepareStatement(query);
            
            populatePreparedStatementFromResultSet(preparedStatement, beneficiary, true );            
            
            preparedStatement.executeUpdate();            
            
            Statement stmt = DAOFactoryImpl.getConnection().createStatement();
            
            ResultSet rs = stmt.executeQuery(sqlLastInsertId);

            if( rs.next() ) {
                lastInsertDependentId = rs.getInt(1);
            }
            
            else {
                result = false;
            }                        
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
            result = false;
        }        
        
        return result;
    }

    @Override
    public boolean delete(Beneficiary beneficiary) {
        
        boolean result = true;
         
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            statement.executeUpdate("delete from beneficiary where id_beneficiary = " + beneficiary.getId() );
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
            result = false;
        }
        return result;
    }

    @Override
    public boolean update(Beneficiary beneficiary) {
        
        String query = "UPDATE funpref.beneficiary\n" +
            "SET enrollment=?, ordinance=?, cpf=?, name=?, sex=?, birth_date=?, ref_id_cadastral_status=?, "
                + "update_number=?, ref_id_user_registration=?, ref_id_user_create=?, ref_id_user_update=?, "
                + "nationality=?, ref_id_city_place_of_birth=?, death_date=?, address=?, ref_id_city_address=?, "
                + "zipcode=?, phone1=?, phone2=?, email=?, ref_id_degree_of_education=?, ref_id_marital_status=?, "
                + "ref_id_deficiency=?, deficiency_reason=?, report_deficiency_date=?, mother_cpf=?, "
                + "mother_name=?, father_cpf=?, father_name=?, pis_pasep=?, voters_title=?, electoral_zone=?, "
                + "electoral_section=?, ref_id_province_electoral_zone=?, rg=?, rg_emission_date=?, "
                + "ref_id_issuing_body=?, ref_id_province_rg=?, admission_date=?, aplication_date=?, "
                + "grant_of_benefit_date=?, ref_id_benefity_type=?, ref_id_calculation_form=?, "
                + "ref_id_stocking_organ=?, office=?, previous_time=?, id_institutes_enrollment=?, "
                + "physical_document_drawer=?, index_physical_document=?, observations=?, bank_agency=?, account=?, "
                + "earnings=?, old_promotion=?, chalk_powder_percentual=?, chalk_powder_value=?, "
                + "more_one_year_percentual=?, more_one_year_value=?, more_five_year_percentual=?, "
                + "more_five_year_value=?, income_tax_rate=?, income_tax_value=?, payroll_loans_value=?, "
                + "payroll_loans_gross_value=?, payroll_loans_net_value=?, active=?, registered_at=?, updated_at=?\n" +
            "WHERE id_beneficiary=?";
        
        boolean result = true;
        
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection().prepareStatement(query);
            
            populatePreparedStatementFromResultSet( preparedStatement, beneficiary, false );            

            preparedStatement.setInt(columnGlobal++, beneficiary.getId());            
            preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
            result = false;
        }
        
        return result;        
    }

    @Override
    public Beneficiary findByID(int beneficiaryID) {
        Beneficiary beneficiary = null;
        int column = 1;
        
        String query = "SELECT * FROM funpref.beneficiary where id_beneficiary = '" + beneficiaryID + "'";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if( resultSet.next() ) {            
                beneficiary = new Beneficiary();
                
                populateBeneficiaryFromResultSet( beneficiary, resultSet );
            }

            } catch (SQLException ex) {
                LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
                System.out.println(ex.getMessage());
            }        
        
        return beneficiary;
    }

    @Override
    public List<Beneficiary> findAll() {
        
        ArrayList<Beneficiary> result = new ArrayList<>();        
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM funpref.beneficiary order by name");

            while (resultSet.next()) {
                
                Beneficiary beneficiary = new Beneficiary();
                
                populateBeneficiaryFromResultSet(beneficiary, resultSet);

                result.add(beneficiary);
            }
        } catch (SQLException ex) {
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
            return null;
        }
        return result;
    }

    @Override
    public List<Beneficiary> findByExample(Beneficiary filterBeneficiary) {
        
        if (filterBeneficiary == null) {
            throw new IllegalArgumentException();
        }
        
        ArrayList<Beneficiary> result = new ArrayList<>();
        PreparedStatement preparedStatement;        
        
        try {
            preparedStatement = DAOFactoryImpl.getConnection().prepareStatement(
                    "select * from funpref.beneficiary "
                    + " where (id_beneficiary = ? or ? = -2147483648)"
                    + " and ( name like ? )"
                    + " order by id_beneficiary");
            if (filterBeneficiary.getId() == -1) {
                preparedStatement.setInt(1, Integer.MIN_VALUE);
                preparedStatement.setInt(2, Integer.MIN_VALUE);
            } else {
                preparedStatement.setInt(1, filterBeneficiary.getId());
                preparedStatement.setInt(2, filterBeneficiary.getId());
            }
            
            if (filterBeneficiary.getName() != null) {
                preparedStatement.setString(3, "%" + filterBeneficiary.getName() + "%");                
            }
            
            else {
                preparedStatement.setString(3, "%%");                
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {                
                Beneficiary beneficiary = new Beneficiary();
                
                populateBeneficiaryFromResultSet(beneficiary, resultSet);

                result.add(beneficiary);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
            result = null;
        }
        return result;
    }
    
    @Override
    public List<Beneficiary> findByExamplePart(Beneficiary filterBeneficiary, boolean updatingSourceBeneficiary, int currentBeneficiaryID) {
        
        if (filterBeneficiary == null) {
            throw new IllegalArgumentException();
        }
        
        ArrayList<Beneficiary> result = new ArrayList<>();
        PreparedStatement preparedStatement;        
        int column;
        
        String query = "select * from funpref.beneficiary "
                    + " where (id_beneficiary = ? or ? = -2147483648)"
                    + " and ( name like ? )";
        
        if( updatingSourceBeneficiary ) {
            query += " and death_date is not null and id_beneficiary <> " + currentBeneficiaryID;
        }        
        
        query += " order by id_beneficiary";
        
        try {
            preparedStatement = DAOFactoryImpl.getConnection().prepareStatement(query);
            if (filterBeneficiary.getId() == -1) {
                preparedStatement.setInt(1, Integer.MIN_VALUE);
                preparedStatement.setInt(2, Integer.MIN_VALUE);
            } else {
                preparedStatement.setInt(1, filterBeneficiary.getId());
                preparedStatement.setInt(2, filterBeneficiary.getId());
            }
            
            if (filterBeneficiary.getName() != null) {
                preparedStatement.setString(3, "%" + filterBeneficiary.getName() + "%");                
            }
            
            else {
                preparedStatement.setString(3, "%%");                
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {                
                column = 1;
                Beneficiary beneficiary = new Beneficiary();
                
                beneficiary.setId(resultSet.getInt(column++));
                beneficiary.setRegistration(resultSet.getInt(column++));
                column++;
                column++;
                beneficiary.setName(resultSet.getString(column++));

                result.add(beneficiary);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
            result = null;
        }
        return result;
    }    
    
    private int getProvinceId(int idCity ) {
        int provinceId = -1;
        String sql = "SELECT id_province FROM funpref.city WHERE id_city = " + idCity;
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if( rs.next() ) {  
                provinceId = rs.getInt(1);                
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        
        return provinceId;        
    }    
    
    @Override
    public String getNameByID(int beneficiaryID) {
        String name = "";
        
        String query = "SELECT name FROM funpref.beneficiary where id_beneficiary = '" + beneficiaryID + "'";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if( resultSet.next() ) {            
                name = resultSet.getString(1);
            }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }        
        
        return name;
    }    
    
    @Override
    public String getCadastralStatusDescriptionById(int idCadastralStatus) {
        String description = "";
        
        String query = "SELECT status FROM funpref.cadastral_status where id_cadastral_status = '" + idCadastralStatus + "'";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if( resultSet.next() ) {            
                description = resultSet.getString(1);
            }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }        
        
        return description;
    }    
    
    @Override
    public int getId() {
        return lastInsertDependentId;
    }
    
    @Override
    public ArrayList<ArrayList<Object>> getReportBeneficiaryData( boolean retrieveDeceasedBeneficiaries, boolean retrievePendingBeneficiaries ) {
        ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> beneficiaryData;
        
        String sql = "SELECT enrollment as matricula, ordinance as portaria, cpf, name as nome, "
                + "address as endereco, phone1 as fone1, phone2 as fone2, funpref.cadastral_status.status\n "                
                + "FROM funpref.beneficiary\n "
                + "inner join funpref.cadastral_status on funpref.cadastral_status.id_cadastral_status = funpref.beneficiary.ref_id_cadastral_status\n";
        
        if( retrieveDeceasedBeneficiaries ) {
            sql += "where death_date is not null\n";
        }
        
        else if( retrievePendingBeneficiaries ) {
            sql += "where funpref.cadastral_status.id_cadastral_status <> 2\n ";
        }
        
        sql += "order by name";
        
        try {                
            Statement stmt = DAOFactoryImpl.getConnection().createStatement();
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
    
    @Override
    public int restartCadastralStatus() {
        
        String sql = "UPDATE funpref.beneficiary\n" +
            "SET ref_id_cadastral_status=1";
        int row = -1;
       
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection().prepareStatement(sql);
            
            row = preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }
        
        return row;         

    }    
    
    private void populatePreparedStatementFromResultSet(PreparedStatement preparedStatement, Beneficiary beneficiary, boolean populateCreateDate) {
        
        columnGlobal = 1;
        //columnReference = new Integer(column);
        
        try {
            preparedStatement.setInt(columnGlobal++, beneficiary.getRegistration());
            preparedStatement.setString(columnGlobal++, beneficiary.getOrdinance());
            preparedStatement.setString(columnGlobal++, beneficiary.getCpf());            
            preparedStatement.setString(columnGlobal++, beneficiary.getName());

            if( null == beneficiary.getSex() ) {
                preparedStatement.setString(columnGlobal, null);
            }

            else switch (beneficiary.getSex()) {
                case MALE:
                    preparedStatement.setString(columnGlobal, "m");
                    break;                
                case FEMALE:
                    preparedStatement.setString(columnGlobal, "f");
                    break;
                default:
                    preparedStatement.setString(columnGlobal, null);
                    break;
            }
            
            columnGlobal++;

            preparedStatement.setDate(columnGlobal++, new java.sql.Date( beneficiary.getBirthDate().getTime() ) );
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdCadastralStatus()); 
            preparedStatement.setNull(columnGlobal++, Types.INTEGER); // update number   
            
            if( beneficiary.getIdUserRegistration() > 0 ) {
                preparedStatement.setInt(columnGlobal++, beneficiary.getIdUserRegistration()); // user registration
            }
            
            else {
                preparedStatement.setNull(columnGlobal++, Types.INTEGER);                
            }
            
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdUserCreate()); // user create
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdUserUpdate()); // user update

            preparedStatement.setString(columnGlobal++, beneficiary.getNationality());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdCityPlaceOfBirth()); 

            if( beneficiary.getDeathDate() != null ) {
                preparedStatement.setDate(columnGlobal, new java.sql.Date( beneficiary.getDeathDate().getTime() ) );
            }

            else {
                preparedStatement.setNull(columnGlobal, Types.DATE);
            }

            columnGlobal++;

            preparedStatement.setString(columnGlobal++, beneficiary.getAddress());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdCityAddress());
            preparedStatement.setString(columnGlobal++, beneficiary.getZipCode());
            preparedStatement.setString(columnGlobal++, beneficiary.getPhone1());
            preparedStatement.setString(columnGlobal++, beneficiary.getPhone2());
            preparedStatement.setString(columnGlobal++, beneficiary.getEmail());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdDegreeOfEducation() );
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdMaritalStatus());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdDeficiency());
            preparedStatement.setString(columnGlobal++, beneficiary.getInvalidityReason());

            if( beneficiary.getInvalidityAwardDate() != null ) {
                preparedStatement.setDate(columnGlobal, new java.sql.Date( beneficiary.getInvalidityAwardDate().getTime() ) );
            }

            else {
                preparedStatement.setNull(columnGlobal, Types.DATE);
            }

            columnGlobal++;

            preparedStatement.setString(columnGlobal++, beneficiary.getMotherCpf());
            preparedStatement.setString(columnGlobal++, beneficiary.getMotherName());
            preparedStatement.setString(columnGlobal++, beneficiary.getFatherCpf());
            preparedStatement.setString(columnGlobal++, beneficiary.getFatherName());
            preparedStatement.setString(columnGlobal++, beneficiary.getPisPasep());
            preparedStatement.setString(columnGlobal++, beneficiary.getVotersTitle());
            preparedStatement.setInt(columnGlobal++, beneficiary.getElectoralZone());
            preparedStatement.setInt(columnGlobal++, beneficiary.getElectoralSection());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdProvinceElectoralZone());
            preparedStatement.setString(columnGlobal++, beneficiary.getRg());
            preparedStatement.setDate(columnGlobal++, new java.sql.Date( beneficiary.getRgEmissionDate().getTime() )  );
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdRgIssuingBody());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdProvinceRg());
            preparedStatement.setDate(columnGlobal++, new java.sql.Date( beneficiary.getAdmissionDate().getTime() ) );

            if( beneficiary.getApplicationDate() != null ) {
                preparedStatement.setDate(columnGlobal, new java.sql.Date( beneficiary.getApplicationDate().getTime() ) );
            }

            else {
                preparedStatement.setNull(columnGlobal, Types.DATE);
            }

            columnGlobal++;

            preparedStatement.setDate(columnGlobal++, new java.sql.Date( beneficiary.getInactivationDate().getTime() ) );
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdBenefitType());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdCalculationForm());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIdStockingOrgan());
            preparedStatement.setString(columnGlobal++, beneficiary.getOffice() );
            preparedStatement.setNull(columnGlobal++, Types.INTEGER); // previous time

            if( beneficiary.getInstituteEnrollment() >= 0 ) {
                preparedStatement.setInt(columnGlobal, beneficiary.getInstituteEnrollment());                
            }

            else {
                preparedStatement.setNull(columnGlobal, Types.INTEGER);                
            }

            columnGlobal++;

            preparedStatement.setInt(columnGlobal++, beneficiary.getPhysicalDocumentDrawer());
            preparedStatement.setInt(columnGlobal++, beneficiary.getIndexPhysicalDocument());
            preparedStatement.setString(columnGlobal++, beneficiary.getObservations());
            preparedStatement.setString(columnGlobal++, beneficiary.getBankAgency());
            preparedStatement.setString(columnGlobal++, beneficiary.getAccount());            
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(52, beneficiary.getEarningsInative());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(53, beneficiary.getOldPromotionValue());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(54, beneficiary.getChalkPowderPercentual());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(55, beneficiary.getChalkPowderValue());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(56, beneficiary.getMoreOneYearPercentual());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(57, beneficiary.getMoreOneYearValue());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(58, beneficiary.getMoreFiveYearPercentual());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(59, beneficiary.getMoreFiveYearValue());            
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(60, beneficiary.getIncomeTaxRate());            
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(61, beneficiary.getIncomeTaxValue());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); //preparedStatement.setDouble(62, beneficiary.getPayrollLoanValue());
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); // payroll_loans_gross_value
            preparedStatement.setNull(columnGlobal++, Types.DOUBLE); // payroll_loans_net_value
            preparedStatement.setBoolean(columnGlobal++, true);                      

            if( beneficiary.getRegisterDate() != null ) {
                preparedStatement.setTimestamp(columnGlobal++, new Timestamp( beneficiary.getRegisterDate().getTime() ) );
            }

            else {
                preparedStatement.setNull(columnGlobal++, Types.TIMESTAMP);
            }

            if (populateCreateDate) {
                preparedStatement.setTimestamp(columnGlobal++, new Timestamp(beneficiary.getCreateDate().getTime() ));
            }
            
            preparedStatement.setTimestamp(columnGlobal++, new Timestamp( beneficiary.getUpdateDate().getTime() ));                        
        
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }        
    }    
    
    private void populateBeneficiaryFromResultSet(Beneficiary beneficiary, ResultSet resultSet) {
        
        int column = 1;
        
        try {
            beneficiary.setId(resultSet.getInt(column++));
            beneficiary.setRegistration(resultSet.getInt(column++));
            beneficiary.setOrdinance(resultSet.getString(column++));
            beneficiary.setCpf(resultSet.getString(column++));
            beneficiary.setName(resultSet.getString(column++));

            if( resultSet.getString(column) == null ) {
                beneficiary.setSex(Beneficiary.Sex.NULL);
            }

            else if( resultSet.getString(column).compareTo("m") == 0 ) {
                beneficiary.setSex(Beneficiary.Sex.MALE);
            }

            else {
                beneficiary.setSex(Beneficiary.Sex.FEMALE);
            }

            column++;

            beneficiary.setBirthDate(resultSet.getDate(column++));
            beneficiary.setIdCadastralStatus(resultSet.getInt(column++));
            column++; // update number
            beneficiary.setIdUserRegistration(resultSet.getInt(column++)); // user registration
            beneficiary.setIdUserCreate(resultSet.getInt(column++)); // user create
            beneficiary.setIdUserUpdate(resultSet.getInt(column++)); // user update
            beneficiary.setNationality(resultSet.getString(column++));
            beneficiary.setIdCityPlaceOfBirth( resultSet.getInt(column++) );
            beneficiary.setIdProvincePlaceOfBirth( getProvinceId( beneficiary.getIdCityPlaceOfBirth() ) );
            beneficiary.setDeathDate(resultSet.getDate(column++));

            if( resultSet.wasNull() ) {
                beneficiary.setDeceased(false);
            }

            else {
                beneficiary.setDeceased(true);
            }

            beneficiary.setAddress(resultSet.getString(column++));
            beneficiary.setIdCityAddress(resultSet.getInt(column++));
            beneficiary.setIdProvinceAddress(getProvinceId(beneficiary.getIdCityAddress()));
            beneficiary.setZipCode(resultSet.getString(column++));
            beneficiary.setPhone1(resultSet.getString(column++));
            beneficiary.setPhone2(resultSet.getString(column++));
            beneficiary.setEmail(resultSet.getString(column++));
            beneficiary.setIdDegreeOfEducation(resultSet.getInt(column++));
            beneficiary.setIdMaritalStatus(resultSet.getInt(column++));
            beneficiary.setIdDeficiency(resultSet.getInt(column++));
            beneficiary.setInvalidityReason(resultSet.getString(column++));
            beneficiary.setInvalidityAwardDate(resultSet.getDate(column++));
            beneficiary.setMotherCpf(resultSet.getString(column++));
            beneficiary.setMotherName(resultSet.getString(column++));
            beneficiary.setFatherCpf(resultSet.getString(column++));
            beneficiary.setFatherName(resultSet.getString(column++));
            beneficiary.setPisPasep(resultSet.getString(column++));
            beneficiary.setVotersTitle(resultSet.getString(column++));
            beneficiary.setElectoralZone(resultSet.getInt(column++));
            beneficiary.setElectoralSection(resultSet.getInt(column++));
            beneficiary.setIdProvinceElectoralZone(resultSet.getInt(column++));
            beneficiary.setRg(resultSet.getString(column++));
            beneficiary.setRgEmissionDate(resultSet.getDate(column++));
            beneficiary.setIdRgIssuingBody(resultSet.getInt(column++));
            beneficiary.setIdProvinceRg(resultSet.getInt(column++));
            beneficiary.setAdmissionDate(resultSet.getDate(column++));
            beneficiary.setApplicationDate(resultSet.getDate(column++));
            beneficiary.setInactivationDate(resultSet.getDate(column++));
            beneficiary.setIdBenefitType(resultSet.getInt(column++));
            beneficiary.setIdCalculationForm(resultSet.getInt(column++));
            beneficiary.setIdStockingOrgan(resultSet.getInt(column++));
            beneficiary.setOffice(resultSet.getString(column++));
            column++; // previous time
            beneficiary.setInstituteEnrollment(resultSet.getInt(column++));
            beneficiary.setPhysicalDocumentDrawer(resultSet.getInt(column++));
            beneficiary.setIndexPhysicalDocument( resultSet.getInt(column++));
            beneficiary.setObservations(resultSet.getString(column++));
            beneficiary.setBankAgency(resultSet.getString(column++));
            beneficiary.setAccount(resultSet.getString(column++));
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
            beneficiary.setRegisterDate(resultSet.getTimestamp(column++));
            beneficiary.setCreateDate(resultSet.getTimestamp(column++));
            beneficiary.setUpdateDate(resultSet.getTimestamp(column++));

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
                Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            if( beneficiary.getInactivationDate() != null ) {
                beneficiary.setInactivityTime( Period.between(new java.util.Date(beneficiary.getInactivationDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) );                
            }
            
            beneficiary.setCityNameAddress( loadCityName( beneficiary.getIdCityAddress() ) );
            beneficiary.setCityNamePlaceOfBirth(loadCityName( beneficiary.getIdCityPlaceOfBirth()) );
            beneficiary.setProvinceInitialsAddress( loadProvinceInitials( beneficiary.getIdProvinceAddress() ) );
            beneficiary.setProvinceInitialsPlaceOfBirth(loadProvinceInitials( beneficiary.getIdProvincePlaceOfBirth()) );
            beneficiary.setProvinceInitialsRg(loadProvinceInitials( beneficiary.getIdProvinceRg()) );
            beneficiary.setProvinceInitialsElectoralZone(loadProvinceInitials( beneficiary.getIdProvinceElectoralZone()) );
            beneficiary.setEducationDegree( loadEducationalDegree( beneficiary.getIdDegreeOfEducation()) );
            beneficiary.setMaritalStatus(loadMaritalStatus( beneficiary.getIdMaritalStatus()) );
            beneficiary.setDeficiency(loadDeficiency( beneficiary.getIdDeficiency()) );
            beneficiary.setRgIssuingBody(loadRgIssuingBody(beneficiary.getIdRgIssuingBody()));
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }
    }        

    private String loadCityName(int idCity) {
        
        String cityName = null;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM city WHERE id_city = " + idCity );

            if (resultSet.next()) {                
                cityName = resultSet.getString(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }        
        
        return cityName;
    }

    private String loadProvinceInitials(int idProvince ) {
        String provinceInitials = null;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM province WHERE id_province = " + idProvince );

            if (resultSet.next()) {                
                provinceInitials = resultSet.getString(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }        
        
        return provinceInitials;
    }

    private String loadEducationalDegree(int idDegreeOfEducation) {
        String educationalDegree = null;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM degree_of_education WHERE id_degree_of_education = " + idDegreeOfEducation );

            if (resultSet.next()) {                
                educationalDegree = resultSet.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }        
        
        return educationalDegree;
    }

    private String loadMaritalStatus(int idMaritalStatus) {
        String maritalStatus = null;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM marital_status WHERE id_marital_status = " + idMaritalStatus );

            if (resultSet.next()) {                
                maritalStatus = resultSet.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }        
        
        return maritalStatus;
    }

    private String loadDeficiency(int idDeficiency) {
        String deficiency = null;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM deficiency WHERE id_deficiency = " + idDeficiency );

            if (resultSet.next()) {                
                deficiency = resultSet.getString(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }        
        
        return deficiency;
    }

    private String loadRgIssuingBody(int idRgIssuingBody) {
        String rgIssuingBody = null;
        
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM issuing_body WHERE id_issuing_body = " + idRgIssuingBody );

            if (resultSet.next()) {                
                rgIssuingBody = resultSet.getString(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(BeneficiaryDAOImpl.class.getName(), ex);
        }        
        
        return rgIssuingBody;
    }    
}
