package org.library.services;

import org.junit.jupiter.api.Test;
import org.library.entity.Author;
import org.library.exceptions.AuthorNotFoundByIdException;
import org.library.exceptions.AuthorNotFoundByNameException;
import org.library.interfaces.AuthorRepository;
import org.library.repositories.AuthorRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthorServiceTest {
    private final AuthorRepository repository = mock(AuthorRepositoryImpl.class);
    private final AuthorService service = new AuthorService(repository);

    @Test
    void findAll() {
        List<Author> list = List.of(
                new Author(1, "author1"),
                new Author(1, "author1"),
                new Author(1, "author1")
        );
        when(repository.findAll()).thenReturn(list);
        assertEquals(list, service.findAll());
    }

    @Test
    void findById() throws AuthorNotFoundByIdException {
        Author author = new Author(1, "author1");
        when(repository.findById(1)).thenReturn(Optional.of(author));
        assertNotNull(service.findById(1));
        assertEquals(author, service.findById(1));

        Throwable throwable = assertThrows(AuthorNotFoundByIdException.class, () -> service.findById(1));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void existsById() {
        when(repository.existsById(1)).thenReturn(true);
        assertTrue(service.existsById(1));
    }

    @Test
    void count() {
        when(repository.count()).thenReturn(1L);
        assertEquals(1, repository.count());
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByName() throws AuthorNotFoundByNameException {
        Author author = new Author(1, "author1");
        when(repository.findByName("author1")).thenReturn(Optional.of(author));
        assertNotNull(service.findByName("author1"));
        assertEquals(author, service.findByName("author1"));
        assertEquals(author.getName(), service.findByName("author1").getName());

        Throwable throwable = assertThrows(AuthorNotFoundByNameException.class, () -> service.findByName("author2"));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }
}