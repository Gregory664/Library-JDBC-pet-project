package org.library.repositories;

import org.library.interfaces.StatisticsRepository;
import org.library.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.library.utils.statements.StatisticsStatements.*;

public class StatisticsRepositoryImpl implements StatisticsRepository {
    @Override
    public Map<String, Integer> countByAuthorNameInShelf() {
        Map<String, Integer> params = new HashMap<>();
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_BY_AUTHOR_NAME_IN_SHELF);
             ResultSet set = statement.executeQuery()) {
            while(set.next()) {
                params.put(set.getString(1), set.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return params;
    }

    @Override
    public Map<String, Integer> countByAuthorNameInRent() {
        Map<String, Integer> params = new HashMap<>();
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_BY_AUTHOR_NAME_IN_RENT);
             ResultSet set = statement.executeQuery()) {
            while(set.next()) {
                params.put(set.getString(1), set.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return params;
    }
}
