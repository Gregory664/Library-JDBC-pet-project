package org.library.exceptions.newExc;

public class GenreNotFoundByTitleException extends Exception {
    public GenreNotFoundByTitleException(String title) {
        super(String.format("Genre with title = %s not found ", title));
    }
}
