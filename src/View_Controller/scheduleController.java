package View_Controller;

import DBAccess.*;
import Model.Appointment;
import Model.Country;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class scheduleController implements Initializable {
    @FXML private TableColumn appointmentCol;
    @FXML private TableColumn customerCol;
    @FXML private TableColumn userCol;
    @FXML private TableColumn titleCol;
    @FXML private TableColumn descriptionCol;
    @FXML private TableColumn locationCol;
    @FXML private TableColumn contactCol;
    @FXML private TableColumn typeCol;
    @FXML private TableColumn startCol;
    @FXML private TableColumn endCol;
    @FXML private Button addButton;
    @FXML private Button modifyButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;
    @FXML private RadioButton monthRadio;
    @FXML private RadioButton weekRadio;
    @FXML private RadioButton allRadio;
    @FXML private TableView scheduleTable;

    private ToggleGroup sortToggleGroup;
    private static Appointment dataInit = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sortToggleGroup = new ToggleGroup();
        this.monthRadio.setToggleGroup(sortToggleGroup);
        this.weekRadio.setToggleGroup(sortToggleGroup);
        this.allRadio.setToggleGroup(sortToggleGroup);
        sortToggleGroup.selectToggle(allRadio);

        appointmentCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));

        customerCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        customerCol.setCellFactory(tc -> new TableCell<TableView, Integer>() {
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

        userCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
        userCol.setCellFactory(tc -> new TableCell<TableView, Integer>() {
            @Override
            protected void updateItem(Integer id, boolean empty) {
                super.updateItem(id, empty);
                if (empty) {
                    setText(null);
                } else {
                    try {
                        setText(DBUser.lookupUser(id));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));

        contactCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactID"));
        contactCol.setCellFactory(tc -> new TableCell<TableView, Integer>() {
            @Override
            protected void updateItem(Integer id, boolean empty) {
                super.updateItem(id, empty);
                if (empty) {
                    setText(null);
                } else {
                    try {
                        setText(DBContact.lookupContact(id));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));

        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startTime"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a");
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

        scheduleTable.setItems(DBAppointment.getAllAppointments());
        scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
        dataInit = null;
        Parent appointmentParent = FXMLLoader.load(getClass().getResource("appointmentScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene appointmentScene = new Scene(appointmentParent);
        stage.setTitle("Add Appointment");
        stage.setScene(appointmentScene);
        stage.show();
    }

    public void modifyButtonClicked(ActionEvent actionEvent) throws IOException {
        if (scheduleTable.getSelectionModel().getSelectedItem() != null) {
            dataInit = (Appointment) scheduleTable.getSelectionModel().getSelectedItem();
            Parent appointmentParent = FXMLLoader.load(getClass().getResource("appointmentScreen.fxml"));
            Stage stage = (Stage) (((Node) actionEvent.getSource()).getScene().getWindow());
            Scene appointmentScene = new Scene(appointmentParent);
            stage.setTitle("Modify Appointment");
            stage.setScene(appointmentScene);
            stage.show();
        }
        else {
            System.out.println("Select an appointment");
        }
    }
    
    public static Appointment initAppointment() {
        return dataInit;
    }

    public void rowSelected(MouseEvent mouseEvent) {
    }
}
