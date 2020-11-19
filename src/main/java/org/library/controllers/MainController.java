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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.library.App;
import org.library.entity.*;
import org.library.exceptions.BookCopyNotFoundException;
import org.library.services.BookCopyService;
import org.library.services.BookRentService;
import org.library.services.BookService;
import org.library.services.ReaderService;
import org.library.utils.MessageBox;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

public class MainController {
    private final BookService bookService = new BookService();
    private final BookRentService bookRentService = new BookRentService();
    private final ReaderService readerService = new ReaderService();
    private final BookCopyService bookCopyService = new BookCopyService();

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

    public TableView<Map.Entry<Book, Period>> rentBookView;
    public TableColumn<Map.Entry<Book, Period>, Integer> rentBookViewId = new TableColumn<>();
    public TableColumn<Map.Entry<Book, Period>, String> rentBookViewTitle = new TableColumn<>();
    public TableColumn<Map.Entry<Book, Period>, String> rentBookViewAuthor = new TableColumn<>();
    public TableColumn<Map.Entry<Book, Period>, Date> rentBookViewStartDate = new TableColumn<>();
    public TableColumn<Map.Entry<Book, Period>, Date> rentBookViewEndDate = new TableColumn<>();

    public MainController() {
    }

    @FXML
    public void initialize() {
        initBookViewCellProperties();
        initReaderViewCellProperties();
        getBookFromShelfMenuItem.setDisable(true);
        initListeners();

        ObservableList<Book> booksList = FXCollections.observableList(bookService.findAll());
        booksView.setItems(booksList);

//        readers = FXCollections.observableList(readerService.findAll());
//        readerView.setItems(readers);
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
//        Reader selectedReader = readerView.getSelectionModel().getSelectedItem();
//        rentBookViewId.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey().getId()));
//        rentBookViewTitle.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey().getTitle()));
//        rentBookViewAuthor.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getKey().getAuthor().getName()));
//        rentBookViewStartDate.setCellValueFactory(param -> new SimpleObjectProperty<>(
//                Date.valueOf(param.getValue().getValue().getStartDate())
//        ));
//        rentBooks = FXCollections.observableArrayList(selectedReader.getRentBooks().entrySet());
//        rentBookView.setItems(rentBooks);
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

                BookCopy bookCopy = bookCopyService.findById(selectedBookCopyId).orElseThrow(() -> new BookCopyNotFoundException(selectedBookCopyId));
                Shelf shelf = shelfView.getSelectionModel().getSelectedItem().getValue();
                Reader reader = optionalReader.get();
                Period period = rentController.getPeriod();
                bookRentService.addRentBookCopiesToReader(reader, bookCopy, period, shelf);

                booksView.getSelectionModel().getSelectedItem().getBookCopyIdAndShelf().remove(selectedBookCopyId);

                fillShelfView();
                booksView.refresh();
                booksView.getSelectionModel().selectFirst();
                MessageBox.OkBox("Книга выдана").show();
            } else {
                MessageBox.WarningBox("Читатель не найден!").showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BookCopyNotFoundException e) {
            MessageBox.WarningBox(e.getMessage());
        }
    }
}
