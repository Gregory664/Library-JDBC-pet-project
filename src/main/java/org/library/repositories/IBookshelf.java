package org.library.repositories;

import org.library.entity.Bookshelf;
import org.library.utils.JDBCRepository;

public interface IBookshelf extends JDBCRepository<Bookshelf, Integer> {
    long countByBookId(Integer id);
}
