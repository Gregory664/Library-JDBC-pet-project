package org.library.exceptions.newExc;

public class BookCopyNotFoundByIdException extends Exception {
    public BookCopyNotFoundByIdException(int id) {
        super(String.format("Book copy with id = %d not found ", id));
    }
}
