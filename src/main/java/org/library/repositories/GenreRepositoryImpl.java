package org.library.repositories;

import org.library.entity.Genre;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.interfaces.GenreRepository;
import org.library.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.library.utils.statements.GenreSQLStatements.*;

public class GenreRepositoryImpl implements GenreRepository {
    @Override
    public Optional<Genre> findByTitle(String title) {
        Genre genre = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TITLE)) {
            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int newId = resultSet.getInt(1);
                    String fTitle = resultSet.getString(2);
                    genre = new Genre(newId, fTitle);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
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
        Genre genre = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
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
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE);
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
    public boolean save(Genre genre) {
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE)) {
            statement.setString(1, genre.getTitle());
            isSave = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isSave;
    }

    @Override
    public long count() {
        long result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT)) {
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
