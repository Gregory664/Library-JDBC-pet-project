package org.library.services;

import org.library.entity.Reader;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.IReader;
import org.library.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReaderService implements IReader {
    @Override
    public boolean existsByPassport(String passport) {
        String query = "SELECT COUNT(*) FROM reader WHERE passport = ?;";
        boolean isExists;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passport);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                isExists = resultSet.getInt(1) == 1;
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isExists;
    }

    @Override
    public List<Reader> findByFioLike(String searchFio) {
        String query = "SELECT * FROM reader WHERE fio LIKE ?;";
        List<Reader> readers = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + searchFio + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String fio = resultSet.getString(2);
                    int age = resultSet.getInt(3);
                    String address = resultSet.getString(4);
                    String phone = resultSet.getString(5);
                    String passport = resultSet.getString(6);
                    readers.add(new Reader(id, fio, age, address, phone, passport));
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return readers;
    }

    @Override
    public List<Reader> findAll() {
        String query = "SELECT * FROM reader;";
        List<Reader> readers = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String fio = resultSet.getString(2);
                int age = resultSet.getInt(3);
                String address = resultSet.getString(4);
                String phone = resultSet.getString(5);
                String passport = resultSet.getString(6);
                readers.add(new Reader(id, fio, age, address, phone, passport));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return readers;
    }

    @Override
    public Optional<Reader> findById(Integer id) {
        String query = "SELECT * FROM reader WHERE id = ?;";
        Reader reader = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int newId = resultSet.getInt(1);
                    String fio = resultSet.getString(2);
                    int age = resultSet.getInt(3);
                    String address = resultSet.getString(4);
                    String phone = resultSet.getString(5);
                    String passport = resultSet.getString(6);
                    reader = new Reader(newId, fio, age, address, phone, passport);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(reader);
    }

    @Override
    public boolean existsById(Integer id) {
        String query = "SELECT COUNT(*) FROM reader WHERE id = ?;";
        boolean isExists;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                isExists = resultSet.getInt(1) == 1;
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isExists;
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM reader;";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM reader WHERE id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean save(Reader reader) {
        String query = "INSERT INTO reader (fio, age, address, phone, passport) VALUES (?, ?, ?, ?, ?);";
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
        String query = "SELECT COUNT(*) FROM reader;";
        long result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }
}
