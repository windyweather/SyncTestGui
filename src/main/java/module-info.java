module net.windyweather.synctestgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.windyweather.synctestgui to javafx.fxml;
    exports net.windyweather.synctestgui;
}