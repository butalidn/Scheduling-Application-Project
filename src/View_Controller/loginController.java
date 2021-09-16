package View_Controller;

import DBAccess.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML private Label userLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField userText;
    @FXML private TextField passwordText;
    @FXML private Button submitButton;
    @FXML private Label locationPromptLabel;
    @FXML private Label locationLabel;

    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        locationLabel.setText(zone.toString());

        /*Locale defaultLocale = Locale.getDefault();
        Locale frLocale = new Locale("fr");

        ResourceBundle defaultMessages = ResourceBundle.getBundle("MessagesBundle", defaultLocale);
        ResourceBundle frMessages = ResourceBundle.getBundle("MessagesBundle", frLocale);
        System.out.println(defaultMessages.getString("Hi there"));
        System.out.println(frMessages.getString("Hi there"));*/

        try {
            rb = ResourceBundle.getBundle("Resources/Nat", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                userLabel.setText(rb.getString("Username") + ":");
                passwordLabel.setText(rb.getString("Password") + ":");
                submitButton.setText(rb.getString("Submit"));
                locationPromptLabel.setText(rb.getString("Your location is") + ":");
            }
        }
        catch (Exception e) {}
    }

    public void submitButtonClicked(ActionEvent actionEvent) throws IOException, SQLException {
        boolean nextScreen = false;

        /*ObservableList<Countries> countryList = DBCountries.getAllCountries();       FROM WEBINAR
        for (Countries C: countryList) {
            System.out.println("Country ID:" + C.getID() + " Name: " + C.getName());
        }


        ObservableList<User> userList = DBUser.getAllUsers();
        for(User U: userList) {
            System.out.println("User ID: " + U.getUserID());
            System.out.println("Username: " + U.getUserName());
            System.out.println("Password: " + U.getPassword());
        } */

        String userName = userText.getText();
        String password = passwordText.getText();

        if (DBUser.searchUser(userName, password)) {
            nextScreen = true;
        }
        else {
            String errorTitle = "Error";
            String errorContext = "User and/or password not found. Try again";
            String errorHeader = "User Not Found";
            if (Locale.getDefault().getLanguage().equals("fr")) {
                errorTitle = rb.getString("Error");
                errorContext = rb.getString("User and/or password not found. Try again");
                errorHeader = rb.getString("User Not Found");
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(errorTitle);
            alert.setContentText(errorContext);
            alert.setHeaderText(errorHeader);
            Optional<ButtonType> result = alert.showAndWait();

        }

        if (nextScreen) {
            Parent manageParent = FXMLLoader.load(getClass().getResource("manageScreen.fxml"));
            Stage stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Scene manageScene = new Scene(manageParent);
            stage.setTitle("Welcome");
            stage.setScene(manageScene);
            stage.show();
        }
    }
}
