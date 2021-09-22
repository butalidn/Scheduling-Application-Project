package Model;

/**
 * This class is for a contact object.
 * Has id, name, and email attributes
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    /**
     * The default constructor for a contact object
     * @param id The int of the contact
     * @param name The name of the contact
     * @param email The email of the contact
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Changes the toString method to return the name of the contact object
     * @return returns the name of the contact
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * The getter for id
     * @return Returns an int for the id
     */
    public int getId() {
        return id;
    }

    /**
     * The setter for id
     * @param id The given id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The getter for name
     * @return Returns the string of the name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for name
     * @param name The name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter for email
     * @return Returns an email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * The setter for email
     * @param email The email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
