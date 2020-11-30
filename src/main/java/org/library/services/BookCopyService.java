package org.library.services;

import org.library.entity.BookCopy;
import org.library.interfaces.BookCopyRepository;

import java.util.List;
import java.util.Optional;

public class BookCopyService implements BookCopyRepository {
    //TODO add bookRepository add book to bookCopy
    //private final BookService bookService = new BookService();

    @Override
    public List<BookCopy> findAll() {
        return null;
    }

    @Override
    public Optional<BookCopy> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean save(BookCopy bookCopy) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}
