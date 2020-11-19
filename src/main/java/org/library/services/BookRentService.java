package org.library.services;

import org.library.entity.BookCopy;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.entity.Shelf;
import org.library.exceptions.BookIsExistsInReaderException;
import org.library.exceptions.RentBookNotFoundInReader;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.IBookRent;
import org.library.utils.ConnectionUtils;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

import static org.library.utils.statements.BookRentSQLStatements.*;

public class BookRentService implements IBookRent {
    private final BookCopyService bookCopyService = new BookCopyService();
    private final BookShelfService bookShelfService = new BookShelfService();

    @Override
    public Map<BookCopy, Period> getRentBookCopiesByReaderId(int readerId) {
        Map<BookCopy, Period> rentBooksByReaderIdMap = new TreeMap<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_RENT_BOOK_COPY_BY_READER_ID)) {
            statement.setInt(1, readerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookCopyId = resultSet.getInt(1);
                    Date startDate = resultSet.getDate(2);
                    Date endDate = resultSet.getDate(3);
                    BookCopy bookCopy = bookCopyService.findById(bookCopyId).orElseThrow(SQLException::new);
                    rentBooksByReaderIdMap.put(bookCopy, new Period(startDate.toLocalDate(), endDate.toLocalDate()));
                }
            }

        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return rentBooksByReaderIdMap;
    }

    @Override
    public boolean deleteRentBookCopiesFromReader(Reader reader, BookCopy bookCopy, Shelf shelf) {
        Map<BookCopy, Period> rentBookCopies = reader.getRentBookCopies();
        boolean result;

        if (!rentBookCopies.containsKey(bookCopy)) {
            throw new RentBookNotFoundInReader(reader.getId(), bookCopy.getId());
        }
        rentBookCopies.remove(bookCopy);
        bookShelfService.addBookCopyToShelf(bookCopy, shelf);

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RENT_BOOK_COPY)) {
            statement.setInt(1, reader.getId());
            statement.setInt(2, bookCopy.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }

    @Override
    public boolean addRentBookCopiesToReader(Reader reader, BookCopy bookCopy, Period period, Shelf shelf) {
        if (reader.getRentBookCopies().containsKey(bookCopy)) {
            throw new BookIsExistsInReaderException(bookCopy.getId(), reader.getId());
        }
        bookShelfService.deleteBookCopyFromShelf(bookCopy, shelf);
        reader.getRentBookCopies().put(bookCopy, period);
        boolean result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_RENT_BOOK_COPY_TO_READER)) {
            statement.setInt(1, reader.getId());
            statement.setInt(2, bookCopy.getId());
            statement.setDate(3, Date.valueOf(period.getStartDate()));
            statement.setDate(4, Date.valueOf(period.getEndDate()));
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }
}
