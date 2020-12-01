package org.library.exceptions.newExc;

public class AuthorNotFoundByIdException extends Exception {
    public AuthorNotFoundByIdException(int id) {
        super(String.format("Author with id = %d not found ", id));
    }
}
