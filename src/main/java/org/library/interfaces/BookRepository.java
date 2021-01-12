package org.library.interfaces;

import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;

import java.util.List;

public interface BookRepository extends JDBCRepository<Book, Integer> {
    List<Book> findByParams(String title, Author author, Genre genre, Publisher publisher);
}
