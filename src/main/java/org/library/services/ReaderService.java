package org.library.services;

import org.library.entity.BookCopy;
import org.library.entity.Period;
import org.library.entity.Reader;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.interfaces.ReaderRepository;
import org.library.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.library.utils.statements.ReaderSQLStatements.*;

public class ReaderService implements ReaderRepository {
    private final BookRentService bookRentService = new BookRentService();

    private Reader getReaderFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt(1);
            String fio = resultSet.getString(2);
            int age = resultSet.getInt(3);
            String address = resultSet.getString(4);
            String phone = resultSet.getString(5);
            String passport = resultSet.getString(6);

            Map<BookCopy, Period> rentBooksByReader = bookRentService.getRentBookCopiesByReaderId(id);
            return new Reader(id, fio, age, address, phone, passport, rentBooksByReader);
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean existsByPassport(String passport) {
        boolean isExists = false;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_PASSPORT)) {
            statement.setString(1, passport);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    isExists = resultSet.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isExists;
    }

    @Override
    public List<Reader> findByFioLike(String searchFio) {
        List<Reader> readers = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_FIO_LIKE)) {
            statement.setString(1, "%" + searchFio + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    readers.add(getReaderFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return readers;
    }

    @Override
    public List<Reader> findAll() {
        List<Reader> readers = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                readers.add(getReaderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return readers;
    }

    @Override
    public Optional<Reader> findById(Integer id) {
        Reader reader = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reader = getReaderFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(reader);
    }

    @Override
    public boolean existsById(Integer id) {
        boolean isExists = false;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    isExists = resultSet.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isExists;
    }

    @Override
    public void deleteAll() {
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setInt(1, id);
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }

    @Override
    public boolean save(Reader reader) {
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE)) {
            statement.setString(1, reader.getFio());
            statement.setInt(2, reader.getAge());
            statement.setString(3, reader.getAddress());
            statement.setString(4, reader.getPhone());
            statement.setString(5, reader.getPassport());
            isSave = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return isSave;
    }

    @Override
    public long count() {
        long result = 0;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }
}
