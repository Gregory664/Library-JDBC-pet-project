package org.library.repositories;

import org.library.entity.Book;
import org.library.entity.Shelf;

import java.util.Map;

public interface IBookshelf {
    Map<Shelf, Integer> getCountOfBookOnShelfByBookId(int bookId);

    boolean deleteBookFromShelf(Book book, Shelf shelf);

    boolean addBookToShelf(Book book, Shelf shelf);
}
