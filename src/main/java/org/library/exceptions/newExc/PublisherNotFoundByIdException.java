package org.library.exceptions.newExc;

public class PublisherNotFoundByIdException extends Exception {
    public PublisherNotFoundByIdException(int id) {
        super(String.format("Publisher with id = %d not found ", id));
    }
}
