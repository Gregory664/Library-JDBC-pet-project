package org.library.services;

import org.library.entity.Genre;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.exceptions.newExc.EntityNotFoundByTitleException;
import org.library.interfaces.GenreRepository;

import java.util.List;

public class GenreService {
    private final GenreRepository repository;

    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public List<Genre> findAll() {
        return repository.findAll();
    }

    public Genre findById(Integer id) throws EntityNotFoundByIdException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(Genre.class, id));
    }

    public Genre findByTitle(String title) throws EntityNotFoundByTitleException {
        return repository.findByTitle(title).orElseThrow(() -> new EntityNotFoundByTitleException(Genre.class, title));
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

    public boolean save(Genre genre) {
        return repository.save(genre);
    }

    public long count() {
        return repository.count();
    }
}
