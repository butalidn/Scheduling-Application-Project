package DBAccess;

import Model.Country;
import Model.Division;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class accesses the division table for division information from the database
 */
public class DBDivision {
//    private String country;

    /**
     * This method generates an observable list of divisions from the database
     * @return Returns a list of divisions from the divisions table
     */
    public static ObservableList<Division> getAllDivisions() {
        ObservableList<Division> dList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                Division D = new Division(divisionID, divisionName, countryID);
                dList.add(D);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dList;
    }

    /**
     * Generates a list of first level divisions located in the given country
     * @param country The method uses this country to filter out the divisions that are not in this country
     * @return Returns an observable list of divisions
     */
    public static ObservableList<Division> filterDivisions(String country) {
        ObservableList<Division> filterList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions where country_id = (select country_id from countries where country = ?);";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, country);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                Division D = new Division(divisionID, divisionName, countryID);
                filterList.add(D);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return filterList;
    }

    /**
     * This method searches the countries table for a certain country ID
     * @param countryId This ID is used to find the matching country
     * @return Returns a string of the name of the country with the matching ID
     * @throws SQLException The SQL exception for the select query
     */
    public static String lookupCountry(int countryId) throws SQLException {
        String countryQuery = "select * from countries where country_id = " + countryId + ";";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(countryQuery);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Country");
    }

    /**
     * Looks for the ID of a division using the name of a certain division
     * @param lookupDivision This string is used to search for the ID of the matching division
     * @return Returns an integer that is the ID of the given division
     * @throws SQLException The SQL exception for the select query
     */
    public static int lookupDivisionID(String lookupDivision) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String lookupSQL = "SELECT * FROM first_level_divisions WHERE division = ?;";
        DBQuery.setPreparedStatement(conn, lookupSQL);
        PreparedStatement lookupStatement = DBQuery.getPreparedStatement();

        lookupStatement.setString(1, lookupDivision);

        lookupStatement.execute();
        ResultSet divisionRS = lookupStatement.getResultSet();
        divisionRS.next();
        return divisionRS.getInt("division_id");
    }
}
