package org.library.exceptions;

public class BookNotFound extends RuntimeException {
    public BookNotFound() {
        super("Book not found");
    }
}
