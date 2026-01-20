package theknife;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import theknife.utility.Enums;
import theknife.utility.FileManager;

import java.io.IOException;

public class MainStageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLoginButtonClick() {

        System.out.println("Login button clicked");
    }

    @FXML
    protected void onRegisterButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void onGuestClick(MouseEvent mouseEvent) {
    }
}
