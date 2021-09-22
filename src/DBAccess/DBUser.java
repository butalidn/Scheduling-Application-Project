package DBAccess;

import Model.User;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * This class is for accessing the database for user information from the users table.
 * The private static member is the user that is logged into the application
 */
public class DBUser {
    private static User user;

    /**
     * This generates a list of all the users in the users table
     * @return Returns an observable list of users
     */
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

    /**
     * Confirms whether or not the given username and password are a matching combination in the database
     * @param searchUser The given user to be searched
     * @param searchPassword The given password to be searched
     * @return Returns a boolean based on if the given combination is in the users table
     * @throws SQLException
     */
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

    /**
     * Getter for the user member
     * @return Returns a User object
     */
    public static User getUser() {
        return user;
    }

    /**
     * Searches for the string name of a user based on an ID
     * @param id The given ID of the user to be searched for
     * @return Returns the string of the name of the user with the matching ID
     * @throws SQLException SQL exception for the select query
     */
    public static String lookupUser(int id) throws SQLException {
        String sql = "select * from users where user_id = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("User_Name");
    }

    /**
     * Searches for the ID of the given username
     * @param name The username of the user to be searched for
     * @return Returns the ID of the user with the matching username
     * @throws SQLException SQL exception for select query
     */
    public static int lookupUserID(String name) throws SQLException {
        String sql = "select * from users where user_name = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("User_ID");
    }

}
