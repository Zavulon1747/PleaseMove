module com.example.pleasemove {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pleasemove to javafx.fxml;
    exports com.example.pleasemove;
}