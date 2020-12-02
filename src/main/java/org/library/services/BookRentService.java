package org.library.services;

import org.library.entity.BookCopy;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.entity.Shelf;
import org.library.exceptions.BookIsExistsInReaderException;
import org.library.exceptions.BookIsExistsInShelfException;
import org.library.exceptions.RentBookNotFoundInReader;
import org.library.interfaces.BookRentRepository;
import org.library.interfaces.BookShelfRepository;

import java.util.Map;

public class BookRentService {
    private final BookRentRepository bookRentRepository;
    private final BookShelfRepository bookShelfRepository;

    public BookRentService(BookRentRepository bookRentRepository, BookShelfRepository bookShelfRepository) {
        this.bookRentRepository = bookRentRepository;
        this.bookShelfRepository = bookShelfRepository;
    }

    public Map<BookCopy, Period> getRentBookCopiesByReaderId(int readerId) {
        return bookRentRepository.getRentBookCopiesByReaderId(readerId);
    }

    public boolean deleteRentBookCopiesFromReader(Reader reader, BookCopy bookCopy, Shelf shelf) throws RentBookNotFoundInReader, BookIsExistsInShelfException {
        bookShelfRepository.addBookCopyToShelf(bookCopy, shelf);
        return bookRentRepository.deleteRentBookCopiesFromReader(reader, bookCopy);
    }

    public boolean addRentBookCopiesToReader(Reader reader, BookCopy bookCopy, Period period, Shelf shelf) throws BookIsExistsInReaderException {
        bookShelfRepository.deleteBookCopyFromShelf(bookCopy, shelf);
        return bookRentRepository.addRentBookCopiesToReader(reader, bookCopy, period, shelf);
    }
}
