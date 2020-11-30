package org.library.services;

import org.library.entity.Genre;
import org.library.interfaces.GenreRepository;

import java.util.List;
import java.util.Optional;


public class GenreService implements GenreRepository {
    @Override
    public List<Genre> findAll() {
        return null;
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Genre> findByTitle(String title) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean save(Genre genre) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}
