package org.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Getter;
import org.library.entity.Author;
import org.library.repositories.AuthorRepositoryImpl;
import org.library.services.AuthorService;
import org.library.utils.Utils;

public class AuthorController {
    private final AuthorService service = new AuthorService(new AuthorRepositoryImpl());
    public TextArea authorFioTextField;
    public Button saveButton;
    public Button cancelButton;
    @Getter
    private boolean save;
    @Getter
    private boolean close;

    @Getter
    private Author author;

    public void setAuthor(Author author) {
        this.author = author;
        authorFioTextField.setText(author.getName());
    }

    @FXML
    void initialize() {
    }

    public void save(ActionEvent actionEvent) {
        if (author == null) {
            author = new Author(authorFioTextField.getText());
            save = service.save(author);
        } else {
            author.setName(authorFioTextField.getText());
            save = service.update(author);
        }

        Utils.getStage(cancelButton).close();
    }

    public void cancel(ActionEvent actionEvent) {
        close = true;
        Utils.getStage(cancelButton).close();
    }
}