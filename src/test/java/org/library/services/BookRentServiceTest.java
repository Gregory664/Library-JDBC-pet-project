package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.*;
import org.library.exceptions.BookIsExistsInReaderException;
import org.library.exceptions.BookIsExistsInShelfException;
import org.library.exceptions.RentBookNotFoundInReader;
import org.library.interfaces.BookRentRepository;
import org.library.interfaces.BookShelfRepository;
import org.library.repositories.BookRentRepositoryImpl;
import org.library.repositories.BookShelfRepositoryImpl;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BookRentServiceTest {
    private static final BookRentRepository bookRentRepository = mock(BookRentRepositoryImpl.class);
    private static final BookShelfRepository bookShelfRepository = mock(BookShelfRepositoryImpl.class);
    private static final BookRentService bookRentService = new BookRentService(bookRentRepository, bookShelfRepository);
    private static final Shelf shelf = new Shelf(1, "Z1");
    private static Map<BookCopy, Period> rentBookCopyPeriod;
    private static Reader reader;
    private static BookCopy bookCopy1;
    private static BookCopy bookCopy2;
    private static BookCopy bookCopy3;

    @BeforeAll
    static void initialize() throws RentBookNotFoundInReader, BookIsExistsInReaderException {
        bookCopy1 = new BookCopy(1, Book.builder()
                .id(1)
                .title("book")
                .author(new Author(1, "author 1"))
                .publisher(new Publisher(1, "publisher 1"))
                .genre(new Genre(1, "genre 1"))
                .length(100)
                .build());

        bookCopy2 = new BookCopy(2, Book.builder()
                .id(2)
                .title("book 2")
                .author(new Author(2, "author 2"))
                .publisher(new Publisher(2, "publisher 2"))
                .genre(new Genre(2, "genre 2"))
                .length(200)
                .build());

        bookCopy3 = new BookCopy(3, Book.builder()
                .id(3)
                .title("book 3")
                .author(new Author(3, "author 3"))
                .publisher(new Publisher(3, "publisher 3"))
                .genre(new Genre(3, "genre 3"))
                .length(300)
                .build());

        rentBookCopyPeriod = Map.of(
                bookCopy1, new Period(LocalDate.now(), LocalDate.now().minusDays(2)),
                bookCopy2, new Period(LocalDate.now(), LocalDate.now().minusDays(7))
        );

        reader = Reader.builder()
                .id(1)
                .fio("test fio")
                .age(10)
                .address("test address")
                .phone("88005553535")
                .passport("2010111222")
                .rentBookCopies(rentBookCopyPeriod)
                .build();

        when(bookRentRepository.getRentBookCopiesByReaderId(1)).thenReturn(rentBookCopyPeriod);
        when(bookRentRepository.deleteRentBookCopiesFromReader(reader, bookCopy1)).thenReturn(true);
        when(bookRentRepository.addRentBookCopiesToReader(reader, bookCopy1, rentBookCopyPeriod.get(bookCopy1), shelf)).thenReturn(true);
    }

    @Test
    void getRentBookCopiesByReaderId() {
        assertNotNull(bookRentService.getRentBookCopiesByReaderId(1));
        verify(bookRentRepository, times(1)).getRentBookCopiesByReaderId(1);
    }

    @Test
    void deleteRentBookCopiesFromReader() throws BookIsExistsInShelfException, RentBookNotFoundInReader {
        assertTrue(bookRentService.deleteRentBookCopiesFromReader(reader, bookCopy1, shelf));
        verify(bookShelfRepository, times(1)).addBookCopyToShelf(bookCopy1, shelf);
        verify(bookRentRepository, times(1)).deleteRentBookCopiesFromReader(reader, bookCopy1);
    }

    @Test
    void addRentBookCopiesToReader() throws BookIsExistsInReaderException {
        assertTrue(bookRentService.addRentBookCopiesToReader(reader, bookCopy1, rentBookCopyPeriod.get(bookCopy1), shelf));
        verify(bookShelfRepository, times(1)).deleteBookCopyFromShelf(bookCopy1, shelf);
        verify(bookRentRepository, times(1)).addRentBookCopiesToReader(reader, bookCopy1, rentBookCopyPeriod.get(bookCopy1), shelf);
    }
}