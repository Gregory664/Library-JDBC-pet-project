package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.Shelf;
import org.library.exceptions.newExc.ShelfNotFoundByIdException;
import org.library.interfaces.ShelfRepository;
import org.library.repositories.ShelfRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShelfServiceTest {
    private static final ShelfRepository repository = mock(ShelfRepositoryImpl.class);
    private static List<Shelf> shelfList;
    private final ShelfService shelfService = new ShelfService(repository);

    @BeforeAll
    static void initialize() {
        shelfList = List.of(
                new Shelf(1, "Z1"),
                new Shelf(2, "Z2"),
                new Shelf(3, "Z3")
        );

        when(repository.findAll()).thenReturn(shelfList);
        when(repository.findById(1)).thenReturn(Optional.of(shelfList.get(0)));
        when(repository.existsById(1)).thenReturn(true);
        when(repository.deleteById(1)).thenReturn(true);
        when(repository.save(shelfList.get(0))).thenReturn(true);
        when(repository.count()).thenReturn(3L);
    }

    @Test
    void findAll() {
        List<Shelf> all = shelfService.findAll();
        assertNotNull(all);
        assertEquals(shelfList, all);
    }

    @Test
    void findById() throws ShelfNotFoundByIdException {
        Shelf byId = shelfService.findById(1);
        assertNotNull(byId);
        assertEquals(shelfList.get(0), byId);

        Throwable throwable = assertThrows(ShelfNotFoundByIdException.class, () -> shelfService.findById(4));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void existsById() {
        assertTrue(shelfService.existsById(1));
        assertFalse(shelfService.existsById(4));
        verify(repository, times(1)).existsById(1);
        verify(repository, times(1)).existsById(4);
    }

    @Test
    void deleteAll() {
        shelfService.deleteAll();
        verify(repository, times(1)).deleteAll();
    }

    @Test
    void deleteById() {
        assertTrue(shelfService.deleteById(1));
        assertFalse(shelfService.deleteById(4));
        verify(repository, times(1)).deleteById(1);
        verify(repository, times(1)).deleteById(4);
    }

    @Test
    void save() {
        assertTrue(shelfService.save(shelfList.get(0)));
        assertFalse(shelfService.save(shelfList.get(1)));
        verify(repository, times(1)).save(shelfList.get(0));
        verify(repository, times(1)).save(shelfList.get(1));
    }

    @Test
    void count() {
        assertEquals(3, shelfService.count());
        verify(repository, times(1)).count();
    }
}