package org.library.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;
import org.library.exceptions.AuthorNotFoundByNameException;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.AuthorRepositoryImpl;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.library.services.GenreService;
import org.library.services.PublisherService;
import org.library.utils.MessageBox;
import org.library.utils.Utils;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class BookController {
    private final AuthorService authorService = new AuthorService(new AuthorRepositoryImpl());
    private final GenreService genreService = new GenreService();
    private final PublisherService publisherService = new PublisherService();
    private final BookService bookService = new BookService();
    public TextField titleTextField = new TextField();
    public ComboBox<String> authorComboBox = new ComboBox<>();
    public ComboBox<String> publisherComboBox = new ComboBox<>();
    public ComboBox<String> genreComboBox = new ComboBox<>();
    public TextField lengthTextField = new TextField();
    public Button saveButton = new Button();
    public Button cancelButton = new Button();
    @Getter
    private boolean close;
    @Getter
    private boolean save;
    @Getter
    private Book book;

    @FXML
    public void initialize() {
        authorComboBox.setItems(FXCollections.observableArrayList(authorService.findAll().stream()
                .map(Author::getName)
                .collect(Collectors.toList())));
        genreComboBox.setItems(FXCollections.observableArrayList(genreService.findAll().stream()
                .map(Genre::getTitle)
                .collect(Collectors.toList())));
        publisherComboBox.setItems(FXCollections.observableArrayList(publisherService.findAll().stream()
                .map(Publisher::getTitle)
                .collect(Collectors.toList())));
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        Author author;
        String authorName = authorComboBox.getEditor().getText();
        if (!authorComboBox.getItems().contains(authorName)) {
            author = new Author(authorName);
            authorService.save(author);
        }

        Publisher publisher;
        String publisherTitle = publisherComboBox.getEditor().getText();
        if (!publisherComboBox.getItems().contains(publisherTitle)) {
            publisher = new Publisher(publisherTitle);
            publisherService.save(publisher);
        }

        Genre genre;
        String genreTitle = genreComboBox.getEditor().getText();
        if (!genreComboBox.getItems().contains(genreTitle)) {
            genre = new Genre(genreTitle);
            genreService.save(genre);
        }

        try {
            String title = titleTextField.getText();
            author = authorService.findByName(authorComboBox.getSelectionModel().getSelectedItem());
            publisher = publisherService.findByTitle(publisherComboBox.getSelectionModel().getSelectedItem()).orElseThrow(SQLException::new);
            genre = genreService.findByTitle(genreComboBox.getSelectionModel().getSelectedItem()).orElseThrow(SQLException::new);
            int length = Integer.parseInt(lengthTextField.getText());
            book = Book.builder()
                    .title(title)
                    .author(author)
                    .publisher(publisher)
                    .genre(genre)
                    .length(length)
                    .build();
            save = bookService.save(book);
            Utils.getStage(saveButton).close();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        } catch (AuthorNotFoundByNameException e) {
            MessageBox.WarningBox(e.getMessage());
        }

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        close = true;
        Utils.getStage(saveButton).close();
    }
}
