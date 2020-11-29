package org.library.repositories;

import org.library.entity.Book;
import org.library.entity.BookCopy;
import org.library.entity.Shelf;

import java.util.Map;

public interface IBookshelf {
    Map<Integer, Shelf> getBookCopyIdAndShelf(int bookId);

    boolean deleteBookCopyFromShelf(BookCopy bookCopy, Shelf shelf);

    boolean addBookCopyToShelf(BookCopy bookCopy, Shelf shelf);
}
