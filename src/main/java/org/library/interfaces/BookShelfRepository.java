package org.library.interfaces;

import org.library.entity.BookCopy;
import org.library.entity.Shelf;
import org.library.exceptions.BookCopyIsExistsInShelfException;

import java.util.Map;

public interface BookShelfRepository {
    Map<Integer, Shelf> getBookCopyIdAndShelf(int bookId);

    boolean deleteBookCopyFromShelf(BookCopy bookCopy, Shelf shelf);

    boolean addBookCopyToShelf(BookCopy bookCopy, Shelf shelf) throws BookCopyIsExistsInShelfException;
}
