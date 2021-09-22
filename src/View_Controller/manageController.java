package View_Controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import DBAccess.DBUser;
import Model.Appointment;
import Model.Contact;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class handles the logic of manage screen. Keeps track of which user is logged in and if a login message
 * has been displayed yet.
 */
public class manageController implements Initializable {
    @FXML private Label userLabel;
    @FXML private Button customerButton;
    @FXML private Button appointmentButton;

    private User user;
    private LocalDateTime loginTime;
    private static boolean loginMessage = true;

    /**
     * Checks if there are any appointments within 15 minutes of the user's login time.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> appointmentList = DBAppointment.getAllAppointments();
        loginTime = LocalDateTime.now();
        this.user = DBUser.getUser();
        userLabel.setText(user.getUserName() + "!");

        long timeBetween;
        boolean upcomingAppointment = false;

        if (loginMessage) {
            for (Appointment a : appointmentList) {
                if (a.getStartTime().getYear() == loginTime.getYear() && a.getStartTime().getMonth().equals(loginTime.getMonth()) && user.getUserID() == a.getUserID()) {
                    timeBetween = ChronoUnit.MINUTES.between(loginTime, a.getStartTime());
                    if (timeBetween <= 15 && timeBetween >= 0) {
                        int appointmentID = a.getAppointmentID();
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
                        String appointmentTime = timeFormatter.format(a.getStartTime());

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Upcoming Appointment");
                        alert.setHeaderText("Reminder!");
                        alert.setContentText("You have an appointment coming up soon. \nAppointment ID: " + appointmentID + "\nAppointment time: " + appointmentTime);
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        Optional<ButtonType> result = alert.showAndWait();
                        upcomingAppointment = true;
                        break;
                    }
                }
            }
            if (!upcomingAppointment) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Upcoming Appointments");
                alert.setHeaderText("Welcome");
                alert.setContentText("You have no upcoming appointments");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
        loginMessage = false;
    }

    /**
     * Handles when the customer button is clicked. Sends user to the customer screen.
     * @param actionEvent
     * @throws IOException
     */
    public void customerButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent customerParent = FXMLLoader.load(getClass().getResource("customerScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene customerScene = new Scene(customerParent);
        stage.setTitle("Customer Records");
        stage.setScene(customerScene);
        stage.show();
    }

    /**
     * Handles when the appointment button is clicked. Sends user to the appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void appointmentButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("scheduleScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene appointmentScene = new Scene(appointmentParent);
        stage.setTitle("Scheduling Records");
        stage.setScene(appointmentScene);
        stage.show();
    }
}
