package Scheduler;

import Utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/** JavaDoc Folder Location
 * Folder containing Javadoc comments is located  under \Butalid_C195_PA.zip\Butalid_C195_PA\javadoc
 */
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        String titleName = "Login";
        try {
            ResourceBundle rb = ResourceBundle.getBundle("Resources/Nat", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                titleName = rb.getString("Login");
            }
        }
        catch (Exception e) {}

        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/loginScreen.fxml"));
        primaryStage.setTitle(titleName);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        //Locale.setDefault(new Locale("fr"));

        DBConnection.startConnection();

//        String sql = "SELECT * FROM appointments";
//        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
//
//        ResultSet rs = ps.executeQuery();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a");
//        rs.next();
//
//        LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
//        System.out.println(startTime.format(formatter));
//
//        ZonedDateTime zonedCDT = startTime.atZone(ZoneId.systemDefault());
//        ZonedDateTime zoneEST = zonedCDT.withZoneSameInstant(ZoneId.of("America/New_York"));
//        zoneEST.toLocalDateTime();
//
//        System.out.println(zoneEST.format(formatter));


        /*Connection conn = DBConnection.getConnection();
        DBQuery.setStatement(conn);
        Statement statement= DBQuery.getStatement();

        String selectStatement = "select * from countries;";
        statement.execute(selectStatement);
        ResultSet rs = statement.getResultSet();

        while (rs.next()) {
            int countryID = rs.getInt("country_id");
            String countryName = rs.getString("country");
            LocalDate date = rs.getDate("create_date").toLocalDate();
            LocalTime time = rs.getTime("create_date").toLocalTime();
            String createdBy = rs.getString("created_by");
            LocalDateTime lastUpdate = rs.getTimestamp("last_update").toLocalDateTime();

            System.out.println("ID: " + countryID);
            System.out.println("Name: " + countryName);
            System.out.println("Date: " + date);
            System.out.println("Time: " + time);
            System.out.println("Created By: " + createdBy);
            System.out.println("Last Update: " + lastUpdate);
        }

        //Returns how many rows are affected
        if (statement.getUpdateCount() > 0) {
            System.out.println(statement.getUpdateCount() + " row(s) affected!");
        }
        else {
            System.out.println("No change");
        }*/

        launch(args);
        DBConnection.closeConnection();
    }
}
