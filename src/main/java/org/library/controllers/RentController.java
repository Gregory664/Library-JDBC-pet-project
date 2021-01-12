package org.library.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.repositories.BookRentRepositoryImpl;
import org.library.repositories.ReaderRepositoryImpl;
import org.library.services.ReaderService;
import org.library.utils.MessageBox;
import org.library.utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class RentController {
    private final ReaderService readerService = new ReaderService(new ReaderRepositoryImpl(), new BookRentRepositoryImpl());
    public Label fioLabel = new Label();
    public TextField timeCountText = new TextField();
    public ComboBox<String> timeComboBox = new ComboBox<>();
    public Button saveButton = new Button();
    public TextField passportTextField;
    @Getter
    private Reader selectedReader = null;
    @Getter
    private Period period;
    @Getter
    private boolean actionOnForm;
    @Getter
    private boolean save;

    @FXML
    public void initialize() {
        fioLabel.setText("");
        timeComboBox.setItems(FXCollections.observableArrayList("День", "Месяц"));
        timeComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void saveRentBook() {
        if (selectedReader == null) {
            MessageBox.WarningBox("Читатель не найден!").show();
            return;
        }

        if (timeCountText.getText().trim().equals("")) {
            MessageBox.WarningBox("Введите интервал!").show();
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
        save = selectedReader != null;
        actionOnForm = true;
        Utils.getStage(saveButton).close();
    }

    @FXML
    public void cancel() {
        Utils.getStage(saveButton).close();
    }

    @FXML
    public void searchReader(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            List<Reader> byPassport = readerService.findByParams("", "", passportTextField.getText());
            if (byPassport != null && byPassport.size() != 0) {
                selectedReader = byPassport.get(0);
                fioLabel.setText(selectedReader.getFio());
            }
        }
    }
}
