package Model;

import DBAccess.DBDivision;
import Utilities.DBConnection;

import java.sql.SQLException;

public class Division {
    private int id;
    private String division;
    private int countryID;
    private String country;

    public Division (int id, String division, int countryID) throws SQLException {
        this.id = id;
        this.division = division;
        this.countryID = countryID;


    }
    public int getId() {
        return id;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountry() throws SQLException {
        country = DBDivision.lookupCountry(countryID);
    }

    public String  getCountry() {
        return country;
    }
}
