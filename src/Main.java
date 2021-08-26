import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/** JavaDoc Folder Location
 * Folder containing Javadoc comments is located  under \Butalid_C195_PA.zip\Butalid_C195_PA\javadoc
 */
public class Main extends Application {
    //Locale.setDefault(new Locale("fr"));      FROM WEBINAR IDK

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
