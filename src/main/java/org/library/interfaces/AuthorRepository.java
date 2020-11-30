package org.library.interfaces;

import org.library.entity.Author;

import java.util.Optional;

public interface AuthorRepository extends JDBCRepository<Author, Integer> {
    Optional<Author> findByName(String name);
}
