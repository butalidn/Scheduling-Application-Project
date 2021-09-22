package DBAccess;

import Model.Contact;
import Model.Country;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is to access the database for Contact information
 */
public class DBContact {
    /**
     * This method generates and returns a list of contacts from the database
     * @return Returns an observable list of contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> clist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact C = new Contact(contactId, name, email);
                clist.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }

    /**
     * This method looks in the contacts table for a specific contact
     * @param id The ID of the contact that is be searched for
     * @return Returns a string of the name of the contact
     * @throws SQLException SQL exception for query
     */
    public static String lookupContact(int id) throws SQLException {
        String sql = "select * from contacts where contact_id = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Contact_Name");
    }

    /**
     * This method looks for the ID of a certain contact in the database
     * @param name The name of the contact to be searched for
     * @return Returns the ID of the contact that is being searched
     * @throws SQLException SQL exception for query
     */
    public static int lookupContactID(String name) throws SQLException {
        String sql = "select * from contacts where contact_name = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Contact_ID");
    }

}
