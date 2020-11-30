package org.library.services;

import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;

import java.util.List;
import java.util.Optional;

public class BookService {
    //TODO add BookShelfRepository and add  Map<Integer, Shelf> bookCopyIdAndShelf = bookShelfService.getBookCopyIdAndShelf(id);
    //private final BookShelfService bookShelfService = new BookShelfService();

    public List<Book> findAll() {
        return null;
    }

    public Optional<Book> findById(Integer id) {
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

    public boolean save(Book book) {
        return false;
    }

    public long count() {
        return 0;
    }

    public List<Book> findByAuthor(Author author) {
        return null;
    }

    public List<Book> findByPublisher(Publisher publisher) {
        return null;
    }

    public List<Book> findByGenre(Genre genre) {
        return null;
    }

    public Optional<Book> findByTitle(String title) {
        return Optional.empty();
    }
}
