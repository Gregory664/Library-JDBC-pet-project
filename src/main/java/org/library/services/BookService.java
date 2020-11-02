package org.library.services;

import org.library.entity.*;
import org.library.exceptions.SQLExceptionWrapper;
import org.library.repositories.IBook;
import org.library.utils.ConnectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookService implements IBook {
    BookShelfService bookShelfService = new BookShelfService();

    @Override
    public List<Book> findAll() {
        String query = "SELECT b.id, b.title, b.length, " +
                "a.id as 'author_id', a.name as 'author_name', " +
                "p.id as 'publisher_id', p.title as 'publisher_title', " +
                "g.id as 'genre_id', g.title as 'genre_title' " +
                "FROM  book b " +
                "JOIN  author a    ON b.author_id    = a.id " +
                "JOIN  publisher p ON b.publisher_id = p.id " +
                "JOIN  genre g     ON b.genre_id     = g.id;";
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
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

                Map<Shelf, Integer> countOfBookOnShelfByBookIdMap = bookShelfService.getCountOfBookOnShelfByBookId(id);

                books.add(new Book(id, title, author, publisher, genre, length, countOfBookOnShelfByBookIdMap));
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Integer id) {
        String query = "SELECT b.id, b.title, b.length, " +
                "a.id as 'author_id', a.name as 'author_name', " +
                "p.id as 'publisher_id', p.title as 'publisher_title', " +
                "g.id as 'genre_id', g.title as 'genre_title' " +
                "FROM  book b " +
                "JOIN  author a    ON b.author_id    = a.id " +
                "JOIN  publisher p ON b.publisher_id = p.id " +
                "JOIN  genre g     ON b.genre_id     = g.id " +
                "WHERE b.id = ?;";
        Book book = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int newId = resultSet.getInt("id");
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

                    Map<Shelf, Integer> countOfBookOnShelfByBookIdMap = bookShelfService.getCountOfBookOnShelfByBookId(id);

                    book = new Book(newId, title, author, publisher, genre, length, countOfBookOnShelfByBookIdMap);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public boolean existsById(Integer id) {
        String query = "SELECT COUNT(*) FROM book WHERE id = ?;";
        boolean isExists;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                set.next();
                isExists = set.getInt(1) == 1;
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isExists;
    }

    @Override
    public void deleteAll() {
        String query = "DELETE FROM book;";

        try (Connection connection = ConnectionUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM book WHERE id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
    }

    @Override
    public boolean save(Book book) {
        String query = "INSERT INTO book (title, author_id, publisher_id, genre_id, length) " +
                "VALUES (?, ?, ?, ?, ?);";
        boolean isSave;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthor().getId());
            statement.setInt(3, book.getPublisher().getId());
            statement.setInt(4, book.getGenre().getId());
            statement.setInt(5, book.getLength());
            isSave = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return isSave;
    }

    @Override
    public long count() {
        String query = "SELECT COUNT(*) FROM book;";
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

    @Override
    public List<Book> findByAuthor(Author author) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.id, b.title, b.length, " +
                "a.id as 'author_id', a.name as 'author_name', " +
                "p.id as 'publisher_id', p.title as 'publisher_title', " +
                "g.id as 'genre_id', g.title as 'genre_title' " +
                "FROM  book b " +
                "JOIN  author a    ON b.author_id    = a.id " +
                "JOIN  publisher p ON b.publisher_id = p.id " +
                "JOIN  genre g     ON b.genre_id     = g.id " +
                "WHERE a.id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, author.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    int length = resultSet.getInt("length");

                    int authorId = resultSet.getInt("author_id");
                    String authorName = resultSet.getString("author_name");
                    Author newAuthor = new Author(authorId, authorName);

                    int publisherId = resultSet.getInt("publisher_id");
                    String publisherTitle = resultSet.getString("publisher_title");
                    Publisher publisher = new Publisher(publisherId, publisherTitle);

                    int genreId = resultSet.getInt("genre_id");
                    String genreTitle = resultSet.getString("genre_title");
                    Genre genre = new Genre(genreId, genreTitle);

                    Map<Shelf, Integer> countOfBookOnShelfByBookIdMap = bookShelfService.getCountOfBookOnShelfByBookId(id);

                    books.add(new Book(id, title, newAuthor, publisher, genre, length, countOfBookOnShelfByBookIdMap));
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return books;
    }

    @Override
    public List<Book> findByPublisher(Publisher publisher) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.id, b.title, b.length, " +
                "a.id as 'author_id', a.name as 'author_name', " +
                "p.id as 'publisher_id', p.title as 'publisher_title', " +
                "g.id as 'genre_id', g.title as 'genre_title' " +
                "FROM  book b " +
                "JOIN  author a    ON b.author_id    = a.id " +
                "JOIN  publisher p ON b.publisher_id = p.id " +
                "JOIN  genre g     ON b.genre_id     = g.id " +
                "WHERE p.id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, publisher.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    int length = resultSet.getInt("length");

                    int authorId = resultSet.getInt("author_id");
                    String authorName = resultSet.getString("author_name");
                    Author author = new Author(authorId, authorName);

                    int publisherId = resultSet.getInt("publisher_id");
                    String publisherTitle = resultSet.getString("publisher_title");
                    Publisher newPublisher = new Publisher(publisherId, publisherTitle);

                    int genreId = resultSet.getInt("genre_id");
                    String genreTitle = resultSet.getString("genre_title");
                    Genre genre = new Genre(genreId, genreTitle);

                    Map<Shelf, Integer> countOfBookOnShelfByBookIdMap = bookShelfService.getCountOfBookOnShelfByBookId(id);

                    books.add(new Book(id, title, author, newPublisher, genre, length, countOfBookOnShelfByBookIdMap));
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return books;
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.id, b.title, b.length, " +
                "a.id as 'author_id', a.name as 'author_name', " +
                "p.id as 'publisher_id', p.title as 'publisher_title', " +
                "g.id as 'genre_id', g.title as 'genre_title' " +
                "FROM  book b " +
                "JOIN  author a    ON b.author_id    = a.id " +
                "JOIN  publisher p ON b.publisher_id = p.id " +
                "JOIN  genre g     ON b.genre_id     = g.id " +
                "WHERE g.id = ?";

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, genre.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
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
                    Genre newGenre = new Genre(genreId, genreTitle);

                    Map<Shelf, Integer> countOfBookOnShelfByBookIdMap = bookShelfService.getCountOfBookOnShelfByBookId(id);

                    books.add(new Book(id, title, author, publisher, newGenre, length, countOfBookOnShelfByBookIdMap));
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return books;
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        String query = "SELECT b.id, b.title, b.length, " +
                "a.id as 'author_id', a.name as 'author_name', " +
                "p.id as 'publisher_id', p.title as 'publisher_title', " +
                "g.id as 'genre_id', g.title as 'genre_title' " +
                "FROM  book b " +
                "JOIN  author a    ON b.author_id    = a.id " +
                "JOIN  publisher p ON b.publisher_id = p.id " +
                "JOIN  genre g     ON b.genre_id     = g.id " +
                "WHERE b.title = ?;";
        Book book = null;

        try (Connection connection = ConnectionUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int newId = resultSet.getInt("id");
                    String newTitle = resultSet.getString("title");
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

                    Map<Shelf, Integer> bookOnShelfByBookId = new BookShelfService().getCountOfBookOnShelfByBookId(newId);

                    book = new Book(newId, newTitle, author, publisher, genre, length, bookOnShelfByBookId);
                }
            }
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e);
        }
        return Optional.ofNullable(book);
    }
}
