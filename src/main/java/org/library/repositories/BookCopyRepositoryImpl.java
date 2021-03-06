package org.library.repositories;

import org.library.entity.Book;
import org.library.entity.BookCopy;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.interfaces.BookCopyRepository;
import org.library.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.library.utils.statements.AuthorSQLStatements.UPDATE;
import static org.library.utils.statements.BookCopySQLStatements.*;

public class BookCopyRepositoryImpl implements BookCopyRepository {
    @Override
    public List<BookCopy> findAll() {
        List<BookCopy> bookCopies = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookCopyId = resultSet.getInt("id");
                    int bookId = resultSet.getInt("copyId");
                    bookCopies.add(new BookCopy(bookCopyId, Book.builder().id(bookId).build()));
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return bookCopies;
    }

    @Override
    public Optional<BookCopy> findById(Integer id) {
        BookCopy bookCopy = null;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int bookCopyId = resultSet.getInt("id");
                    int bookId = resultSet.getInt("book_id");
                    bookCopy = new BookCopy(bookCopyId, Book.builder().id(bookId).build());
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(bookCopy);
    }

    @Override
    public boolean existsById(Integer id) {
        boolean result;
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                result = resultSet.getInt(1) == 1;
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = MySQLConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_ALL);
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean result;
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, id);
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }

    @Override
    public boolean save(BookCopy bookCopy) {
        boolean result;
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, bookCopy.getBook().getId());
            result = statement.executeUpdate() == 1;

            if (result) {
                try (ResultSet set = statement.getGeneratedKeys()) {
                    while (set.next()) {
                        bookCopy.setId(set.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }

    @Override
    public long count() {
        long result = 0;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }

    @Override
    public boolean update(BookCopy bookCopy) {
        boolean result;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setInt(1, bookCopy.getBook().getId());
            statement.setInt(2, bookCopy.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }
}
