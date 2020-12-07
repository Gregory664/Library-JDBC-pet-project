package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.*;
import org.library.exceptions.BookIsExistsInReaderException;
import org.library.exceptions.BookIsExistsInShelfException;
import org.library.exceptions.BookNotFoundOnShelfException;
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
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRentServiceTest {
    private static final BookRentRepository bookRentRepository = mock(BookRentRepositoryImpl.class);
    private static final BookShelfRepository bookShelfRepository = mock(BookShelfRepositoryImpl.class);
    private static final BookCopyRepository bookCopyRepository = mock(BookCopyRepository.class);
    private static final BookRepository bookRepository = mock(BookRepositoryImpl.class);
    private static final BookRentService bookRentService = new BookRentService(bookRentRepository, bookShelfRepository, bookCopyRepository, bookRepository);
    private static Map<BookCopy, Period> bookCopyPeriodMap = new TreeMap<>();
    private static Reader reader;
    private static BookCopy bookCopy;
    private static BookCopy bookCopy2;
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
                        .bookCopyIdAndShelf(Map.of(2, shelf))
                        .build())
                .build();

        Map<Integer, Shelf> map = new TreeMap<>();
        map.put(2, shelf);

        bookCopy2 = BookCopy.builder()
                .id(2)
                .book(Book.builder()
                        .id(2)
                        .title("title")
                        .author(new Author(2, "author"))
                        .publisher(new Publisher(2, "publisher"))
                        .genre(new Genre(2, "genre"))
                        .length(200)
                        .bookCopyIdAndShelf(map)
                        .build())
                .build();

        bookCopyPeriodMap.put(bookCopy, period);

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
        Reader reader1 = reader;

        when(bookShelfRepository.addBookCopyToShelf(bookCopy, shelf)).thenReturn(true);
        when(bookRentRepository.deleteRentBookCopiesFromReader(reader1, bookCopy)).thenReturn(true);

        assertTrue(bookRentService.deleteRentBookCopiesFromReader(reader1, bookCopy, shelf));
        assertFalse(reader1.getRentBookCopies().containsKey(bookCopy));

        verify(bookShelfRepository).addBookCopyToShelf(bookCopy, shelf);
        verify(bookRentRepository).deleteRentBookCopiesFromReader(reader1, bookCopy);
    }

    @Test
    void addRentBookCopiesToReader() throws BookIsExistsInReaderException, BookNotFoundOnShelfException {
        when(bookShelfRepository.deleteBookCopyFromShelf(bookCopy2, shelf)).thenReturn(true);
        when(bookRentRepository.addRentBookCopiesToReader(reader, bookCopy2, period, shelf)).thenReturn(true);

        assertTrue(bookRentService.addRentBookCopiesToReader(reader, bookCopy2, period, shelf));
        assertFalse(bookCopy2.getBook().getBookCopyIdAndShelf().containsKey(bookCopy2.getId()));
        assertTrue(reader.getRentBookCopies().containsKey(bookCopy2));

        verify(bookShelfRepository, times(1)).deleteBookCopyFromShelf(bookCopy2, shelf);
        verify(bookRentRepository, times(1)).addRentBookCopiesToReader(reader, bookCopy2, period, shelf);
    }
}