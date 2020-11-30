package org.library.services;

import org.library.entity.Genre;

import java.util.List;
import java.util.Optional;


public class GenreService {
    //TODO
    public List<Genre> findAll() {
        return null;
    }

    public Optional<Genre> findById(Integer id) {
        return Optional.empty();
    }

    public Optional<Genre> findByTitle(String title) {
        return Optional.empty();
    }

    public boolean existsById(Integer id) {
        return false;
    }

    public void deleteAll() {

    }

    public boolean deleteById(Integer id) {
        return false;
    }

    public boolean save(Genre genre) {
        return false;
    }

    public long count() {
        return 0;
    }
}
