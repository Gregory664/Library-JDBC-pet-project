package org.library.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lombok.Getter;
import org.library.entity.Shelf;
import org.library.repositories.ShelfRepositoryImpl;
import org.library.services.ShelfService;
import org.library.utils.Utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShelfController {
    private final ShelfService shelfService = new ShelfService(new ShelfRepositoryImpl());
    public ComboBox<String> shelfComboBox = new ComboBox<>();
    public Button saveButton = new Button();
    public Button cancelButton = new Button();
    @Getter
    private Optional<Shelf> selectedShelf = Optional.empty();
    @Getter
    private boolean close;

    @FXML
    public void initialize() {
        List<String> listShelfNumbers = shelfService.findAll().stream()
                .map(Shelf::getInventNum)
                .collect(Collectors.toList());
        shelfComboBox.setItems(FXCollections.observableArrayList(listShelfNumbers));
    }

    @FXML
    public void selectShelf(ActionEvent actionEvent) {
        String selectedShelfNumber = shelfComboBox.getSelectionModel().getSelectedItem();
        selectedShelf = shelfService.findAll().stream()
                .filter(shelf -> shelf.getInventNum().equals(selectedShelfNumber))
                .findFirst();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        Utils.getStage(saveButton).close();
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        close = true;
        Utils.getStage(saveButton).close();
    }
}
