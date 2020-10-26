module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.library to javafx.fxml;
    exports org.library;
}