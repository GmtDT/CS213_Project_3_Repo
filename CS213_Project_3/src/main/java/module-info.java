module com.example.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.TuitionManager to javafx.fxml;
    exports com.example.TuitionManager;
}