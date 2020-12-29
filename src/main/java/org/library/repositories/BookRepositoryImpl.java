package org.library.repositories;

import org.library.entity.Author;
import org.library.entity.Book;
import org.library.entity.Genre;
import org.library.entity.Publisher;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.interfaces.BookRepository;
import org.library.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import static org.library.utils.statements.BookSQLStatements.*;

public class BookRepositoryImpl implements BookRepository {
    private Book getBookFromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int length = resultSet.getInt("length");

            int authorId = resultSet.getInt("author_id");
            String authorName = resultSet.getString("author_name");
            Author author = new Author(authorId, authorName);

            int publisherId = resultSet.getInt("publisher_id");
            String publisherTitle = resultSet.getString("publisher_title");
            Publisher publisher = new Publisher(publisherId, publisherTitle);

            int genreId = resultSet.getInt("genre_id");
            String genreTitle = resultSet.getString("genre_title");
            Genre genre = new Genre(genreId, genreTitle);

            return Book.builder()
                    .id(id)
                    .title(title)
                    .length(length)
                    .author(author)
                    .publisher(publisher)
                    .genre(genre)
                    .bookCopyIdAndShelf(new TreeMap<>())
                    .build();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Integer id) {
        Book book = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    book = getBookFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public boolean existsById(Integer id) {
        boolean isExists = false;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    isExists = set.getInt(1) == 1;
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
            statement.executeUpdate(DELETE_ALL);
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
    public boolean save(Book book) {
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthor().getId());
            statement.setInt(3, book.getPublisher().getId());
            statement.setInt(4, book.getGenre().getId());
            statement.setInt(5, book.getLength());
            isSave = statement.executeUpdate() == 1;

            if (isSave) {
                try (ResultSet set = statement.getGeneratedKeys()) {
                    while (set.next()) {
                        book.setId(set.getInt(1));
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
    public boolean update(Book book) {
        boolean result;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthor().getId());
            statement.setInt(3, book.getPublisher().getId());
            statement.setInt(4, book.getGenre().getId());
            statement.setInt(5, book.getLength());
            statement.setInt(6, book.getId());
            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }

        return result;
    }

    @Override
    public List<Book> findByParams(String title, Author author, Genre genre, Publisher publisher) {
        List<String> whereQuery = new ArrayList<>();

        if (!title.equals("")) {
            whereQuery.add("b.title like '%" + title + "%'");
        }

        if (author != null) {
            whereQuery.add("a.id = " + author.getId());
        }

        if (genre != null) {
            whereQuery.add("g.id = " + genre.getId());
        }

        if (publisher != null) {
            whereQuery.add("p.id = " + publisher.getId());
        }

        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + "WHERE " + String.join(" AND ", whereQuery));
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return books;
    }
}
