package RequestsModule.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by ohad on
 * 27/4/2015.
 */
public class TestNewDB {


    private final String USER_AGENT = "Mozilla/5.0";
    final static String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final static String DB_URL = "jdbc:sqlserver://localhost\\sqlexpress";


    public static void test() throws Exception {
        try
        {
            Class.forName(JDBC_DRIVER);
            Properties props = new Properties();
            //props.setProperty("integratedSecurity", "true");

            Connection connection = DriverManager.getConnection(DB_URL);

            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            //statement.addBatch("DROP DATABASE " + DBName);
            //statement.addBatch("CREATE database " + DBName);
            ResultSet rs = statement.executeQuery("SELECT * FROM p_communityMembers ");
            while (rs.next()){
                System.out.println(rs.getInt("community_member_id"));
            }

            connection.commit();
            statement.executeBatch();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();}

    }
}

