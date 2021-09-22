package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is for establishing a connection with the given database.
 * Uses given information from PA.
 * From 'Connecting to DB' webinar.
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ08hMv";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String mySQLDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String userName = "U08hMv";
    private static final String password = "53689292981";

    /**
     * Creates a connection with the database using given username and password
     * @return Returns a connection object
     */
    public static Connection startConnection() {
        try{
            Class.forName(mySQLDriver);
            conn = DriverManager.getConnection(jdbcURL,userName,password);
            System.out.println("Connection Successful");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

    /**
     * This closes the connection with the database
     */
    public static void closeConnection() {
        try {
            conn.close();
        }
        catch (Exception e) {
        }
    }

    /**
     * The getter for conn
     * @return Returns connection conn
     */
    public static Connection getConnection() {
        return conn;
    }

}
