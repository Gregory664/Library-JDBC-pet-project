package org.library.exceptions.newExc;

public class ShelfNotFoundByInventNumException extends Exception {
    public ShelfNotFoundByInventNumException(String inventNum) {
        super(String.format("Shelf with invent number = %s not found ", inventNum));
    }
}
