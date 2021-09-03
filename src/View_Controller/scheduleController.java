package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class scheduleController implements Initializable {
    @FXML private Button addButton;
    @FXML private Button modifyButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private RadioButton monthRadio;
    @FXML private RadioButton weekRadio;
    @FXML private TableView scheduleTable;

    private ToggleGroup sortToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sortToggleGroup = new ToggleGroup();
        this.monthRadio.setToggleGroup(sortToggleGroup);
        this.weekRadio.setToggleGroup(sortToggleGroup);
        sortToggleGroup.selectToggle(monthRadio);
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent manageParent = FXMLLoader.load(getClass().getResource("manageScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene manageScene = new Scene(manageParent);
        stage.setTitle("Welcome");
        stage.setScene(manageScene);
        stage.show();
    }

    public void addButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("appointmentScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene appointmentScene = new Scene(appointmentParent);
        stage.setTitle("Add Appointment");
        stage.setScene(appointmentScene);
        stage.show();
    }

    public void modifyButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("appointmentScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene appointmentScene = new Scene(appointmentParent);
        stage.setTitle("Modify Appointment");
        stage.setScene(appointmentScene);
        stage.show();
    }


}
