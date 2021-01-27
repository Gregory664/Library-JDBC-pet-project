package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.*;
import org.library.exceptions.BookCopyIsExistsInShelfException;
import org.library.interfaces.BookShelfRepository;
import org.library.repositories.BookShelfRepositoryImpl;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookShelfServiceTest {
    private static final BookShelfRepository bookShelfRepository = mock(BookShelfRepositoryImpl.class);
    private static final Map<Integer, Shelf> map = new TreeMap<>();
    private static final BookShelfService bookShelfService = new BookShelfService(bookShelfRepository);
    private static BookCopy bookCopy;
    private static Shelf shelf;

    @BeforeAll
    static void initialize() {
        shelf = new Shelf(1, "Z1");

        map.put(1, shelf);
        map.put(2, shelf);

        bookCopy = BookCopy.builder()
                .id(1)
                .book(Book.builder()
                        .id(1)
                        .title("title")
                        .author(new Author(1, "author"))
                        .publisher(new Publisher(1, "publisher"))
                        .genre(new Genre(1, "genre"))
                        .length(100)
                        .bookCopyIdAndShelf(map)
                        .build())
                .build();
    }

    @Test
    void getBookCopyIdAndShelf() {
        when(bookShelfRepository.getBookCopyIdAndShelf(bookCopy.getId())).thenReturn(bookCopy.getBook().getBookCopyIdAndShelf());
        assertNotNull(bookShelfService.getBookCopyIdAndShelf(bookCopy.getId()));

        verify(bookShelfRepository).getBookCopyIdAndShelf(bookCopy.getId());
    }

    @Test
    void deleteBookCopyFromShelf() {
        when(bookShelfRepository.deleteBookCopyFromShelf(bookCopy.getId(), shelf.getId())).thenReturn(true);
        assertTrue(bookShelfService.deleteBookCopyFromShelf(bookCopy.getId(), shelf.getId()));
    }

    @Test
    void addBookCopyToShelf() throws BookCopyIsExistsInShelfException {
        when(bookShelfRepository.addBookCopyToShelf(bookCopy, shelf)).thenReturn(true);

        bookCopy.setId(3);
        assertFalse(bookCopy.getBook().getBookCopyIdAndShelf().containsKey(bookCopy.getId()));
        assertTrue(bookShelfService.addBookCopyToShelf(bookCopy, shelf));
        assertTrue(bookCopy.getBook().getBookCopyIdAndShelf().containsKey(bookCopy.getId()));

        Throwable throwable = assertThrows(BookCopyIsExistsInShelfException.class, () -> bookShelfService.addBookCopyToShelf(bookCopy, shelf));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void updateShelf() {
        when(bookShelfRepository.updateShelf(1, 1, 1)).thenReturn(true);
        assertTrue(bookShelfService.updateShelf(1, 1, 1));
        verify(bookShelfRepository).updateShelf(1, 1, 1);
    }
}