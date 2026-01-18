package theknife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import theknife.models.User;
import theknife.utility.Enums;
import theknife.utility.FileManager;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The main stage class
 */
public class MainStage extends Application {

    /**
     * TheKnife app name for title.
     */
    public static String AppName = "TheKnife";

    /**
     * Main stage
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainStage.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(AppName);

        try (InputStream is = MainStage.class.getResourceAsStream("/assets/TheKnife.png")) {

            if (is == null) {
                System.out.println("Errore: Il file non è stato trovato!");
                return;
            }

            Image icon = new Image(is);
            stage.getIcons().add(icon);

        } catch (Exception e) {
            e.printStackTrace();
        }


        FileManager fileManager = FileManager.getInstance();

        User a = new User("gino", "Lino", "Pino", "123", new Date(), "gg", Enums.Roles.CUSTOMER, false);

        List<User> users = Arrays.asList(
                new User("gino", "Lino", "Pino", "123", new Date(), "gg", Enums.Roles.CUSTOMER, false),
                new User("gino2", "Lino2", "Pino2", "123", new Date(), "gg", Enums.Roles.CUSTOMER, false),
                new User("gino3", "Lino3", "Pino3", "123", new Date(), "gg", Enums.Roles.CUSTOMER, false)
        );

        FileManager.serializeData(users, Enums.FileType.USERS);

        FileManager.deserializeData(Enums.FileType.USERS);


        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
