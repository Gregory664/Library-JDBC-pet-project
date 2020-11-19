package org.library.exceptions;

public class BookCopyNotFoundException extends RuntimeException {
    public BookCopyNotFoundException(int bookCopyId) {
        super(String.format("book copy (id = %d) not found in DB", bookCopyId));
    }
}
