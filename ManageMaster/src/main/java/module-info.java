module com.example.managemaster {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.managemaster.view to javafx.fxml;
    opens com.example.managemaster.controllers to javafx.fxml;

    exports com.example.managemaster.view;
    exports com.example.managemaster.controllers;
}
