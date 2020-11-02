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
        Book book = bookService.findById(1).get();
        Shelf shelf = shelfService.findById(5).get();
        bookShelfService.deleteBookFromShelf(book, shelf);
        System.out.println(book);
    }
}