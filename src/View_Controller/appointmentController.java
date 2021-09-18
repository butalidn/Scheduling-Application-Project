package View_Controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import DBAccess.DBCustomer;
import DBAccess.DBUser;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class appointmentController implements Initializable {
    @FXML private Label locationLabel;
    @FXML private ComboBox startCombo;
    @FXML private ComboBox endCombo;
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
    @FXML private TextField appointmentIDText;

    private Appointment appointment = null;
    private boolean isAdd = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalDateTime startBusinessTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0));
        LocalDateTime endBusinessTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(22,0));

        ZonedDateTime zonedStartET = startBusinessTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zonedStartLocal = zonedStartET.withZoneSameInstant(ZoneId.systemDefault());

        ZonedDateTime zonedEndET = endBusinessTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zonedEndLocal = zonedEndET.withZoneSameInstant(ZoneId.systemDefault());

        startCombo.setCellFactory(new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> listView) {
                ListCell<LocalDateTime> cell = new ListCell<LocalDateTime>(){
                    @Override
                    public void updateItem(LocalDateTime time, boolean empty) {
                        super.updateItem(time, empty);
                        if (time != null) {
                            setText(timeFormatter.format(time));
                        }
                        else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        startCombo.setButtonCell(new ListCell<LocalDateTime>(){
            @Override
            protected void updateItem(LocalDateTime item, boolean empty){
                super.updateItem(item, empty);
                if(item != null) {
                    setText(timeFormatter.format(item));
                }
            }
        });


        endCombo.setCellFactory(new Callback<ListView<LocalDateTime>, ListCell<LocalDateTime>>() {
            @Override
            public ListCell<LocalDateTime> call(ListView<LocalDateTime> listView) {
                ListCell<LocalDateTime> cell = new ListCell<LocalDateTime>(){
                    @Override
                    public void updateItem(LocalDateTime time, boolean empty) {
                        super.updateItem(time, empty);
                        if (time != null) {
                            setText(timeFormatter.format(time));
                        }
                        else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        endCombo.setButtonCell(new ListCell<LocalDateTime>(){
            @Override
            protected void updateItem(LocalDateTime item, boolean empty){
                super.updateItem(item, empty);
                if(item != null) {
                    setText(timeFormatter.format(item));
                }
            }
        });

        ObservableList<LocalDateTime> startList = FXCollections.observableArrayList();
        while (zonedStartLocal.isBefore(zonedEndLocal) || zonedStartLocal.isEqual(zonedEndLocal)) {
            startList.add(zonedStartLocal.toLocalDateTime());
            zonedStartLocal = zonedStartLocal.plusMinutes(15);
        }
        startCombo.setItems(startList);
        startCombo.getSelectionModel().selectFirst();
        endCombo.setItems(startList);
        endCombo.getSelectionModel().selectFirst();


        userCombo.setItems(DBUser.getAllUsers());
        userCombo.getSelectionModel().selectFirst();

        customerCombo.setItems(DBCustomer.getAllCustomers());
        customerCombo.getSelectionModel().selectFirst();

        contactCombo.setItems(DBContact.getAllContacts());
        contactCombo.getSelectionModel().selectFirst();

        ZoneId zone = ZoneId.systemDefault();
        locationLabel.setText(zone.toString());



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

            datePicker.setValue(appointment.getStartTime().toLocalDate());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            startCombo.setValue(formatter.format(appointment.getStartTime()));
            endCombo.setValue(formatter.format(appointment.getEndTime()));
        }
        catch (NullPointerException | SQLException e){
            isAdd = true;
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
        if (isAdd) {
            try {
                boolean goodInfo = true;

                if (titleText.getText().trim().isEmpty()) {
                    System.out.println("Missing title");
                    goodInfo = false;
                }
                if (descriptionText.getText().trim().isEmpty()) {
                    System.out.println("Missing description");
                    goodInfo = false;
                }
                if (locationText.getText().trim().isEmpty()) {
                    System.out.println("Missing location");
                    goodInfo = false;
                }
                if (datePicker.getValue() == null) {
                    System.out.println("Missing date");
                    goodInfo = false;
                }

                if (goodInfo) {
                    LocalTime start = ((LocalDateTime) startCombo.getSelectionModel().getSelectedItem()).toLocalTime();
                    LocalTime end = ((LocalDateTime) endCombo.getSelectionModel().getSelectedItem()).toLocalTime();

                    LocalDateTime startTime = LocalDateTime.of(datePicker.getValue(), start);
                    LocalDateTime endTime = LocalDateTime.of(datePicker.getValue(), end);
                    try {
                        Appointment a = new Appointment(
                                0,
                                ((Customer)customerCombo.getSelectionModel().getSelectedItem()).getId(),
                                ((User)userCombo.getSelectionModel().getSelectedItem()).getUserID(),
                                titleText.getText(),
                                descriptionText.getText(),
                                locationText.getText(),
                                ((Contact)contactCombo.getSelectionModel().getSelectedItem()).getId(),
                                typeText.getText(),
                                startTime,
                                endTime);
                        DBAppointment.addAppointment(a);
                        Parent scheduleParent = FXMLLoader.load(getClass().getResource("scheduleScreen.fxml"));
                        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
                        Scene scheduleScene = new Scene(scheduleParent);
                        stage.setTitle("Scheduling Records");
                        stage.setScene(scheduleScene);
                        stage.show();

                    } catch (Exception e) {
                        System.out.println("Missing info");
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Missing info");
            }
        }
    }

    public void startComboUpdate(ActionEvent actionEvent) {
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
//        System.out.println(timeFormatter.format((LocalDateTime) startCombo.getValue()));
    }

    public void endComboUpdate(ActionEvent actionEvent) {
        //DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    }
}
