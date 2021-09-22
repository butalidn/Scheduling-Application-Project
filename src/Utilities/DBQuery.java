package Utilities;

import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is for preparing a SQL query
 */
public class DBQuery {
    private static PreparedStatement statement;
    /**
     * This method sets a prepared statement
     * @param conn The connection to be used
     * @param sqlStatement The SQL statement to be utilized
     * @throws SQLException SQL exception for preparing a statement
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * Getter for a prepared statement
     * @return Returns the prepared statement
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
