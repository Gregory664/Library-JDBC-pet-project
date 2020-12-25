package org.library.controllers;

import javafx.event.ActionEvent;
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
    private boolean close;

    @Getter
    private Genre genre;

    public void setGenre(Genre genre) {
        this.genre = genre;
        genreTitleTextField.setText(genre.getTitle());
    }

    public void save(ActionEvent actionEvent) {
        if(genre == null) {
            genre = new Genre(genreTitleTextField.getText());
            save = service.save(genre);
        } else {
            genre.setTitle(genreTitleTextField.getText());
            save = service.update(genre);
        }

        Utils.getStage(cancelButton).close();
    }

    public void cancel(ActionEvent actionEvent) {
        close = true;
        Utils.getStage(cancelButton).close();
    }
}
