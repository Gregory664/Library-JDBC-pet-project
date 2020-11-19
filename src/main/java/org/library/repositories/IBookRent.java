package org.library.repositories;

import org.library.entity.BookCopy;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.entity.Shelf;

import java.util.Map;

public interface IBookRent {
    Map<BookCopy, Period> getRentBookCopiesByReaderId(int readerId);

    boolean deleteRentBookCopiesFromReader(Reader reader, BookCopy book, Shelf shelf);

    boolean addRentBookCopiesToReader(Reader reader, BookCopy book, Period period, Shelf shelf);
}
