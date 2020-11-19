package org.library.exceptions;

public class BookIsExistsInShelfException extends RuntimeException {
    public BookIsExistsInShelfException(int bookCopyId, String inventNum) {
        super(String.format("Book (copyId = %d) is already exists in shelf (invent number = %S)", bookCopyId, inventNum));
    }
}
