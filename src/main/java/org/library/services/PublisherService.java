package org.library.services;

import org.library.entity.Publisher;
import org.library.interfaces.PublisherRepository;

import java.util.List;
import java.util.Optional;

public class PublisherService implements PublisherRepository {
    @Override
    public List<Publisher> findAll() {
        return null;
    }

    @Override
    public Optional<Publisher> findById(Integer id) {
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
    public boolean save(Publisher publisher) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Optional<Publisher> findByTitle(String title) {
        return Optional.empty();
    }
}
