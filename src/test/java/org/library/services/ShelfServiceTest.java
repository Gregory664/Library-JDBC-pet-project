package org.library.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.entity.Shelf;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShelfServiceTest {
    ShelfService service = new ShelfService();

    @Test
    void findAll() {
        List<Shelf> shelves = service.findAll();
        assertNotNull(shelves);
        assertNotEquals(0, shelves.size());
    }


    @Test
    void findById() {
        Optional<Shelf> optionalShelf = service.findById(1);
        assertTrue(optionalShelf.isPresent());
        Shelf shelf = optionalShelf.get();
        assertNotEquals(0, shelf.getId());
        assertNotNull(shelf.getInventNum());
    }

    @Test
    void existsById() {
        assertTrue(service.existsById(12));
        assertFalse(service.existsById(-1));
    }

    @Test
    void deleteAll() {
        long beforeDeleteAll = service.count();
        List<Shelf> shelves = service.findAll();
        assertEquals(beforeDeleteAll, shelves.size());

        service.deleteAll();
        long afterDeleteAll = service.count();
        assertEquals(0, afterDeleteAll);

        shelves.forEach(service::save);
        long afterAdd = service.count();
        assertEquals(beforeDeleteAll, afterAdd);
    }

    @Test
    void deleteById() {
        long beforeAdd = service.count();
        Shelf newShelf = new Shelf();
        newShelf.setInventNum("TEST");
        service.save(newShelf);
        long afterADD = service.count();
        assertEquals(beforeAdd + 1, afterADD);

        List<Shelf> shelves = service.findAll();
        int lastId = shelves.get(shelves.size() - 1).getId();
        service.deleteById(lastId);
        long afterDelete = service.count();
        assertEquals(beforeAdd, afterDelete);
    }

    @Test
    void save() {
        Shelf newShelf = new Shelf();
        newShelf.setInventNum("TEST");
        long beforeAdd = service.count();
        assertTrue(service.save(newShelf));
        long afterAdd = service.count();
        assertEquals(beforeAdd + 1, afterAdd);

        List<Shelf> shelves = service.findAll();
        Optional<Shelf> optionalShelf = shelves.stream()
                .filter(shelf -> shelf.getInventNum().equals(newShelf.getInventNum()))
                .findFirst();
        assertTrue(optionalShelf.isPresent());
        service.deleteById(optionalShelf.get().getId());
    }

    @Test
    void count() {
        List<Shelf> shelves = service.findAll();
        assertEquals(shelves.size(), service.count());
    }
}