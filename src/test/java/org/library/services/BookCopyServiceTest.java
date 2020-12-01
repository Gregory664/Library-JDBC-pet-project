package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.*;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.BookCopyRepository;
import org.library.interfaces.BookRepository;
import org.library.repositories.BookCopyRepositoryImpl;
import org.library.repositories.BookRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookCopyServiceTest {
    private static final BookCopyRepository bookCopyRepository = mock(BookCopyRepositoryImpl.class);
    private static final BookRepository bookRepository = mock(BookRepositoryImpl.class);
    private static List<BookCopy> bookCopyList;
    private final BookCopyService bookCopyService = new BookCopyService(bookRepository, bookCopyRepository);

    @BeforeAll
    static void initialize() {
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

        Book book1 = Book.builder()
                .id(1)
                .title("book 1")
                .author(new Author(1, "test author"))
                .publisher(new Publisher(1, "test publisher"))
                .genre(new Genre(1, "test genre"))
                .length(100)
                .bookCopyIdAndShelf(bookCopyIdAndShelf1)
                .build();
        Book book2 = Book.builder()
                .id(2)
                .title("book 2")
                .author(new Author(1, "test author"))
                .publisher(new Publisher(1, "test publisher"))
                .genre(new Genre(1, "test genre"))
                .length(200)
                .bookCopyIdAndShelf(bookCopyIdAndShelf2)
                .build();

        bookCopyList = List.of(
                new BookCopy(1, book1),
                new BookCopy(2, book2)
        );

        when(bookRepository.findById(1)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(2)).thenReturn(Optional.of(book2));
        when(bookCopyRepository.findById(1)).thenReturn(Optional.of(bookCopyList.get(0)));
        when(bookCopyRepository.findAll()).thenReturn(bookCopyList);
        when(bookCopyRepository.existsById(1)).thenReturn(true);
        when(bookCopyRepository.deleteById(1)).thenReturn(true);
        when(bookCopyRepository.save(bookCopyList.get(0))).thenReturn(true);
        when(bookCopyRepository.count()).thenReturn(1L);
    }

    @Test
    void findAll() throws EntityNotFoundByIdException {
        List<BookCopy> all = bookCopyService.findAll();
        assertNotNull(all);
        assertEquals(bookCopyList, all);
    }

    @Test
    void findById() throws EntityNotFoundByIdException {
        BookCopy byId = bookCopyService.findById(1);
        assertNotNull(byId);
        assertEquals(bookCopyList.get(0), byId);

        Throwable throwable = assertThrows(EntityNotFoundByIdException.class, () -> bookCopyService.findById(3));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void deleteAll() {
        bookCopyService.deleteAll();
        verify(bookCopyRepository, times(1)).deleteAll();
    }

    @Test
    void existsById() {
        assertTrue(bookCopyService.existsById(1));
        verify(bookCopyRepository, times(1)).existsById(1);
    }

    @Test
    void deleteById() {
        assertTrue(bookCopyService.deleteById(1));
        verify(bookCopyRepository, times(1)).deleteById(1);
    }

    @Test
    void save() {
        assertTrue(bookCopyService.save(bookCopyList.get(0)));
        verify(bookCopyRepository, times(1)).save(bookCopyList.get(0));
    }

    @Test
    void count() {
        assertEquals(1L, bookCopyService.count());
        verify(bookCopyRepository, times(1)).count();
    }
}