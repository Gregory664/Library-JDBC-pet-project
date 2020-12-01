package org.library.exceptions.newExc;

public class GenreNotFoundByIdException extends Exception {
    public GenreNotFoundByIdException(int id) {
        super(String.format("Genre with id = %d not found ", id));
    }
}
