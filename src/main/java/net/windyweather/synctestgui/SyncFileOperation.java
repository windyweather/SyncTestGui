package net.windyweather.synctestgui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/*
    Encodes a file operation between a source and destination file path.
    The only file operations we do here in Sync are to COPY a file.
    The older programs did Delete, Find Duplicates etc. We leave those for
    the normal file explorer. Experience indicates that the other features are
    used so infrequently that it's not worth coding them.
    Woops. I forgot verify copy, and verify not copied.
 */
public class SyncFileOperation extends TreeItem<String> {
    public SyncFileOperation() {

    }

    /*
        Operation constants
     */
    static final String SFO_COPY = "COPY";
    static final String SFO_COPY_VERIFY = "COPY/VERIFY";
    static final String SFO_VERIFY_NOCOPY = "VERIFY ONLY";
    /*
        Status Constants
     */
    static final String SFO_PEND = "PENDING";
    static final String SFO_COPIED = "COPIED";
    static final String SFO_COPYVERIFY = "COPIED/VERIFIED";
    static final String SFO_VERFIED = "VERIFIED";
    static final String SFO_RECOVERED = "RECOVERED";

    String sSourcePath;
    String sDestinationPath;
    int intSize;
    String sOperation; //
    String Status;

    /*
    public static Image folderCollapseImage = new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("net.windyweather.synchtestgui/File_Folder.svg")));
    public static Image folderExpandImage = new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("net.windyweather.synchtestgui/File_Folder-open.svg")));
    public static Image fileImage = new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("net.windyweather.synchtestgui/File_Text-x-generic.svg")));
    */

    private void printSysOut( String str ) {
        System.out.println( str );
    }

    //this stores the full path to the file or directory
    private String fullPath;

    public String getFullPath() {
        return (this.fullPath);
    }

    private boolean isDirectory;

    public boolean isDirectory() {
        return (this.isDirectory);
    }

    public SyncFileOperation(Path file) {
        super(file.toString());
        this.fullPath = file.toString();
        //test if this is a directory and set the icon
        if (Files.isDirectory(file)) {
            this.isDirectory = true;
            //this.setGraphic(new ImageView(folderCollapseImage));
        } else {
            this.isDirectory = false;
            //this.setGraphic(new ImageView(fileImage));
            //if you want different icons for different file types this is where you'd do it
        }

        printSysOut("SynchFileOperation() path: "+fullPath );

        //set the value
        if (!fullPath.endsWith(File.separator)) {
            //set the value (which is what is displayed in the tree)
            String value = file.toString();
            int indexOf = value.lastIndexOf(File.separator);
            if (indexOf > 0) {
                this.setValue(value.substring(indexOf + 1));
            } else {
                this.setValue(value);
            }
            printSysOut("SFO() value "+value);
        }

        this.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler() {
            @Override
            public void handle(Event e) {
                SyncFileOperation source = (SyncFileOperation) e.getSource();
                if (source.isDirectory() && source.isExpanded()) {
                    printSysOut("SFO() EventTreeItem Expanded folder" + source.fullPath);
                    //ImageView iv = (ImageView) source.getGraphic();
                    //iv.setImage(folderExpandImage);
                }
                try {
                    if (source.getChildren().isEmpty()) {
                        Path path = Paths.get(source.getFullPath());
                        BasicFileAttributes attribs = Files.readAttributes(path, BasicFileAttributes.class);
                        if (attribs.isDirectory()) {
                            printSysOut("TreeItem.branchExpandedEvent dirSearch of "+path.toString() );
                            DirectoryStream<Path> dir = Files.newDirectoryStream(path);
                            for (Path file : dir) {
                                SyncFileOperation treeNode = new SyncFileOperation(file);
                                source.getChildren().add(treeNode);
                            }
                        }
                    } else {
                        //if you want to implement rescanning a directory for changes this would be the place to do it
                        printSysOut("SFO() EventTreeItem already expanded folder" + source.fullPath);
                    }
                } catch (IOException x) {
                    //x.printStackTrace();
                    printSysOut( "SFO Branch expanded event catch "+fullPath);
                }
            }
        });


        this.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler() {
            @Override
            public void handle(Event e) {
                SyncFileOperation source = (SyncFileOperation) e.getSource();
                if (source.isDirectory() && !source.isExpanded()) {
                    //ImageView iv = (ImageView) source.getGraphic();
                    //iv.setImage(folderCollapseImage);
                    printSysOut("SFO() EventTreeItem Collapse Event" + source.fullPath);
                }
            }
        });

    } // end of constructor
} // end of SyncFileOperation class
