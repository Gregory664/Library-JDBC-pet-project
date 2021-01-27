package org.library.exceptions;

public class BookCopyIsExistsInShelfException extends Exception {
    public BookCopyIsExistsInShelfException(int bookCopyId, String inventNum) {
        super(String.format("Book copy (copyId = %d) is already exists in shelf (invent number = %S)", bookCopyId, inventNum));
    }
}
