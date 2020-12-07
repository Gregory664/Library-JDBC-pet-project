package org.library.repositories;

import org.library.entity.BookCopy;
import org.library.entity.Shelf;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.interfaces.BookShelfRepository;
import org.library.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import static org.library.utils.statements.BookShelfSQLStatements.*;

public class BookShelfRepositoryImpl implements BookShelfRepository {
    @Override
    public Map<Integer, Shelf> getBookCopyIdAndShelf(int bookId) {
        Map<Integer, Shelf> bookCopyIdAndShelf = new TreeMap<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(BOOK_COPY_AND_SHELF)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookCopyId = resultSet.getInt(1);
                    int shelfId = resultSet.getInt(2);
                    String shelfInventNum = resultSet.getString(3);
                    bookCopyIdAndShelf.put(bookCopyId, new Shelf(shelfId, shelfInventNum));
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return bookCopyIdAndShelf;
    }

    @Override
    public boolean deleteBookCopyFromShelf(BookCopy bookCopy, Shelf shelf) {
        boolean result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_FROM_BOOK_SHELF)) {
            statement.setInt(1, shelf.getId());
            statement.setInt(2, bookCopy.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }

    @Override
    public boolean addBookCopyToShelf(BookCopy bookCopy, Shelf shelf) {
        boolean result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_BOOK_COPY_TO_SHELF)) {
            statement.setInt(1, bookCopy.getId());
            statement.setInt(2, shelf.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }
}
