package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//From Connecting to DB webinar
public class DBConnection {
    //JDBC Url Components
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ08hMv";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String mySQLDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String userName = "U08hMv";
    private static final String password = "53689292981";

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

    public static void closeConnection() {
        try {
            conn.close();
        }
        catch (Exception e) {
        }
    }

    public static Connection getConnection() {
        return conn;
    }

}
