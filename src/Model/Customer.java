package Model;

/**
 * This class is for a customer object.
 * Contains the customer id, name, address, postal code, phone number, first level division, and the country of the customer
 */
public class Customer {
        private int id;
        private String name;
        private String address;
        private String postal;
        private String phone;
        private String firstLevel;
        private String country;

    /**
     * The constructor for a customer object
     * @param id The customer ID
     * @param name The name of the customer
     * @param address The address of the customer
     * @param postal The postal code of the customer
     * @param phone The phone number of the customer
     * @param firstLevel The first level division of the customer
     * @param country The country of the customer
     */
    public Customer(int id, String name, String address, String postal, String phone, String firstLevel, String country) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.postal = postal;
            this.phone = phone;
            this.firstLevel = firstLevel;
            this.country = country;
    }

    /**
     * This alters the toString method of the customer object
     * @return Returns the name of the customer
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * The getter for the customer ID
     * @return Returns the ID int of the customer
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the customer name
     * @return returns the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the address of the customer
     * @return Returns the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for the postal code of the customer
     * @return Returns the postal code
     */
    public String getPostal() {
        return postal;
    }

    /**
     * Getter for the phone number of the customer
     * @return Returns the customer's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Getter for the first level division of the customer
     * @return Returns the customer's first level division
     */
    public String getFirstLevel() {
        return firstLevel;
    }

    /**
     * Getter for the country of the customer
     * @return Returns the customer's country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter for the customer id
     * @param id The given id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for the customer name
     * @param name The given name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the customer address
     * @param address The given address to be set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Setter for the customer postal code
     * @param postal The given postal code to be set
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     * Setter for the customer phone number
     * @param phone The given phone number to be set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Setter for the customer first level division
     * @param firstLevel The given first level division to be set
     */
    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    /**
     * Setter for the customer country
     * @param country The given country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
