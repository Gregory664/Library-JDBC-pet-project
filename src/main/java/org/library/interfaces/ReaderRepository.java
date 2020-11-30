package org.library.interfaces;

import org.library.entity.Reader;

import java.util.List;

public interface ReaderRepository extends JDBCRepository<Reader, Integer> {
    boolean existsByPassport(String passport);

    List<Reader> findByFioLike(String fio);
}
