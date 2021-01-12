package org.library.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lombok.Getter;
import org.library.entity.Shelf;
import org.library.exceptions.newExc.ShelfNotFoundByInventNumException;
import org.library.repositories.ShelfRepositoryImpl;
import org.library.services.ShelfService;
import org.library.utils.MessageBox;
import org.library.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class ShelfController {
    private final ShelfService shelfService = new ShelfService(new ShelfRepositoryImpl());
    public ComboBox<String> shelfComboBox = new ComboBox<>();
    public Button saveButton = new Button();
    public Button cancelButton = new Button();
    @Getter
    private Shelf selectedShelf;
    @Getter
    private boolean actionOnForm;
    @Getter
    private boolean save;

    public void setSelectedShelf(Shelf selectedShelf) {
        this.selectedShelf = selectedShelf;
        shelfComboBox.getSelectionModel().select(selectedShelf.getInventNum());
    }

    @FXML
    public void initialize() {
        List<String> listShelfNumbers = shelfService.findAll().stream()
                .map(Shelf::getInventNum)
                .collect(Collectors.toList());
        shelfComboBox.setItems(FXCollections.observableArrayList(listShelfNumbers));
    }

    @FXML
    public void save() {
        try {
            String selectedShelfNumber = shelfComboBox.getSelectionModel().getSelectedItem();

            if (selectedShelf == null) {
                selectedShelf = shelfService.findByInventNum(selectedShelfNumber);
            } else {
                Utils.updateShelf(selectedShelf, shelfService.findByInventNum(selectedShelfNumber));
            }
            actionOnForm = true;
            save = true;
            Utils.getStage(saveButton).close();
        } catch (ShelfNotFoundByInventNumException e) {
            MessageBox.WarningBox(e.getMessage()).show();
        }
    }

    @FXML
    public void cancel() {
        Utils.getStage(saveButton).close();
    }
}
