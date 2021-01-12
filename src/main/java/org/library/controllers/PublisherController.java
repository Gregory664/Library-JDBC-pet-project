package org.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import lombok.Getter;
import org.library.entity.Publisher;
import org.library.repositories.PublisherRepositoryImpl;
import org.library.services.PublisherService;
import org.library.utils.Utils;

public class PublisherController {
    private final PublisherService service = new PublisherService(new PublisherRepositoryImpl());
    public TextArea publisherTitleTextField;
    public Button saveButton;
    public Button cancelButton;
    @Getter
    private boolean save;
    @Getter
    private boolean actionOnForm;

    @Getter
    private Publisher publisher;

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
        publisherTitleTextField.setText(publisher.getTitle());
    }

    @FXML
    public void save() {
        if (publisher == null) {
            publisher = new Publisher(publisherTitleTextField.getText());
            save = service.save(publisher);
        } else {
            publisher.setTitle(publisherTitleTextField.getText());
            save = service.update(publisher);
        }
        actionOnForm = true;
        Utils.getStage(cancelButton).close();
    }

    @FXML
    public void cancel() {
        Utils.getStage(cancelButton).close();
    }
}
