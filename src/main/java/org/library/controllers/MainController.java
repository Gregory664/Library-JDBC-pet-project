package org.library.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.library.App;
import org.library.entity.*;
import org.library.exceptions.*;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.repositories.*;
import org.library.services.*;
import org.library.utils.MessageBox;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

public class MainController {
    private final BookService bookService = new BookService(new BookShelfRepositoryImpl(), new BookRepositoryImpl());
    private final BookRentService bookRentService = new BookRentService(new BookRentRepositoryImpl(), new BookShelfRepositoryImpl(), new BookCopyRepositoryImpl(), new BookRepositoryImpl());
    private final ReaderService readerService = new ReaderService(new ReaderRepositoryImpl(), new BookRentRepositoryImpl());
    private final BookCopyService bookCopyService = new BookCopyService(new BookRepositoryImpl(), new BookCopyRepositoryImpl(), new BookShelfRepositoryImpl());
    private final BookShelfService bookShelfService = new BookShelfService(new BookShelfRepositoryImpl());

    public TableView<Map.Entry<Integer, Shelf>> shelfView;
    public TableColumn<Map.Entry<Integer, Shelf>, String> shelfViewName;
    public TableColumn<Map.Entry<Integer, Shelf>, Integer> shelfViewBookCopyId;
    public ContextMenu shelfViewContextMenu = new ContextMenu();
    public MenuItem getBookFromShelfMenuItem = new MenuItem();
    public TableView<Book> booksView = new TableView<>();
    public TableColumn<Book, Integer> booksViewId = new TableColumn<>();
    public TableColumn<Book, String> booksViewTitle = new TableColumn<>();
    public TableColumn<Book, String> booksViewAuthor;
    public TableColumn<Book, String> booksViewPublisher;
    public TableColumn<Book, String> booksViewGenre;
    public TableColumn<Book, Integer> booksViewLength = new TableColumn<>();
    public TableColumn<Book, Integer> booksViewCount = new TableColumn<>();

    public TableView<Reader> readerView;
    public TableColumn<Reader, Integer> readerViewID;
    public TableColumn<Reader, String> readerViewFio;
    public TableColumn<Reader, Integer> readerViewAge;
    public TableColumn<Reader, String> readerViewAddress;
    public TableColumn<Reader, String> readerViewPhone;
    public TableColumn<Reader, String> readerViewPassport;

    public TableView<Map.Entry<BookCopy, Period>> rentBookView;
    public TableColumn<Map.Entry<BookCopy, Period>, Integer> rentBookViewCopyId = new TableColumn<>();
    public TableColumn<Map.Entry<BookCopy, Period>, Integer> rentBookViewId = new TableColumn<>();
    public TableColumn<Map.Entry<BookCopy, Period>, String> rentBookViewTitle = new TableColumn<>();
    public TableColumn<Map.Entry<BookCopy, Period>, String> rentBookViewAuthor = new TableColumn<>();
    public TableColumn<Map.Entry<BookCopy, Period>, Date> rentBookViewStartDate = new TableColumn<>();
    public TableColumn<Map.Entry<BookCopy, Period>, Date> rentBookViewEndDate = new TableColumn<>();
    public MenuItem addBookCopyToShelfMenuItem = new MenuItem();
    public MenuItem addBookMenuItem;
    public MenuItem deleteBookMenuItem;
    public MenuItem addBookCopyMenuItem;
    public MenuItem deleteBookCopy;
    public MenuItem updateBookCopyMenuItem;
    public MenuItem addReaderMenuItem;
    public MenuItem updateReaderMenuItem;

    public MainController() {
    }

    @FXML
    public void initialize() {
        initBookViewCellProperties();
        initReaderViewCellProperties();
        getBookFromShelfMenuItem.setDisable(true);
        addBookCopyToShelfMenuItem.setDisable(true);
        initListeners();

        ObservableList<Book> booksList = FXCollections.observableList(bookService.findAll());
        booksView.setItems(booksList);
        booksView.getSelectionModel().selectFirst();

        ObservableList<Reader> readers = FXCollections.observableList(readerService.findAll());
        for (Reader reader : readers) {
            reader.setRentBookCopies(bookRentService.getRentBookCopiesByReaderId(reader.getId()));
        }
        readerView.setItems(readers);
        readerView.getSelectionModel().selectFirst();
    }

    private void initListeners() {
        shelfView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                getBookFromShelfMenuItem.setDisable(newValue == null)
        );

