package org.library.services;

import org.library.entity.*;
import org.library.exceptions.BookCopyIsExistsInShelfException;
import org.library.exceptions.BookIsExistsInReaderException;
import org.library.exceptions.BookNotFoundOnShelfException;
import org.library.exceptions.RentBookNotFoundInReader;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.BookCopyRepository;
import org.library.interfaces.BookRentRepository;
import org.library.interfaces.BookRepository;
import org.library.interfaces.BookShelfRepository;
import org.library.utils.MessageBox;

import java.util.Map;
import java.util.TreeMap;

public class BookRentService {
    private final BookRentRepository bookRentRepository;
    private final BookShelfRepository bookShelfRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;

    public BookRentService(BookRentRepository bookRentRepository,
                           BookShelfRepository bookShelfRepository,
                           BookCopyRepository bookCopyRepository,
                           BookRepository bookRepository) {
        this.bookRentRepository = bookRentRepository;
        this.bookShelfRepository = bookShelfRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
    }

    public Map<BookCopy, Period> getRentBookCopiesByReaderId(int readerId) {
        Map<BookCopy, Period> fromRepo = bookRentRepository.getRentBookCopiesByReaderId(readerId);
        if (fromRepo.size() == 0) {
            return fromRepo;
        }

        Map<BookCopy, Period> bookCopyPeriodMap = new TreeMap<>();
        for (Map.Entry<BookCopy, Period> entry : fromRepo.entrySet()) {
            BookCopy bookCopy = entry.getKey();
            Period period = entry.getValue();
            try {
                int bookCopyId = bookCopy.getId();
                bookCopy = bookCopyRepository.findById(bookCopyId).orElseThrow(() -> new EntityNotFoundByIdException(BookCopy.class, bookCopyId));
                int bookId = bookCopy.getBook().getId();
                Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundByIdException(Book.class, bookId));
                Map<Integer, Shelf> bookCopyIdAndShelf = bookShelfRepository.getBookCopyIdAndShelf(bookId);
                book.setBookCopyIdAndShelf(bookCopyIdAndShelf);
                bookCopy.setBook(book);
            } catch (EntityNotFoundByIdException e) {
                MessageBox.WarningBox(e.getMessage()).show();
            }
            bookCopyPeriodMap.put(bookCopy, period);
        }
        return bookCopyPeriodMap;
    }

    public boolean deleteRentBookCopiesFromReader(Reader reader, BookCopy bookCopy, Shelf shelf) throws RentBookNotFoundInReader, BookCopyIsExistsInShelfException {
        Map<BookCopy, Period> rentBookCopies = reader.getRentBookCopies();

        if (!rentBookCopies.containsKey(bookCopy)) {
            throw new RentBookNotFoundInReader(reader.getId(), bookCopy.getId());
        }
        if (bookCopy.getBook().getBookCopyIdAndShelf().containsKey(bookCopy.getId())) {
            throw new BookCopyIsExistsInShelfException(bookCopy.getId(), shelf.getInventNum());
        }
        rentBookCopies.remove(bookCopy);

        bookShelfRepository.addBookCopyToShelf(bookCopy, shelf);
        return bookRentRepository.deleteRentBookCopiesFromReader(reader, bookCopy);
    }

    public boolean addRentBookCopiesToReader(Reader reader, BookCopy bookCopy, Period period, Shelf shelf) throws BookIsExistsInReaderException, BookNotFoundOnShelfException {
        Map<Integer, Shelf> bookCopyIdAndShelf = bookCopy.getBook().getBookCopyIdAndShelf();

        if (!bookCopyIdAndShelf.containsKey(bookCopy.getId())) {
            throw new BookNotFoundOnShelfException(bookCopy.getId(), shelf.getId());
        }
        if (reader.getRentBookCopies().containsKey(bookCopy)) {
            throw new BookIsExistsInReaderException(bookCopy.getId(), reader.getId());
        }
        bookCopyIdAndShelf.remove(bookCopy.getId());
        reader.getRentBookCopies().put(bookCopy, period);

        bookShelfRepository.deleteBookCopyFromShelf(bookCopy, shelf);
        return bookRentRepository.addRentBookCopiesToReader(reader, bookCopy, period, shelf);
    }
}
