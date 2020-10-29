package org.library.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.entity.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GenreServiceTest {
    GenreService service = new GenreService();
    Genre genre = null;

    @BeforeEach
    void setUpGenre() {
        genre = new Genre();
        genre.setTitle("test genre title");
    }

    @Test
    void findAll() {
        List<Genre> genres = service.findAll();
        assertNotNull(genres);
    }

    @Test
    void findById() {
        Optional<Genre> optionalGenre = service.findById(1);
        assertTrue(optionalGenre.isPresent());
        Genre genre = optionalGenre.get();
        assertNotEquals(0, genre.getId());
        assertNotNull(genre.getTitle());
    }

    @Test
    void existsById() {
        assertTrue(service.existsById(10));
        assertFalse(service.existsById(-1));
    }

    @Test
    void deleteAll() {
        long beforeDeleteAll = service.count();
        List<Genre> genres = service.findAll();
        assertEquals(beforeDeleteAll, genres.size());

        service.deleteAll();
        long afterDeleteAll = service.count();
        assertEquals(0, afterDeleteAll);

        genres.forEach(service::save);
        long afterAdd = service.count();
        assertEquals(beforeDeleteAll, afterAdd);
    }

    @Test
    void deleteById() {
        long beforeAdd = service.count();
        service.save(genre);
        long afterADD = service.count();
        assertEquals(beforeAdd + 1, afterADD);

        List<Genre> genres = service.findAll();
        int lastId = genres.stream().max(Comparator.comparingInt(Genre::getId)).get().getId();
        service.deleteById(lastId);
        long afterDelete = service.count();
        assertEquals(beforeAdd, afterDelete);
    }

    @Test
    void save() {
        long beforeAdd = service.count();
        assertTrue(service.save(genre));
        long afterAdd = service.count();
        assertEquals(beforeAdd + 1, afterAdd);

        List<Genre> genres = service.findAll();
        Optional<Genre> optionalGenre = genres.stream().
                filter(genre1 -> genre1.getTitle().equals(genre.getTitle()))
                .findFirst();

        assertTrue(optionalGenre.isPresent());
        service.deleteById(optionalGenre.get().getId());
    }

    @Test
    void count() {
        List<Genre> genres = service.findAll();
        assertEquals(genres.size(), service.count());
    }
}