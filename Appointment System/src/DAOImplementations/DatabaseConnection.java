package DAOImplementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to Database
 */
public class DatabaseConnection {
    private static final String protocol = "redacted";
    private static final String vendorName = ":redacted:";
    private static final String ipAddress = "//redacted:redacted/";
    private static final String dbName = "redacted";
    private static final String dbPass = "redacted";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    private static final String MYSQLJBCDriver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "redacted";
    private static Connection conn = null;

    /**
     * Starts the connection
     * @return Connection
     */
    public static Connection startConnection(){
        try{
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, dbPass);
            System.out.println("Connection Successful");
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Gets connection
     * @return Connection
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * Closes connection
     */
    public static void closeConnection(){
        try{
            conn.close();
        }
        catch(Exception ignored){
        }
    }
}
