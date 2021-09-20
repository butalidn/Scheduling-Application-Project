package DBAccess;

import Model.User;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBUser {
    private static User user;

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(userID,userName,password);
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userList;
    }

    public static boolean searchUser(String searchUser, String searchPassword) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String selectUserStatement = "SELECT * FROM users WHERE User_Name = ? AND Password = ?;";
        DBQuery.setPreparedStatement(conn, selectUserStatement);
        PreparedStatement userStatement = DBQuery.getPreparedStatement();

        userStatement.setString(1, searchUser);
        userStatement.setString(2, searchPassword);

        userStatement.execute();
        ResultSet userRS = userStatement.getResultSet();

        if (userRS.next()) {
            int userID = userRS.getInt("User_ID");
            String username = userRS.getString("User_Name");
            String password = userRS.getString("password");
            user = new User(userID,username,password);

            return true;
        }
        else {
            return false;
        }

    }

    public static User getUser() {
        return user;
    }

    public static String lookupUser(int id) throws SQLException {
        String sql = "select * from users where user_id = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("User_Name");
    }
    public static int lookupUserID(String name) throws SQLException {
        String sql = "select * from users where user_name = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("User_ID");
    }

}
