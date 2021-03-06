package org.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Getter;
import org.library.entity.Author;
import org.library.repositories.AuthorRepositoryImpl;
import org.library.services.AuthorService;
import org.library.utils.UtilityClass;

public class AuthorController {
    private final AuthorService service = new AuthorService(new AuthorRepositoryImpl());
    public TextArea authorFioTextField;
    public Button saveButton;
    public Button cancelButton;
    @Getter
    private boolean save;
    @Getter
    private boolean actionOnForm;

    @Getter
    private Author author;

    public void setAuthor(Author author) {
        this.author = author;
        authorFioTextField.setText(author.getName());
    }

    @FXML
    void initialize() {
    }

    @FXML
    public void save() {
        if (author == null) {
            author = new Author(authorFioTextField.getText());
            save = service.save(author);
        } else {
            author.setName(authorFioTextField.getText());
            save = service.update(author);
        }
        actionOnForm = true;
        UtilityClass.getStage(cancelButton).close();
    }

    @FXML
    public void cancel() {
        UtilityClass.getStage(cancelButton).close();
    }
}