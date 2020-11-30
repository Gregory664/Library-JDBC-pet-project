package org.library.services;

import org.library.entity.BookCopy;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.entity.Shelf;
import org.library.interfaces.BookRentRepository;

import java.util.Map;

public class BookRentService implements BookRentRepository {
    //TODO add BookCopyRepository, BookShelfRepository
//    private final BookCopyService bookCopyService = new BookCopyService();
//    private final BookShelfService bookShelfService = new BookShelfService();

    @Override
    public Map<BookCopy, Period> getRentBookCopiesByReaderId(int readerId) {
        return null;
    }

    @Override
    public boolean deleteRentBookCopiesFromReader(Reader reader, BookCopy bookCopy, Shelf shelf) {
//        Map<BookCopy, Period> rentBookCopies = reader.getRentBookCopies();
//        boolean result;
//
//        if (!rentBookCopies.containsKey(bookCopy)) {
//            throw new RentBookNotFoundInReader(reader.getId(), bookCopy.getId());
//        }
//        rentBookCopies.remove(bookCopy);
//        bookShelfService.addBookCopyToShelf(bookCopy, shelf);
//
//        try (Connection connection = ConnectionUtils.getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_RENT_BOOK_COPY)) {
//            statement.setInt(1, reader.getId());
//            statement.setInt(2, bookCopy.getId());
//            result = statement.executeUpdate() == 1;
//        } catch (SQLException e) {
//            throw new SQLExceptionWrapper(e);
//        }
//        return result;
        return false;
    }

    @Override
    public boolean addRentBookCopiesToReader(Reader reader, BookCopy bookCopy, Period period, Shelf shelf) {
//        if (reader.getRentBookCopies().containsKey(bookCopy)) {
//            throw new BookIsExistsInReaderException(bookCopy.getId(), reader.getId());
//        }
//        bookShelfService.deleteBookCopyFromShelf(bookCopy, shelf);
//        reader.getRentBookCopies().put(bookCopy, period);
//        boolean result;
//
//        try (Connection connection = ConnectionUtils.getConnection();
//             PreparedStatement statement = connection.prepareStatement(ADD_RENT_BOOK_COPY_TO_READER)) {
//            statement.setInt(1, reader.getId());
//            statement.setInt(2, bookCopy.getId());
//            statement.setDate(3, Date.valueOf(period.getStartDate()));
//            statement.setDate(4, Date.valueOf(period.getEndDate()));
//            result = statement.executeUpdate() == 1;
//        } catch (SQLException e) {
//            throw new SQLExceptionWrapper(e);
//        }
//        return result;
        return false;
    }
}
