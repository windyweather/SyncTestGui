package net.windyweather.synctestgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SyncTestGui extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SyncTestGui.class.getResource("sync-test-gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 800);
        stage.setTitle("Sync Test GUI Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}