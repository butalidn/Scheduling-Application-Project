package View_Controller;

import DBAccess.DBCountry;
import DBAccess.DBCustomer;
import DBAccess.DBDivision;
import DBAccess.DBUser;
import Model.Country;
import Model.Customer;
import Model.Division;
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

        //countryCombo.getItems().addAll(DBCountry.getAllCountries());

        for (Country c: DBCountry.getAllCountries()) {
            countryCombo.getItems().add(c.getName());
        }
        countryCombo.getSelectionModel().selectFirst();

        for (Division D: DBDivision.getAllDivisions()) {
            firstLevelCombo.getItems().add(D.getDivision());
        }

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


            firstLevelCombo.getSelectionModel().select(((Customer) customerTable.getSelectionModel().getSelectedItem()).getFirstLevel());
            countryCombo.getSelectionModel().select(((Customer) customerTable.getSelectionModel().getSelectedItem()).getCountry());
        }
        catch (Exception e) {}
    }

    public void addButtonClicked(ActionEvent actionEvent) {
        try {
            Customer c = new Customer(0, nameText.getText(), addressText.getText(), postalText.getText(),
                    numberText.getText(), firstLevelCombo.getSelectionModel().getSelectedItem().toString(), countryCombo.getSelectionModel().getSelectedItem().toString());
            DBCustomer.addCustomer(c);
            customerTable.refresh();
        }
        catch (Exception e) {
            System.out.println("Invalid customer info");
        }
    }

    public void updateButtonClicked(ActionEvent actionEvent) throws SQLException {
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

    public void deleteButtonClicked(ActionEvent actionEvent) {
    }
}
