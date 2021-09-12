package View_Controller;

import DBAccess.DBCountry;
import DBAccess.DBCustomer;
import DBAccess.DBDivision;
import DBAccess.DBUser;
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

//        for (Division D: DBDivision.getAllDivisions()) {
//            firstLevelCombo.getItems().add(D.getDivision());
//        }

//        for (Division D: DBDivision.getAllDivisions()) {
//            try {
//                if (D.getCountry().equals(countryCombo.getSelectionModel().getSelectedItem().toString())) {
//                    firstLevelCombo.getItems().add(D.getDivision());
//
//                }
//            }
//            catch (Exception e) {System.out.println(e.getMessage());}
//        }

    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent manageParent = FXMLLoader.load(getClass().getResource("manageScreen.fxml"));
        Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        Scene manageScene = new Scene(manageParent);
        stage.setTitle("Welcome");
        stage.setScene(manageScene);
        stage.show();
    }


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

    public void addButtonClicked(ActionEvent actionEvent) throws SQLException {
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
            //firstLevelCombo.setPromptText("Select a country first");
        }
        catch (Exception e) {
            System.out.println("Missing info");
        }
    }

    public void updateButtonClicked(ActionEvent actionEvent) throws SQLException {
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

        }
        catch (NullPointerException e) {
            System.out.println("Error. Customer not selected");
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
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
                    customerText.setText(null);
                    nameText.setText(null);
                    numberText.setText(null);
                    addressText.setText(null);
                    postalText.setText(null);


                    firstLevelCombo.getSelectionModel().clearSelection();
                    countryCombo.getSelectionModel().clearSelection();
                    firstLevelCombo.setPromptText("Select a country first");
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Appointment(s) associated with customer must be deleted");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            }
        else {
            System.out.println("Please select a row to delete");
        }
    }

    public void firstLevelSelected(ActionEvent actionEvent) throws SQLException {
        //countryCombo.getSelectionModel().select(DBDivision.lookupCountry(firstLevelCombo.getSelectionModel().getSelectedItem().toString()));
    }

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
