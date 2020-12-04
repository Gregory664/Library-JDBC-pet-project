package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.*;
import org.library.exceptions.BookIsExistsInReaderException;
import org.library.exceptions.BookIsExistsInShelfException;
import org.library.exceptions.RentBookNotFoundInReader;
import org.library.interfaces.BookCopyRepository;
import org.library.interfaces.BookRentRepository;
import org.library.interfaces.BookRepository;
import org.library.interfaces.BookShelfRepository;
import org.library.repositories.BookRentRepositoryImpl;
import org.library.repositories.BookRepositoryImpl;
import org.library.repositories.BookShelfRepositoryImpl;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRentServiceTest {
    private static final BookRentRepository bookRentRepository = mock(BookRentRepositoryImpl.class);
    private static final BookShelfRepository bookShelfRepository = mock(BookShelfRepositoryImpl.class);
    private static final BookCopyRepository bookCopyRepository = mock(BookCopyRepository.class);
    private static final BookRepository bookRepository = mock(BookRepositoryImpl.class);
    private static final BookRentService bookRentService = new BookRentService(bookRentRepository, bookShelfRepository, bookCopyRepository, bookRepository);
    private static Map<BookCopy, Period> bookCopyPeriodMap;
    private static Reader reader;
    private static BookCopy bookCopy;
    private static Shelf shelf;
    private static Period period;

    @BeforeAll
    static void initialize() {
        shelf = new Shelf(1, "Z");
        period = new Period(LocalDate.now(), LocalDate.now().plusDays(2));

        bookCopy = BookCopy.builder()
                .id(1)
                .book(Book.builder()
                        .id(1)
                        .title("title")
                        .author(new Author(1, "author"))
                        .publisher(new Publisher(1, "publisher"))
                        .genre(new Genre(1, "genre"))
                        .length(100)
                        .bookCopyIdAndShelf(Map.of(1, shelf))
                        .build())
                .build();

        bookCopyPeriodMap = Map.of(bookCopy, period);

        reader = Reader.builder()
                .id(1)
                .fio("fio")
                .age(12)
                .address("address")
                .phone("88005553535")
                .passport("2000111222")
                .rentBookCopies(bookCopyPeriodMap)
                .build();
    }

    @Test
    void getRentBookCopiesByReaderId() {
        when(bookRentRepository.getRentBookCopiesByReaderId(1)).thenReturn(
                Map.of(
                        new BookCopy(1), new Period(LocalDate.now(), LocalDate.now().plusDays(2))
                )
        );

        when(bookCopyRepository.findById(1)).thenReturn(Optional.of(new BookCopy(1, Book.builder().id(1).build())));
        when(bookRepository.findById(1)).thenReturn(Optional.of(Book.builder()
                .id(1)
                .title("title")
                .author(new Author(1, "author"))
                .publisher(new Publisher(1, "publisher"))
                .genre(new Genre(1, "genre"))
                .length(100)
                .build()
        ));
        when(bookShelfRepository.getBookCopyIdAndShelf(1)).thenReturn(Map.of(1, shelf));

        assertNotNull(bookRentService.getRentBookCopiesByReaderId(1));
        assertEquals(bookCopyPeriodMap, bookRentService.getRentBookCopiesByReaderId(1));
    }

    @Test
    void deleteRentBookCopiesFromReader() throws BookIsExistsInShelfException, RentBookNotFoundInReader {
        when(bookShelfRepository.addBookCopyToShelf(bookCopy, shelf)).thenReturn(true);
        when(bookRentRepository.deleteRentBookCopiesFromReader(reader, bookCopy)).thenReturn(true);

        assertTrue(bookRentService.deleteRentBookCopiesFromReader(reader, bookCopy, shelf));
        verify(bookShelfRepository).addBookCopyToShelf(bookCopy, shelf);
        verify(bookRentRepository).deleteRentBookCopiesFromReader(reader, bookCopy);
    }

    @Test
    void addRentBookCopiesToReader() throws BookIsExistsInReaderException {
        when(bookShelfRepository.deleteBookCopyFromShelf(bookCopy, shelf)).thenReturn(true);
        when(bookRentRepository.addRentBookCopiesToReader(reader, bookCopy, period, shelf)).thenReturn(true);

        assertTrue(bookRentService.addRentBookCopiesToReader(reader, bookCopy, period, shelf));
        verify(bookShelfRepository, times(1)).deleteBookCopyFromShelf(bookCopy, shelf);
        verify(bookRentRepository, times(1)).addRentBookCopiesToReader(reader, bookCopy, period, shelf);
    }
}