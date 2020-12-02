package org.library.exceptions;

public class RentBookNotFoundInReader extends Exception {
    public RentBookNotFoundInReader(int readerId, int bookId) {
        super(String.format("Rent book (book copy id = %d) not fount in reader (id = %d)", bookId, readerId));
    }
}
