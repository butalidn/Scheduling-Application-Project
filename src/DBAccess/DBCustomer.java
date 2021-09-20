package DBAccess;

import Model.Customer;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomer {
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM customers";
            int customerID = 0;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");

                String divisionQuery = "select * from first_level_divisions where division_id = (" +
                        "select division_id countries from customers where customer_id = " + customerID + ");";
                PreparedStatement divisionStatement = DBConnection.getConnection().prepareStatement(divisionQuery);
                ResultSet divisionSet = divisionStatement.executeQuery();
                divisionSet.next();
                String firstDivision = divisionSet.getString("Division");

                int countryId = divisionSet.getInt("COUNTRY_ID");
                String countryQuery = "select * from countries where country_id = " + countryId + ";";
                PreparedStatement countryStatement = DBConnection.getConnection().prepareStatement(countryQuery);
                ResultSet countrySet = countryStatement.executeQuery();
                countrySet.next();
                String country = countrySet.getString("Country");

                Customer customer  = new Customer(customerID, name, address,postal,phone, firstDivision, country);
                customerList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

    public static void updateCustomer(Customer c) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String divisionSQL = "select * from first_level_divisions where division = ?;";
        DBQuery.setPreparedStatement(conn, divisionSQL);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, c.getFirstLevel());
        ps.execute();
        ResultSet divisionSet = ps.getResultSet();
        divisionSet.next();

        int divisionID = divisionSet.getInt("Division_ID");

        String updateSQL = "update customers set customer_name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_id = ? where customer_id = ?;";
        DBQuery.setPreparedStatement(conn, updateSQL);
        PreparedStatement updateStatement = DBQuery.getPreparedStatement();
        updateStatement.setString(1, c.getName());
        updateStatement.setString(2, c.getAddress());
        updateStatement.setString(3, c.getPostal());
        updateStatement.setString(4, c.getPhone());
        updateStatement.setInt(5, divisionID);
        updateStatement.setInt(6, c.getId());

        updateStatement.execute();
    }

    public static void addCustomer(Customer c) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String addSQL = "insert into customers (customer_name, address, postal_code, phone, division_id) " +
                "values (?, ?, ?, ?, ?);";
        DBQuery.setPreparedStatement(conn, addSQL);
        PreparedStatement addStatement = DBQuery.getPreparedStatement();
        addStatement.setString(1, c.getName());
        addStatement.setString(2, c.getAddress());
        addStatement.setString(3, c.getPostal());
        addStatement.setString(4, c.getPhone());
        addStatement.setInt(5, DBDivision.lookupDivisionID(c.getFirstLevel()));
        addStatement.execute();
    }

    public static void removeCustomer(Customer c) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String deleteDQL = "delete from customers where customer_id = ?;";
        DBQuery.setPreparedStatement(conn, deleteDQL);
        PreparedStatement deleteStatement = DBQuery.getPreparedStatement();
        deleteStatement.setInt(1, c.getId());
        deleteStatement.execute();
    }

    public static String lookupCustomer(int id) throws SQLException {
        String sql = "select * from customers where customer_id = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString("Customer_Name");
    }

    public static int lookupCustomerID(String name) throws SQLException {
        String sql = "select * from customers where customer_name = ?;";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("Customer_ID");
    }
}
