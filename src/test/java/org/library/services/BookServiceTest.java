package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.*;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.BookRepository;
import org.library.interfaces.BookShelfRepository;
import org.library.repositories.BookRepositoryImpl;
import org.library.repositories.BookShelfRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {
    private static final BookShelfRepository bookShelfRepository = mock(BookShelfRepositoryImpl.class);
    private static final BookRepository bookRepository = mock(BookRepositoryImpl.class);
    private static final BookService bookService = new BookService(bookShelfRepository, bookRepository);
    private static List<Book> books;

    @BeforeAll
    static void initBookCopyIdAndShelfMaps() {
        Map<Integer, Shelf> bookCopyIdAndShelf1 = Map.of(
                1, new Shelf(1, "z1"),
                2, new Shelf(1, "z1"),
                3, new Shelf(2, "z2")
        );

        Map<Integer, Shelf> bookCopyIdAndShelf2 = Map.of(
                4, new Shelf(1, "z1"),
                5, new Shelf(1, "z1"),
                6, new Shelf(2, "z2")
        );

        books = List.of(
                Book.builder()
                        .id(1)
                        .title("book 1")
                        .author(new Author(1, "test author"))
                        .publisher(new Publisher(1, "test publisher"))
                        .genre(new Genre(1, "test genre"))
                        .length(100)
                        .bookCopyIdAndShelf(bookCopyIdAndShelf1)
                        .build(),
                Book.builder()
                        .id(2)
                        .title("book 2")
                        .author(new Author(1, "test author"))
                        .publisher(new Publisher(1, "test publisher"))
                        .genre(new Genre(1, "test genre"))
                        .length(200)
                        .bookCopyIdAndShelf(bookCopyIdAndShelf2)
                        .build()
        );

        when(bookShelfRepository.getBookCopyIdAndShelf(1)).thenReturn(bookCopyIdAndShelf1);
        when(bookShelfRepository.getBookCopyIdAndShelf(2)).thenReturn(bookCopyIdAndShelf2);
        when(bookRepository.findAll()).thenReturn(books);
        when(bookRepository.findById(1)).thenReturn(Optional.of(books.get(0)));
        when(bookRepository.existsById(1)).thenReturn(true);
        when(bookRepository.deleteById(2)).thenReturn(true);
        when(bookRepository.save(books.get(0))).thenReturn(true);
        when(bookRepository.count()).thenReturn(2L);
        when(bookRepository.update(books.get(0))).thenReturn(true);
        when(bookRepository.findByParams("book 1",
                books.get(0).getAuthor(),
                books.get(0).getGenre(),
                books.get(0).getPublisher())).thenReturn(List.of(books.get(0)));
    }

    @Test
    void findAll() {
        List<Book> alBookList = bookService.findAll();
        assertNotNull(alBookList);
        assertEquals(books, alBookList);
    }

    @Test
    void findById() throws EntityNotFoundByIdException {
        Book byId = bookService.findById(1);
        assertNotNull(byId);
        assertEquals(books.get(0), byId);

        Throwable throwable = assertThrows(EntityNotFoundByIdException.class, () -> bookService.findById(3));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void existsById() {
        assertTrue(bookService.existsById(1));
        verify(bookRepository, times(1)).existsById(1);
    }

    @Test
    void deleteAll() {
        bookService.deleteAll();
        verify(bookRepository, times(1)).deleteAll();
    }

    @Test
    void deleteById() {
        assertTrue(bookService.deleteById(2));
        verify(bookRepository, times(1)).deleteById(2);
    }

    @Test
    void save() {
        assertTrue(bookService.save(books.get(0)));
        verify(bookRepository, times(1)).save(books.get(0));
    }

    @Test
    void count() {
        assertEquals(2, bookService.count());
        verify(bookRepository, times(1)).count();
    }

    @Test
    void update() {
        assertTrue(bookService.update(books.get(0)));
        verify(bookRepository).update(books.get(0));
    }

    @Test
    void findByParams() {
        assertEquals(List.of(books.get(0)), bookService.findByParams("book 1",
                books.get(0).getAuthor(),
                books.get(0).getGenre(),
                books.get(0).getPublisher()));
        verify(bookRepository, atLeast(1)).findByParams(anyString(), any(Author.class), any(Genre.class), any(Publisher.class));
        verify(bookShelfRepository, atLeast(1)).getBookCopyIdAndShelf(anyInt());
    }
}