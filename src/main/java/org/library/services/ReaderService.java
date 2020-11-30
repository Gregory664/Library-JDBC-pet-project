package org.library.services;

import org.library.entity.Reader;
import org.library.interfaces.ReaderRepository;

import java.util.List;
import java.util.Optional;

public class ReaderService implements ReaderRepository {
    //  TODO add BookRentRepository
    //  add Map<BookCopy, Period> rentBooksByReader = bookRentRepository.getRentBookCopiesByReaderId(id);
    //  to reader
//    private final BookRentService bookRentService = new BookRentService();

    @Override
    public boolean existsByPassport(String passport) {
        return false;
    }

    @Override
    public List<Reader> findByFioLike(String searchFio) {
        return null;
    }

    @Override
    public List<Reader> findAll() {
        return null;
    }

    @Override
    public Optional<Reader> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean save(Reader reader) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }
}
