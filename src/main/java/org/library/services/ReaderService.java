package org.library.services;

import org.library.entity.Reader;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.BookRentRepository;
import org.library.interfaces.ReaderRepository;

import java.util.List;

public class ReaderService {
    private final ReaderRepository readerRepository;
    private final BookRentRepository bookRentRepository;

    public ReaderService(ReaderRepository readerRepository, BookRentRepository bookRentRepository) {
        this.readerRepository = readerRepository;
        this.bookRentRepository = bookRentRepository;
    }

    public boolean existsByPassport(String passport) {
        return readerRepository.existsByPassport(passport);
    }

    public List<Reader> findByFioLike(String searchFio) {
        List<Reader> byFioLike = readerRepository.findByFioLike(searchFio);
        for (Reader reader : byFioLike) {
            reader.setRentBookCopies(bookRentRepository.getRentBookCopiesByReaderId(reader.getId()));
        }
        return byFioLike;
    }

    public List<Reader> findAll() {
        List<Reader> all = readerRepository.findAll();
        for (Reader reader : all) {
            reader.setRentBookCopies(bookRentRepository.getRentBookCopiesByReaderId(reader.getId()));
        }
        return all;
    }

    public Reader findById(Integer id) throws EntityNotFoundByIdException {
        return readerRepository.findById(id).orElseThrow(() -> new EntityNotFoundByIdException(Reader.class, id));
    }

    public boolean existsById(Integer id) {
        return readerRepository.existsById(id);
    }

    public void deleteAll() {
        readerRepository.deleteAll();
    }

    public boolean deleteById(Integer id) {
        return readerRepository.deleteById(id);
    }

    public boolean save(Reader reader) {
        return readerRepository.save(reader);
    }

    public long count() {
        return readerRepository.count();
    }

    public boolean update(Reader reader) {
        return readerRepository.update(reader);
    }
}
