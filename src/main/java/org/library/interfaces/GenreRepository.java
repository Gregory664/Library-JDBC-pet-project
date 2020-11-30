package org.library.interfaces;

import org.library.entity.Genre;

import java.util.Optional;

public interface GenreRepository extends JDBCRepository<Genre, Integer> {
    Optional<Genre> findByTitle(String title);
}