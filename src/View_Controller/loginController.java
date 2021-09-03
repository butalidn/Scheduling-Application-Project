package View_Controller;

import DBAccess.DBCountries;
import DBAccess.DBUser;
import Model.Countries;
import Model.User;
import Utilities.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    @FXML private TextField userText;
    @FXML private TextField passwordText;
    @FXML private Button submitButton;
    @FXML private Label locationPromptLabel;
    @FXML private Label locationLabel;



    public void submitButtonClicked(ActionEvent actionEvent) throws IOException {
        /*ObservableList<Countries> countryList = DBCountries.getAllCountries();       FROM WEBINAR
        for (Countries C: countryList) {
            System.out.println("Country ID:" + C.getID() + " Name: " + C.getName());
        }*/

        ObservableList<User> userList = DBUser.getAllUsers();
        for(User U: userList) {
            System.out.println("User ID: " + U.getUserID());
            System.out.println("Username: " + U.getUserName());
            System.out.println("Password: " + U.getPassword());
        }



        Parent manageParent = FXMLLoader.load(getClass().getResource("manageScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene manageScene = new Scene(manageParent);
        stage.setTitle("Welcome");
        stage.setScene(manageScene);
        stage.show();
    }
}
