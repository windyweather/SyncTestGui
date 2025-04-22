package net.windyweather.synctestgui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.nio.file.Path;

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
                    btnOneToTwo.setDisable( false );
                }
            }
        });


        txtFilePathTwo.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    System.out.println( String.format("Change FilePathTwo? %s", txtFilePathTwo.getText() ) );
                    btnTwoToOne.setDisable( false );
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
        /*
            Load up the tree starting with the path in the PathOne
         */
        Path pathPathOne = new File( txtFilePathOne.getText() ).toPath();

        SyncFileOperation treeNode=new SyncFileOperation(pathPathOne);
        treeNode.getChildren().add(treeNode);

        treeNode.setExpanded(true);

        tvFileTree = new TreeTableView<SyncFileOperation>();

    }

    public void OnClickFilePathTwo(ActionEvent actionEvent) {
        btnTwoToOne.setDisable( false );
    }

    public void OnClickFilePathOne(ActionEvent actionEvent) {
        btnOneToTwo.setDisable( false );
    }
}