package org.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import org.library.entity.Reader;
import org.library.repositories.BookRentRepositoryImpl;
import org.library.repositories.ReaderRepositoryImpl;
import org.library.services.ReaderService;
import org.library.utils.Gender;
import org.library.utils.Utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
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
    public ComboBox<String> genderComboBox;
    public DatePicker dobDatePicker;
    @Getter
    private boolean close;
    @Getter
    private boolean save;
    @Getter
    private Reader reader;

    @FXML
    public void initialize() {
        Arrays.stream(Gender.values()).forEach(gender -> genderComboBox.getItems().add(gender.name()));
    }

    @FXML
    public void save() {
        if (reader == null) {
            reader = Reader.builder()
                    .fio(fioTextField.getText())
                    .age(Integer.parseInt(ageTextField.getText()))
                    .address(addressTextField.getText())
                    .passport(passportTextField.getText())
                    .phone(phoneTextField.getText())
                    .gender(Gender.valueOf(genderComboBox.getSelectionModel().getSelectedItem()))
                    .DOB(Date.valueOf(dobDatePicker.getValue()))
                    .rentBookCopies(new TreeMap<>())
                    .build();

            save = readerService.save(reader);
        } else {
            reader.setFio(fioTextField.getText());
            reader.setAge(Integer.parseInt(ageTextField.getText()));
            reader.setAddress(addressTextField.getText());
            reader.setPhone(phoneTextField.getText());
            reader.setPassport(passportTextField.getText());
            reader.setGender(Gender.valueOf(genderComboBox.getSelectionModel().getSelectedItem()));
            reader.setDOB(Date.valueOf(dobDatePicker.getValue()));

            save = readerService.update(reader);
        }
        Utils.getStage(saveButton).close();
    }

    @FXML
    public void close() {
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
        genderComboBox.getSelectionModel().select(reader.getGender().name());
        dobDatePicker.setValue(LocalDate.from(reader.getDOB().toLocalDate()));
    }

    @FXML
    public void calcAge() {
        ageTextField.setText("" + ChronoUnit.YEARS.between(dobDatePicker.getValue(), LocalDate.now()));
    }
}
