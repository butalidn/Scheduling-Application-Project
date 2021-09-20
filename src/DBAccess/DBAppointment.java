package DBAccess;

import Model.Appointment;
import Model.Customer;
import Utilities.DBConnection;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DBAppointment {
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("appointment_id");
                int customerID = rs.getInt("customer_id");
                int userID = rs.getInt("user_id");
                int contactID = rs.getInt("contact_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String type = rs.getString("type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();

                Appointment appointment  = new Appointment(appointmentID, customerID, userID, title, description, location, contactID, type, startTime, endTime);
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    public static void addAppointment(Appointment a) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String addSQL = "insert into appointments (title, description, location, type, start, end, customer_id, user_id, contact_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        DBQuery.setPreparedStatement(conn, addSQL);
        PreparedStatement addStatement = DBQuery.getPreparedStatement();

        Timestamp startTimeStamp = Timestamp.valueOf(a.getStartTime());
        Timestamp endTimeStamp = Timestamp.valueOf(a.getEndTime());

        addStatement.setString(1, a.getTitle());
        addStatement.setString(2, a.getDescription());
        addStatement.setString(3, a.getLocation());
        addStatement.setString(4, a.getType());
        addStatement.setTimestamp(5, startTimeStamp);
        addStatement.setTimestamp(6, endTimeStamp);
        addStatement.setInt(7, a.getCustomerID());
        addStatement.setInt(8, a.getUserID());
        addStatement.setInt(9, a.getContactID());
        addStatement.execute();
    }

    public static void updateAppointment(Appointment a) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String updateSQL = "update appointments set title = ?, description = ?, location = ?, type = ?, start = ?, end = ?," +
                "customer_id = ?, user_id = ?, contact_id = ? where appointment_id = ?;";
        DBQuery.setPreparedStatement(conn, updateSQL);
        PreparedStatement updateStatement = DBQuery.getPreparedStatement();

        Timestamp startTimeStamp = Timestamp.valueOf(a.getStartTime());
        Timestamp endTimeStamp = Timestamp.valueOf(a.getEndTime());

        updateStatement.setString(1, a.getTitle());
        updateStatement.setString(2, a.getDescription());
        updateStatement.setString(3, a.getLocation());
        updateStatement.setString(4, a.getType());
        updateStatement.setTimestamp(5, startTimeStamp);
        updateStatement.setTimestamp(6,endTimeStamp);
        updateStatement.setInt(7, a.getCustomerID());
        updateStatement.setInt(8, a.getUserID());
        updateStatement.setInt(9, a.getContactID());
        updateStatement.setInt(10, a.getAppointmentID());

        updateStatement.execute();
    }

    public static void removeAppointment(Appointment a) throws SQLException {
        Connection conn = DBConnection.getConnection();

        String deleteSQL = "delete from appointments where appointment_id = ?;";
        DBQuery.setPreparedStatement(conn, deleteSQL);
        PreparedStatement deleteStatement = DBQuery.getPreparedStatement();
        deleteStatement.setInt(1, a.getAppointmentID());
        deleteStatement.execute();
    }
}
