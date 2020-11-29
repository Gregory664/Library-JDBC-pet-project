package org.library.exceptions;

import javafx.scene.control.Label;

public class ReaderNotFoundByPassportException extends RuntimeException {
    public ReaderNotFoundByPassportException(Label label) {
        label.setText("Не найдено");
    }
}
