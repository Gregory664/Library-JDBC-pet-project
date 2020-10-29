package org.library.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    BookService service = new BookService();
    Book book = null;

    @BeforeEach
    void setUpBook() {
        book = new Book();
        book.setTitle("test book title");
        book.setAuthor(new Author(1, "Федор Достоевский"));
        book.setPublisher(new Publisher(1, "Эксмо"));
        book.setGenre(new Genre(1, "Классическая литература"));
        book.setLength(666);
    }

    @Test
    void findAll() {
        List<Book> books = service.findAll();
        assertNotNull(books);
    }

    @Test
    void findById() {
        Optional<Book> optionalBook = service.findById(1);
        assertTrue(optionalBook.isPresent());
        Book book = optionalBook.get();
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getPublisher());
        assertNotNull(book.getGenre());
        assertNotEquals(0, book.getLength());
    }

    @Test
    void existsById() {
        assertTrue(service.existsById(1));
        assertFalse(service.existsById(-1));
    }

    @Test
    void deleteAll() {
        long beforeDeleteAll = service.count();
        List<Book> books = service.findAll();
        assertEquals(beforeDeleteAll, books.size());

        service.deleteAll();
        long afterDeleteAll = service.count();
        assertEquals(0, afterDeleteAll);

        books.forEach(service::save);
        long afterAdd = service.count();
        assertEquals(beforeDeleteAll, afterAdd);
    }

    @Test
    void deleteById() {
        long beforeAdd = service.count();
        service.save(book);
        long afterADD = service.count();
        assertEquals(beforeAdd + 1, afterADD);

        List<Book> books = service.findAll();
        int lastId = books.stream().max(Comparator.comparingInt(Book::getId)).get().getId();
        service.deleteById(lastId);
        long afterDelete = service.count();
        assertEquals(beforeAdd, afterDelete);
    }

    @Test
    void save() {
        long beforeAdd = service.count();
        assertTrue(service.save(book));
        long afterAdd = service.count();
        assertEquals(beforeAdd + 1, afterAdd);

        List<Book> books = service.findAll();
        Optional<Book> optionalBook = books.stream().
                filter(book1 -> book1.getTitle().equals(book.getTitle()) &&
                        book1.getAuthor().equals(book.getAuthor()) &&
                        book1.getPublisher().equals(book.getPublisher()) &&
                        book1.getGenre().equals(book.getGenre()) &&
                        book1.getLength() == book.getLength())
                .findFirst();
        assertTrue(optionalBook.isPresent());
        service.deleteById(optionalBook.get().getId());
    }

    @Test
    void count() {
        List<Book> books = service.findAll();
        assertEquals(books.size(), service.count());
    }

    @Test
    void findByAuthor() {
        List<Book> books = service.findAll();
        long countBookWithTestAuthor = books.stream()
                .filter(book1 -> book1.getAuthor().equals(book.getAuthor()))
                .count();
        assertEquals(countBookWithTestAuthor, service.findByAuthor(book.getAuthor()).size());
    }

    @Test
    void findByPublisher() {
        List<Book> books = service.findAll();
        long countBookWithTestAuthor = books.stream()
                .filter(book1 -> book1.getPublisher().equals(book.getPublisher()))
                .count();
        assertEquals(countBookWithTestAuthor, service.findByPublisher(book.getPublisher()).size());
    }

    @Test
    void findByGenre() {
        List<Book> books = service.findAll();
        long countBookWithTestAuthor = books.stream()
                .filter(book1 -> book1.getGenre().equals(book.getGenre()))
                .count();
        assertEquals(countBookWithTestAuthor, service.findByGenre(book.getGenre()).size());
    }

    @Test
    void findByTitle() {
        Optional<Book> optionalBook = service.findByTitle("book 1");
        assertTrue(optionalBook.isPresent());
        Book book = optionalBook.get();
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getPublisher());
        assertNotNull(book.getGenre());
        assertNotEquals(0, book.getLength());
    }
}