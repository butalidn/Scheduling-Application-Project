package DBAccess;

import Model.Country;
import Model.Division;
import Utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDivision {
    private String country;
    private int divisionID;

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

    public static String lookupCountry(int countryId) throws SQLException {
        String countryQuery = "select * from countries where country_id = " + countryId + ";";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(countryQuery);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Country");
    }


}
