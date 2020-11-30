package org.library.services;

import org.library.entity.BookCopy;
import org.library.entity.Shelf;

import java.util.Map;

public class BookShelfService {
    public Map<Integer, Shelf> getBookCopyIdAndShelf(int bookId) {
        return null;
    }

    public boolean deleteBookCopyFromShelf(BookCopy bookCopy, Shelf shelf) {
        return false;
    }

    public boolean addBookCopyToShelf(BookCopy bookCopy, Shelf shelf) {
        return false;
    }
}
