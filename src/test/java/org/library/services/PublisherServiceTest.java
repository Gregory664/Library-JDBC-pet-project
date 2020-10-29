package org.library.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.entity.Publisher;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PublisherServiceTest {
    PublisherService service = new PublisherService();
    Publisher publisher = null;

    @BeforeEach
    void setUpPublisher() {
        publisher = new Publisher();
        publisher.setTitle("test publisher");
    }

    @Test
    void findAll() {
        List<Publisher> publishers = service.findAll();
        assertNotNull(publishers);
    }

    @Test
    void findById() {
        Optional<Publisher> optionalPublisher = service.findById(1);
        assertTrue(optionalPublisher.isPresent());
        Publisher publisher = optionalPublisher.get();
        assertNotEquals(0, publisher.getId());
        assertNotNull(publisher.getTitle());
    }

    @Test
    void existsById() {
        assertTrue(service.existsById(1));
        assertFalse(service.existsById(-1));
    }

    @Test
    void deleteAll() {
        long beforeDeleteAll = service.count();
        List<Publisher> publishers = service.findAll();
        assertEquals(beforeDeleteAll, publishers.size());

        service.deleteAll();
        long afterDeleteAll = service.count();
        assertEquals(0, afterDeleteAll);

        publishers.forEach(service::save);
        long afterAdd = service.count();
        assertEquals(beforeDeleteAll, afterAdd);
    }

    @Test
    void deleteById() {
        long beforeAdd = service.count();
        service.save(publisher);
        long afterADD = service.count();
        assertEquals(beforeAdd + 1, afterADD);

        List<Publisher> publishers = service.findAll();
        int lastId = publishers.stream().max(Comparator.comparingInt(Publisher::getId)).get().getId();
        service.deleteById(lastId);
        long afterDelete = service.count();
        assertEquals(beforeAdd, afterDelete);
    }

    @Test
    void save() {
        long beforeAdd = service.count();
        assertTrue(service.save(publisher));
        long afterAdd = service.count();
        assertEquals(beforeAdd + 1, afterAdd);

        List<Publisher> publishers = service.findAll();
        Optional<Publisher> optionalPublisher = publishers.stream().
                filter(publisher1 -> publisher1.getTitle().equals(publisher.getTitle()))
                .findFirst();

        assertTrue(optionalPublisher.isPresent());
        service.deleteById(optionalPublisher.get().getId());
    }

    @Test
    void count() {
        List<Publisher> publishers = service.findAll();
        assertEquals(publishers.size(), service.count());
    }
}