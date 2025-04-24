package net.windyweather.synctestgui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.io.File;
import java.nio.file.Path;

import org.codehaus.plexus.util.DirectoryScanner;
import org.apache.commons.io.FilenameUtils;

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
    public Label lblLastSyncDateTime;
    public Label lblTotalBytes;
    public Label lblOperations;
    public Label lblProgress;
    @FXML
    private Label welcomeText;


    private  long longTotalBytes;
    private int intOperations;

    /*
        Make the total bytes easily readable
     */
    private void SetTotalBytes() {

        String sTotalBytes = "";

        if ( longTotalBytes < 10000 )  {
            sTotalBytes = String.valueOf(longTotalBytes);
        } else if ( longTotalBytes < 10000000 ) {
            sTotalBytes = String.valueOf( longTotalBytes / 1024 )+" KB";
        } else if ( longTotalBytes < 10000000000L ) {
            sTotalBytes = String.valueOf( longTotalBytes / (1024*1024) )+" MB";
        } else if ( longTotalBytes < 10000000000000L ) {
            sTotalBytes = String.valueOf( longTotalBytes / ( 1024L*1024L*1024L) )+" GB";
        }
        lblTotalBytes.setText( sTotalBytes);
    }

    private void printSysOut( String str ) {
        System.out.println( str );
    }


    public STGController () {};

    public void initialize() {

        System.out.println("STGController initialize called");

        chkIncludeSubfolders.setSelected( true );

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


    private void GetTreeChildren( TreeItem<SyncFileOperation> treeNode ) {
        /*
            Get the children of this node, then for each that is a folder,
            this again to get their children. Expand the whole tree.
         */
        SyncFileOperation sfo = treeNode.getValue();
        String sPath = sfo.getFullPath();
        printSysOut("GetTreeChildren : " + sPath);

        String[] saIncludeEverything = new String[]{"*.*", "*"};

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(saIncludeEverything);
        scanner.setCaseSensitive(false);
        scanner.setBasedir(new File(sPath));
        scanner.scan();

       /*
            Get a list of the source files we found
        */
        String[] sSourceFiles = scanner.getIncludedFiles();
        printSysOut("GetTreeChildren Scanner Found : " + String.valueOf(sSourceFiles.length));

        for (String sSourceFile : sSourceFiles) {
            String sDeeperPath = sPath + File.separator + sSourceFile;

            File aFile = new File(sDeeperPath);
            Path pDeeperPath = new File(sDeeperPath).toPath();

            printSysOut("GetTreeChildren Add File : " + sDeeperPath);
            SyncFileOperation sfoDeeper = new SyncFileOperation(pDeeperPath);
            TreeItem<SyncFileOperation> deepNode = new TreeItem<>(sfoDeeper);
            longTotalBytes += sfoDeeper.getIntSize();

            treeNode.getChildren().add(deepNode);

        }
        /*
            Look in subfolders?
         */
        if ( !chkIncludeSubfolders.isSelected() ) {
            /*
                Nope, we are done
             */
            return;
        }
        /*
            Scan all the dirs
         */
        String[] sSourceDirs = scanner.getIncludedDirectories();
        for (String sSourceDir : sSourceDirs) {
            String sDeeperPath = sPath + File.separator + sSourceDir;
            Path pDeeperPath = new File(sDeeperPath).toPath();

            printSysOut("GetTreeChildren Add Directory : " + sDeeperPath);
            SyncFileOperation sfoDeeper = new SyncFileOperation(pDeeperPath);

            TreeItem<SyncFileOperation> deepNode = new TreeItem<>(sfoDeeper);
            deepNode.setExpanded(true);

            treeNode.getChildren().add(deepNode);
            treeNode.setExpanded(true);

            GetTreeChildren(deepNode);
        }
    }

    public void OnClickOneToTwo(ActionEvent actionEvent) {
        System.out.println( "OnClickOneToTwo" );

        /*
            Property Value Factories for Tree Columns
         */
        tcSourcePath.setCellValueFactory( new TreeItemPropertyValueFactory<>("SSourcePath"));
        tcFileSize.setCellValueFactory( new TreeItemPropertyValueFactory<>("IntSize"));
        tcActionPending.setCellValueFactory( new TreeItemPropertyValueFactory<>( "SOperation"));
        tcStatus.setCellValueFactory( new TreeItemPropertyValueFactory<>("Status"));

        tcFileSize.setStyle("-fx-alignment: CENTER-RIGHT;");
        tcActionPending.setStyle("-fx-alignment: CENTER;");
        tcStatus.setStyle("-fx-alignment: CENTER;");

        longTotalBytes = 0L;
        intOperations = 0;


        /*
            Load up the tree starting with the path in the PathOne
         */
        File aFile = new File( txtFilePathOne.getText() );
        Path pathPathOne = aFile.toPath();

        if ( !aFile.exists() ) {
            // nowhere....
        }

        SyncFileOperation root = new SyncFileOperation(pathPathOne);
        TreeItem<SyncFileOperation> treeNode = new TreeItem<> (root);
        treeNode.setExpanded(true);

        tvFileTree.setRoot( treeNode );
        longTotalBytes += root.getIntSize();
        /*
            If the root is a folder, then scan it all the way down
         */
        if ( root.isDirectory() ) {
            GetTreeChildren( treeNode );
        }
        /*
            Put a readable size in the display
         */
        SetTotalBytes();

    }


    public void OnClickFilePathOne(ActionEvent actionEvent) {
        txtFilePathOne.setText("D:\\zzSSATest\\BunchOfImages");
        btnOneToTwo.setDisable( false );
    }


    public void OnClickFilePathTwo(ActionEvent actionEvent) {
        txtFilePathTwo.setText("D:\\zzSSATest\\SourceTest\\Stuff Here\\Stuff_2024_12_01_11_06_41_327.png");
        btnTwoToOne.setDisable( false );
    }
}