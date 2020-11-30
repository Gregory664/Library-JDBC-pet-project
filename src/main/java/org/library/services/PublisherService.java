package org.library.services;

import org.library.entity.Publisher;

import java.util.List;
import java.util.Optional;

public class PublisherService {
    //TODO
    public List<Publisher> findAll() {
        return null;
    }

    public Optional<Publisher> findById(Integer id) {
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

    public boolean save(Publisher publisher) {
        return false;
    }

    public long count() {
        return 0;
    }

    public Optional<Publisher> findByTitle(String title) {
        return Optional.empty();
    }
}
