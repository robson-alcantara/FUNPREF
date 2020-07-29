package funpref.dao.concrete;

import funpref.controller.LogController;
import funpref.dao.interfaces.BeneficiaryDAO;
import funpref.dao.interfaces.DAOFactory;
import funpref.dao.interfaces.DependentDAO;
import funpref.dao.interfaces.JComboBoxItensDAO;
import funpref.dao.interfaces.UserDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DAOFactoryImpl implements DAOFactory{
    public static Connection getConnection() {
        return DataBaseConnection.getConnection();
    }

    @Override
    public BeneficiaryDAO getBeneficiaryDAO() {
        return new BeneficiaryDAOImpl();
    }

    @Override
    public DependentDAO getDependentDAO() {
        return new DependentDAOImpl();
    }

    @Override
    public JComboBoxItensDAO getJComboBoxItensDAO() {
        return new JComboBoxItensDAOImpl();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
    
    private static class DataBaseConnection {

        private static volatile DataBaseConnection DataBaseConnection;
        private static Connection connection;

        private DataBaseConnection() throws Exception {
            String url = "jdbc:mysql://localhost/funpref?useUnicode=true&characterEncoding=utf-8";
            String name = "funpref";
            String password = "fund0pr3v1";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, name, password);
                
            } catch (SQLException ex) {
                LogController.reportException(DataBaseConnection.class.getName(), ex);
                throw ex;
            }
        }

        public static Connection getConnection() {
            if (DataBaseConnection == null) {
                synchronized (DataBaseConnection.class) {
                    if (DataBaseConnection == null) {
                        try {
                            DataBaseConnection = new DataBaseConnection();
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
    }
    
}
