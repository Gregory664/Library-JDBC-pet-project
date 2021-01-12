package org.library.services;

import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.BookRepository;
import org.library.interfaces.BookShelfRepository;

import java.util.List;

public class BookService {
    private final BookShelfRepository bookShelfRepository;
    private final BookRepository bookRepository;

    public BookService(BookShelfRepository bookShelfRepository, BookRepository bookRepository) {
        this.bookShelfRepository = bookShelfRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        List<Book> all = bookRepository.findAll();
        all.forEach(book -> book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId())));
        return all;
    }

    public Book findById(Integer id) throws EntityNotFoundByIdException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(Book.class, id));
        book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId()));
        return book;
    }

    public boolean existsById(Integer id) {
        return bookRepository.existsById(id);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public boolean deleteById(Integer id) {
        return bookRepository.deleteById(id);
    }

    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    public long count() {
        return bookRepository.count();
    }

    public boolean update(Book book) {
        return bookRepository.update(book);
    }

    public List<Book> findByParams(String title, Author author, Genre genre, Publisher publisher) {
        List<Book> booksByParams = bookRepository.findByParams(title, author, genre, publisher);
        booksByParams.forEach(book -> book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId())));
        return booksByParams;
    }
}
