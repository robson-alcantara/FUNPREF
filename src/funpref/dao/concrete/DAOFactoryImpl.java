package funpref.dao.concrete;

import funpref.controller.BeneficiaryController;
import funpref.controller.DependentController;
import funpref.controller.JComboBoxModelController;
import funpref.controller.LogController;
import funpref.controller.UserController;
import funpref.dao.interfaces.BeneficiaryDAO;
import funpref.dao.interfaces.DAOFactory;
import funpref.dao.interfaces.DependentDAO;
import funpref.dao.interfaces.JComboBoxItensDAO;
import funpref.dao.interfaces.UserDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DAOFactoryImpl implements DAOFactory{
    public static Connection getConnection( String host) {
        return DataBaseConnection.getConnection(host);
    }

    @Override
    public BeneficiaryDAO getBeneficiaryDAO(BeneficiaryController beneficiaryController) {
        return new BeneficiaryDAOImpl(beneficiaryController);
    }

    @Override
    public DependentDAO getDependentDAO(DependentController dependentController) {
        return new DependentDAOImpl(dependentController);
    }

    @Override
    public JComboBoxItensDAO getJComboBoxItensDAO(JComboBoxModelController jComboBoxModelController) {
        return new JComboBoxItensDAOImpl(jComboBoxModelController);
    }

    @Override
    public UserDAO getUserDAO( UserController userController ) {
        return new UserDAOImpl(userController);
    }
    
    public static String getUser() {
        return DataBaseConnection.getUser();
    }

    public static String getPassword() {
        return DataBaseConnection.getPassword();
    }    
    
    private static class DataBaseConnection {

        private static volatile DataBaseConnection DataBaseConnection;
        private static Connection connection;
        private final static String user = "funpref";
        private final static String password = "fund0pr3v1";

        private DataBaseConnection(String host) throws Exception {
            String url = "jdbc:mysql://" + host + "/funpref?useUnicode=true&characterEncoding=utf-8";
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                
            } catch (SQLException ex) {
                LogController.reportException(DataBaseConnection.class.getName(), ex);
                throw ex;
            }
        }

        public static Connection getConnection(String host) {
            if (DataBaseConnection == null) {
                synchronized (DataBaseConnection.class) {
                    if (DataBaseConnection == null) {
                        try {
                            DataBaseConnection = new DataBaseConnection( host );
                        } catch (Exception e) {
                            e.printStackTrace(System.out);
                            LogController.reportException(DataBaseConnection.class.getName(), e);                            
                            return null;
                        }
                    }
                }
            }
            return DataBaseConnection.connection;
        }

        public static void closeConnection() {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                    LogController.reportException(DataBaseConnection.class.getName(), ex);
                }
            }
        }

        public static String getUser() {
            return user;
        }

        public static String getPassword() {
            return password;
        }
        
        
    }
    
}
