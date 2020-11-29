package org.library.repositories;

import org.library.entity.Author;
import org.library.utils.JDBCRepository;

import java.util.Optional;

public interface IAuthor extends JDBCRepository<Author, Integer> {
    Optional<Author> findByName(String name);
}
