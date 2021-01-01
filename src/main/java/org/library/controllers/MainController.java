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
import javafx.util.StringConverter;
import org.library.App;
import org.library.entity.*;
import org.library.exceptions.*;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.repositories.*;
import org.library.services.*;
import org.library.utils.MessageBox;
import org.library.utils.Utils;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MainController {
    private final BookService bookService = new BookService(new BookShelfRepositoryImpl(), new BookRepositoryImpl());
    private final BookRentService bookRentService = new BookRentService(new BookRentRepositoryImpl(), new BookShelfRepositoryImpl(), new BookCopyRepositoryImpl(), new BookRepositoryImpl());
    private final ReaderService readerService = new ReaderService(new ReaderRepositoryImpl(), new BookRentRepositoryImpl());
    private final BookCopyService bookCopyService = new BookCopyService(new BookRepositoryImpl(), new BookCopyRepositoryImpl(), new BookShelfRepositoryImpl());
    private final BookShelfService bookShelfService = new BookShelfService(new BookShelfRepositoryImpl());
    private final AuthorService authorService = new AuthorService(new AuthorRepositoryImpl());
    private final GenreService genreService = new GenreService(new GenreRepositoryImpl());
    private final PublisherService publisherService = new PublisherService(new PublisherRepositoryImpl());
    private final ShelfService shelfService = new ShelfService(new ShelfRepositoryImpl());

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
    public TableColumn<Reader, String> readerViewGender;
    public TableColumn<Reader, Date> readerViewAgeDOB;

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
    public MenuItem deleteReaderMenuItem;
    public MenuItem editShelfInBookCopyMenuItem;

    public TabPane tabPane;
    public Tab booksTab;
    public Tab readersTab;
    public Tab editDataTab;
    public Tab statisticsTab;

    public TableView<Author> authorsView;
    public TableColumn<Author, Integer> authorViewId;
    public TableColumn<Author, String> authorViewFIO;

    public TableView<Genre> genresView;
    public TableColumn<Genre, Integer> genresViewId;
    public TableColumn<Genre, String> genresViewTitle;

    public TableView<Publisher> publishersView;
    public TableColumn<Publisher, Integer> publishersViewId;
    public TableColumn<Publisher, String> publishersViewTitle;

    public TableView<Shelf> shelfEditDataView;
    public TableColumn<Shelf, Integer> shelfEditDataViewId;
    public TableColumn<Shelf, String> shelfEditDataViewNumber;
    public CheckBox searchTitleCheckBox;
    public CheckBox searchAuthorCheckBox;
    public CheckBox searchPublisherCheckBox;
    public CheckBox searchGenreCheckBox;
    public TextField searchTitleTextField;
    public ComboBox<Author> searchAuthorComboBox;
    public ComboBox<Publisher> searchPublisherComboBox;
    public ComboBox<Genre> searchGenreComboBox;
    public Button searchButton;
    public Button refreshButton;
    public CheckBox debtorsCheckBox;

    public MainController() {
    }

    @FXML
    public void initialize() {
        initBookViewCellProperties();
        initReaderViewCellProperties();
        initAuthorsViewCellProperties();
        initGenresViewCellProperties();
        initPublishersViewCellProperties();
        initShelfEditViewCellProperties();
        initSearchComboBoxes();

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

        authorsView.setItems(FXCollections.observableArrayList(authorService.findAll()));
        genresView.setItems(FXCollections.observableArrayList(genreService.findAll()));
        publishersView.setItems(FXCollections.observableArrayList(publisherService.findAll()));
        shelfEditDataView.setItems(FXCollections.observableArrayList(shelfService.findAll()));

        tabPane.getSelectionModel().select(booksTab);
    }

    private void initSearchComboBoxes() {
        searchAuthorComboBox.setItems(FXCollections.observableArrayList(authorService.findAll()));
        searchAuthorComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Author object) {
                return object != null ? object.getName() : "";
            }

            @Override
            public Author fromString(String string) {
                return searchAuthorComboBox.getItems().stream()
                        .filter(author -> author.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        searchPublisherComboBox.setItems(FXCollections.observableArrayList(publisherService.findAll()));
        searchPublisherComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Publisher object) {
                return object != null ? object.getTitle() : "";
            }

            @Override
            public Publisher fromString(String string) {
                return searchPublisherComboBox.getItems().stream()
                        .filter(publisher -> publisher.getTitle().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        searchGenreComboBox.setItems(FXCollections.observableArrayList(genreService.findAll()));
        searchGenreComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Genre object) {
                return object != null ? object.getTitle() : "";
            }

            @Override
            public Genre fromString(String string) {
                return searchGenreComboBox.getItems().stream()
                        .filter(Genre -> Genre.getTitle().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

    private void initAuthorsViewCellProperties() {
        authorViewId.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorViewFIO.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void initGenresViewCellProperties() {
        genresViewId.setCellValueFactory(new PropertyValueFactory<>("id"));
        genresViewTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    private void initPublishersViewCellProperties() {
        publishersViewId.setCellValueFactory(new PropertyValueFactory<>("id"));
        publishersViewTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    private void initShelfEditViewCellProperties() {
        shelfEditDataViewId.setCellValueFactory(new PropertyValueFactory<>("id"));
        shelfEditDataViewNumber.setCellValueFactory(new PropertyValueFactory<>("inventNum"));
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
        readerViewGender.setCellValueFactory(param -> new ObservableValueBase<>() {
            @Override
            public String getValue() {
                return param.getValue().getGender().name();
            }
        });
        readerViewAgeDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));

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


            if (shelfController.isSave()) {
                Reader reader = readerView.getSelectionModel().getSelectedItem();
                BookCopy bookCopy = rentBookView.getSelectionModel().getSelectedItem().getKey();
                Shelf shelf = shelfController.getSelectedShelf();

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
        alert.setHeaderText("Вы действительно хотите удалить книгу?  \nВсе упоминания о книге будут удалены!");

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent()) {
            if (buttonType.get() == ButtonType.OK) {
                bookService.deleteById(selectedBook.getId());

                readerView.getItems().forEach(reader -> reader.getRentBookCopies().entrySet()
                        .removeIf(entry -> entry.getKey().getBook().getId() == selectedBook.getId()));

                booksView.getItems().remove(selectedBook);
                MessageBox.OkBox("Книга успешно удалена!").show();
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

            if (shelfController.isSave()) {
                Book selectedBook = booksView.getSelectionModel().getSelectedItem();
                BookCopy bookCopy = BookCopy.builder().book(selectedBook).build();
                bookCopyService.save(bookCopy);
                bookShelfService.addBookCopyToShelf(bookCopy, shelfController.getSelectedShelf());
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

    public void editBookCopyShelf(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("shelf.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

            int bookCopyId = shelfView.getSelectionModel().getSelectedItem().getKey();
            Shelf currentShelf = booksView.getSelectionModel().getSelectedItem().getBookCopyIdAndShelf().get(bookCopyId);
            int currentShelfId = currentShelf.getId();

            ShelfController shelfController = fxmlLoader.getController();
            shelfController.setSelectedShelf(currentShelf);

            stage.showAndWait();
            if (shelfController.isClose()) {
                return;
            }

            if (shelfController.isSave()) {
                if (bookShelfService.updateShelf(currentShelfId, bookCopyId, currentShelf.getId())) {
                    booksView.refresh();
                    shelfView.refresh();
                    MessageBox.OkBox("Номер полки успешно обновлен!").show();
                } else {
                    MessageBox.WarningBox("Ошибка обновления номера полки!").show();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
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
            Book selectedBook = booksView.getSelectionModel().getSelectedItem();
            bookController.setBook(selectedBook);

            stage.showAndWait();
            if (bookController.isClose()) {
                return;
            }

            if (bookController.isSave()) {
                booksView.refresh();

                for (Reader reader : readerView.getItems()) {
                    reader.getRentBookCopies().keySet().stream()
                            .filter(bookCopy -> bookCopy.getBook().getId() == selectedBook.getId())
                            .forEach(bookCopy -> Utils.updateBook(bookCopy.getBook(), selectedBook));
                }
                readerView.refresh();

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

    public void deleteReader(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание");
        alert.setHeaderText("Вы действительно хотите удалить читателя?  \nВсе упоминания о читателе будут удалены!");

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            if (buttonType.get() == ButtonType.OK) {
                Reader reader = readerView.getSelectionModel().getSelectedItem();
                readerService.deleteById(reader.getId());
                readerView.getItems().remove(reader);
                readerView.refresh();
                MessageBox.OkBox("Удаление читателя выполнено успешно!").show();
            }
        }
    }

    @FXML
    public void showBooksTab(ActionEvent actionEvent) {
        tabPane.getSelectionModel().select(booksTab);
    }

    @FXML
    public void showReadersTab(ActionEvent actionEvent) {
        tabPane.getSelectionModel().select(readersTab);
    }

    public void showEditData(ActionEvent actionEvent) {
        tabPane.getSelectionModel().select(editDataTab);
    }

    public void showStatistics(ActionEvent actionEvent) {
        tabPane.getSelectionModel().select(statisticsTab);
    }

    public void addAuthor(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("author.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AuthorController authorController = loader.getController();

            stage.showAndWait();
            if (authorController.isClose()) {
                return;
            }

            if (authorController.isSave()) {
                authorsView.getItems().add(authorController.getAuthor());
                searchAuthorComboBox.getItems().add(authorController.getAuthor());
                authorsView.refresh();
                MessageBox.OkBox("Автор успешно добавлен!").show();
            } else {
                MessageBox.WarningBox("Ошибка добавления автора").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editAuthor(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("author.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            AuthorController authorController = loader.getController();
            Author selectedAuthor = authorsView.getSelectionModel().getSelectedItem();
            authorController.setAuthor(selectedAuthor);

            stage.showAndWait();
            if (authorController.isClose()) {
                return;
            }

            if (authorController.isSave()) {
                authorsView.refresh();

                booksView.getItems().stream()
                        .filter(book -> book.getAuthor().getId() == selectedAuthor.getId())
                        .forEach(book -> book.getAuthor().setName(selectedAuthor.getName()));
                booksView.refresh();

                for (Reader reader : readerView.getItems()) {
                    reader.getRentBookCopies().keySet().stream()
                            .filter(bookCopy -> bookCopy.getBook().getAuthor().getId() == selectedAuthor.getId())
                            .forEach(bookCopy -> bookCopy.getBook().getAuthor().setName(selectedAuthor.getName()));
                }
                readerView.refresh();

                searchAuthorComboBox.getItems().stream()
                        .filter(author -> author.getId() == selectedAuthor.getId())
                        .forEach(author -> author.setName(selectedAuthor.getName()));

                MessageBox.OkBox("Автор успешно редактирован!").show();
            } else {
                MessageBox.WarningBox("Ошибка редактирования автора").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAuthor(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание");
        alert.setHeaderText("Вы действительно хотите удалить автора?  \nВсе упоминания о авторе будут удалены!");

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            if (buttonType.get() == ButtonType.OK) {
                Author authorForDelete = authorsView.getSelectionModel().getSelectedItem();
                authorService.deleteById(authorForDelete.getId());
                authorsView.getItems().remove(authorForDelete);

                booksView.getItems().stream()
                        .filter(book -> book.getAuthor().equals(authorForDelete))
                        .forEach(book -> book.setAuthor(new Author(-1, "Нет данных")));
                booksView.refresh();

                for (Reader reader : readerView.getItems()) {
                    reader.getRentBookCopies().keySet().stream()
                            .filter(bookCopy -> bookCopy.getBook().getAuthor().equals(authorForDelete))
                            .forEach(bookCopy -> bookCopy.getBook().setAuthor(new Author(-1, "Нет данных")));
                }
                searchAuthorComboBox.getItems().remove(authorForDelete);

                MessageBox.OkBox("Удаление автора выполнено успешно!").show();
            }
        }
    }

    public void addGenre(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("genre.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            GenreController genreController = loader.getController();

            stage.showAndWait();
            if (genreController.isClose()) {
                return;
            }

            if (genreController.isSave()) {
                genresView.getItems().add(genreController.getGenre());
                genresView.refresh();
                searchGenreComboBox.getItems().add(genreController.getGenre());
                MessageBox.OkBox("Жанр успешно добавлен!").show();
            } else {
                MessageBox.WarningBox("Ошибка добавления жанра").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editGenre(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("genre.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            GenreController genreController = loader.getController();
            Genre selectedGenre = genresView.getSelectionModel().getSelectedItem();
            genreController.setGenre(selectedGenre);

            stage.showAndWait();
            if (genreController.isClose()) {
                return;
            }

            if (genreController.isSave()) {
                genresView.refresh();

                booksView.getItems().stream()
                        .filter(book -> book.getGenre().getId() == selectedGenre.getId())
                        .forEach(book -> book.getGenre().setTitle(selectedGenre.getTitle()));
                booksView.refresh();

                searchGenreComboBox.getItems().stream()
                        .filter(genre -> genre.getId() == selectedGenre.getId())
                        .forEach(genre -> genre.setTitle(selectedGenre.getTitle()));

                MessageBox.OkBox("Жанр успешно обновлен!").show();
            } else {
                MessageBox.WarningBox("Ошибка редактирования жанра").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteGenre(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание");
        alert.setHeaderText("Вы действительно хотите удалить жанр?  \nВсе упоминания о жанре будут удалены!");

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            if (buttonType.get() == ButtonType.OK) {
                Genre genreForDelete = genresView.getSelectionModel().getSelectedItem();
                genreService.deleteById(genreForDelete.getId());
                genresView.getItems().remove(genreForDelete);

                booksView.getItems().stream()
                        .filter(book -> book.getGenre().equals(genreForDelete))
                        .forEach(book -> book.setGenre(new Genre(-1, "Нет данных")));
                booksView.refresh();

                for (Reader reader : readerView.getItems()) {
                    reader.getRentBookCopies().keySet().stream()
                            .filter(bookCopy -> bookCopy.getBook().getGenre().equals(genreForDelete))
                            .forEach(bookCopy -> bookCopy.getBook().setGenre(new Genre(-1, "Нет данных")));
                }
                searchGenreComboBox.getItems().remove(genreForDelete);

                MessageBox.OkBox("Удаление жанра выполнено успешно!").show();
            }
        }
    }

    public void addPublisher(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("publisher.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            PublisherController publisherController = loader.getController();

            stage.showAndWait();
            if (publisherController.isClose()) {
                return;
            }

            if (publisherController.isSave()) {
                publishersView.getItems().add(publisherController.getPublisher());
                publishersView.refresh();
                searchPublisherComboBox.getItems().add(publisherController.getPublisher());
                MessageBox.OkBox("Издатель успешно добавлен!").show();
            } else {
                MessageBox.WarningBox("Ошибка добавления издателя").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editPublisher(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("publisher.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            PublisherController publisherController = loader.getController();
            Publisher selectedPublisher = publishersView.getSelectionModel().getSelectedItem();
            publisherController.setPublisher(selectedPublisher);

            stage.showAndWait();
            if (publisherController.isClose()) {
                return;
            }

            if (publisherController.isSave()) {
                publishersView.refresh();

                booksView.getItems().stream()
                        .filter(book -> book.getPublisher().getId() == selectedPublisher.getId())
                        .forEach(book -> book.getPublisher().setTitle(selectedPublisher.getTitle()));
                booksView.refresh();

                searchPublisherComboBox.getItems().stream()
                        .filter(publisher -> publisher.getId() == selectedPublisher.getId())
                        .forEach(publisher -> publisher.setTitle(selectedPublisher.getTitle()));

                MessageBox.OkBox("Издатель успешно обновлен!").show();
            } else {
                MessageBox.WarningBox("Ошибка редактирования издателя").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePublisher(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание");
        alert.setHeaderText("Вы действительно хотите удалить издателя?  \nВсе упоминания о издателе будут удалены!");

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            if (buttonType.get() == ButtonType.OK) {
                Publisher publisherForDelete = publishersView.getSelectionModel().getSelectedItem();
                publisherService.deleteById(publisherForDelete.getId());
                publishersView.getItems().remove(publisherForDelete);

                booksView.getItems().stream()
                        .filter(book -> book.getPublisher().equals(publisherForDelete))
                        .forEach(book -> book.setPublisher(new Publisher(-1, "Нет данных")));
                booksView.refresh();

                for (Reader reader : readerView.getItems()) {
                    reader.getRentBookCopies().keySet().stream()
                            .filter(bookCopy -> bookCopy.getBook().getPublisher().equals(publisherForDelete))
                            .forEach(bookCopy -> bookCopy.getBook().setPublisher(new Publisher(-1, "Нет данных")));
                }
                searchPublisherComboBox.getItems().remove(publisherForDelete);

                MessageBox.OkBox("Удаление издателя выполнено успешно!").show();
            }
        }
    }

    public void addShelf(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editShelfData.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            EditShelfDataController editShelfDataController = loader.getController();

            stage.showAndWait();
            if (editShelfDataController.isClose()) {
                return;
            }

            if (editShelfDataController.isSave()) {
                shelfEditDataView.getItems().add(editShelfDataController.getShelf());
                shelfEditDataView.refresh();
                MessageBox.OkBox("Номер полки успешно добавлен!").show();
            } else {
                MessageBox.WarningBox("Ошибка добавления номера полки").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editShelf(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editShelfData.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            EditShelfDataController editShelfDataController = loader.getController();
            Shelf selectedShelf = shelfEditDataView.getSelectionModel().getSelectedItem();
            editShelfDataController.setShelf(selectedShelf);

            stage.showAndWait();
            if (editShelfDataController.isClose()) {
                return;
            }

            if (editShelfDataController.isSave()) {
                shelfEditDataView.refresh();

                for (Book book : booksView.getItems()) {
                    book.getBookCopyIdAndShelf().values().stream()
                            .filter(shelf -> shelf.getId() == selectedShelf.getId())
                            .forEach(shelf -> shelf.setInventNum(selectedShelf.getInventNum()));
                }
                booksView.refresh();
                shelfView.refresh();

                MessageBox.OkBox("Номер полки успешно обновлен!").show();
            } else {
                MessageBox.WarningBox("Ошибка редактирования номера полки").show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteShelf(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание");
        alert.setHeaderText("Вы действительно хотите удалить полку?  \nВсе упоминания о полке будут удалены!");

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent()) {
            if (buttonType.get() == ButtonType.OK) {
                Shelf shelfForDelete = shelfEditDataView.getSelectionModel().getSelectedItem();
                shelfService.deleteById(shelfForDelete.getId());
                shelfEditDataView.getItems().remove(shelfForDelete);

                for (Book book : booksView.getItems()) {
                    book.getBookCopyIdAndShelf().values().stream()
                            .filter(shelf -> shelf.getId() == shelfForDelete.getId())
                            .forEach(Utils::resetShelf);
                }
                booksView.refresh();

                MessageBox.OkBox("Удаление полки выполнено успешно!").show();
            }
        }
    }

    public void checkSearchTitle(ActionEvent actionEvent) {
        searchTitleTextField.setDisable(!searchTitleCheckBox.isSelected());
        checkSearchButton();
    }

    public void checkSearchAuthor(ActionEvent actionEvent) {
        searchAuthorComboBox.setDisable(!searchAuthorCheckBox.isSelected());
        checkSearchButton();
    }

    public void checkSearchPublisher(ActionEvent actionEvent) {
        searchPublisherComboBox.setDisable(!searchPublisherCheckBox.isSelected());
        checkSearchButton();
    }

    public void checkSearchGenre(ActionEvent actionEvent) {
        searchGenreComboBox.setDisable(!searchGenreCheckBox.isSelected());
        checkSearchButton();
    }

    private void checkSearchButton() {
        searchButton.setDisable(!(searchTitleCheckBox.isSelected() ||
                searchAuthorCheckBox.isSelected() ||
                searchPublisherCheckBox.isSelected() ||
                searchGenreCheckBox.isSelected())
        );
    }

    public void searchBooks(ActionEvent actionEvent) {
        booksView.setItems(FXCollections.observableArrayList(bookService.findAll()));
        String title = "";
        Author author = null;
        Publisher publisher = null;
        Genre genre = null;

        if (searchTitleCheckBox.isSelected()) {
            title = searchTitleTextField.getText();
        }

        if (searchAuthorCheckBox.isSelected()) {
            author = searchAuthorComboBox.getValue();
        }

        if (searchPublisherCheckBox.isSelected()) {
            publisher = searchPublisherComboBox.getValue();
        }

        if (searchGenreCheckBox.isSelected()) {
            genre = searchGenreComboBox.getValue();
        }

        booksView.setItems(FXCollections.observableArrayList(bookService.findByParams(title, author, genre, publisher)));
    }

    public void refreshBooks() {
        searchTitleCheckBox.setSelected(false);
        searchAuthorCheckBox.setSelected(false);
        searchGenreCheckBox.setSelected(false);
        searchPublisherCheckBox.setSelected(false);

        searchTitleTextField.clear();
        searchTitleTextField.setDisable(true);
        searchAuthorComboBox.setDisable(true);
        searchGenreComboBox.setDisable(true);
        searchPublisherComboBox.setDisable(true);

        booksView.setItems(FXCollections.observableArrayList(bookService.findAll()));
    }

    public void showDebtors(ActionEvent actionEvent) {
        if (debtorsCheckBox.isSelected()) {
            List<Reader> forDelete = new ArrayList<>();
            for (Reader reader : readerView.getItems()) {
                Map<BookCopy, Period> rentBookCopies = reader.getRentBookCopies();
                boolean isDebt = rentBookCopies.values().stream()
                        .allMatch(period -> ChronoUnit.DAYS.between(period.getEndDate(), LocalDate.now()) <= 0);
                if (isDebt) {
                    forDelete.add(reader);
                }
            }
            readerView.getItems().removeAll(forDelete);
        } else {
            ObservableList<Reader> readers = FXCollections.observableList(readerService.findAll());
            for (Reader reader : readers) {
                reader.setRentBookCopies(bookRentService.getRentBookCopiesByReaderId(reader.getId()));
            }
            readerView.setItems(readers);
        }
    }
}
