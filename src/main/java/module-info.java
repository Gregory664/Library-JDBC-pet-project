module org.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires lombok;
    requires commons.cli;

    opens org.library.controllers to javafx.fxml;
    exports org.library;
    exports org.library.entity;
    exports org.library.controllers;
}