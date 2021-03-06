package org.library.repositories;

import org.library.entity.Publisher;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.interfaces.PublisherRepository;
import org.library.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.library.utils.statements.PublisherSQLStatements.*;

public class PublisherRepositoryImpl implements PublisherRepository {
    @Override
    public Optional<Publisher> findByTitle(String title) {
        Publisher publisher = null;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TITLE)) {
            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String fTitle = resultSet.getString("title");
                    publisher = new Publisher(id, fTitle);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(publisher);
    }

    @Override
    public List<Publisher> findAll() {
        List<Publisher> publishers = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                publishers.add(new Publisher(id, title));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return publishers;
    }

    @Override
    public Optional<Publisher> findById(Integer id) {
        Publisher publisher = null;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int newId = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    publisher = new Publisher(newId, title);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(publisher);
    }

    @Override
    public boolean existsById(Integer id) {
        boolean isExists = false;

        try (Connection connection = MySQLConnection.getConnection();
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
        try (Connection connection = MySQLConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE);
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
    public boolean save(Publisher publisher) {
        boolean isSave;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, publisher.getTitle());
            isSave = statement.executeUpdate() == 1;

            if (isSave) {
                try (ResultSet set = statement.getGeneratedKeys()) {
                    while (set.next()) {
                        publisher.setId(set.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isSave;
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
    public boolean update(Publisher publisher) {
        boolean result;

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, publisher.getTitle());
            statement.setInt(2, publisher.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }
}
