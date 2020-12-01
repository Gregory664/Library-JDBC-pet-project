package org.library.exceptions.newExc;

public class BookNotFoundByTitleException extends Exception {
    public BookNotFoundByTitleException(String title) {
        super(String.format("Book with title = %s not found ", title));
    }
}
