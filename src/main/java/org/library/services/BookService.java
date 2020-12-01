package org.library.services;

import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.exceptions.newExc.EntityNotFoundByTitleException;
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

    public List<Book> findByAuthor(Author author) {
        List<Book> byAuthor = bookRepository.findByAuthor(author);
        byAuthor.forEach(book -> book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId())));
        return byAuthor;
    }

    public List<Book> findByPublisher(Publisher publisher) {
        List<Book> byPublisher = bookRepository.findByPublisher(publisher);
        byPublisher.forEach(book -> book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId())));
        return byPublisher;
    }

    public List<Book> findByGenre(Genre genre) {
        List<Book> byGenre = bookRepository.findByGenre(genre);
        byGenre.forEach(book -> book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId())));
        return byGenre;
    }

    public Book findByTitle(String title) throws EntityNotFoundByTitleException {
        Book book = bookRepository.findByTitle(title).orElseThrow(() -> new EntityNotFoundByTitleException(Book.class, title));
        book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId()));
        return book;
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
}
