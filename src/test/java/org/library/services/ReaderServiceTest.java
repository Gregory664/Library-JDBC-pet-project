package org.library.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.entity.Reader;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReaderServiceTest {
    ReaderService service = new ReaderService();
    Reader reader = null;

    @BeforeEach
    void setUpReader() {
        reader = new Reader();
        reader.setFio("test fio");
        reader.setAge(111);
        reader.setAddress("test address");
        reader.setPhone("88005553535");
        reader.setPassport("2000111222");
    }

    @Test
    void existsByPassport() {
        service.save(reader);
        assertTrue(service.existsByPassport(reader.getPassport()));

        int readerId = service.findAll().stream()
                .filter(reader1 -> reader1.getPassport().equals(reader.getPassport()))
                .findFirst()
                .get().getId();
        service.deleteById(readerId);
    }

    @Test
    void findByFioLike() {
        service.save(reader);
        List<Reader> byFioLike = service.findByFioLike("test");
        assertTrue(byFioLike.stream().anyMatch(reader1 -> reader1.getFio().contains("test")));
        int readerId = byFioLike.stream()
                .filter(reader1 -> reader1.getPassport().equals(reader.getPassport()))
                .findFirst().get().getId();
        service.deleteById(readerId);
    }

    @Test
    void findAll() {
        List<Reader> readers = service.findAll();
        assertNotNull(readers);
        assertNotEquals(0, readers.size());
    }

    @Test
    void findById() {
        Optional<Reader> optionalReader = service.findById(1);
        assertTrue(optionalReader.isPresent());
        Reader reader = optionalReader.get();
        assertNotEquals(0, reader.getId());
        assertNotNull(reader.getFio());
        assertNotEquals(0, reader.getAge());
        assertNotNull(reader.getAddress());
        assertNotNull(reader.getPhone());
        assertNotNull(reader.getPassport());
    }

    @Test
    void existsById() {
        assertTrue(service.existsById(25));
        assertFalse(service.existsById(-1));
    }

    @Test
    void deleteAll() {
        long beforeDeleteAll = service.count();
        List<Reader> readers = service.findAll();
        assertEquals(beforeDeleteAll, readers.size());

        service.deleteAll();
        long afterDeleteAll = service.count();
        assertEquals(0, afterDeleteAll);

        readers.forEach(service::save);
        long afterAdd = service.count();
        assertEquals(beforeDeleteAll, afterAdd);
    }

    @Test
    void deleteById() {
        long beforeAdd = service.count();
        service.save(reader);
        long afterADD = service.count();
        assertEquals(beforeAdd + 1, afterADD);

        List<Reader> readers = service.findAll();
        int lastId = readers.get(readers.size() - 1).getId();
        service.deleteById(lastId);
        long afterDelete = service.count();
        assertEquals(beforeAdd, afterDelete);
    }

    @Test
    void save() {
        long beforeAdd = service.count();
        assertTrue(service.save(reader));
        long afterAdd = service.count();
        assertEquals(beforeAdd + 1, afterAdd);

        List<Reader> readers = service.findAll();
        Optional<Reader> optionalReader = readers.stream().
                filter(reader1 -> reader1.getPassport().equals(reader.getPassport()))
                .findFirst();

        assertTrue(optionalReader.isPresent());
        service.deleteById(optionalReader.get().getId());
    }

    @Test
    void count() {
        List<Reader> readers = service.findAll();
        assertEquals(readers.size(), service.count());
    }
}