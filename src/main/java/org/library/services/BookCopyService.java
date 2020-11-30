package org.library.services;

import org.library.entity.BookCopy;

import java.util.List;
import java.util.Optional;

public class BookCopyService {
    //TODO add bookRepository add book to bookCopy
    //private final BookService bookService = new BookService();

    public List<BookCopy> findAll() {
        return null;
    }

    public Optional<BookCopy> findById(Integer id) {
        return Optional.empty();
    }

    public boolean existsById(Integer id) {
        return false;
    }

    public void deleteAll() {

    }

    public boolean deleteById(Integer id) {
        return false;
    }

    public boolean save(BookCopy bookCopy) {
        return false;
    }

    public long count() {
        return 0;
    }
}
