package net.windyweather.synctestgui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class STGController {
    public TreeTableView<SyncFileOperation> tvFileTree;
    public TreeTableColumn<SyncFileOperation, String> tcSourcePath;
    public TreeTableColumn<SyncFileOperation, Integer> tcFileSize;
    public TreeTableColumn<SyncFileOperation, String> tcActionPending;
    public TreeTableColumn<SyncFileOperation, String> tcStatus;
    public CheckBox chkExcludeFileTypes;
    public TextField txtExcludeFileTypes;
    public TextField txtFilePathOne;
    public TextField txtFilePathTwo;
    public CheckBox chkIncludeSubfolders;
    public Button btnTwoToOne;
    public Button btnOneToTwo;
    @FXML
    private Label welcomeText;

    public STGController () {};

    public void initialize() {

        System.out.println("STGController initialize called");

        txtFilePathOne.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    System.out.println( String.format("Change FilePathOne? %s", txtFilePathOne.getText() ) );
                    btnOneToTwo.setDisable( true );
                }
            }
        });


        txtFilePathTwo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    System.out.println( String.format("Change FilePathTwo? %s", txtFilePathTwo.getText() ) );
                    btnTwoToOne.setDisable( true );
                }
            }
        });
    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void OnClickTwoToOne(ActionEvent actionEvent) {
        System.out.println( "OnClickTwoToOne" );
    }

    public void OnClickOneToTwo(ActionEvent actionEvent) {
        System.out.println( "OnClickOneToTwo" );
    }

    public void OnClickFilePathTwo(ActionEvent actionEvent) {
        btnTwoToOne.setDisable( false );
    }

    public void OnClickFilePathOne(ActionEvent actionEvent) {
        btnOneToTwo.setDisable( false );
    }
}