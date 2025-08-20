package theknife;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainStageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLoginButtonClick() {

        System.out.println("Login button clicked");
    }

    @FXML
    protected void onRegisterButtonClick() {

        System.out.println("Register button clicked");
    }
}
