package org.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Getter;
import org.library.entity.Genre;
import org.library.repositories.GenreRepositoryImpl;
import org.library.services.GenreService;
import org.library.utils.Utils;

public class GenreController {
    private final GenreService service = new GenreService(new GenreRepositoryImpl());
    public Button saveButton;
    public Button cancelButton;
    public TextArea genreTitleTextField;
    @Getter
    private boolean save;
    @Getter
    private boolean actionOnForm;

    @Getter
    private Genre genre;

    public void setGenre(Genre genre) {
        this.genre = genre;
        genreTitleTextField.setText(genre.getTitle());
    }

    @FXML
    public void save() {
        if (genre == null) {
            genre = new Genre(genreTitleTextField.getText());
            save = service.save(genre);
        } else {
            genre.setTitle(genreTitleTextField.getText());
            save = service.update(genre);
        }
        actionOnForm = true;
        Utils.getStage(cancelButton).close();
    }

    @FXML
    public void cancel() {
        Utils.getStage(cancelButton).close();
    }
}
