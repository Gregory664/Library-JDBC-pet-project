package org.library.exceptions.newExc;

public class PublisherNotFoundByTitleException extends Exception {
    public PublisherNotFoundByTitleException(String title) {
        super(String.format("Publisher with title = %s not found ", title));
    }
}
