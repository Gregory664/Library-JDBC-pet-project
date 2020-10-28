package org.library.services;

import org.junit.jupiter.api.Test;
import org.library.entity.Author;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {
    AuthorService service = new AuthorService();

    @Test
    void findAll() {
        List<Author> authors = service.findAll();
        assertNotNull(authors);
    }

    @Test
    void findById() {
        Optional<Author> optionalAuthor = service.findById(1);
        assertTrue(optionalAuthor.isPresent());
        Author author = optionalAuthor.get();
        assertNotEquals(0, author.getId());
        assertNotNull(author.getName());
    }

    @Test
    void existsById() {
        assertTrue(service.existsById(1));
        assertFalse(service.existsById(-1));
    }

    @Test
    void deleteAll() {
        long beforeDeleteAll = service.count();
        List<Author> authors = service.findAll();
        assertEquals(beforeDeleteAll, authors.size());

        service.deleteAll();
        long afterDeleteAll = service.count();
        assertEquals(0, afterDeleteAll);

        authors.forEach(service::save);
        long afterAdd = service.count();
        assertEquals(beforeDeleteAll, afterAdd);
    }

    @Test
    void deleteById() {
        long beforeAdd = service.count();
        Author author = new Author();
        author.setName("test author");
        service.save(author);
        long afterADD = service.count();
        assertEquals(beforeAdd + 1, afterADD);

        List<Author> authors = service.findAll();
        int lastId = authors.get(authors.size() - 1).getId();
        service.deleteById(lastId);
        long afterDelete = service.count();
        assertEquals(beforeAdd, afterDelete);
    }

    @Test
    void save() {
        Author newAuthor = new Author();
        newAuthor.setName("test author");
        long beforeAdd = service.count();
        assertTrue(service.save(newAuthor));
        long afterAdd = service.count();
        assertEquals(beforeAdd + 1, afterAdd);

        List<Author> authors = service.findAll();
        Optional<Author> optionalAuthor = authors.stream().
                filter(author -> author.getName().equals(newAuthor.getName()))
                .findFirst();

        assertTrue(optionalAuthor.isPresent());
        service.deleteById(optionalAuthor.get().getId());
    }

    @Test
    void count() {
        List<Author> authors = service.findAll();
        assertEquals(authors.size(), service.count());
    }
}