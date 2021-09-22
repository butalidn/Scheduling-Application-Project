package DBAccess;

import Model.Country;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *This class is for accessing information from the countries table in the database
 */
public class DBCountry {
    /**
     * This generates an observable list of countries from the database
     * @return Returns a list of countries
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> clist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryID, countryName);
                clist.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clist;
    }
}
