package org.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.library.entity.Reader;
import org.library.repositories.BookRentRepositoryImpl;
import org.library.repositories.ReaderRepositoryImpl;
import org.library.services.ReaderService;
import org.library.utils.Utils;

import java.util.TreeMap;

public class ReaderController {
    private final ReaderService readerService = new ReaderService(new ReaderRepositoryImpl(), new BookRentRepositoryImpl());
    public Button saveButton;
    public Button cancelButton;
    public TextField fioTextField;
    public TextField ageTextField;
    public TextField phoneTextField;
    public TextField passportTextField;
    public TextArea addressTextField;
    @Getter
    private boolean close;
    @Getter
    private boolean save;
    @Getter
    private Reader reader;

    @FXML
    public void initialize() {

    }

    public void save(ActionEvent actionEvent) {
        reader = Reader.builder()
                .fio(fioTextField.getText())
                .age(Integer.parseInt(ageTextField.getText()))
                .address(addressTextField.getText())
                .passport(passportTextField.getText())
                .phone(phoneTextField.getText())
                .rentBookCopies(new TreeMap<>())
                .build();

        save = readerService.save(reader);
        Utils.getStage(saveButton).close();
    }

    public void close(ActionEvent actionEvent) {
        close = true;
        Utils.getStage(saveButton).close();
    }

    public void setReader(Reader reader) {
        this.reader = reader;
        viewReaderForUpdate();
    }

    private void viewReaderForUpdate() {
        fioTextField.setText(reader.getFio());
        ageTextField.setText(String.valueOf(reader.getAge()));
        phoneTextField.setText(reader.getPhone());
        passportTextField.setText(reader.getPassport());
        addressTextField.setText(reader.getAddress());
    }
}
