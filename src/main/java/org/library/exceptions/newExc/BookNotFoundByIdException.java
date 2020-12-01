package org.library.exceptions.newExc;

public class BookNotFoundByIdException extends Exception {
    public BookNotFoundByIdException(Integer id) {
        super(String.format("Book with id = %d not found ", id));
    }
}
