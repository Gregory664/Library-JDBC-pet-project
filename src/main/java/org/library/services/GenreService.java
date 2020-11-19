package org.library.services;

import org.library.entity.Genre;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.IGenre;
import org.library.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreService implements IGenre {
    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT * FROM genre;";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                genres.add(new Genre(id, title));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return genres;
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        String query = "SELECT * FROM genre WHERE id = ?;";
        Genre genre = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int newId = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    genre = new Genre(newId, title);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(genre);
    }

    @Override
    public boolean existsById(Integer id) {
        String query = "SELECT COUNT(1) FROM genre WHERE id = ?;";
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
        String query = "DELETE FROM genre;";

        try (Connection connection = ConnectionUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        boolean result;
        String query = "DELETE FROM genre WHERE id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }

    @Override
    public boolean save(Genre genre) {
        String query = "INSERT INTO genre (title) VALUES (?);";
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, genre.getTitle());
            isSave = statement.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isSave;
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM genre;";
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
