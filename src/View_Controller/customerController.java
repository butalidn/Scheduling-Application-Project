package View_Controller;

import DBAccess.*;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.application.Platform;
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
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class handles the logic of the customer screen.
 */
public class customerController implements Initializable {
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

    /**
     * The customer table and combo boxes are set up. The list of customers are loaded into the table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postal"));
        numberCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        firstLevelCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstLevel"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));

        customerTable.setItems(DBCustomer.getAllCustomers());
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        firstLevelCombo.setPromptText("Select a country first");

        //countryCombo.getItems().addAll(DBCountry.getAllCountries());

        for (Country c: DBCountry.getAllCountries()) {
            countryCombo.getItems().add(c.getName());
        }
    }

    /**
     * Handles when the back button is clicked. Sends the user back to the manage screen.
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent manageParent = FXMLLoader.load(getClass().getResource("manageScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene manageScene = new Scene(manageParent);
        stage.setTitle("Welcome");
        stage.setScene(manageScene);
        stage.show();
    }

    /**
     * This handles when the user selects a row on the table. When they do, the information from the selected
     * customer is loaded into the appropriate fields
     * @param mouseEvent
     * @throws Exception
     */
    public void rowClicked(MouseEvent mouseEvent) throws Exception{
        try {
            customerText.setText(Integer.toString(((Customer) customerTable.getSelectionModel().getSelectedItem()).getId()));
            nameText.setText(((Customer) customerTable.getSelectionModel().getSelectedItem()).getName());
            numberText.setText(((Customer) customerTable.getSelectionModel().getSelectedItem()).getPhone());
            addressText.setText(((Customer) customerTable.getSelectionModel().getSelectedItem()).getAddress());
            postalText.setText(((Customer) customerTable.getSelectionModel().getSelectedItem()).getPostal());


            countryCombo.getSelectionModel().select(((Customer) customerTable.getSelectionModel().getSelectedItem()).getCountry());
            firstLevelCombo.getSelectionModel().select(((Customer) customerTable.getSelectionModel().getSelectedItem()).getFirstLevel());
        }
        catch (Exception e) {}
    }

    /**
     * Handles when the add button is clicked. Checks for logical errors and creates a new customer object if valid
     * @param actionEvent
     * @throws SQLException
     */
    public void addButtonClicked(ActionEvent actionEvent) throws SQLException {
        String message = "";
        try {
            boolean goodInfo = true;

            if (nameText.getText().trim().isEmpty()) {
                message += "Missing name\n";
                goodInfo = false;
            }
            if (addressText.getText().trim().isEmpty()) {
                message += "Missing address\n";
                goodInfo = false;
            }
            if (postalText.getText().trim().isEmpty()) {
                message += "Missing postal\n";
                goodInfo = false;
            }
            if (numberText.getText().trim().isEmpty()) {
                message += "Missing number\n";
                goodInfo = false;
            }
            if (countryCombo.getSelectionModel().getSelectedItem() == null) {
                message += "Missing country\n";
                goodInfo = false;
            }
            if (goodInfo) {
                try {
                    Customer c = new Customer(0, nameText.getText(), addressText.getText(), postalText.getText(),
                            numberText.getText(), firstLevelCombo.getSelectionModel().getSelectedItem().toString(), countryCombo.getSelectionModel().getSelectedItem().toString());
                    DBCustomer.addCustomer(c);
                    customerTable.setItems(DBCustomer.getAllCustomers());
                    customerText.setText(null);
                    nameText.setText(null);
                    numberText.setText(null);
                    addressText.setText(null);
                    postalText.setText(null);

                    firstLevelCombo.getSelectionModel().clearSelection();
                    countryCombo.getSelectionModel().clearSelection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Information");
                alert.setContentText(message);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles when the update button is clicked. Checks for logical errors. Updates the appropriate customer record
     * if information is valid.
     * @param actionEvent
     * @throws SQLException
     */
    public void updateButtonClicked(ActionEvent actionEvent) throws SQLException {
        String message = "";
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            boolean goodInfo = true;
            if (nameText.getText().trim().isEmpty()) {
                message += "Missing name\n";
                goodInfo = false;
            }
            if (addressText.getText().trim().isEmpty()) {
                message += "Missing address\n";
                goodInfo = false;
            }
            if (postalText.getText().trim().isEmpty()) {
                message += "Missing postal\n";
                goodInfo = false;
            }
            if (numberText.getText().trim().isEmpty()) {
                message += "Missing number\n";
                goodInfo = false;
            }
            if (countryCombo.getSelectionModel().getSelectedItem() == null) {
                message += "Missing country\n";
                goodInfo = false;
            }
            if (goodInfo) {
                try {
                    Customer c = (Customer) customerTable.getSelectionModel().getSelectedItem();
                    c.setName(nameText.getText());
                    c.setPhone(numberText.getText());
                    c.setAddress(addressText.getText());
                    c.setPostal(postalText.getText());
                    c.setFirstLevel(firstLevelCombo.getSelectionModel().getSelectedItem().toString());
                    c.setCountry(countryCombo.getSelectionModel().getSelectedItem().toString());
                    DBCustomer.updateCustomer(c);

                    customerTable.refresh();

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Information");
                alert.setContentText(message);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Customer not selected");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * Handles when the delete button is clicked. If a row selected, the user has to confirm if they want to delete a customer.
     * All appointments connected to the customer must be deleted first. If all is valid, the customer is deleted from the customers table.
     * @param actionEvent
     */
    public void deleteButtonClicked(ActionEvent actionEvent) {
        boolean goodDelete = true;
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            for (Appointment a : DBAppointment.getAllAppointments()) {
                if (a.getCustomerID() == ((Customer) customerTable.getSelectionModel().getSelectedItem()).getId()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment(s) associated with customer must be deleted");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    Optional<ButtonType> result = alert.showAndWait();
                    goodDelete = false;
                }
            }
            if (goodDelete) {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Customer");
                    alert.setContentText("Are you sure you want to delete this customer? Make sure all associated appointments are removed first");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        Customer c = (Customer) customerTable.getSelectionModel().getSelectedItem();
                        DBCustomer.removeCustomer(c);
                        DBCustomer.getAllCustomers().remove(c);
                        customerTable.setItems(DBCustomer.getAllCustomers());
                        customerText.setText("");
                        nameText.setText("");
                        numberText.setText("");
                        addressText.setText("");
                        postalText.setText("");


                        firstLevelCombo.getSelectionModel().clearSelection();
                        countryCombo.getSelectionModel().clearSelection();
                        firstLevelCombo.setPromptText("Select a country first");
                    }
                } catch (SQLIntegrityConstraintViolationException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Appointment(s) associated with customer must be deleted");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    Optional<ButtonType> result = alert.showAndWait();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please select a row to delete");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * This handles when a country is selected in the country combo box. When a country is selected, the first level
     * divisions that are located in the country are listed in the first level division combo box.
     * @param actionEvent
     */
    public void countrySelected(ActionEvent actionEvent) {
        firstLevelCombo.getItems().clear();
        if (countryCombo.getSelectionModel().getSelectedItem() != null) {
            for (Division D : DBDivision.filterDivisions(countryCombo.getSelectionModel().getSelectedItem().toString())) {
                firstLevelCombo.getItems().add(D.getDivision());
            }
            firstLevelCombo.getSelectionModel().selectFirst();
        }
        else {
            firstLevelCombo.setPromptText("Select a country first");
        }
    }
}
