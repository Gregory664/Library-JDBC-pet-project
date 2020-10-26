package org.library.repositories;

import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;
import org.library.utils.JDBCRepository;

import java.util.List;
import java.util.Optional;

public interface IBook extends JDBCRepository<Book, Integer> {
    List<Book> findByAuthor(Author author);

    List<Book> findByPublisher(Publisher publisher);

    List<Book> findByGenre(Genre genre);

    Optional<Book> findByTitle(String title);
}
