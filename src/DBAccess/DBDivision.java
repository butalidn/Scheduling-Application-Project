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

public class DBDivision {
    private String country;

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

    public static ObservableList<Division> filterDivisions(int lookupCountryID) {
        ObservableList<Division> filterList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions where country_id = ?;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, lookupCountryID);

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

    public static String lookupCountry(int countryId) throws SQLException {
        String countryQuery = "select * from countries where country_id = " + countryId + ";";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(countryQuery);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Country");
    }

    public static String lookupCountry(String division) throws SQLException {
        String countryQuery = "select * from countries where country_id = (select country_id from first_level_divisions where division = ?);";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(countryQuery);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Country");
    }

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
