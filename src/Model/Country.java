package Model;

/**
 * This class for a country object.
 * Contains an id and name
 */
public class Country {
    private int ID;
    private String name;

    /**
     * The constructor of a country object
     * @param ID The country id
     * @param name The name of the country
     */
    public Country(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    /**
     * The getter of the country ID
     * @return Returns the country id
     */
    public int getID() {
        return ID;
    }

    /**
     * The getter for the name of the country
     * @return Returns the the string of the country name
     */
    public String getName() {
        return name;
    }
}