        booksView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillShelfView();
            }
        });

        readerView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillRentBookView();
            }
        });

        rentBookView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                addBookCopyToShelfMenuItem.setDisable(newValue == null)
        );
    }

    private void initBookViewCellProperties() {
        booksViewId.setCellValueFactory(new PropertyValueFactory<>("id"));
        booksViewTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        booksViewAuthor.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public String getValue() {
                return param.getValue().getAuthor().getName();
            }
        });
        booksViewPublisher.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public String getValue() {
                return param.getValue().getPublisher().getTitle();
            }
        });
        booksViewGenre.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public String getValue() {
                return param.getValue().getGenre().getTitle();
            }
        });
        booksViewLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        booksViewCount.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public Integer getValue() {
                return param.getValue().getBookCopyIdAndShelf().size();
            }
        });
        booksViewTitle.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        booksViewAuthor.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        booksViewPublisher.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        booksViewGenre.setMaxWidth(1f * Integer.MAX_VALUE * 25);
    }

    private void initReaderViewCellProperties() {
        readerViewID.setCellValueFactory(new PropertyValueFactory<>("id"));
        readerViewFio.setCellValueFactory(new PropertyValueFactory<>("fio"));
        readerViewAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        readerViewAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        readerViewPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        readerViewPassport.setCellValueFactory(new PropertyValueFactory<>("passport"));

        readerViewFio.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        readerViewAddress.setMaxWidth(1f * Integer.MAX_VALUE * 50);
    }

    private void fillShelfView() {
        Book selectedBook = booksView.getSelectionModel().getSelectedItem();
        shelfViewName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getInventNum()));
        shelfViewBookCopyId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey()));
        ObservableList<Map.Entry<Integer, Shelf>> bookCopyShelfList = FXCollections.observableArrayList(selectedBook.getBookCopyIdAndShelf().entrySet());
        shelfView.setItems(bookCopyShelfList);
    }

    private void fillRentBookView() {
        Reader selectedReader = readerView.getSelectionModel().getSelectedItem();
        rentBookViewCopyId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey().getId()));
        rentBookViewId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey().getBook().getId()));
        rentBookViewTitle.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey().getBook().getTitle()));
        rentBookViewAuthor.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey().getBook().getAuthor().getName()));
        rentBookViewStartDate.setCellValueFactory(param -> new SimpleObjectProperty<>(
                Date.valueOf(param.getValue().getValue().getStartDate())
        ));
        rentBookViewEndDate.setCellValueFactory(param -> new SimpleObjectProperty<>(
                Date.valueOf(param.getValue().getValue().getEndDate())
        ));
        ObservableList<Map.Entry<BookCopy, Period>> rentBooks = FXCollections.observableArrayList(selectedReader.getRentBookCopies().entrySet());
        rentBookView.setItems(rentBooks);
    }

    @FXML
    public void rentBook(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("rent.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            RentController rentController = fxmlLoader.getController();

            stage.showAndWait();
            if (rentController.isClose()) {
                return;
            }

            Optional<Reader> optionalReader = rentController.getSelectedReader();
            if (optionalReader.isPresent()) {
                int selectedBookCopyId = shelfView.getSelectionModel().getSelectedItem().getKey();

                BookCopy bookCopy = bookCopyService.findById(selectedBookCopyId);
                Shelf shelf = shelfView.getSelectionModel().getSelectedItem().getValue();
                Reader reader = optionalReader.get();
                Period period = rentController.getPeriod();
                bookRentService.addRentBookCopiesToReader(reader, bookCopy, period, shelf);

                booksView.getSelectionModel().getSelectedItem().getBookCopyIdAndShelf().remove(selectedBookCopyId);

                Reader changedReader = readerView.getItems().stream()
                        .filter(reader1 -> reader1.getId() == reader.getId())
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
                changedReader.getRentBookCopies().put(bookCopy, period);

                fillShelfView();
                fillRentBookView();
                booksView.refresh();
                booksView.getSelectionModel().selectFirst();
                readerView.refresh();
                MessageBox.OkBox("Книга выдана").show();
            } else {
                MessageBox.WarningBox("Читатель не найден!").show();
            }
        } catch (IOException e) {
            MessageBox.WarningBox("Error of loading form: \n " + e.getMessage()).showAndWait();
        } catch (BookCopyNotFoundException | EntityNotFoundByIdException | BookIsExistsInReaderException | BookNotFoundOnShelfException e) {
            MessageBox.WarningBox(e.getMessage()).show();
        }
    }

    @FXML
    public void returnBook(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("shelf.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            ShelfController shelfController = fxmlLoader.getController();

            stage.showAndWait();
            if (shelfController.isClose()) {
                return;
            }

            Optional<Shelf> optionalShelf = shelfController.getSelectedShelf();
            if (optionalShelf.isPresent()) {
                Reader reader = readerView.getSelectionModel().getSelectedItem();
                BookCopy bookCopy = rentBookView.getSelectionModel().getSelectedItem().getKey();
                Shelf shelf = optionalShelf.get();

                bookRentService.deleteRentBookCopiesFromReader(reader, bookCopy, shelf);

                Book changedBook = booksView.getItems().stream()
                        .filter(book -> book.getId() == bookCopy.getBook().getId())
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
                changedBook.getBookCopyIdAndShelf().put(bookCopy.getId(), shelf);

                fillRentBookView();
                rentBookView.refresh();
                fillShelfView();
                booksView.refresh();
                readerView.getSelectionModel().select(reader);

                MessageBox.OkBox("Книга возвращена").show();
            } else {
                MessageBox.WarningBox("Полки с таким номером не существует!").showAndWait();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (RentBookNotFoundInReader | BookCopyIsExistsInShelfException e) {
            MessageBox.WarningBox(e.getMessage()).show();
        }
    }

    @FXML
    public void addBook(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("book.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            BookController bookController = fxmlLoader.getController();

            stage.showAndWait();
            if (bookController.isClose()) {
                return;
            }

            if (bookController.isSave()) {
                booksView.getItems().add(bookController.getBook());
                MessageBox.OkBox("Книга успешно добавлена!").show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(ActionEvent actionEvent) {
        Book selectedBook = booksView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание");
        alert.setHeaderText("Вы действительно хотите удалить книгу?  \nВсе упоминания о книге будут удалены");

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent()) {
            if (buttonType.get() == ButtonType.OK) {
                bookService.deleteById(selectedBook.getId());

                readerView.getItems().forEach(reader -> reader.getRentBookCopies().entrySet()
                        .removeIf(entry -> entry.getKey().getBook().getId() == selectedBook.getId()));

                booksView.getItems().remove(selectedBook);
            }
        }
    }

    public void addBookCopy(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("shelf.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            ShelfController shelfController = fxmlLoader.getController();

            stage.showAndWait();
            if (shelfController.isClose()) {
                return;
            }

            Optional<Shelf> optionalShelf = shelfController.getSelectedShelf();
            if (optionalShelf.isPresent()) {
                Book selectedBook = booksView.getSelectionModel().getSelectedItem();
                BookCopy bookCopy = BookCopy.builder().book(selectedBook).build();
                bookCopyService.save(bookCopy);
                bookShelfService.addBookCopyToShelf(bookCopy, optionalShelf.get());
                booksView.refresh();
                fillShelfView();
                MessageBox.OkBox("Копия книги успешно добавлена!").show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BookCopyIsExistsInShelfException e) {
            MessageBox.WarningBox(e.getMessage()).show();
        }
    }

    public void deleteBookCopy(ActionEvent actionEvent) {
        Map.Entry<Integer, Shelf> entry = shelfView.getSelectionModel().getSelectedItem();
        Book selectedItem = booksView.getSelectionModel().getSelectedItem();

        if (bookCopyService.deleteById(entry.getKey())) {
            selectedItem.getBookCopyIdAndShelf().remove(entry.getKey());
            fillShelfView();
            booksView.refresh();

            MessageBox.OkBox("Копия успешно удалена!").show();
        } else {
            MessageBox.WarningBox("Ошибка удаления копии книги!").show();
        }
    }

    public void updateBook(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("book.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

            BookController bookController = fxmlLoader.getController();
            bookController.setBook(booksView.getSelectionModel().getSelectedItem());

            stage.showAndWait();
            if (bookController.isClose()) {
                return;
            }

            if (bookController.isSave()) {
                booksView.refresh();
                MessageBox.OkBox("Книга успешно редактирована!").show();
            } else {
                MessageBox.WarningBox("Ошибка редактирования книги").show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addReader(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("reader.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            ReaderController readerController = fxmlLoader.getController();

            stage.showAndWait();
            if (readerController.isClose()) {
                return;
            }

            if (readerController.isSave()) {
                readerView.getItems().add(readerController.getReader());
                MessageBox.OkBox("Читатель успешно добавлен!").show();
            } else {
                MessageBox.WarningBox("Ошибка добавления читателя").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateReader(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("reader.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            ReaderController readerController = fxmlLoader.getController();
            readerController.setReader(readerView.getSelectionModel().getSelectedItem());

            stage.showAndWait();
            if (readerController.isClose()) {
                return;
            }

            if (readerController.isSave()) {
                readerView.refresh();
                MessageBox.OkBox("Читатель успешно редактирован!").show();
            } else {
                MessageBox.WarningBox("Ошибка редактирования читателя");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
