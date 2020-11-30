package org.library.exceptions;

public class AuthorNotFoundByIdException extends Exception {
    public AuthorNotFoundByIdException(Integer id) {
        super(String.format("Author with id = %d not found ", id));
    }
}
