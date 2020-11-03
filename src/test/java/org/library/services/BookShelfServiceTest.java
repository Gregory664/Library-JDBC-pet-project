package org.library.services;

import org.junit.jupiter.api.Test;
import org.library.entity.Book;
import org.library.entity.Shelf;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookShelfServiceTest {
    BookService bookService = new BookService();
    BookShelfService bookShelfService = new BookShelfService();
    ShelfService shelfService = new ShelfService();

    @Test
    void deleteBookFromShelf() {
        Book book = bookService.findById(1).orElseThrow(() -> new RuntimeException("book not found"));
        Shelf shelf = shelfService.findById(5).orElseThrow(() -> new RuntimeException("shelf not found"));
        int beforeDelete = book.getCountOfBookInShelf().size();
        assertTrue(bookShelfService.deleteBookFromShelf(book, shelf));
        int afterDelete = book.getCountOfBookInShelf().size();
        assertNotEquals(beforeDelete, afterDelete);
    }
}