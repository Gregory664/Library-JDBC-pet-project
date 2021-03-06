package org.library.interfaces;

import java.util.List;
import java.util.Optional;

public interface JDBCRepository<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    void deleteAll();

    boolean deleteById(ID id);

    boolean save(T entity);

    boolean update(T entity);

    long count();
}
