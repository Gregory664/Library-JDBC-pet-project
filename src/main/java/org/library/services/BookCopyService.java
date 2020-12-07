package org.library.services;

import org.library.entity.Book;
import org.library.entity.BookCopy;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.BookCopyRepository;
import org.library.interfaces.BookRepository;
import org.library.interfaces.BookShelfRepository;

import java.util.List;

public class BookCopyService {
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookShelfRepository bookShelfRepository;

    public BookCopyService(BookRepository bookRepository, BookCopyRepository bookCopyRepository, BookShelfRepository bookShelfRepository) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.bookShelfRepository = bookShelfRepository;
    }

    public List<BookCopy> findAll() throws EntityNotFoundByIdException {
        List<BookCopy> bookCopies = bookCopyRepository.findAll();
        for (BookCopy bookCopy : bookCopies) {
            int bookId = bookCopy.getId();
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundByIdException(BookCopy.class, bookId));
            book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId()));
            bookCopy.setBook(book);
        }
        return bookCopies;
    }

    public BookCopy findById(Integer id) throws EntityNotFoundByIdException {
        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(BookCopy.class, id));
        Book book = bookRepository.findById(bookCopy.getBook().getId()).orElseThrow(() -> new EntityNotFoundByIdException(BookCopy.class, id));
        book.setBookCopyIdAndShelf(bookShelfRepository.getBookCopyIdAndShelf(book.getId()));
        bookCopy.setBook(book);
        return bookCopy;
    }

    public boolean existsById(Integer id) {
        return bookCopyRepository.existsById(id);
    }

    public void deleteAll() {
        bookCopyRepository.deleteAll();
    }

    public boolean deleteById(Integer id) {
        return bookCopyRepository.deleteById(id);
    }

    public boolean save(BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public long count() {
        return bookCopyRepository.count();
    }
}
