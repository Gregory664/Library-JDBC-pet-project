package org.library.services;

import org.library.entity.Book;
import org.library.entity.Shelf;
import org.library.exceptions.BookNotFoundOnShelfException;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.IBookshelf;
import org.library.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class BookShelfService implements IBookshelf {
    @Override
    public Map<Shelf, Integer> getCountOfBookOnShelfByBookId(int bookId) {
        String getCountOfBookOnShelfQuery = "" +
                "SELECT s.id, s.invent_num, COUNT(bh.book_id) as 'count'" +
                "FROM   bookshelf bh " +
                "JOIN   shelf s ON bh.shelf_id = s.id " +
                "WHERE  bh.book_id = ? " +
                "GROUP  BY (s.id); ";
        Map<Shelf, Integer> countOfBookOnShelf = new TreeMap<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(getCountOfBookOnShelfQuery)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int shelfId = resultSet.getInt(1);
                    String inventNum = resultSet.getString(2);
                    int count = resultSet.getInt(3);
                    countOfBookOnShelf.put(new Shelf(shelfId, inventNum), count);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return countOfBookOnShelf;
    }

    @Override
    public boolean deleteBookFromShelf(Book book, Shelf shelf) {
        String query = "DELETE from bookshelf WHERE shelf_id = ? AND book_id = ? LIMIT 1;";
        Map<Shelf, Integer> countOfBookInShelf = book.getCountOfBookInShelf();
        boolean result = false;

        if (!countOfBookInShelf.containsKey(shelf)) {
            throw new BookNotFoundOnShelfException(book.getId(), shelf.getId());
        }
        countOfBookInShelf.compute(shelf, (shelf1, integer) -> integer - 1);
        if (countOfBookInShelf.get(shelf) == 0) {
            countOfBookInShelf.remove(shelf);
        }

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, shelf.getId());
            statement.setInt(2, book.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
