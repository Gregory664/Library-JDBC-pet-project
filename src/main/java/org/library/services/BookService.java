package org.library.services;

import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;
import org.library.interfaces.BookRepository;

import java.util.List;
import java.util.Optional;

public class BookService implements BookRepository {
    //TODO add BookShelfRepository and add  Map<Integer, Shelf> bookCopyIdAndShelf = bookShelfService.getBookCopyIdAndShelf(id);
    //private final BookShelfService bookShelfService = new BookShelfService();

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Optional<Book> findById(Integer id) {
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
    public boolean save(Book book) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Book> findByPublisher(Publisher publisher) {
        return null;
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return null;
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return Optional.empty();
    }
}
