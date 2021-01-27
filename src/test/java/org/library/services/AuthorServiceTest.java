package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.Author;
import org.library.exceptions.newExc.AuthorNotFoundByNameException;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.AuthorRepository;
import org.library.repositories.AuthorRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {
    private static final AuthorRepository repository = mock(AuthorRepositoryImpl.class);
    private static final AuthorService service = new AuthorService(repository);
    private static List<Author> authorList;

    @BeforeAll
    static void initialize() {
        authorList = List.of(
                new Author(1, "author1"),
                new Author(2, "author2"),
                new Author(1, "author3")
        );

        when(repository.findAll()).thenReturn(authorList);
        when(repository.findById(1)).thenReturn(Optional.of(authorList.get(0)));
        when(repository.existsById(1)).thenReturn(true);
        when(repository.count()).thenReturn(3L);
        when(repository.deleteById(1)).thenReturn(true);
        when(repository.findByName("author1")).thenReturn(Optional.of(authorList.get(0)));
        when(repository.save(authorList.get(0))).thenReturn(true);
        when(repository.update(authorList.get(0))).thenReturn(true);
    }

    @Test
    void findAll() {
        List<Author> all = service.findAll();
        assertNotNull(all);
        assertEquals(authorList, service.findAll());
    }

    @Test
    void findById() throws EntityNotFoundByIdException {
        Author author = new Author(1, "author1");
        assertNotNull(service.findById(1));
        assertEquals(author, service.findById(1));

        Throwable throwable = assertThrows(EntityNotFoundByIdException.class, () -> service.findById(2));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void deleteAll() {
        service.deleteAll();
        verify(repository, times(1)).deleteAll();
    }

    @Test
    void existsById() {
        assertTrue(service.existsById(1));
        verify(repository, times(1)).existsById(1);
    }

    @Test
    void count() {
        assertEquals(3, service.count());
        verify(repository, times(1)).count();
    }

    @Test
    void deleteById() {
        assertTrue(service.deleteById(1));
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void findByName() throws AuthorNotFoundByNameException {
        Author byName = service.findByName(authorList.get(0).getName());
        assertNotNull(byName);
        assertEquals(authorList.get(0), byName);

        Throwable throwable = assertThrows(AuthorNotFoundByNameException.class, () -> service.findByName("author2"));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void save() {
        assertTrue(service.save(authorList.get(0)));
        verify(repository, times(1)).save(authorList.get(0));
    }

    @Test
    void update() {
        assertTrue(service.update(authorList.get(0)));
        verify(repository).update(authorList.get(0));
    }
}