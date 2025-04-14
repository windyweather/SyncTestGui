package net.windyweather.synctestgui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class STGController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}