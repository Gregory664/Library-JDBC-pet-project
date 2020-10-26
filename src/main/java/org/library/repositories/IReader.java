package org.library.repositories;

import org.library.entity.Reader;
import org.library.utils.JDBCRepository;

import java.util.List;

public interface IReader extends JDBCRepository<Reader, Integer> {
    boolean existsByPassport(String passport);

    List<Reader> findByFioLike(String fio);
}
