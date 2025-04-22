module net.windyweather.synctestgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.io;
    requires plexus.utils;


    opens net.windyweather.synctestgui to javafx.fxml;
    exports net.windyweather.synctestgui;
}