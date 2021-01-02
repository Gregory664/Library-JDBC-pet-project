package org.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Getter;
import org.library.entity.Shelf;
import org.library.repositories.ShelfRepositoryImpl;
import org.library.services.ShelfService;
import org.library.utils.Utils;

public class EditShelfDataController {
    private final ShelfService service = new ShelfService(new ShelfRepositoryImpl());
    public TextArea shelfNumberTextField;
    public Button saveButton;
    public Button cancelButton;
    @Getter
    private boolean save;
    @Getter
    private boolean close;

    @Getter
    private Shelf shelf;

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
        shelfNumberTextField.setText(shelf.getInventNum());
    }

    @FXML
    public void save() {
        if (shelf == null) {
            shelf = new Shelf(shelfNumberTextField.getText());
            save = service.save(shelf);
        } else {
            shelf.setInventNum(shelfNumberTextField.getText());
            save = service.update(shelf);
        }

        Utils.getStage(cancelButton).close();
    }

    @FXML
    public void cancel() {
        close = true;
        Utils.getStage(cancelButton).close();
    }
}
