package org.library.services;

import org.library.entity.Shelf;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.ShelfRepository;

import java.util.List;

public class ShelfService {
    private final ShelfRepository repository;

    public ShelfService(ShelfRepository repository) {
        this.repository = repository;
    }

    public List<Shelf> findAll() {
        return repository.findAll();
    }

    public Shelf findById(Integer id) throws EntityNotFoundByIdException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(Shelf.class, id));
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

    public boolean save(Shelf shelf) {
        return repository.save(shelf);
    }

    public long count() {
        return repository.count();
    }
}
