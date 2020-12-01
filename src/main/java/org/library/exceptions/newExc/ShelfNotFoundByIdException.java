package org.library.exceptions.newExc;

public class ShelfNotFoundByIdException extends Exception {
    public ShelfNotFoundByIdException(int id) {
        super(String.format("Author with id = %d not found ", id));
    }
}
