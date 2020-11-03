package org.library.services;

import org.library.entity.Book;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.entity.Shelf;
import org.library.exceptions.RentBookNotFoundInReader;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.IBookRent;
import org.library.utils.ConnectionUtils;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

public class BookRentService implements IBookRent {
    BookService bookService = new BookService();
    BookShelfService bookShelfService = new BookShelfService();

    @Override
    public Map<Book, Period> getRentBooksByReaderId(int readerId) {
        String rentBooksByReaderIdQuery = "" +
                "SELECT book_id, start_date, end_date " +
                "FROM   book_rent " +
                "WHERE  reader_id = ?;";

        Map<Book, Period> rentBooksByReaderIdMap = new TreeMap<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(rentBooksByReaderIdQuery)) {
            statement.setInt(1, readerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookId = resultSet.getInt(1);
                    Date startDate = resultSet.getDate(2);
                    Date endDate = resultSet.getDate(3);
                    Book book = bookService.findById(bookId).orElseThrow(SQLException::new);
                    rentBooksByReaderIdMap.put(book, new Period(startDate.toLocalDate(), endDate.toLocalDate()));
                }
            }

        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return rentBooksByReaderIdMap;
    }

    @Override
    public boolean deleteRentBookFromReader(Reader reader, Book book, Shelf shelf) {
        String query = "DELETE FROM book_rent WHERE reader_id = ? AND book_id = ?;";
        Map<Book, Period> rentBooks = reader.getRentBooks();
        boolean result;

        if (!rentBooks.containsKey(book)) {
            throw new RentBookNotFoundInReader(reader.getId(), book.getId());
        }
        rentBooks.remove(book);
        bookShelfService.addBookToShelf(book, shelf);

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reader.getId());
            statement.setInt(2, book.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }

    @Override
    public boolean addRentBookToReader(Reader reader, Book book, Period period, Shelf shelf) {
        String query = "INSERT INTO book_rent (reader_id, book_id, start_date, end_date) VALUES(?, ?, ?, ?)";
        bookShelfService.deleteBookFromShelf(book, shelf);
        reader.getRentBooks().put(book, period);
        boolean result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reader.getId());
            statement.setInt(2, book.getId());
            statement.setDate(3, Date.valueOf(period.getStartDate()));
            statement.setDate(4, Date.valueOf(period.getEndDate()));
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }
}
