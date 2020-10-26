package org.library.repositories;

import org.library.entity.BookRent;
import org.library.entity.Reader;
import org.library.utils.JDBCRepository;

import java.util.List;

public interface IBookRent extends JDBCRepository<BookRent, Integer> {
    List<BookRent> findByReader(Reader reader);

    long countByReader(Reader reader);

    List<BookRent> findByExpiredDate();
}
