package sessionFactory;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3poDataSource {
	 
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
 
    static {
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://localhost/zerodha_development");
            cpds.setUser("root");
            cpds.setPassword("admin123");
        } catch (PropertyVetoException e) {
            // handle the exception
        }
    }
     
    public static Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }
     
    private C3poDataSource(){}
}
