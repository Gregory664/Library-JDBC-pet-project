package org.library.exceptions;

public class AuthorNotFoundByNameException extends Exception{
    public AuthorNotFoundByNameException(String name) {
        super(String.format("Author with name = %s not found ", name));
    }
}
