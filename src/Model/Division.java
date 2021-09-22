package Model;

import DBAccess.DBDivision;
import java.sql.SQLException;

/**
 * The class for a division object.
 * Contains the division id, the name, the country id, and the country name
 */
public class Division {
    private int id;
    private String division;
    private int countryID;
    private String country;

    /**
     * The constructor for a division object
     * @param id The first level division id
     * @param division The name of the division
     * @param countryID The country id
     */
    public Division (int id, String division, int countryID) {
        this.id = id;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Getter for division id
     * @return Returns int for id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for division name
     * @return Returns a string of the division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * The getetr for the country id
     * @return Returns the int of the country id
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for country name
     * @throws SQLException SQL exception for the lookupCountry method
     */
    public void setCountry() throws SQLException {
        country = DBDivision.lookupCountry(countryID);
    }

    /**
     * Getter for country
     * @return Returns string of country name
     */
    public String getCountry() {
        return country;
    }
}
