package org.library.utils;

import javafx.scene.control.ButtonBase;
import javafx.stage.Stage;
import org.library.entity.Book;

public class Utils {
    public static Stage getStage(ButtonBase buttonBase) {
        return (Stage) buttonBase.getScene().getWindow();
    }

    public static void updateBook(Book forUpdate, Book updated) {
        forUpdate.setTitle(updated.getTitle());
        forUpdate.setAuthor(updated.getAuthor());
        forUpdate.setGenre(updated.getGenre());
        forUpdate.setPublisher(updated.getPublisher());
        forUpdate.setLength(updated.getLength());
    }
}
