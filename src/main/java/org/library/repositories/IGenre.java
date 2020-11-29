package org.library.repositories;

import org.library.entity.Genre;
import org.library.utils.JDBCRepository;

import java.util.Optional;

public interface IGenre extends JDBCRepository<Genre, Integer> {
    Optional<Genre> findByTitle(String title);
}