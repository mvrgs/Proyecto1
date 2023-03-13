module com.example.proyecto1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyecto1 to javafx.fxml;
    exports com.example.proyecto1;
}