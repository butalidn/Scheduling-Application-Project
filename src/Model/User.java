package Model;

/**
 * The class for a user object.
 * Contains the username, password, and user ID
 */
public class User {
    private String userName;
    private String password;
    private int userID;

    /**
     * The constructor for a user object
     * @param userID The user id
     * @param userName The username of the user
     * @param password The user's password
     */
    public User(int userID, String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
    }

    /**
     * This changes the toString method of the user object
     * @return Returns the username of the user object
     */
    @Override
    public String toString() {
        return userName;
    }

    /**
     * Getter for username
     * @return Returns username string
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter for user's password
     * @return Returns the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the user's id
     * @return Returns the user's id
     */
    public int getUserID() {
        return userID;
    }

}
