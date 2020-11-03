package org.library.repositories;

import org.library.entity.Book;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.entity.Shelf;

import java.util.Map;

public interface IBookRent {
    Map<Book, Period>  getRentBooksByReaderId(int readerId);

    boolean deleteRentBookFromReader(Reader reader, Book book, Shelf shelf);

    boolean addRentBookToReader(Reader reader, Book book, Period period, Shelf shelf);
}
