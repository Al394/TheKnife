package theknife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main stage class
 */
public class MainStage extends Application {

    /**
     * TheKnife app name for title.
     */
    public static String AppName = "TheKnife";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainStage.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(AppName);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
