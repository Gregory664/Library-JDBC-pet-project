package org.library.services;

import org.library.entity.Shelf;
import org.library.interfaces.ShelfRepository;

import java.util.List;
import java.util.Optional;

public class ShelfService implements ShelfRepository {
    @Override
    public List<Shelf> findAll() {
        return null;
    }

    @Override
    public Optional<Shelf> findById(Integer id) {
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
    public boolean save(Shelf shelf) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}
