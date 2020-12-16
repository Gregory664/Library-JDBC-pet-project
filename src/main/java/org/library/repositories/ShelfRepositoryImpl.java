package org.library.repositories;

import org.library.entity.Shelf;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.interfaces.ShelfRepository;
import org.library.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.library.utils.statements.ShelfSQLStatements.*;

public class ShelfRepositoryImpl implements ShelfRepository {
    @Override
    public List<Shelf> findAll() {
        List<Shelf> shelves = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
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
        Shelf shelf = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
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
        boolean result = false;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID)) {
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
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
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
    public boolean save(Shelf shelf) {
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE)) {
            statement.setString(1, shelf.getInventNum());
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

    @Override
    public boolean update(Shelf shelf) {
        boolean result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, shelf.getInventNum());
            statement.setInt(2, shelf.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }
}
