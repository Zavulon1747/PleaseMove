module com.example.pleasemove {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens com.example.pleasemove to javafx.fxml;
    exports com.example.pleasemove;
}