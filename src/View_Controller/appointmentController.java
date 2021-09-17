package View_Controller;

import DBAccess.DBContact;
import DBAccess.DBCustomer;
import DBAccess.DBUser;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
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
import java.util.ResourceBundle;

public class appointmentController implements Initializable {
    @FXML private TextField typeText;
    @FXML private ComboBox customerCombo;
    @FXML private ComboBox userCombo;
    @FXML private DatePicker datePicker;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private TextField customerIDText;
    @FXML private TextField titleText;
    @FXML private TextField descriptionText;
    @FXML private TextField locationText;
    @FXML private ComboBox contactCombo;
    @FXML private ComboBox typeCombo;
    @FXML private DatePicker startDatePicker;
    @FXML private ChoiceBox startTimeChoice;
    @FXML private DatePicker endDatePicker;
    @FXML private ChoiceBox endTimeChoice;
    @FXML private TextField appointmentIDText;

    private Appointment appointment = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userCombo.setItems(DBUser.getAllUsers());
        userCombo.getSelectionModel().selectFirst();

        customerCombo.setItems(DBCustomer.getAllCustomers());
        customerCombo.getSelectionModel().selectFirst();

        contactCombo.setItems(DBContact.getAllContacts());
        contactCombo.getSelectionModel().selectFirst();

        try {
            appointment = scheduleController.initAppointment();
            appointmentIDText.setText(Integer.toString(appointment.getAppointmentID()));
            userCombo.getSelectionModel().select(DBUser.lookupUser(appointment.getUserID()));
            customerCombo.getSelectionModel().select(DBCustomer.lookupCustomer(appointment.getCustomerID()));
            titleText.setText(appointment.getTitle());
            descriptionText.setText(appointment.getDescription());
            locationText.setText(appointment.getLocation());
            contactCombo.getSelectionModel().select(DBContact.lookupContact(appointment.getContactID()));
            typeText.setText(appointment.getType());
        }
        catch (NullPointerException | SQLException e){
            appointmentIDText.setText("BOINK");
        }
    }

    public void cancelButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent scheduleParent = FXMLLoader.load(getClass().getResource("scheduleScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scheduleScene = new Scene(scheduleParent);
        stage.setTitle("Scheduling Records");
        stage.setScene(scheduleScene);
        stage.show();
    }

    public void saveButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent scheduleParent = FXMLLoader.load(getClass().getResource("scheduleScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scheduleScene = new Scene(scheduleParent);
        stage.setTitle("Scheduling Records");
        stage.setScene(scheduleScene);
        stage.show();
    }
}
