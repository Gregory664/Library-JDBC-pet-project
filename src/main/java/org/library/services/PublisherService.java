package org.library.services;

import org.library.entity.Publisher;
import org.library.exceptions.newExc.PublisherNotFoundByIdException;
import org.library.exceptions.newExc.PublisherNotFoundByTitleException;
import org.library.interfaces.PublisherRepository;

import java.util.List;

public class PublisherService {
    private final PublisherRepository repository;

    public PublisherService(PublisherRepository repository) {
        this.repository = repository;
    }

    public List<Publisher> findAll() {
        return repository.findAll();
    }

    public Publisher findById(Integer id) throws PublisherNotFoundByIdException {
        return repository.findById(id).orElseThrow(() -> new PublisherNotFoundByIdException(id));
    }

    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public boolean deleteById(Integer id) {
        return repository.deleteById(id);
    }

    public boolean save(Publisher publisher) {
        return repository.save(publisher);
    }

    public long count() {
        return repository.count();
    }

    public Publisher findByTitle(String title) throws PublisherNotFoundByTitleException {
        return repository.findByTitle(title).orElseThrow(() -> new PublisherNotFoundByTitleException(title));
    }
}
