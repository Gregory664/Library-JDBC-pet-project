package org.library.services;

import org.library.entity.Author;
import org.library.exceptions.AuthorNotFoundByIdException;
import org.library.exceptions.AuthorNotFoundByNameException;
import org.library.interfaces.AuthorRepository;

import java.util.List;

public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Integer id) throws AuthorNotFoundByIdException {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundByIdException(id));
    }

    public boolean existsById(Integer id) {
        return authorRepository.existsById(id);
    }

    public void deleteAll() {
        authorRepository.deleteAll();
    }

    public long count() {
        return authorRepository.count();
    }

    public boolean deleteById(Integer id) {
        return authorRepository.deleteById(id);
    }

    public Author findByName(String name) throws AuthorNotFoundByNameException {
        return authorRepository.findByName(name).orElseThrow(() -> new AuthorNotFoundByNameException(name));
    }

    public boolean save(Author author) {
        return authorRepository.save(author);
    }
}
