package org.library.utils;

import javafx.scene.control.Alert;

import static javafx.scene.control.Alert.*;

public class MessageBox {
    public static Alert WarningBox(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Внимание");
        alert.setHeaderText(message);
        return alert;
    }

    public static Alert OkBox(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Успешно");
        alert.setHeaderText(message);
        return alert;
    }
}
