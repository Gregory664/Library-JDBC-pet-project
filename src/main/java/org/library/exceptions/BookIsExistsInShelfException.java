package org.library.exceptions;

public class BookIsExistsInShelfException extends Exception {
    public BookIsExistsInShelfException(int bookCopyId, String inventNum) {
        super(String.format("Book (copyId = %d) is already exists in shelf (invent number = %S)", bookCopyId, inventNum));
    }
}
