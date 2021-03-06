package org.library.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;
import org.library.exceptions.newExc.AuthorNotFoundByNameException;
import org.library.exceptions.newExc.EntityNotFoundByTitleException;
import org.library.repositories.*;
import org.library.services.AuthorService;
import org.library.services.BookService;
import org.library.services.GenreService;
import org.library.services.PublisherService;
import org.library.utils.MessageBox;
import org.library.utils.UtilityClass;

import java.util.TreeMap;
import java.util.stream.Collectors;

public class BookController {
    private final AuthorService authorService = new AuthorService(new AuthorRepositoryImpl());
    private final BookService bookService = new BookService(new BookShelfRepositoryImpl(), new BookRepositoryImpl());
    private final GenreService genreService = new GenreService(new GenreRepositoryImpl());
    private final PublisherService publisherService = new PublisherService(new PublisherRepositoryImpl());
    public TextField titleTextField = new TextField();
    public ComboBox<String> authorComboBox = new ComboBox<>();
    public ComboBox<String> publisherComboBox = new ComboBox<>();
    public ComboBox<String> genreComboBox = new ComboBox<>();
    public TextField lengthTextField = new TextField();
    public Button saveButton = new Button();
    public Button cancelButton = new Button();
    @Getter
    private boolean actionOnForm;
    @Getter
    private boolean save;
    @Getter
    private Book book;

    public void setBook(Book book) {
        this.book = book;
        viewUpdateBook();
    }

    private void viewUpdateBook() {
        titleTextField.setText(book.getTitle());
        authorComboBox.getSelectionModel().select(book.getAuthor().getName());
        publisherComboBox.getSelectionModel().select(book.getPublisher().getTitle());
        genreComboBox.getSelectionModel().select(book.getGenre().getTitle());
        lengthTextField.setText(String.valueOf(book.getLength()));
    }

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
    public void save() {
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
            publisher = publisherService.findByTitle(publisherComboBox.getSelectionModel().getSelectedItem());
            genre = genreService.findByTitle(genreComboBox.getSelectionModel().getSelectedItem());
            int length = Integer.parseInt(lengthTextField.getText());
            if (book == null) {
                book = Book.builder()
                        .title(title)
                        .author(author)
                        .publisher(publisher)
                        .genre(genre)
                        .length(length)
                        .bookCopyIdAndShelf(new TreeMap<>())
                        .build();

                save = bookService.save(book);
            } else {
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setGenre(genre);
                book.setLength(length);

                save = bookService.update(book);
            }
            actionOnForm = true;
            UtilityClass.getStage(saveButton).close();
        } catch (AuthorNotFoundByNameException | EntityNotFoundByTitleException e) {
            MessageBox.WarningBox(e.getMessage()).show();
        }
    }

    @FXML
    public void cancel() {
        UtilityClass.getStage(saveButton).close();
    }
}
