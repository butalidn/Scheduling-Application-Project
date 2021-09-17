package Model;

public class User {
    private String userName;
    private String password;
    private int userID;

    public User(int userID, String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return userName;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }

}
