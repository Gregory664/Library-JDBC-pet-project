package org.library.services;

import org.library.entity.Author;

import java.util.List;
import java.util.Optional;

public class AuthorService {
    //TODO

    public List<Author> findAll() {
        return null;
    }

    public Optional<Author> findById(Integer id) {
        return Optional.empty();
    }

    public boolean existsById(Integer id) {
        return false;
    }

    public void deleteAll() {
    }

    public long count() {
        return 0;
    }

    public boolean deleteById(Integer id) {
        return false;
    }

    public Optional<Author> findByName(String name) {
        return Optional.empty();
    }
}
