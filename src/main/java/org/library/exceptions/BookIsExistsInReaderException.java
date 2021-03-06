package org.library.exceptions;

public class BookIsExistsInReaderException extends Exception {
    public BookIsExistsInReaderException(int bookCopyId, int readerId) {
        super(String.format("Book (copyId = %d) is already exists in reader (reader id = %d)", bookCopyId, readerId));
    }
}
