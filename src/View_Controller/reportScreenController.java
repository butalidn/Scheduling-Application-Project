package View_Controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import DBAccess.DBCustomer;
import Model.Appointment;
import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles the logic of the report screen
 */
public class reportScreenController implements Initializable {
    @FXML private DatePicker datePicker;
    @FXML private Button backButton;
    @FXML private TableView dateReportTable;
    @FXML private TableColumn dateAppointmentCol;
    @FXML private TableColumn dateStartCol;
    @FXML private TableColumn dateEndCol;
    @FXML private TableColumn dateTitleCol;
    @FXML private TableColumn dateLocationCol;
    @FXML private TableColumn dateCustomerCol;
    @FXML private ComboBox monthCombo;
    @FXML private ComboBox typeCombo;
    @FXML private TableView reportTable;
    @FXML private TableColumn appointmentIDCol;
    @FXML private TableColumn titleCol;
    @FXML private TableColumn typeCol;
    @FXML private TableColumn descriptionCol;
    @FXML private TableColumn startCol;
    @FXML private TableColumn endCol;
    @FXML private TableColumn customerIDCol;
    @FXML private ComboBox contactCombo;
    @FXML private Label appointmentLabel;
    @FXML private Button submitButton;

    private ObservableList<Appointment> appointments;
    private ObservableList<Contact> contacts;


    /**
     * Sets up the tables and the combo boxes on this screen. The lists of appointments and contacts are also loaded in.
     *
     * LAMBDA USAGE: On line 171, a lambda expression is used to set the action of when a item is selected from the combo box.
     * This was done to immediately show the usage of the combo box after it it is set and to help with readability.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointments = DBAppointment.getAllAppointments();
        contacts = DBContact.getAllContacts();

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startTime"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        startCol.setCellFactory(tc -> new TableCell<TableView, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endTime"));
        endCol.setCellFactory(tc -> new TableCell<TableView, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(formatter.format(date));
                }
            }
        });
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));

        dateAppointmentCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        dateStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startTime"));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        dateStartCol.setCellFactory(tc -> new TableCell<TableView, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(timeFormatter.format(date));
                }
            }
        });
        dateEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endTime"));
        dateEndCol.setCellFactory(tc -> new TableCell<TableView, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(timeFormatter.format(date));
                }
            }
        });
        dateTitleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        dateLocationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        dateCustomerCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        dateCustomerCol.setCellFactory(tc -> new TableCell<TableView, Integer>() {
            @Override
            protected void updateItem(Integer id, boolean empty) {
                super.updateItem(id, empty);
                if (empty) {
                    setText(null);
                } else {
                    try {
                        setText(DBCustomer.lookupCustomer(id));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        ObservableList<Month> months = FXCollections.observableArrayList();
        for (int i = 0; i < 12; i++) {
            months.add(Month.JANUARY.plus(i));
        }
        monthCombo.setItems(months);

        ObservableList<String> types = FXCollections.observableArrayList();
        for (Appointment a: appointments) {
            if (!(types.contains(a.getType().toUpperCase()))) {
                types.add(a.getType().toUpperCase());
            }
        }

        typeCombo.setItems(types);

        contactCombo.setItems(contacts);
        contactCombo.setOnAction(e -> {
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            for (Appointment a: appointments) {
                if (a.getContactID() == ((Contact) contactCombo.getSelectionModel().getSelectedItem()).getId()) {
                    appointmentList.add(a);
                }
            }
            reportTable.setItems(appointmentList);
        });
    }

    /**
     * Handles when a date is picked. Shows all appointments that are on the selected date.
     * @param actionEvent
     */
    public void datePicked(ActionEvent actionEvent) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        for (Appointment a: appointments) {
            if (a.getStartTime().toLocalDate().equals(datePicker.getValue())) {
                appointmentList.add(a);
            }
        }
        dateReportTable.setItems(appointmentList);
        dateReportTable.getSortOrder().add(dateStartCol);
    }

    /**
     * Handles when the submit button is pressed. Checks if a month and type are selected.
     * Gives how many appointments have the given month and type.
     * @param actionEvent
     */
    public void submitPressed(ActionEvent actionEvent) {
        int counter = 0;
        if (monthCombo.getSelectionModel().getSelectedItem() != null && typeCombo.getSelectionModel().getSelectedItem() != null) {
            Month selectedMonth = (Month) monthCombo.getSelectionModel().getSelectedItem();
            String type = (String) typeCombo.getSelectionModel().getSelectedItem();

            for (Appointment a: appointments) {
                if (a.getStartTime().getMonth().equals(selectedMonth) && a.getType().equalsIgnoreCase(type)) {
                    counter++;
                }
            }
            appointmentLabel.setText(Integer.toString(counter));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing info");
            alert.setContentText("Select a month and type");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * Handles when the back button is pressed. Sends user to the schedule screen.
     * @param actionEvent
     * @throws IOException
     */
    public void backPressed(ActionEvent actionEvent) throws IOException {
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("scheduleScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene appointmentScene = new Scene(appointmentParent);
        stage.setTitle("Scheduling Records");
        stage.setScene(appointmentScene);
        stage.show();
    }
}
