package org.library.services;

import org.library.entity.Publisher;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.IPublisher;
import org.library.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PublisherService implements IPublisher {
    @Override
    public List<Publisher> findAll() {
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT * FROM publisher;";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
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
        String query = "SELECT * FROM publisher WHERE id = ?;";
        Publisher publisher = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
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
        String query = "SELECT COUNT(1) FROM publisher WHERE id = ?;";
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
        String query = "DELETE FROM publisher;";

        try (Connection connection = ConnectionUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM publisher WHERE id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean save(Publisher publisher) {
        String query = "INSERT INTO publisher (title) VALUES (?);";
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, publisher.getTitle());
            isSave = statement.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isSave;
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM publisher;";
        long result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }
}
