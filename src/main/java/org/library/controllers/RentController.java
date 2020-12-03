package org.library.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Getter;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.exceptions.ReaderNotFoundByPassportException;
import org.library.repositories.BookRentRepositoryImpl;
import org.library.repositories.ReaderRepositoryImpl;
import org.library.services.ReaderService;
import org.library.utils.MessageBox;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RentController {
    private final ReaderService readerService = new ReaderService(new ReaderRepositoryImpl(), new BookRentRepositoryImpl());
    public Label fioLabel = new Label();
    public TextField timeCountText = new TextField();
    public ComboBox<String> timeComboBox = new ComboBox<>();
    public Button saveButton = new Button();
    public TextField passportTextField;
    private List<Reader> readers;
    @Getter
    private Optional<Reader> selectedReader = Optional.empty();
    @Getter
    private Period period;
    @Getter
    private boolean close;

    @FXML
    public void initialize() {
        readers = readerService.findAll();
        fioLabel.setText("");
        timeComboBox.setItems(FXCollections.observableArrayList("День", "Месяц"));
        timeComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void saveRentBook(ActionEvent actionEvent) {
        if (selectedReader.isEmpty()) {
            throw new ReaderNotFoundByPassportException(fioLabel);
        }

        if (timeCountText.getText().trim().equals("")) {
            MessageBox.WarningBox("Введите интервал!").showAndWait();
            return;
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate finalDate = null;

        switch (timeComboBox.getSelectionModel().getSelectedItem()) {
            case "День":
                finalDate = currentDate.plusDays(Long.parseLong(timeCountText.getText()));
                break;
            case "Месяц":
                finalDate = currentDate.plusMonths(Long.parseLong(timeCountText.getText()));
                break;
        }

        period = new Period(currentDate, finalDate);

        Stage stage = (Stage) timeCountText.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        close = true;
        Stage stage = (Stage) timeCountText.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void searchReader(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            selectedReader = readers.stream()
                    .filter(reader -> reader.getPassport().contains(passportTextField.getText()))
                    .findAny();
            selectedReader.ifPresent(reader -> fioLabel.setText(reader.getFio()));
        }
    }
}
