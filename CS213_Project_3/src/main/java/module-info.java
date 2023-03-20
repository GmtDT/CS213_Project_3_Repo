module com.example.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.ControllClasses to javafx.fxml;
    exports com.example.ControllClasses;
    exports TuitionManager;
    opens TuitionManager to javafx.fxml;
}