package net.windyweather.synctestgui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class STGController {
    public TreeTableView tvFileTree;
    public TreeTableColumn tcSourcePath;
    public TreeTableColumn tcFileSize;
    public TreeTableColumn tcActionPending;
    public TreeTableColumn tcStatus;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}