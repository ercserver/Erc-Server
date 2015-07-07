package Utilities;

/**
 * Created by ohad on 18/6/2015.
 */
public class ErcConfiguration {

    /********* URLs ***********/
    public final static String REQUESTS_URL_ROOT = "http://mba4.ad.biu.ac.il/Erc-Server/requests/";

    public final static String VERIFY_EMAIL_URL = "http://mba4.ad.biu.ac.il/Erc-Server/requests/verify_email";

    public final static String AUTH_DOCTOR_URL = "http://mba4.ad.biu.ac.il/Erc-Server/requests/auth_doctor";

    public final static String EMS_SERVER_URL = "http://mba4.ad.biu.ac.il:3000/doc_ems";

    public static final String GIS_SERVER_URL = "http://mba4.ad.biu.ac.il/gisWebProject/Mapping";


    /********* DB configurations ***********/
    public final static String JDBC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";

    public final static String DB_URL = "jdbc:jtds:sqlserver://socmedserver.mssql.somee.com;";//databaseName=ercserver-socmed";

    public final static String DB_Name = "socmedserver";

    public final static String DB_USERNAME = "saaccount";

    public final static String DB_PASS = "saaccount";


    /********* Email configurations ***********/
    public static final String EMAIL_USERNAME = "ercserver@gmail.com";

    public static final String EMAIL_PASSWORD = "serverpassword123";

    public static final String EMAIL_HOST = "smtp.gmail.com";

}
