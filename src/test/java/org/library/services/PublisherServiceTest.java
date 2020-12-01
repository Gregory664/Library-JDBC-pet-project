package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.Publisher;
import org.library.exceptions.newExc.PublisherNotFoundByIdException;
import org.library.exceptions.newExc.PublisherNotFoundByTitleException;
import org.library.interfaces.PublisherRepository;
import org.library.repositories.PublisherRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublisherServiceTest {
    private static final PublisherRepository repository = mock(PublisherRepositoryImpl.class);
    private static final PublisherService service = new PublisherService(repository);
    private static List<Publisher> publisherList;

    @BeforeAll
    static void initialize() {
        publisherList = List.of(
                new Publisher(1, "publisher 1"),
                new Publisher(2, "publisher 2"),
                new Publisher(3, "publisher 3")
        );

        when(repository.findAll()).thenReturn(publisherList);
        when(repository.findById(1)).thenReturn(Optional.ofNullable(publisherList.get(0)));
        when(repository.findByTitle("publisher 1")).thenReturn(Optional.ofNullable(publisherList.get(0)));
        when(repository.existsById(1)).thenReturn(true);
        when(repository.deleteById(1)).thenReturn(true);
        when(repository.save(publisherList.get(0))).thenReturn(true);
        when(repository.count()).thenReturn(3L);
    }

    @Test
    void findAll() {
        List<Publisher> all = service.findAll();
        assertNotNull(all);
        assertEquals(publisherList, all);
    }

    @Test
    void findById() throws PublisherNotFoundByIdException {
        Publisher byId = service.findById(1);
        assertNotNull(byId);
        assertEquals(publisherList.get(0), byId);

        Throwable throwable = assertThrows(PublisherNotFoundByIdException.class, () -> service.findById(4));
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
        assertTrue(service.save(publisherList.get(0)));
        verify(repository, times(1)).save(publisherList.get(0));
    }

    @Test
    void count() {
        assertEquals(3, service.count());
        verify(repository, times(1)).count();
    }

    @Test
    void findByTitle() throws PublisherNotFoundByTitleException {
        Publisher byPublisher = service.findByTitle("publisher 1");
        assertNotNull(byPublisher);
        assertEquals(publisherList.get(0), byPublisher);

        Throwable throwable = assertThrows(PublisherNotFoundByIdException.class, () -> service.findById(4));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }
}