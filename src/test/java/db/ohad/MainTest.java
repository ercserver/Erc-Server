package db.ohad;

import java.sql.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ohad on 29/5/2015.
 */
public class MainTest {
    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String DB_URL = "jdbc:sqlserver://./sqlexpress/integratedSecurity=true";
    static final String DBName = "ercserver";
    /*static final private String USERNAME = "saaccount";
    static final private String PASS = "saaccount";*/
    private static Connection connection = null;
    private static Logger logger = Logger.getLogger(MainTest.class.getName());
    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dbo.Availability");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                System.out.println(rs.toString());
            }

        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }


    }

}
