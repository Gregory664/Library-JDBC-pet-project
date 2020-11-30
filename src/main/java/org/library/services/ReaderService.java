package org.library.services;

import org.library.entity.Reader;

import java.util.List;
import java.util.Optional;

public class ReaderService {
    //  TODO add BookRentRepository
    //  add Map<BookCopy, Period> rentBooksByReader = bookRentRepository.getRentBookCopiesByReaderId(id);
    //  to reader
//    private final BookRentService bookRentService = new BookRentService();

    public boolean existsByPassport(String passport) {
        return false;
    }

    public List<Reader> findByFioLike(String searchFio) {
        return null;
    }

    public List<Reader> findAll() {
        return null;
    }

    public Optional<Reader> findById(Integer id) {
        return Optional.empty();
    }

    public boolean existsById(Integer id) {
        return false;
    }

    public void deleteAll() {

    }

    public boolean deleteById(Integer id) {
        return false;
    }

    public boolean save(Reader reader) {
        return false;
    }

    public long count() {
        return 0;
    }
}
