package View_Controller;

import DBAccess.DBUser;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class manageController implements Initializable {
    @FXML private Label userLabel;
    @FXML private Button customerButton;
    @FXML private Button appointmentButton;

    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.user = DBUser.getUser();
        userLabel.setText(user.getUserName() + "!");
    }

    public void customerButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent customerParent = FXMLLoader.load(getClass().getResource("customerScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene customerScene = new Scene(customerParent);
        stage.setTitle("Customer Records");
        stage.setScene(customerScene);
        stage.show();
    }

    public void appointmentButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("scheduleScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene appointmentScene = new Scene(appointmentParent);
        stage.setTitle("Scheduling Records");
        stage.setScene(appointmentScene);
        stage.show();
    }
}
