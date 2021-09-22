package Scheduler;

import Utilities.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/** JavaDoc Folder Location
 * Folder containing Javadoc comments is located  under \Butalid_C195_PA.zip\Butalid_C195_PA\javadoc
 */
public class Main extends Application{
    /**
     * The main stage is loaded and the stage is set. The appropriate language is set for the login screen's title.
     * @param primaryStage
     * @throws Exception
     */
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

    /** This is the main method of the application. It is launched with args arguments.
     * A connection is opened and closed with the database
     */
    public static void main(String[] args) throws SQLException {
        //Locale.setDefault(new Locale("fr"));
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
