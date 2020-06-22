/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.dao.concrete;

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
    
    public BeneficiaryDAOImpl() {
        formatDate = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public boolean insertBeneficiary(Beneficiary beneficiary) {
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
        
        boolean result = true;
        Integer columnReference = null;
        
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection().prepareStatement(query);
            
            populatePreparedStatementFromResultSet(preparedStatement, beneficiary, columnReference, true );
            
            preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            result = false;
        }   
        
        return result;
    }

    @Override
    public boolean deleteBeneficiary(Beneficiary beneficiary) {
        
        boolean result = true;
         
        try {
            Statement statement = DAOFactoryImpl.getConnection().createStatement();
            statement.executeUpdate("delete from beneficiary where id_beneficiary = " + beneficiary.getId() );
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            result = false;
        }
        return result;
    }

    @Override
    public boolean updateBeneficiary(Beneficiary beneficiary) {
        
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
        Integer columnReference = null;
        
        try {                
            PreparedStatement preparedStatement = DAOFactoryImpl.getConnection().prepareStatement(query);
            
            populatePreparedStatementFromResultSet( preparedStatement, beneficiary, columnReference, false );            

            preparedStatement.setInt(columnReference++, beneficiary.getId());            
            preparedStatement.executeUpdate();            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
                    + " and ( name like ? or name is null)"
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
                preparedStatement.setString(3, "%Iac8Ji%Gdl!w%");                
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {                
                Beneficiary beneficiary = new Beneficiary();
                
                populateBeneficiaryFromResultSet(beneficiary, resultSet);

                result.add(beneficiary);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    private void populatePreparedStatementFromResultSet(PreparedStatement preparedStatement, Beneficiary beneficiary, Integer columnReference, boolean populateCreateDate) {
        
        int column = 1;
        
        try {
            preparedStatement.setInt(column++, beneficiary.getRegistration());
            preparedStatement.setString(column++, beneficiary.getOrdinance());
            preparedStatement.setString(column++, beneficiary.getCpf());            
            preparedStatement.setString(column++, beneficiary.getName());

            if( null == beneficiary.getSex() ) {
                preparedStatement.setString(column, null);
            }

            else switch (beneficiary.getSex()) {
                case MALE:
                    preparedStatement.setString(column, "m");
                    break;                
                case FEMALE:
                    preparedStatement.setString(column, "f");
                    break;
                default:
                    preparedStatement.setString(column, null);
                    break;
            }
            
            column++;

            preparedStatement.setDate(column++, new java.sql.Date( beneficiary.getBirthDate().getTime() ) );
            preparedStatement.setInt(column++, beneficiary.getIdCadastralStatus()); 
            preparedStatement.setNull(column++, Types.INTEGER); // update number            
            preparedStatement.setInt(column++, beneficiary.getIdUserRegistration()); // user registration
            preparedStatement.setInt(column++, beneficiary.getIdUserCreate()); // user create
            preparedStatement.setInt(column++, beneficiary.getIdUserUpdate()); // user update

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
            preparedStatement.setString(column++, beneficiary.getObservations());
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

            if( beneficiary.getRegisterDate() != null ) {
                preparedStatement.setTimestamp(column++, new Timestamp( beneficiary.getRegisterDate().getTime() ) );
            }

            else {
                preparedStatement.setNull(column++, Types.TIMESTAMP);
            }

            if (populateCreateDate) {
                preparedStatement.setTimestamp(column++, new Timestamp(beneficiary.getCreateDate().getTime() ));
            }
            
            preparedStatement.setTimestamp(column++, new Timestamp( beneficiary.getUpdateDate().getTime() ));                        
            columnReference = column;
        
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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

            //beneficiary.setDependents( loadDependents( beneficiary.getId() ) );

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
        } catch (SQLException ex) {
            Logger.getLogger(BeneficiaryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
