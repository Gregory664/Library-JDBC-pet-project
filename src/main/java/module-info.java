module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    opens org.library to javafx.fxml;
    exports org.library;
}