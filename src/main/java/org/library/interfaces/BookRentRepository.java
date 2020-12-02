package org.library.interfaces;

import org.library.entity.BookCopy;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.entity.Shelf;
import org.library.exceptions.BookIsExistsInReaderException;
import org.library.exceptions.RentBookNotFoundInReader;

import java.util.Map;

public interface BookRentRepository {
    Map<BookCopy, Period> getRentBookCopiesByReaderId(int readerId);

    boolean deleteRentBookCopiesFromReader(Reader reader, BookCopy bookCopy) throws RentBookNotFoundInReader;

    boolean addRentBookCopiesToReader(Reader reader, BookCopy bookCopy, Period period, Shelf shelf) throws BookIsExistsInReaderException;
}
