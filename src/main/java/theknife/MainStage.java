package theknife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import theknife.models.Cliente;
import theknife.models.Utente;
import theknife.utility.Enums;
import theknife.utility.FileManager;

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
     *
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


//        FileManager fileManager = FileManager.getInstance();

//        FileManager.serializeData(clienti, Enums.TipoFile.UTENTI);
//
//        FileManager.deserializeData(Enums.TipoFile.UTENTI);


        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
