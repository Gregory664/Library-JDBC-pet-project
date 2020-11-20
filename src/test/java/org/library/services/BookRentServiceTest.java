package org.library.services;

import org.junit.jupiter.api.Test;
import org.library.entity.Book;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.entity.Shelf;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookRentServiceTest {
    ReaderService readerService = new ReaderService();
    BookRentService bookRentService = new BookRentService();
    BookService bookService = new BookService();
    ShelfService shelfService = new ShelfService();

    @Test
    void getRentBooksByReaderId() {
        Reader reader = readerService.findById(1).orElseThrow(() -> new RuntimeException("reader not found"));
//        assertEquals(reader.getRentBooks(), bookRentService.getRentBooksByReaderId(reader.getId()));
    }

    @Test
    void deleteRentBookFromReader() {
        Reader reader = readerService.findById(1).orElseThrow(() -> new RuntimeException("reader not found"));
        Book book = bookService.findById(1).orElseThrow(() -> new RuntimeException("book not found"));
        Shelf shelf = shelfService.findById(1).orElseThrow(() -> new RuntimeException("shelf not found"));
        assertNotNull(reader);
        assertNotNull(book);
        assertNotNull(shelf);

//        assertTrue(reader.getRentBooks().containsKey(book));
////        int countOfBookInShelf = book.getCountOfBookInShelf().getOrDefault(shelf, 0);
//        assertTrue(bookRentService.deleteRentBookFromReader(reader, book, shelf));
//        assertFalse(reader.getRentBooks().containsKey(book));
////        assertTrue(book.getCountOfBookInShelf().containsKey(shelf));
////        assertEquals(countOfBookInShelf + 1, book.getCountOfBookInShelf().get(shelf));

    }

    @Test
    void addRentBookToReader() {
        Reader reader = readerService.findById(1).orElseThrow(() -> new RuntimeException("reader not found"));
        Book book = bookService.findById(1).orElseThrow(() -> new RuntimeException("book not found"));
        Shelf shelf = shelfService.findById(1).orElseThrow(() -> new RuntimeException("shelf not found"));
        Period period = new Period(LocalDate.now().minusMonths(3), LocalDate.now());
        assertNotNull(reader);
        assertNotNull(book);
        assertNotNull(shelf);

//        assertTrue(book.getCountOfBookInShelf().containsKey(shelf));
//        assertNotEquals(0, book.getCountOfBookInShelf().get(shelf));
//        int countOfBookInShelf = book.getCountOfBookInShelf().get(shelf);
//        assertFalse(reader.getRentBooks().containsKey(book));
//
//
//        assertTrue(bookRentService.addRentBookToReader(reader, book, period, shelf));
////        if (book.getCountOfBookInShelf().containsKey(shelf)) {
////            assertNotEquals(countOfBookInShelf, book.getCountOfBookInShelf().get(shelf));
////            assertEquals(countOfBookInShelf - 1, book.getCountOfBookInShelf().get(shelf));
//        }
//        assertTrue(reader.getRentBooks().containsKey(book));

    }
}