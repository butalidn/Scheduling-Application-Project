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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
        String userName = userText.getText();
        String password = passwordText.getText();
        String status;

        if (DBUser.searchUser(userName, password)) {
            status = "Successful";
            nextScreen = true;
        }
        else {
            status = "Invalid";
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

        FileWriter loginWriter = new FileWriter("login_activity.txt", true);
        PrintWriter loginPW = new PrintWriter(loginWriter);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        ZonedDateTime zdtNow = ZonedDateTime.now(ZoneOffset.UTC);
        if (userName.isBlank()) {
            userName = "null";
        }
        String loginText = status + " login by " + userName + " at " + dateTimeFormatter.format(zdtNow) + " UTC";
        loginPW.println(loginText);
        loginPW.close();

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
