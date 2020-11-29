package org.library.exceptions;

public class BookNotFoundOnShelfException extends RuntimeException {
    public BookNotFoundOnShelfException(int bookId, int shelfId) {
        super(String.format("Book on shelf not fount: book id = %d, shelf id = %d", bookId, shelfId));
    }
}
