package org.library.controllers;

import javafx.event.ActionEvent;
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
    private boolean close;

    @Getter
    private Publisher publisher;

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
        publisherTitleTextField.setText(publisher.getTitle());
    }

    public void save(ActionEvent actionEvent) {
        if (publisher == null) {
            publisher = new Publisher(publisherTitleTextField.getText());
            save = service.save(publisher);
        } else {
            publisher.setTitle(publisherTitleTextField.getText());
            save = service.update(publisher);
        }

        Utils.getStage(cancelButton).close();
    }

    public void cancel(ActionEvent actionEvent) {
        close = true;
        Utils.getStage(cancelButton).close();
    }
}
