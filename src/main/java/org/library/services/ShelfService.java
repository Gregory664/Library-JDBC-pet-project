package org.library.services;

import org.library.entity.Shelf;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.IShelf;
import org.library.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShelfService implements IShelf {
    @Override
    public List<Shelf> findAll() {
        String query = "SELECT * FROM shelf;";
        List<Shelf> shelves = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String inventNum = resultSet.getString(2);
                    shelves.add(new Shelf(id, inventNum));
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return shelves;
    }

    @Override
    public Optional<Shelf> findById(Integer id) {
        String query = "SELECT * FROM shelf WHERE id = ?;";
        Shelf shelf = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String inventNum = resultSet.getString(2);
                    shelf = new Shelf(id, inventNum);
                }
            }

        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(shelf);
    }

    @Override
    public boolean existsById(Integer id) {
        String query = "SELECT COUNT(*) FROM shelf WHERE id = ?;";
        boolean result = false;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM shelf;";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM shelf WHERE id = ?;";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean save(Shelf shelf) {
        String query = "INSERT INTO shelf (invent_num) VALUES (?);";
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shelf.getInventNum());
            isSave = statement.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isSave;
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM shelf;";
        long result = 0;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return result;
    }
}
