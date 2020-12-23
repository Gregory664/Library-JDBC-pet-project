package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.Genre;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.exceptions.newExc.EntityNotFoundByTitleException;
import org.library.interfaces.GenreRepository;
import org.library.repositories.GenreRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenreServiceTest {
    private static final GenreRepository repository = mock(GenreRepositoryImpl.class);
    private static final GenreService service = new GenreService(repository);
    private static List<Genre> genreList;

    @BeforeAll
    static void initialize() {
        genreList = List.of(
                new Genre(1, "genre 1"),
                new Genre(2, "genre 2"),
                new Genre(3, "genre 3")
        );

        when(repository.findAll()).thenReturn(genreList);
        when(repository.findById(1)).thenReturn(Optional.of(genreList.get(0)));
        when(repository.findByTitle("genre 1")).thenReturn(Optional.of(genreList.get(0)));
        when(repository.existsById(1)).thenReturn(true);
        when(repository.deleteById(1)).thenReturn(true);
        when(repository.save(genreList.get(0))).thenReturn(true);
        when(repository.count()).thenReturn(3L);
        when(repository.update(genreList.get(0))).thenReturn(true);
    }

    @Test
    void findAll() {
        List<Genre> all = service.findAll();
        assertNotNull(all);
        assertEquals(genreList, all);
    }

    @Test
    void findById() throws EntityNotFoundByIdException {
        Genre byId = service.findById(1);
        assertNotNull(byId);
        assertEquals(genreList.get(0), byId);

        Throwable throwable = assertThrows(EntityNotFoundByIdException.class, () -> service.findById(4));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void findByTitle() throws EntityNotFoundByTitleException {
        Genre byTitle = service.findByTitle("genre 1");
        assertNotNull(byTitle);
        assertEquals(genreList.get(0), byTitle);

        Throwable throwable = assertThrows(EntityNotFoundByTitleException.class, () -> service.findByTitle("genre 4"));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void existsById() {
        assertTrue(service.existsById(1));
        assertFalse(service.existsById(4));
        verify(repository, times(1)).existsById(1);
        verify(repository, times(1)).existsById(4);
    }

    @Test
    void deleteAll() {
        service.deleteAll();
        verify(repository, times(1)).deleteAll();
    }

    @Test
    void deleteById() {
        assertTrue(service.deleteById(1));
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void save() {
        assertTrue(service.save(genreList.get(0)));
        verify(repository, times(1)).save(genreList.get(0));
    }

    @Test
    void count() {
        assertEquals(3, service.count());
        verify(repository, times(1)).count();
    }

    @Test
    void update() {
        assertTrue(service.update(genreList.get(0)));
        verify(repository).update(genreList.get(0));
    }
}