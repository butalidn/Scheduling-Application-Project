package Utilities;

import java.lang.ref.PhantomReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {
    private static PreparedStatement statement;

    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
    }

    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}
