package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class customerController {
    @FXML private TableView customerTable;
    @FXML private TableColumn customerIDCol;
    @FXML private TableColumn nameCol;
    @FXML private TableColumn addressCol;
    @FXML private TableColumn postalCol;
    @FXML private TableColumn numberCol;
    @FXML private TableColumn firstLevelCol;
    @FXML private TableColumn countryCol;
    @FXML private TextField customerText;
    @FXML private TextField nameText;
    @FXML private TextField numberText;
    @FXML private TextField addressText;
    @FXML private TextField postalText;
    @FXML private ComboBox firstLevelCombo;
    @FXML private ComboBox countryCombo;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent manageParent = FXMLLoader.load(getClass().getResource("manageScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene manageScene = new Scene(manageParent);
        stage.setTitle("Welcome");
        stage.setScene(manageScene);
        stage.show();
    }
}
